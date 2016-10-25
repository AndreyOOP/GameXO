package jfiles.service;

import jfiles.model.Session;
import jfiles.model.UserEntity;
import org.springframework.stereotype.Service;
import java.util.concurrent.ConcurrentHashMap;

/**Pool of authorized users*/
@Service
public class SessionService {

    private ConcurrentHashMap<Integer, Session> loggedUsers = new ConcurrentHashMap<>();


    /**Create login session for user<br>
     * Add user to <i>logged user</i> list*/
    public void addUser(int authKey, UserEntity loginEntity){

        Session session = new Session(loginEntity);

        loggedUsers.put(authKey, session);
    }

    public Boolean isUserAlreadyLoggedIn(String userName){

        for(Session s: loggedUsers.values()){
            if( s.getUserName().contentEquals( userName)){
                return true;
            }
        }

        return false;
    }

    /**Authorization key is random integer value<br>
     * It is generated during login or registration process, it is needed for access to any program menu<br>*/
    public int generateAuthorizationKey(){

        int authKey;

        do{
            authKey = (int)(Integer.MAX_VALUE * Math.random());

        }while ( loggedUsers.containsKey(authKey));

        return authKey;
    }


    public Session getBy(int authKey){

        return loggedUsers.get(authKey);
    }

    public Session getBy(String name){

        for(Integer authKey: loggedUsers.keySet()){

            Session session = loggedUsers.get(authKey);

            if( session.getUserName().contentEquals( name))
                return session;
        }

        return null;
    }

    public void removeBy(int authKey){

        loggedUsers.remove(authKey);
    }

    public void removeBy(String userName){

        for(Integer authKey: loggedUsers.keySet()){

            if( loggedUsers.get(authKey).getUserName().contentEquals( userName)){

                loggedUsers.remove( authKey);
                return;
            }
        }
    }

    public ConcurrentHashMap<Integer, Session> getLoggedUsers(){
        return loggedUsers;
    }
}
