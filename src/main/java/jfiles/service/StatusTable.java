package jfiles.service;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class StatusTable {

    private List<StatusRecord> statusTable = new LinkedList<>();

    public void addRecord(String userName, String status){

        statusTable.add( new StatusRecord(userName, status));
    }

    public void setStatusForRecord(String userName, String status){

        for(StatusRecord r: statusTable){
            if( r.getUserName().contentEquals( userName)){
                r.setStatus( status);
                return;
            }
        }
    }

    public int size(){
        return statusTable.size();
    }

    public List<StatusRecord> list(){
        return statusTable;
    }
}
