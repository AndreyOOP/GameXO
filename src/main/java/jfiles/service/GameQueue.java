package jfiles.service;

import jfiles.Constants.XO;
import jfiles.model.Game.GameSession;
import jfiles.model.Game.Player;
import jfiles.model.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**Players looking for game or already playing are registered here<br>
 * It generates game session for player pair or add player to <i>looking for game</i> queue*/
@Service("GamePool")
public class GameQueue {

//    private static List<GameSession> gameSessions   = new ArrayList<>(); //better change to hash map id -> game
    private static List<Integer>      lookingForGame = new ArrayList<>();

    /*private static Boolean isGameSessionExist(int authKey){

        for(GameSession gs: gameSessions){

            if( gs.getPlayer1().getAuthKey() == authKey || gs.getPlayer2().getAuthKey() == authKey){
                return true;
            }
        }

        return false;
    }*/

    /**Return link to GameSession where player1 or player2 name is equal to userName*/

    public static Boolean isUserInQueue(int authKey){

        for(Integer key: lookingForGame){
            if( key == authKey)
                return true;
        }

        return false;
    }

    public static void addToQueue(int authKey){

//        if( !isGameSessionExist(authKey)){
//            lookingForGame.add(authKey);
//        }

        lookingForGame.add(authKey);
    }

    /**Method just gets first name from <i>lookingForGame</i> queue*/
    public static int findPair(int authKey){

        if( lookingForGame.size()>=2){

            for(int i=0; i<lookingForGame.size(); i++){

                int pairKey = lookingForGame.get(i);

                if( pairKey != authKey)
                    return pairKey;
            }
        }

        return -1;
    }

    public static void removeFromLookingForGameList(Integer authKey){

        /*for(int i=0; i<lookingForGame.size(); i++){

            if( lookingForGame.get(i) == authKey){


                lookingForGame.remove(i);

                return;
            }
        }*/

        lookingForGame.remove(authKey);
    }

    /*public static void createGameSesssionAndSetToPlayers(int player2Key, SessionService sessionService ,Session session, int authKey){


    }*/

    /*public static List<Integer> getLookingForGame() {
        return lookingForGame;
    }*/
}
