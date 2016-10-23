package jfiles.service.Game;

import jfiles.Constants.XO;
import jfiles.service.StatisticService;
import jfiles.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**Class stored information about game session - players, turn, game field etc<br>
 * It has methods for game over check and statistics database update*/
@Service
public class GameSession {

    @Autowired
    private StatisticService statisticService;

    private String player1;
    private String player2;
    private Boolean turn1  = true;
    private int[][] matrix = new int[XO.FIELD_SIZE][XO.FIELD_SIZE];
    private int player1Status;
    private int player2Status;
    private Boolean isGameOver = false;

    private String blobPlayer1;
    private String blobPlayer2;

    public GameSession(){}

    public GameSession(StatisticService statisticService){

        this.statisticService = statisticService;

        setMatrixToBlank();
    }

    /**Check is game over (player create line of appropriate length or there is no cell available)<br>
     * Set win/loose/even status for players and update <i>Statistic</i> database record*/
    public void checkIfWinnerAndUpdateDB(int type){

        if(isWinner(type)){

            if(type == XO.X){

                player1Status = XO.WIN;
                player2Status = XO.LOOSE;
                isGameOver = true;

                statisticService.addWin(player1, player2);
                statisticService.addLoose(player2, player1);
            }

            if(type == XO.O){

                player1Status = XO.LOOSE;
                player2Status = XO.WIN;
                isGameOver = true;

                statisticService.addWin(player2, player1);
                statisticService.addLoose(player1, player2);
            }

        }else {

            if( !isAnyFreeCell()){

                player1Status = XO.EVEN;
                player2Status = XO.EVEN;
                isGameOver = true;

                statisticService.addEven(player2, player1);
                statisticService.addEven(player1, player2);
            }
        }
    }

    /**Check if line of appropriate length exist*/
    private Boolean isWinner(int type){

        int winH, winV;

        for(int i=0; i<XO.FIELD_SIZE; i++){ //horizontal & vertical checks

            winH = 0; winV = 0;

            for(int j=0; j<XO.FIELD_SIZE; j++){

                if( matrix[i][j] == type)
                    winH++;
                else
                    winH = 0;

                if( matrix[j][i] == type)
                    winV++;
                else
                    winV = 0;

                if(winH >= XO.WIN_LINE_SIZE || winV >= XO.WIN_LINE_SIZE)
                    return true;
            }
        }

        for(int k=0; k<XO.FIELD_SIZE; k++){

            if( checkMainDiagonal(k, 0, type))
                return true;

            if( checkMainDiagonal(0, k, type))
                return true;

            if( checkBelowNotMainDiagonal(k, XO.FIELD_SIZE-1, type))
                return true;

            if( checkAboveNotMainDiagonal(k, 0, type))
                return true;

        }

        return false;
    }

    private Boolean checkAboveNotMainDiagonal(int i, int j, int type){

        int winD = 0;

        while ( i>=0 && j<XO.FIELD_SIZE){

            if( matrix[i][j] == type)
                winD++;
            else
                winD = 0;

            if(winD >= XO.WIN_LINE_SIZE)
                return true;

            i--;
            j++;
        }

        return false;
    }

    private Boolean checkBelowNotMainDiagonal(int i, int j, int type){

        int winD = 0;

        while ( i<XO.FIELD_SIZE && j>=0){

            if( matrix[i][j] == type)
                winD++;
            else
                winD = 0;

            if(winD >= XO.WIN_LINE_SIZE)
                return true;

            i++;
            j--;
        }

        return false;
    }

    private Boolean checkMainDiagonal(int i, int j, int type){

        int winD  = 0;

        while ( i<XO.FIELD_SIZE && j<XO.FIELD_SIZE){

            if( matrix[i][j] == type)
                winD++;
            else
                winD = 0;

            if(winD >= XO.WIN_LINE_SIZE)
                return true;

            i++;
            j++;
        }

        return false;
    }


    /**Check if there is any free cell*/
    private Boolean isAnyFreeCell(){

        for( int i=0; i < XO.FIELD_SIZE; i++){

            for( int j=0; j < XO.FIELD_SIZE; j++){

                if( matrix[i][j] == XO.BLANK)
                    return true;
            }
        }

        return false;
    }

    private void setMatrixToBlank(){

        for( int i=0; i < XO.FIELD_SIZE; i++){

            for( int j=0; j < XO.FIELD_SIZE; j++){

                matrix[i][j] = XO.BLANK;
            }
        }
    }

    public Boolean isGameOver(){
        return isGameOver;
    }

    public void setIsGameOver(Boolean gameOver){
        isGameOver = gameOver;
    }

    public Boolean isPlayer1Turn(String player){

        return player1.contentEquals(player) && turn1;
    }

    public Boolean isPlayer2Turn(String player){

        return player2.contentEquals(player) && !turn1;
    }

    public void setCellValue(int i, int j, int value){

        matrix[i][j] = value;
    }

    /**Method checks is cell(i, j) populated by X or O*/
    public Boolean isCell(int i, int j, int type){

        return matrix[i][j] == type;
    }

    public String getPlayer1() {
        return player1;
    }

    public void setPlayer1(String player1) {
        this.player1 = player1;
    }

    public String getPlayer2() {
        return player2;
    }

    public void setPlayer2(String player2) {
        this.player2 = player2;
    }

    public void setTurn1(Boolean turn1) {
        this.turn1 = turn1;
    }

    public int[][] getMatrix() {
        return matrix;
    }

    public int getPlayer1Status() {
        return player1Status;
    }

    public void setPlayer1Status(int player1Status) {
        this.player1Status = player1Status;
    }

    public int getPlayer2Status() {
        return player2Status;
    }

    public void setPlayer2Status(int player2Status) {
        this.player2Status = player2Status;
    }

    public String getBlobPlayer1() {
        return blobPlayer1;
    }

    public void setBlobPlayer1(String blobPlayer1) {
        this.blobPlayer1 = blobPlayer1;
    }

    public String getBlobPlayer2() {
        return blobPlayer2;
    }

    public void setBlobPlayer2(String blobPlayer2) {
        this.blobPlayer2 = blobPlayer2;
    }

}
