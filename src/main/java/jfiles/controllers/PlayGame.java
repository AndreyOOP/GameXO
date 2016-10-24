package jfiles.controllers;

import jfiles.Constants.Page;
import jfiles.Constants.PageService.Message;
import jfiles.Constants.PageService.Tag;
import jfiles.Constants.XO;
import jfiles.service.Game.GamePool;
import jfiles.service.Game.GameSession2;
import jfiles.service.Game.Player;
import jfiles.service.PageService;
import jfiles.service.SessionLogin.LoginSession;
import jfiles.service.SessionLogin.Session;
import jfiles.service.StatisticService;
import jfiles.service.UserService;
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

//    @Autowired
//    private GamePool gamePool;

    @Autowired
    private StatisticService statisticService;

    @Autowired
    UserService userService;
    //endregion

    /**Looking for game or show current game session (GamePool.getGame)<br>
     * Check is game over - display appropriate message*/
    @RequestMapping(value = "/findgame", method = RequestMethod.GET)
    public String findGame(Model model,
                           @RequestParam int authKey){

        Session session = loginSession.getSession(authKey);

        PageService page = new PageService().setModel(model);

        if( session == null){
            page.add( Tag.ERROR_MESSAGE, Message.ERROR_NO_LOGIN_SESSION);
            return Page.ERROR;
        }

        page.add( Tag.MAIN_MENU_USER_NAME    , session.getUserName())
            .add( Tag.MAIN_MENU_USER_ROLE    , session.getUserRole())
            .add( Tag.MAIN_MENU_AUTH_KEY     , authKey)
            .add( Tag.MAIN_MENU_IS_FIND_GAME , true)
            .add( Tag.GAME_GAME_FOUND        , false);

        GameSession2 gameSession = session.getGameSession();

        if( gameSession != null){

            if( gameSession.isNotGameOver()){

                String playerOnPage = session.getUserName();

                Player currPlayer = gameSession.getPlayer(playerOnPage);
                Player vsPlayer   = gameSession.getVsPlayer(playerOnPage);

                page.add( Tag.GAME_GAME_FOUND , true)
                    .add( Tag.GAME_USER       , currPlayer.getName())
                    .add( Tag.GAME_FIELD_SIZE , XO.FIELD_SIZE)
                    .add( Tag.GAME_MATRIX     , gameSession.getMatrix())
                    .add( Tag.GAME_PLAYER_1   , currPlayer.getName())
                    .add( Tag.GAME_PLAYER_2   , vsPlayer.getName())
                    .add( Tag.GAME_PICTURE_1  , currPlayer.getBlobKey())
                    .add( Tag.GAME_PICTURE_2  , vsPlayer.getBlobKey());

                if( currPlayer.isHisTurn()){
                    page.add( Tag.GAME_MESSAGE, Message.GAME_YOUR_TURN);

                } else {
                    page.add( Tag.GAME_MESSAGE, Message.GAME_OPPONENT_TURN);
                }

            }else {

                Player currPlayer = gameSession.getPlayer( session.getUserName());
                Player vsPlayer   = gameSession.getVsPlayer(session.getUserName());

                displayGameEndMessage(page, currPlayer.getGameStatus());

                if( !currPlayer.getUpdatedDB()){

                    updateDatabaseRecord(currPlayer.getGameStatus(), currPlayer.getName(), vsPlayer.getName());

                    currPlayer.setUpdatedDB(true);
                }

                page.add( Tag.GAME_USER       , session.getUserName())
                    .add( Tag.GAME_FIELD_SIZE , XO.FIELD_SIZE)
                    .add( Tag.GAME_MATRIX     , gameSession.getMatrix())
                    .add( Tag.GAME_PLAYER_1   , currPlayer.getName())
                    .add( Tag.GAME_PLAYER_2   , vsPlayer.getName())
                    .add( Tag.GAME_PICTURE_1  , currPlayer.getBlobKey())
                    .add( Tag.GAME_PICTURE_2  , vsPlayer.getBlobKey())
                    .add( Tag.GAME_GAME_FOUND , true);
            }

        } else {
            createGameSesssionAndSetToPlayers(session, authKey);
        }

        return Page.MAIN_MENU;
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

        GameSession2 gameSession = session.getGameSession();

        Player player   = gameSession.getPlayer(authKey);
        Player vsPlayer = gameSession.getVsPlayer(authKey);

        if( player.isHisTurn()){

            if( gameSession.isCell(iPos, jPos, XO.BLANK)){

                gameSession.setCellValue( iPos, jPos, player.getMark());
                player.setTurn(false);
                vsPlayer.setTurn(true);

                if( gameSession.isWinner( player.getMark())){

                    player.setGameStatus(XO.WIN);
                    vsPlayer.setGameStatus(XO.LOOSE);
                    gameSession.setGameOver(true);

                }else if ( gameSession.isNoFreeCell()) {

                    player.setGameStatus(XO.EVEN);
                    vsPlayer.setGameStatus(XO.EVEN);
                    gameSession.setGameOver(true);
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

        PageService page = new PageService();

        Session session = loginSession.getSession(authKey);
        session.setGameSession(null);

        page.setRedirectAttributes(redirectAttributes)
                .addRedirect( Tag.MAIN_MENU_AUTH_KEY, authKey);

        return "redirect:" + Page.GAME_FIND;
    }

    /**Method of <i>Back</i> button which appears after finishing of the previous game<br>
     * Remove from previous game session, redirect to main menu <i>Welcome</i> page*/
    @RequestMapping(value = "/backtomainmenu")
    public String goToMainMenu(@RequestParam int authKey){

        GamePool.removeFromLookingForGameList(authKey);

        Session session = loginSession.getSession(authKey);
        session.setGameSession(null);

        return "redirect:welcome/" + authKey;
    }

    @RequestMapping(value = "/endgame")
    public String endGame(RedirectAttributes redirectAttributes,
                          @RequestParam int authKey){

        PageService page = new PageService();

        Session session = loginSession.getSession(authKey);

        GameSession2 gameSession = session.getGameSession();

        Player player   = gameSession.getPlayer(authKey);
        Player vsPlayer = gameSession.getVsPlayer(authKey);

        player.setGameStatus(XO.LOOSE);
        vsPlayer.setGameStatus(XO.WIN);
        gameSession.setGameOver(true);

        page.setRedirectAttributes(redirectAttributes)
                .addRedirect( Tag.MAIN_MENU_AUTH_KEY, authKey);

        return "redirect:" + Page.GAME_FIND;
    }

    /**Load game.jsp used into mainmenu.jsp*/
    @RequestMapping(value = "/gamepagecontent")
    public String loadGamePage(){

        return Page.GAME;
    }

    private void createGameSesssionAndSetToPlayers(Session session, int authKey){

        if ( !GamePool.isUserInLookingForGameList( authKey)){
            GamePool.addToLookingForGameList(authKey);
        }

        int player2Key = GamePool.findPair(authKey);

        if( player2Key != -1) {

            Player player1 = new Player(true,
                    XO.IN_GAME,
                    session.getBlobKey(),
                    session.getUserName(),
                    XO.X,
                    authKey);

            Player player2 = new Player(false,
                    XO.IN_GAME,
                    loginSession.getSession(player2Key).getBlobKey(),
                    loginSession.getSession(player2Key).getUserName(),
                    XO.O,
                    player2Key);

            GameSession2 gs = new GameSession2(player1, player2);
            gs.setMatrixToBlank();
            gs.setGameOver(false);

            GamePool.removeFromLookingForGameList(authKey);
            GamePool.removeFromLookingForGameList(player2Key);

            session.setGameSession(gs);
            loginSession.getSession(player2Key).setGameSession(gs);
        }
    }

    private void displayGameEndMessage(PageService page, int status){

        switch (status){

            case XO.WIN:
                page.add( Tag.GAME_WIN   , true);
                break;

            case XO.LOOSE:
                page.add( Tag.GAME_LOOSE , true);
                break;

            case XO.EVEN:
                page.add( Tag.GAME_EVEN  , true);
        }
    }

    private void updateDatabaseRecord(int status, String player, String vsPLayer){

        switch (status){

            case XO.WIN:
                statisticService.addWin( player, vsPLayer);
                break;

            case XO.LOOSE:
                statisticService.addLoose( player, vsPLayer);
                break;

            case XO.EVEN:
                statisticService.addEven( player, vsPLayer);
        }
    }

}
