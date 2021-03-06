package jfiles.model.StatusTable;

public class StatusRecord {

    private String userName;
    private String status;

    public StatusRecord(String userName, String status) {
        this.userName = userName;
        this.status = status;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
