package jfiles.model.StatusTable;

import java.util.LinkedList;
import java.util.List;

public class StatusTable {

    private List<StatusRecord> statusTable = new LinkedList<>();

    public void addRecord(String userName, String status){

        statusTable.add( new StatusRecord(userName, status));
    }

    public int size(){
        return statusTable.size();
    }

    public List<StatusRecord> list(){
        return statusTable;
    }
}
