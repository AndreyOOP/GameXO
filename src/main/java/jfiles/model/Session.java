package jfiles.model;

import jfiles.model.Game.GameSession;
import org.springframework.stereotype.Service;

import java.util.List;

/**Session contains information about authorized(logged in) user<br>
 * User information extracted once during authorization after this it could be accessed via <i>authorization key</i> (it is stored into HashMap into LoginSession class)*/
@Service //todo is it service ??
public class Session {

    //region Variables declaration
    private int userRole;

    private String userName;
    private String userPassword;
    private String userEmail;
    private String blobKey;

    private GameSession gameSession;

    private List<UserEntity>      userEntities;      //cached Users table
    private List<StatisticEntity> statisticEntities; //cached Statistics table
    //endregion

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

    public String getBlobKey() {
        return blobKey;
    }

    public void setBlobKey(String blobKey) {
        this.blobKey = blobKey;
    }

    public List<UserEntity> getUserEntities() {
        return userEntities;
    }

    public void setUserEntities(List<UserEntity> userEntities) {
        this.userEntities = userEntities;
    }

    public List<StatisticEntity> getStatisticEntities() {
        return statisticEntities;
    }

    public void setStatisticEntities(List<StatisticEntity> statisticEntities) {
        this.statisticEntities = statisticEntities;
    }

    public GameSession getGameSession() {
        return gameSession;
    }

    public void setGameSession(GameSession gameSession) {
        this.gameSession = gameSession;
    }
}
