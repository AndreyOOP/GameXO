package jfiles.service;

import jfiles.Constants.XO;
import jfiles.dao.StatisticDAO;
import jfiles.model.StatisticEntity;
import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**Service for work with <i>Statistic</i> table. (Add, Update, Select records etc)*/
@Service("StatisticServiceBean")
public class StatisticService {

    @Autowired
    private StatisticDAO statisticDAO;

    @Transactional
    public StatisticEntity getRecordById(int id) {
        return statisticDAO.getStatisticEntryById(id);
    }

    /**After game end there is need to update record with exact combination of User-VsUser*/
    @Transactional
    public StatisticEntity getRecordByUserNames(String userName, String vsUserName) {
        return statisticDAO.getRecordByUserNames(userName, vsUserName);
    }

    /**Method is used to prepare personal statistic in Statistic menu*/
    @Transactional
    public List<StatisticEntity> getAllRecordsWithUser(String userName) {
        return statisticDAO.getAllRecordsWithUser(userName);
    }

    @Transactional
    public List<StatisticEntity> getAllRecords() {
        return statisticDAO.getAllRecords();
    }

    @Transactional
    public void addRecord(String userName, String vsUserName, int win, int loose, int even) {

        StatisticEntity record = new StatisticEntity();

        record.setUser(userName);
        record.setVsUser(vsUserName);
        record.setWin(win);
        record.setLoose(loose);
        record.setEven(even);

        statisticDAO.addRecord(record);
    }

    @Transactional
    public void updateRecord(int id, String vsUserName, int win, int loose, int even){

        StatisticEntity record = getRecordById(id);

        record.setVsUser(vsUserName);
        record.setWin(win);
        record.setLoose(loose);
        record.setEven(even);

        statisticDAO.update(record);
    }

    @Transactional
    public void deleteRecord(int id) {
        statisticDAO.deleteRecord(id);
    }

    /**Increment win counter for user-vsUser unique record<br>
     * If combination not found - create new record. (It means that players play in the first time) */
    @Transactional
    public void addWin(String userName, String vsUserName){

        StatisticEntity record = getRecordByUserNames(userName, vsUserName);

        if( record != null){

            record.setWin( record.getWin() + 1);
            statisticDAO.update(record);

        } else{
            addRecord(userName, vsUserName, 1, 0 ,0);
        }
    }

    /**Increment loose counter for user-vsUser unique record<br>
     * If combination not found - create new record. (It means that players play in the first time) */
    @Transactional
    public void addLoose(String userName, String vsUserName){

        StatisticEntity record = getRecordByUserNames(userName, vsUserName);

        if( record != null){

            record.setLoose( record.getLoose() + 1);
            statisticDAO.update(record);

        } else{
            addRecord(userName, vsUserName, 0, 1 ,0);
        }

    }

    /**Increment even counter for user-vsUser unique record<br>
     * If combination not found - create new record. (It means that players play in the first time) */
    @Transactional
    public void addEven(String userName, String vsUserName){

        StatisticEntity record = getRecordByUserNames(userName, vsUserName);

        if( record != null){

            record.setEven( record.getEven() + 1);
            statisticDAO.update(record);

        } else{
            addRecord(userName, vsUserName, 0, 0 ,1);
        }
    }

    @Transactional //todo why without this not work
    public void updateWinLooseDrawTable(int status, String player, String vsPLayer){

        switch (status){

            case XO.WIN:
                addWin( player, vsPLayer);
                break;

            case XO.LOOSE:
                addLoose( player, vsPLayer);
                break;

            case XO.EVEN:
                addEven( player, vsPLayer);
        }
    }

}
