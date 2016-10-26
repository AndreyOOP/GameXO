package jfiles.model.Game;

public class Player {

    private Boolean turn;       //true - player's turn
    private int     gameStatus; //in game, win, lose, draw
    private String  blobKey;
    private String  name;
    private int     mark;       //X or O
    private int     authKey;
    private Boolean statisticUpdated;

    public Player(Boolean turn, int gameStatus, String blobKey, String name, int mark, int authKey) {

        this.turn       = turn;
        this.gameStatus = gameStatus;
        this.blobKey    = blobKey;
        this.name       = name;
        this.mark       = mark;
        this.authKey    = authKey;

        this.statisticUpdated = false;
    }

    public Boolean isPlayerTurn() {
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMark() {
        return mark;
    }

    public int getAuthKey() {
        return authKey;
    }

    public void setAuthKey(int authKey) {
        this.authKey = authKey;
    }

    public Boolean isNotUpdatedInDB(){
        return !statisticUpdated;
    }

    public void setStatisticUpdated(Boolean statisticUpdated) {
        this.statisticUpdated = statisticUpdated;
    }
}
