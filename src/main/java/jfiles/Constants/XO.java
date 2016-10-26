package jfiles.Constants;

/**Constants related to XO game<br>
 * X, O, BLANK - game filed constants - cross, zero, nothing<br>
 * FIELD_SIZE, WIN_LINE_SIZE - Size of game field, Qty of X or O in a row to win game<br>
 * WIN, LOOSE, EVEN - ids of game result    */
public interface XO {

    int X       = 1;
    int O       = 0;
    int BLANK   = -1;

    int FIELD_SIZE    = 10;
    int WIN_LINE_SIZE = 5;

    int WIN     =  11;
    int LOOSE   = -11;
    int EVEN    =  10;
    int IN_GAME =  20;

}
