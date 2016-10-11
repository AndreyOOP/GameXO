package jfiles.service.SessionLogin;

import jfiles.model.UserEntity;
import org.springframework.stereotype.Service;

/**Session contains information about authorized(logged in) user<br>
 * User information extracted once during authorization after this it could be accessed via <i>authorization key</i> (it is stored into HashMap into LoginSession class)*/
@Service
public class Session {

    private String userName;
    private String userPassword;
    private String userEmail;
    private String blobKey;
    private int    userRole;

    private int    authKey;

    public Session(){}

    public Session(UserEntity userEntity){

        userName     = userEntity.getName();
        userPassword = userEntity.getPassword();
        userRole     = userEntity.getRole();
        blobKey      = userEntity.getBlobKey();
        userEmail    = userEntity.getEmail();
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getUserRole() {
        return userRole;
    }

    public void setUserRole(int userRole) {
        this.userRole = userRole;
    }

    public int getAuthKey() {
        return authKey;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public void setAuthKey(int authKey) {
        this.authKey = authKey;
    }

    public String getBlobKey() {
        return blobKey;
    }

    public void setBlobKey(String blobKey) {
        this.blobKey = blobKey;
    }
}
