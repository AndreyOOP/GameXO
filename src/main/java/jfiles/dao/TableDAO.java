package jfiles.dao;

import jfiles.model.TableEntity;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

//todo to remove later
@Repository
public class TableDAO {

    @PersistenceContext
    private EntityManager em;

    public TableEntity hibernateDbAccess(String login){

        return em.find(TableEntity.class, login);
    }

}
