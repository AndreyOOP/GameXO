package jfiles.Constants;

/**Parameters of tables which could be displayed from Admin menu<br>*/
public interface Table {

    /**How many lines is displayed on the screen*/
    int LINES_PER_PAGE = 7;

    /**For example 3 means that will be displayed 3 pages, previous and next symbols, like below<br> <<0 1 2>>*/
    int DISPLAY_PAGES  = 3;


    /**"Statistics" table id*/
    int    STATISCTIC          = 0;

    /**Name of "Statistics" table .csv file which appears during download*/
    String STATISTIC_FILE_NAME = "statistic_table.csv";


    /**"User" table id*/
    int    USER                = 1;

    /**Name of "User" table .csv file which appears during download*/
    String USER_FILE_NAME      = "user_table.csv";
}
