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

    public StatisticEntity getStatisticEntryById(int id) {

        return entityManager.find(StatisticEntity.class, id);
    }

    /**After game end there is need to update exact combination of User-VsUser<br>
     * Normally only one pair exist but during development fake records have been added and list could contain more than 1 record*/
    public StatisticEntity getRecordByUserNames(String userName, String vsUserName){

        List<StatisticEntity> list = entityManager.createQuery(
                "SELECT R FROM StatisticEntity R WHERE R.user = '" + userName+ "' AND R.vsUser = '" + vsUserName +"'" ).getResultList();

        return list.get(0);
    }

    public List<StatisticEntity> getAllRecords() {

        List<StatisticEntity> list = entityManager.createQuery("FROM StatisticEntity ").getResultList();

        return list;
    }

    public List<StatisticEntity> getAllRecordsWithUser(String user) {

        return entityManager.createQuery("SELECT R FROM StatisticEntity R WHERE R.user = '" + user + "'").getResultList();
    }

    public void addRecord(StatisticEntity record) {

        entityManager.persist(record);
    }

    public void update(StatisticEntity record) {

        entityManager.merge(record);
    }

    public void deleteRecord(int id) {

        StatisticEntity record = entityManager.find(StatisticEntity.class, id);

        entityManager.remove(record);
    }

    public void deleteRecord(StatisticEntity record) {

        entityManager.remove(record);
    }

}
