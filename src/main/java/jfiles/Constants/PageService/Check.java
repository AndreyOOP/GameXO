package jfiles.Constants.PageService;

/**Types of form fields check<br>
* Same checks could be used in different parts of program<br>
* e.g. Login, Registration, Add User forms - could check if password field blank etc*/
public interface Check {

    int USER_NAME_BLANK                    = 1;
    int USER_CONTAIN_SPACE                 = 2;
    int USER_LENGTH                        = 3;
    int USER_MISSING_IN_DATABASE           = 4;
    int USER_ROLE                          = 6;
    int USER_IN_DATABASE_AND_PASSWORD_USER = 8;
    int USER_IN_DATABASE_AND_PASSWORD_PASS = 9;
    int BOTH_IN_DB_USER                    = 10;
    int BOTH_IN_DB_VS_USER                 = 11;

    int PASSWORD_BLANK                     = 12;
    int PASSWORD_LENGTH                    = 13;
    int PASSWORD_MATCH                     = 14;
    int PASSWORD_SYNTAX                    = 15;

    int EMAIL_IN_DATABASE                  = 16;
    int NEW_EMAIL                          = 17;
    int NEW_PASSWORD                       = 18;

    int NEW_AVATAR                         = 19;
    int AVATAR_SIZE                        = 20;

    int VS_USER_NAME_BLANK                 = 21;
    int VS_USER_MISSING_IN_DB              = 22;

    int USER_OR_EMAIL_EXIST_USER           = 23;
    int USER_OR_EMAIL_EXIST_EMAIL          = 24;
}
