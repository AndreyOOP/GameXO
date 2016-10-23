package jfiles.dao;

import jfiles.model.UserEntity;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**Responsible for interaction with <i>User</i> database<br>
 * Methods are self-explanatory*/
@Repository
public class UserDAO{

    @PersistenceContext
    private EntityManager entityManager;

    public UserEntity getUserByName(String name) {

        return entityManager.find(UserEntity.class, name);
    }

    public void addUser(UserEntity user) {

        entityManager.persist(user);
    }

    public void remove(String user) {

        UserEntity ue = entityManager.find(UserEntity.class, user);

        entityManager.remove(ue);
    }

    public void remove(UserEntity user) {

        entityManager.remove(user);
    }

    public void update(UserEntity user) {

        entityManager.merge(user);
    }

    public List<UserEntity> getAllUsers() {

        List<UserEntity> list = entityManager.createQuery("SELECT U FROM UserEntity U").getResultList();

        return list;
    }

    public List<String> getRecordsWithEmail(String email) {

        List<String> emails = entityManager.createQuery("SELECT U.email FROM UserEntity U WHERE U.email = '" + email + "'").getResultList();

        return emails;
    }

    public List<UserEntity> getRecordsWithUserNameOrEmail(String name, String email){

        List<UserEntity> list = entityManager
                                .createQuery("SELECT U FROM UserEntity U WHERE U.name = '" + name + "' OR U.email = '" + email + "'")
                                .getResultList();
        return list;
    }

    public List<UserEntity> getBoth(String user, String vsUser){

        List<UserEntity> list = entityManager
                .createQuery("SELECT U FROM UserEntity U WHERE U.name = '" + user + "' OR U.name = '" + vsUser + "'")
                .getResultList();

        return list;
    }
}
