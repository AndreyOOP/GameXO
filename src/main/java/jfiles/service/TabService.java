package jfiles.service;

import jfiles.dao.TableDAO;
import jfiles.model.TableEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TabService {

    @Autowired
    private TableDAO tableDAO;

    @Transactional
    public TableEntity getRecord(String login){
        return tableDAO.hibernateDbAccess(login);
    }

}
