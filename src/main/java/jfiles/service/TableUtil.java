package jfiles.service;

import jfiles.Constants.Table;
import jfiles.Constants.XO;
import jfiles.model.StatisticEntity;
import jfiles.model.StatusTable.StatusTable;
import jfiles.model.UserEntity;
import jfiles.service.Game.GamePool;
import jfiles.service.Game.GameSession;
import jfiles.service.SessionLogin.LoginSession;
import jfiles.service.SessionLogin.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**Service for preparation <i>Statistic</i> and <i>User</i> tables.<br>
 * It prepares page list (e.g << 2 3 4 6 >> ) which is shown under table . List created based on displayed page number*/
@Service
public class TableUtil {

    private int pageNum;
    private int linesInTable;
    private int pageQty;

    private int fromPage;
    private int toPage;
    private int next;
    private int prev;

    @Autowired
    private StatisticService statisticService;

    @Autowired
    private UserService       userService;

//    @Autowired
//    private LoginSession loginSession;


    public TableUtil(){}

    /**Extracts <i>Statistic</i> records which should be displayed on page with number 'page'*/
    public List<StatisticEntity> getServiceRecords(int page){

        List<StatisticEntity> allRecords = statisticService.getAllRecords();

        return allRecords.subList( getFromLine(), getToLine());
    }

    public List<StatisticEntity> getServiceRecords(List<StatisticEntity> list){

        return list.subList( getFromLine(), getToLine());
    }

    /**Extracts <i>User</i> records which should be displayed on page with number 'page'*/
//    public List<UserEntity> getUserRecords(int page){
//
//        List<UserEntity> allRecords = userService.getAllUsers();
//
//        return allRecords.subList( getFromLine(), getToLine());
//    }

    public List<UserEntity> getUserRecords(List<UserEntity> list){

        return list.subList( getFromLine(), getToLine());
    }

    /*public List<Session> getOnlineUsers(int page){

        //todo to update
        Collection<Session> allRecords = loginSession.getLoggedUsers().values();
        List<Session> list = new ArrayList<>(allRecords);

        return list.subList( getFromLine(), getToLine());
    }*/


    /**Calculates page number <b>From</b> which should begin page list*/
    public int getFromPage() {

        fromPage = (pageNum/Table.DISPLAY_PAGES) * Table.DISPLAY_PAGES;

        return fromPage;
    }

    /**Calculates page number <b>To</b> which should begin page list*/
    public int getToPage() {

        if (linesInTable % Table.LINES_PER_PAGE == 0){
            pageQty = linesInTable / Table.LINES_PER_PAGE;
        }else {
            pageQty = linesInTable / Table.LINES_PER_PAGE + 1;
        }

        toPage = fromPage + Table.DISPLAY_PAGES;

        if (fromPage + Table.DISPLAY_PAGES >= pageQty)
            toPage = pageQty;

        return toPage-1;
    }

    /**Calculates page number of <b>Next</b> button*/
    public int getNext() {

        next = ((toPage+1)>=pageQty) ? pageQty-1 : toPage+1;

        return next;
    }

    /**Calculates page number of <b>Previous</b> button*/
    public int getPrev() {

        prev = (fromPage>0) ? fromPage - 1 : 0;

        return prev;
    }

    /**Extracts all table records from database, convert it to .csv format*/
    public byte[] prepareTable(int tableId){

        List<Object> records = new ArrayList<Object>();
        StringBuilder table  = new StringBuilder();

        if(tableId == Table.STATISCTIC){

            records = (List)statisticService.getAllRecords();
            table.append("Id;User;VsUser;Win;Loose;Even\n");

        } else if (tableId == Table.USER){

            records = (List)userService.getAllUsers();
            table.append("Name;Password;Role;Email\n");
        }

        for(Object record: records)
            table.append( record.toString()).append("\n");

        return table.toString().getBytes();
    }

    public void setParam(int page, int tableSize){

        pageNum = page;
        linesInTable = tableSize;
    }

    private int getFromLine() {

        return Table.LINES_PER_PAGE * pageNum;
    }

    private int getToLine() {

        int to = (pageNum + 1) * Table.LINES_PER_PAGE;

        if (to >= linesInTable) to = linesInTable;

        return to;
    }

    public void remove(String name, List<UserEntity> table){

        for(UserEntity ue: table){
            if( ue.getName().contentEquals(name)){
                table.remove(ue);
                return;
            }
        }
    }

//    public void remove(UserEntity entity, List<UserEntity> table){
//
//        table.remove(entity);
//    }

    public UserEntity getByName(String name, List<UserEntity> table){

        for(UserEntity ue: table){
            if( ue.getName().contentEquals(name)){
                return ue;
            }
        }

        return null;
    }

    public void update(String name, String userPassword, String userEmail, String blobKey, int userRole, List<UserEntity> table){

        for(UserEntity ue: table){

            if( ue.getName().contentEquals(name)){

                ue.setPassword(userPassword);
                ue.setEmail(userEmail);
                ue.setRole( userRole);
                ue.setBlobKey(blobKey);

                return;
            }
        }
    }

    public StatisticEntity getById(int id, List<StatisticEntity> table){

        for(StatisticEntity se: table){
            if( se.getId() == id){
                return se;
            }
        }
        return null;
    }

    public void updateById(int id, String vsUserName, int win, int loose, int even, List<StatisticEntity> table){

        for(StatisticEntity se: table){
            if( se.getId() == id){

                se.setVsUser(vsUserName);
                se.setWin(win);
                se.setLoose(loose);
                se.setEven(even);

                return;
            }
        }
    }

    public StatusTable createStatusTable(LoginSession loginSession, GamePool gamePool){

        StatusTable statusTable = new StatusTable();

        for(Session s: loginSession.getLoggedUsers().values()){
            statusTable.addRecord( s.getUserName(), Table.ONLINE);
        }

        for(GameSession gs: gamePool.getGameSessions()){

            String name = gs.getPlayer1();

            if(name != null)
                statusTable.setStatusForRecord(name, Table.IN_GAME);

            name = gs.getPlayer2();

            if(name != null)
                statusTable.setStatusForRecord(name, Table.IN_GAME);
        }

        return statusTable;
    }

}
