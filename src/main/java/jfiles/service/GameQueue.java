package jfiles.service;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**Players looking for game or already playing are registered here<br>
 * It generates game session for player pair or add player to <i>looking for game</i> queue*/
@Service("GamePool")
public class GameQueue {

    private static List<Integer>      lookingForGame = new ArrayList<>();

    public static Boolean isUserInQueue(int authKey){

        for(Integer key: lookingForGame){
            if( key == authKey)
                return true;
        }

        return false;
    }

    public static void addToQueue(int authKey){

        lookingForGame.add(authKey);
    }

    public static void removeFromQueue(Integer authKey){

        lookingForGame.remove(authKey);
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

}
