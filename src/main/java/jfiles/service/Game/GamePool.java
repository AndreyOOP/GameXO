package jfiles.service.Game;

import jfiles.service.SessionLogin.Session;
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

    private static List<GameSession2> gameSessions   = new ArrayList<>(); //better change to hash map id -> game
    private static List<Integer>      lookingForGame = new ArrayList<>();

    /**Return existing GameSession for userName<br>
     * If there is no GameSession add user to queue of players looking for game and<br>
     * try to find second player. In case of success creates new GameSession*/


    /**Check is there game session with for the userName<br>
     * Returns true if player1 or player2 name is equal to userName*/
    private static Boolean isGameSessionExist(int authKey){

        for(GameSession2 gs: gameSessions){

            if( gs.getPlayer1().getAuthKey() == authKey || gs.getPlayer2().getAuthKey() == authKey){
                return true;
            }
        }

        return false;
    }

    /**Return link to GameSession where player1 or player2 name is equal to userName*/

    public static Boolean isUserInLookingForGameList(int authKey){

        for(Integer aK: lookingForGame){
            if( aK == authKey){
                return true;
            }
        }

        return false;
    }

    public static void addToLookingForGameList(int authKey){

        if( !isGameSessionExist(authKey)){
            lookingForGame.add(authKey);
        }
    }

    /**Method just gets first name from <i>lookingForGame</i> queue*/
    public static int findPair(int authKey){

        if( lookingForGame.size()>=2){

            for(int i=0; i<lookingForGame.size(); i++){

                int pairKey = lookingForGame.get(i);
                if(  pairKey != authKey ){
                    return pairKey;
                }
            }
        }

        return -1;
    }

    public static void removeFromLookingForGameList(int authKey){

        for(int i=0; i<lookingForGame.size(); i++){

            if( lookingForGame.get(i) == authKey){

                lookingForGame.remove(i);

                return;
            }
        }
    }

    /**Method creates new GameSession, add it to pool of GameSessions and <br>
     * remove players from <i>lookingForGame</i> queue*/
}
