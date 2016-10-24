package jfiles.service.Game;

/**
 * Created by an.yudaichev on 24.10.2016.
 */
public class Player {

    private Boolean turn; //true - his turn, false not his turn
    private int     gameStatus; //in game, win, loose, even
    private String  blobKey;
    private String  name;
    private int     mark; //X or O
    private int     authKey;
    private Boolean updatedDB = false;

    public Player(Boolean turn, int gameStatus, String blobKey, String name, int mark, int authKey) {
        this.turn = turn;
        this.gameStatus = gameStatus;
        this.blobKey = blobKey;
        this.name = name;
        this.mark = mark;
        this.authKey = authKey;
    }

    public Boolean getTurn() {
        return turn;
    }

    public Boolean isHisTurn() {
        return turn;
    }

    public void setTurn(Boolean turn) {
        this.turn = turn;
    }

    public int getGameStatus() {
        return gameStatus;
    }

    public void setGameStatus(int gameStatus) {
        this.gameStatus = gameStatus;
    }

    public String getBlobKey() {
        return blobKey;
    }

    public void setBlobKey(String blobKey) {
        this.blobKey = blobKey;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMark() {
        return mark;
    }

    public void setMark(int mark) {
        this.mark = mark;
    }

    public int getAuthKey() {
        return authKey;
    }

    public void setAuthKey(int authKey) {
        this.authKey = authKey;
    }

    public Boolean getUpdatedDB() {
        return updatedDB;
    }

    public void setUpdatedDB(Boolean updatedDB) {
        this.updatedDB = updatedDB;
    }
}
