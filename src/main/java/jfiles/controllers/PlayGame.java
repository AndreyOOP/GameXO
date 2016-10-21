package jfiles.controllers;

import jfiles.Constants.Page;
import jfiles.Constants.PageService.Tag;
import jfiles.Constants.XO;
import jfiles.service.Game.GamePool;
import jfiles.service.Game.GameSession;
import jfiles.service.PageService;
import jfiles.service.SessionLogin.LoginSession;
import jfiles.service.SessionLogin.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**Controller is responsible for game loop*/
@org.springframework.stereotype.Controller
public class PlayGame {

    //region Services declaration
    @Autowired
    private LoginSession loginSession;

    private PageService page = new PageService();

    @Autowired
    private GamePool gamePool;
    //endregion

    /**Looking for game or show current game session (GamePool.getGame)<br>
     * Check is game over - display appropriate message*/
    @RequestMapping(value = "/findgame", method = RequestMethod.GET)
    public synchronized String findGame(Model model,
                           @RequestParam int authKey){

        Session session = loginSession.getSession(authKey);

        if( session == null)
            return Page.ERROR;

        page.setModel(model)
            .add( Tag.MAIN_MENU_USER_NAME    , session.getUserName())
            .add( Tag.MAIN_MENU_USER_ROLE    , session.getUserRole())
            .add( Tag.MAIN_MENU_AUTH_KEY     , authKey)
            .add( Tag.MAIN_MENU_IS_FIND_GAME , true)
            .add( Tag.GAME_GAME_FOUND        , false);

        GameSession gameSession = gamePool.getGame( session.getUserName());

        if(gameSession != null){

            page.add( Tag.GAME_GAME_FOUND , true)
                .add( Tag.GAME_FIELD_SIZE , XO.FIELD_SIZE)
                .add( Tag.GAME_MATRIX     , gameSession.getMatrix())
                .add( Tag.GAME_PLAYER_1   , gameSession.getPlayer1())
                .add( Tag.GAME_PLAYER_2   , gameSession.getPlayer2())
            .add("key1", gameSession.getBlobPlayer1())
            .add("key2", gameSession.getBlobPlayer2());

            String user = session.getUserName();

            if( gameSession.isGameOver()){

                if( gameSession.getPlayer1().contentEquals(user))
                    setGameEndStatus( gameSession.getPlayer1Status());

                if( gameSession.getPlayer2().contentEquals(user))
                    setGameEndStatus( gameSession.getPlayer2Status());

            } else {

                page.add( Tag.GAME_MESSAGE, "Opponent Turn");

                if( gameSession.isPlayer1Turn(user))
                    page.add( Tag.GAME_MESSAGE, "Your Turn");

                if( gameSession.isPlayer2Turn(user))
                    page.add( Tag.GAME_MESSAGE, "Your Turn");
            }
        }

        return Page.MAIN_MENU;
    }

    /**If game is in process - define active player, send turn information (position) to server, make 'win' check*/
    @RequestMapping(value = "/turn", method = RequestMethod.POST)
    public String makeTurn(RedirectAttributes redirectAttributes,
                           @RequestParam int iPos,
                           @RequestParam int jPos,
                           @RequestParam int authKey){

        Session session = loginSession.getSession(authKey);

        if( session == null)
            return Page.ERROR;

        String player = session.getUserName();

        GameSession gameSession = gamePool.getGame(player);

        if( !gameSession.isGameOver()){

            if( gameSession.isPlayer1Turn(player)){

                if( gameSession.isCell( iPos , jPos, XO.BLANK)){

                    gameSession.setCellValue( iPos, jPos, XO.X);
                    gameSession.setTurn1(false);
                    gameSession.checkIfWinnerAndUpdateDB(XO.X);
                }
            }

            if( gameSession.isPlayer2Turn(player)){

                if( gameSession.isCell( iPos , jPos, XO.BLANK)){

                    gameSession.setCellValue( iPos, jPos, XO.O);
                    gameSession.setTurn1(true);
                    gameSession.checkIfWinnerAndUpdateDB(XO.O);
                }
            }
        }

        page.setRedirectAttributes(redirectAttributes)
            .addRedirect( Tag.GAME_GAME_FOUND, true)
            .addRedirect( Tag.MAIN_MENU_AUTH_KEY, authKey);

        return "redirect:" + Page.GAME_FIND;
    }

    /**Method of <i>New Game</i> button which appears after finishing of the previous game<br>
     * Remove from previous game session, redirect to /findgame for new game*/
    @RequestMapping(value = "/newgame")
    public String createNewGame(RedirectAttributes redirectAttributes,
                                @RequestParam int authKey){

        Session session = loginSession.getSession(authKey);

        gamePool.removeUser( session.getUserName());

        page.setRedirectAttributes(redirectAttributes)
            .addRedirect( Tag.MAIN_MENU_AUTH_KEY, authKey);

        return "redirect:" + Page.GAME_FIND;
    }

    /**Method of <i>Back</i> button which appears after finishing of the previous game<br>
     * Remove from previous game session, redirect to main menu <i>Welcome</i> page*/
    @RequestMapping(value = "/backtomainmenu")
    public String goToMainMenu(Model model,
                               @RequestParam int authKey){

        Session session = loginSession.getSession(authKey);

        gamePool.removeUser( session.getUserName());

        page.setModel(model)
                .add( Tag.MAIN_MENU_USER_NAME    , session.getUserName())
                .add( Tag.MAIN_MENU_USER_ROLE    , session.getUserRole())
                .add( Tag.MAIN_MENU_WELCOME_PAGE , true)
                .add( Tag.MAIN_MENU_AUTH_KEY     , authKey);

        return Page.MAIN_MENU;
    }

    /**Load game.jsp used into mainmenu.jsp*/
    @RequestMapping(value = "/gamepagecontent")
    public String loadGamePage(){

        return Page.GAME;
    }

    private void setGameEndStatus(int status){

        if( status == XO.WIN)
            page.add( Tag.GAME_WIN, true);

        if( status == XO.LOOSE)
            page.add( Tag.GAME_LOOSE, true);

        if( status == XO.EVEN)
            page.add( Tag.GAME_EVEN, true);
    }

}
