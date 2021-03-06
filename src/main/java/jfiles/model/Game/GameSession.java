package jfiles.model.Game;

import jfiles.Constants.XO;
import org.springframework.stereotype.Service;

@Service
public class GameSession {

    private Player  player1;
    private Player  player2;
    private Boolean gameOver;
    private int[][] matrix;

    public GameSession(){}

    public GameSession(Player player1, Player player2) {

        this.player1 = player1;
        this.player2 = player2;

        gameOver = false;
        matrix   = new int[XO.FIELD_SIZE][XO.FIELD_SIZE];
    }

    public Player getPlayer(int authKey){

        if(player1.getAuthKey() == authKey)
            return player1;
        else
            return player2;
    }

    public Player getVsPlayer(int authKey){

        if(player1.getAuthKey() != authKey)
            return player1;
        else
            return player2;
    }

    public Boolean isCell(int i, int j, int type){

        return matrix[i][j] == type;
    }

    public void setCellValue(int i, int j, int value){

        matrix[i][j] = value;
    }

    public void setMatrixToBlank(){

        for( int i=0; i < XO.FIELD_SIZE; i++){

            for( int j=0; j < XO.FIELD_SIZE; j++){

                matrix[i][j] = XO.BLANK;
            }
        }
    }

    /**Check if there is any free cell*/
    public Boolean isNoFreeCell(){

        for( int i=0; i < XO.FIELD_SIZE; i++){

            for( int j=0; j < XO.FIELD_SIZE; j++){

                if( matrix[i][j] == XO.BLANK)
                    return false;
            }
        }

        return true;
    }

    public Boolean isGameContinue(){
        return !gameOver;
    }

    /**Check if line of appropriate length exist*/
    public Boolean isWinner(int type){

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


    public Player getPlayer1() {
        return player1;
    }

    public Player getPlayer2() {
        return player2;
    }

    public int[][] getMatrix() {
        return matrix;
    }

    public void setGameOver(Boolean gameOver) {
        this.gameOver = gameOver;
    }

    public Boolean getGameOver() {
        return gameOver;
    }
}
