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

    public void update(UserEntity user) {

        entityManager.merge(user);
    }

    public List<UserEntity> getAllUsers() {

        //todo check query, why it marks as error Entity?
        List<UserEntity> list = entityManager.createQuery("SELECT U FROM UserEntity U").getResultList();

        return list;
    }

    public List<String> getRecordsWithEmail(String email) {

        //todo check query
        List<String> emails = entityManager.createQuery("SELECT U.email FROM UserEntity U WHERE U.email = '" + email + "'").getResultList();

        return emails;
    }
}
