package jfiles.service.Game;

import jfiles.service.StatisticService;
import jfiles.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**Players looking for game or already playing are registered here<br>
 * It generates game session for player pair or add player to <i>looking for game</i> queue*/
@Service("GamePool")
public class GamePool {

    @Autowired
    private UserService userService;

    @Autowired
    private StatisticService statisticService;

    private List<GameSession> gameSessions   = new ArrayList<GameSession>(); //better change to hash map id -> game
    private List<String> lookingForGame = new ArrayList<String>();

    /**Return existing GameSession for userName<br>
     * If there is no GameSession add user to queue of players looking for game and<br>
     * try to find second player. In case of success creates new GameSession*/
    public GameSession getGame(String userName){

        if( isGameSessionExist( userName)){
            return getGameSession( userName);
        }

        if ( !isUserInLookingForGameList( userName)){
            addToLookingForGameList(userName);
        }

        String player2 = findPair(userName);
        if( !player2.equalsIgnoreCase("")){
            createNewGameSession( userName, player2);
        }

        return null;
    }

    /**Remove userName from GameSession instance, so it become not accessible via getGame<br>
     * If both players have been removed - GameSession instance removed as well<br>
     * Note: GameSession should be removed from server only when both players are decided to leave game, otherwise when one player leave the game other one leaves as well*/
    public void removeUser(String userName){

        GameSession gameSession = getGameSession(userName);

        if( gameSession.getPlayer1().contentEquals(userName))
            gameSession.setPlayer1("");

        if( gameSession.getPlayer2().contentEquals(userName))
            gameSession.setPlayer2("");

        if( gameSession.getPlayer1().isEmpty() && gameSession.getPlayer2().isEmpty())
            gameSessions.remove(gameSession);
    }

    /**Check is there game session with for the userName<br>
     * Returns true if player1 or player2 name is equal to userName*/
    private Boolean isGameSessionExist(String userName){

        for(GameSession gs: gameSessions){
            if( gs.getPlayer1().equalsIgnoreCase(userName) || gs.getPlayer2().equalsIgnoreCase(userName)){
                return true;
            }
        }

        return false;
    }

    /**Return link to GameSession where player1 or player2 name is equal to userName*/
    private GameSession getGameSession(String userName){

        for(GameSession gs: gameSessions){
            if( gs.getPlayer1().equalsIgnoreCase(userName) || gs.getPlayer2().equalsIgnoreCase(userName)){
                return gs;
            }
        }

        return null;
    }

    private Boolean isUserInLookingForGameList(String userName){

        for(String user: lookingForGame){
            if( user.equalsIgnoreCase(userName)){
                return true;
            }
        }

        return false;
    }

    private void addToLookingForGameList(String userName){

        if( !isGameSessionExist(userName)){
            lookingForGame.add(userName);
        }
    }

    /**Method just gets first name from <i>lookingForGame</i> queue*/
    private String findPair(String userName){

        if( lookingForGame.size()>=2){

            for(int i=0; i<lookingForGame.size(); i++){

                String pairName = lookingForGame.get(i);
                if( !pairName.equalsIgnoreCase(userName)){
                    return pairName;
                }
            }
        }

        return "";
    }

    /**Method creates new GameSession, add it to pool of GameSessions and <br>
     * remove players from <i>lookingForGame</i> queue*/
    private void createNewGameSession(String player1, String player2){

        GameSession gameSession = new GameSession( statisticService);
        gameSession.setPlayer1( player1);
        gameSession.setPlayer2( player2);
        gameSession.setTurn1(true);

        gameSession.setBlobPlayer1( userService.getUserByName(player1).getBlobKey());
        gameSession.setBlobPlayer2( userService.getUserByName(player2).getBlobKey());

        lookingForGame.remove(player1);
        lookingForGame.remove(player2);

        gameSessions.add(gameSession);
    }

    public List<GameSession> getGameSessions(){
        return gameSessions;
    }
}
