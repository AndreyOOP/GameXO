package jfiles.Constants.PageService;

/**Types of form fields check<br>
* Same checks could be used in different parts of program<br>
* e.g. Login, Registration, Add User forms - could check if password field blank etc*/
public interface Check {

    int USER_NAME_BLANK           = 1;
    int USER_CONTAIN_SPACE        = 2;
    int USER_LENGTH               = 3;
    int USER_MISSING_IN_DATABASE  = 4;
    int USER_ROLE                 = 5;
    int USER_ALREADY_REGISTERED   = 6;

    int PASSWORD_BLANK            = 7;
    int PASSWORD_LENGTH           = 8;
    int PASSWORD_MATCH            = 9;
    int PASSWORD_SYNTAX           = 10;

    int EMAIL_IN_DATABASE         = 11;
    int NEW_EMAIL                 = 12;
    int NEW_PASSWORD              = 13;

    int NEW_AVATAR                = 14;
    int AVATAR_SIZE               = 15;

    int VS_USER_NAME_BLANK        = 16;
    int VS_USER_MISSING_IN_DB     = 17;
}
