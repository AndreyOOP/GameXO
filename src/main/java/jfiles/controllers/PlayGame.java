package jfiles.controllers;

import jfiles.Constants.Page;
import jfiles.Constants.PageService.Message;
import jfiles.Constants.PageService.Tag;
import jfiles.Constants.XO;
import jfiles.service.Game.GamePool;
import jfiles.service.Game.GameSession;
import jfiles.service.PageService;
import jfiles.service.SessionLogin.LoginSession;
import jfiles.service.SessionLogin.Session;
import jfiles.service.StatisticService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private GamePool gamePool;

    @Autowired
    private StatisticService statisticService;
    //endregion

    /**Looking for game or show current game session (GamePool.getGame)<br>
     * Check is game over - display appropriate message*/
    @RequestMapping(value = "/findgame", method = RequestMethod.GET)
    public String findGame(Model model,
                           @RequestParam int authKey){

        PageService page = new PageService().setModel(model);

        Session session = loginSession.getSession(authKey);

        if( session == null){

            page.add( Tag.ERROR_MESSAGE, Message.ERROR_NO_LOGIN_SESSION);
            return Page.ERROR;
        }

        page.add( Tag.MAIN_MENU_USER_NAME    , session.getUserName())
            .add( Tag.MAIN_MENU_USER_ROLE    , session.getUserRole())
            .add( Tag.MAIN_MENU_AUTH_KEY     , authKey)
            .add( Tag.MAIN_MENU_IS_FIND_GAME , true)
            .add( Tag.GAME_GAME_FOUND        , false);



        GameSession gameSession = gamePool.getGame( session.getUserName());

        if(gameSession != null){

            page.add( Tag.GAME_GAME_FOUND , true)
                .add( Tag.GAME_USER       , session.getUserName())
                .add( Tag.GAME_FIELD_SIZE , XO.FIELD_SIZE)
                .add( Tag.GAME_MATRIX     , gameSession.getMatrix())
                .add( Tag.GAME_PLAYER_1   , gameSession.getPlayer1())
                .add( Tag.GAME_PLAYER_2   , gameSession.getPlayer2())
                .add( Tag.GAME_PICTURE_1  , gameSession.getBlobPlayer1())
                .add( Tag.GAME_PICTURE_2  , gameSession.getBlobPlayer2());

//            gameSession.setRefresh(false);
//            page.add( "refresh"     , false);

            String user = session.getUserName();

            if( gameSession.isGameOver()){

                if( gameSession.getPlayer1().contentEquals(user))
                    setGameEndStatus( page, gameSession.getPlayer1Status());

                if( gameSession.getPlayer2().contentEquals(user))
                    setGameEndStatus( page, gameSession.getPlayer2Status());

            } else {

                page.add( Tag.GAME_MESSAGE, Message.GAME_OPPONENT_TURN);

                if( gameSession.isPlayer1Turn(user)){
                    page.add( Tag.GAME_MESSAGE, Message.GAME_YOUR_TURN);
                    gameSession.setRefreshP1(false);
                }

                if (gameSession.isPlayer2Turn(user)) {
                    page.add( Tag.GAME_MESSAGE, Message.GAME_YOUR_TURN);
                    gameSession.setRefreshP2(false);
                }

            }
        }

        return Page.MAIN_MENU;
    }

    @RequestMapping(value = "/loadtableonly", method = RequestMethod.GET)
    public String loadGameMatrix(Model model, @RequestParam int authKey){

        PageService page = new PageService().setModel(model);

        Session session = loginSession.getSession(authKey);
        GameSession gameSession = gamePool.getGame( session.getUserName());

        if(gameSession == null){

            page.add( "refresh1"     , true);
            page.add( "refresh2"     , true);

        }else{

            if(gameSession.getRefreshP1()){
                page.add( "refresh1"     , true);
            } else {
                page.add( "refresh1"     , false);
            }

            if(gameSession.getRefreshP2()){
                page.add( "refresh2"     , true);
            } else {
                page.add( "refresh2"     , false);
            }
        }




//        if( gameSession.isPlayer1Turn(user))
//            page.add( "refresh"     , true);
//        else
//            page.add( "refresh"     , false);
//
//        if( gameSession.isPlayer2Turn(user))
//            page.add( "refresh"     , true);
//        else
//            page.add( "refresh"     , false);

        return "authorized/menu/matrix";
    }

    /**If game is in process - define active player, send turn information (position) to server, make 'win' check*/
    @RequestMapping(value = "/turn", method = RequestMethod.POST)
    public String makeTurn(RedirectAttributes redirectAttributes, Model model,
                           @RequestParam int iPos,
                           @RequestParam int jPos,
                           @RequestParam int authKey){

        PageService page = new PageService().setModel(model);

        Session session = loginSession.getSession(authKey);

        if( session == null){

            page.add( Tag.ERROR_MESSAGE, Message.ERROR_NO_LOGIN_SESSION);
            return Page.ERROR;
        }

        String player = session.getUserName();

        GameSession gameSession = gamePool.getGame(player);

        if( !gameSession.isGameOver()){

            if( gameSession.isPlayer1Turn(player)){

                if( gameSession.isCell( iPos , jPos, XO.BLANK)){

                    gameSession.setCellValue( iPos, jPos, XO.X);
                    gameSession.setTurn1(false);
                    gameSession.checkIfWinnerAndUpdateDB(XO.X);
                    gameSession.setRefresh(true);
//                    page.add( "refresh"     , true);
                }
            }

            if( gameSession.isPlayer2Turn(player)){

                if( gameSession.isCell( iPos , jPos, XO.BLANK)){

                    gameSession.setCellValue( iPos, jPos, XO.O);
                    gameSession.setTurn1(true);
                    gameSession.checkIfWinnerAndUpdateDB(XO.O);
                    gameSession.setRefresh(true);
//                    page.add( "refresh"     , true);
                }
            }
        }

        page.setRedirectAttributes(redirectAttributes)
            .addRedirect( Tag.GAME_GAME_FOUND, true)
            .addRedirect( Tag.MAIN_MENU_AUTH_KEY, authKey);

        return "redirect:" + Page.GAME_FIND;
//        return "redirect:" + "/loadtableonly";
    }

    /**Method of <i>New Game</i> button which appears after finishing of the previous game<br>
     * Remove from previous game session, redirect to /findgame for new game*/
    @RequestMapping(value = "/newgame")
    public String createNewGame(RedirectAttributes redirectAttributes,
                                @RequestParam int authKey){

        PageService page = new PageService();

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

        PageService page = new PageService();

        Session session = loginSession.getSession(authKey);

        try {
            gamePool.removeUser( session.getUserName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        page.setModel(model)
                .add( Tag.MAIN_MENU_USER_NAME    , session.getUserName())
                .add( Tag.MAIN_MENU_USER_ROLE    , session.getUserRole())
                .add( Tag.MAIN_MENU_WELCOME_PAGE , true)
                .add( Tag.MAIN_MENU_AUTH_KEY     , authKey);

        return Page.MAIN_MENU;
    }

    @RequestMapping(value = "/endgame")
    public String endGame(Model model,
                          @RequestParam int authKey){

        PageService page = new PageService();

        Session session = loginSession.getSession(authKey);

        String user = session.getUserName();
        GameSession gameSession = gamePool.getGame(user);

        if( gameSession.getPlayer1().contentEquals(user)){

            gameSession.setIsGameOver(true);
            gameSession.setPlayer1Status(XO.LOOSE);
            gameSession.setPlayer2Status(XO.WIN);

            statisticService.addWin(user, gameSession.getPlayer2());
            statisticService.addLoose(gameSession.getPlayer2(), user);
        }

        if( gameSession.getPlayer2().contentEquals(user)){

            gameSession.setIsGameOver(true);
            gameSession.setPlayer2Status(XO.LOOSE);
            gameSession.setPlayer1Status(XO.WIN);

            statisticService.addWin(gameSession.getPlayer1(), user);
            statisticService.addLoose(user, gameSession.getPlayer1());
        }

        try {
            gamePool.removeUser( session.getUserName());
        } catch (Exception e) {
            e.printStackTrace();
        }

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

    private void setGameEndStatus(PageService page, int status){

        if( status == XO.WIN)
            page.add( Tag.GAME_WIN, true);

        if( status == XO.LOOSE)
            page.add( Tag.GAME_LOOSE, true);

        if( status == XO.EVEN)
            page.add( Tag.GAME_EVEN, true);
    }

}
