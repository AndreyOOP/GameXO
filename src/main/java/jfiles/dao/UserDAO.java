package jfiles.dao;

import jfiles.model.UserEntity;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**Responsible for interaction with <i>User</i> database<br>
 * Methods are self-explanatory*/
//todo REWRITE todos
@Repository
public class UserDAO{

    @PersistenceContext
    private EntityManager entityManager;

//    private SessionFactory sessionFactory;

//    public void setSessionFactory(SessionFactory sessionFactory){
//        this.sessionFactory = sessionFactory;
//    }

    public UserEntity getUserByName(String name) {

//        Session session = entityManager.find(UserEntity.class, name);
//        Session session = sessionFactory.getCurrentSession();

        return entityManager.find(UserEntity.class, name);
    }

    public void addUser(UserEntity user) {

        entityManager.persist(user);
//        Session session = sessionFactory.getCurrentSession();

//        session.persist(user);
    }

    public void remove(String user) {

//        Session session = sessionFactory.getCurrentSession();
//        UserEntity ue = (UserEntity)session.get(UserEntity.class, user);
//        session.delete(ue);

        UserEntity ue = entityManager.find(UserEntity.class, user);

        entityManager.remove(ue);
    }

    public void update(UserEntity user) {

//        Session session = sessionFactory.getCurrentSession();
//        session.update(user);

        entityManager.merge(user);

//        entityManager
    }

    public List<UserEntity> getAllUsers() {

//        Session session = sessionFactory.getCurrentSession();
//
//        List<UserEntity> list = session.createQuery("FROM UserEntity ").list();
        //todo check query
//        List<UserEntity> list = entityManager.createQuery("SELECT U FROM UserEntity U").getResultList();
//        List<UserEntity> list = entityManager.createQuery("FROM UserEntity ").getResultList();
        List<UserEntity> list = null;
        return list;
    }

//    @Override
    public List<String> getRecordsWithEmail(String email) {

//        Session session = sessionFactory.getCurrentSession();
//
//        List<String> emails = session.createQuery("SELECT U.email FROM UserEntity U WHERE U.email = '" + email + "'").list();
        //todo check query
//        List<String> emails = entityManager.createQuery("SELECT U.email FROM UserEntity U WHERE U.email = '" + email + "'").getResultList();
        List<String> emails = null;
        return emails;
    }
}
