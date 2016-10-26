package jfiles.service;

import jfiles.dao.UserDAO;
import jfiles.model.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**Service for work with <i>User</i> table. (Add, Update, Select records etc)<br>
 * Methods are self-explanatory*/
@Service
public class UserService{

    @Autowired
    private UserDAO userDAO;

    @Transactional
    public UserEntity getUserByName(String name) {
        return userDAO.getUserByName(name);
    }

    @Transactional
    public List<UserEntity> getAllUsers() {
        return userDAO.getAllUsers();
    }

    @Transactional
    public void addUser(UserEntity user) {
        userDAO.addUser(user);
    }

    @Transactional
    public void addUser(String userName, String userPassword, int userRole, String userEmail, String blobKey){

        UserEntity newUser = new UserEntity();

        newUser.setName     ( userName);
        newUser.setPassword ( userPassword);
        newUser.setBlobKey  ( blobKey);
        newUser.setRole     ( userRole);
        newUser.setEmail    ( userEmail);

        addUser( newUser);
    }

    @Transactional
    public void remove(String user) {
        userDAO.remove(user);
    }


    @Transactional
    public void update(UserEntity user) {
        userDAO.update(user);
    }

    @Transactional
    public void updateUserInDatabase(String userName, String userPassword, String userEmail, String blobKey) {

        UserEntity updateUser = getUserByName( userName);

        updateUser.setPassword ( userPassword);

        updateUser.setEmail    ( userEmail);

        updateUser.setBlobKey( blobKey);

        update( updateUser);
    }

    @Transactional
    public void updateUserInDatabase(String userName, String userPassword, String userEmail, String blobKey, int userRole) {

        UserEntity updateUser = getUserByName( userName);

        updateUser.setPassword ( userPassword);

        updateUser.setEmail    ( userEmail);

        updateUser.setBlobKey( blobKey);

        updateUser.setRole     ( userRole);

        update( updateUser);
    }

    @Transactional
    public Boolean isEmailInDatabase(String email) {

        return userDAO.getRecordsWithEmail(email).size() > 0;
    }

    @Transactional
    public List<UserEntity> getRecordsWithUserNameOrEmail(String userName, String userEmail){

        return userDAO.getRecordsWithUserNameOrEmail( userName, userEmail);
    }

    @Transactional
    public List<UserEntity> getBoth(String user, String vsUser){

        return userDAO.getBoth(user, vsUser);
    }

}
