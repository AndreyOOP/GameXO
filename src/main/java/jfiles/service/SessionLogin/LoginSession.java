package jfiles.service.SessionLogin;

import jfiles.model.UserEntity;
import jfiles.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

/**Pool of authorized users*/
@Service
public class LoginSession {

    @Autowired
    private UserService userService;

    private ConcurrentHashMap<Integer, Session> loggedUsers = new ConcurrentHashMap<>();

    /**Create login session for user<br>
     * Add user to <i>logged user</i> list*/
    public void addUser(int authKey, String userName){

        UserEntity user = userService.getUserByName(userName);

        Session session = new Session(user);

        loggedUsers.put(authKey, session);

    }

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

    public void removeUserByName(String userName){

        for(Integer authKey: loggedUsers.keySet()){
            if( loggedUsers.get(authKey).getUserName().contentEquals( userName)){
                removeSession( authKey);
                return;
            }
        }
    }

    public Session getSession(int authKey){

        return loggedUsers.get(authKey);
    }

    public void removeSession(int authKey){

        loggedUsers.remove(authKey);
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

    //todo temoporary solution !
    public ConcurrentHashMap<Integer, Session> getLoggedUsers(){
        return loggedUsers;
    }
}
