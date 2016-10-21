package jfiles.dao;

import jfiles.model.StatisticEntity;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**Responsible for interaction with <i>Statistic</i> database<br>
 * Methods are self-explanatory*/
@Repository
public class StatisticDAO {

    @PersistenceContext
    private EntityManager entityManager;

//    private SessionFactory sessionFactory;

//    public void setSessionFactory(SessionFactory sessionFactory){
//        this.sessionFactory = sessionFactory;
//    }

    public StatisticEntity getStatisticEntryById(int id) {


//        Session session = sessionFactory.getCurrentSession();

//        return (StatisticEntity) session.id(StatisticEntity.class, id);
        return (StatisticEntity) entityManager.find(StatisticEntity.class, id);
    }

    public StatisticEntity getRecordByUserName(String userName){

//        Session session = sessionFactory.getCurrentSession();

        List<StatisticEntity> list = entityManager.createQuery("SELECT R FROM StatisticEntity R WHERE R.user = '" + userName+ "'").getResultList();

        if( list.size() > 0){

            return list.get(0);
        }
        else {
            return null;
        }
    }

    /**After game end there is need to update exact combination of User-VsUser<br>
     * Normally only one pair exist but during development fake records have been added and list could contain more than 1 record*/
    public StatisticEntity getRecordByUserNames(String userName, String vsUserName){

//        Session session = sessionFactory.getCurrentSession();

        List<StatisticEntity> list = entityManager.createQuery(
                "SELECT R FROM StatisticEntity R WHERE R.user = '" + userName+ "' AND R.vsUser = '" + vsUserName +"'" ).getResultList();

        if( list.size() > 0){
            return list.get(0);
        }
        else {
            return null;
        }
    }

    public List<StatisticEntity> getAllRecords() {

//        Session session = sessionFactory.getCurrentSession();

        List<StatisticEntity> list = entityManager.createQuery("FROM StatisticEntity ").getResultList();

        return list;
    }

    public List<StatisticEntity> getAllRecordsWithUser(String user) {

//        Session session = sessionFactory.getCurrentSession();

        return entityManager.createQuery("SELECT R FROM StatisticEntity R WHERE R.user = '" + user + "'").getResultList();
    }

    public void addRecord(StatisticEntity record) {

//        Session session = sessionFactory.getCurrentSession();

        entityManager.persist(record);
//        StatisticEntity se = (StatisticEntity) entityManager.find(StatisticEntity.class, record);

//        se.getId();
    }

    public void update(StatisticEntity record) {

//        Session session= sessionFactory.getCurrentSession();
        entityManager.merge(record);
    }

    public void deleteRecord(int id) {

//        Session session = sessionFactory.getCurrentSession();

        StatisticEntity record = (StatisticEntity)entityManager.find(StatisticEntity.class, id);

        entityManager.remove(record);
    }

    public void deleteRecord(StatisticEntity record) {

        entityManager.remove(record);
    }

}
