package jfiles.Constants.PageService;

/**These tags are used into JSP pages <br>
 * Almost on each page there is need to save input parameters after page refresh and show error/warning message*/
public interface Tag {

    String LOGIN_SAVED_USER_NAME            = "Login_SavedName";
    String LOGIN_SAVED_USER_PASSWORD        = "Login_SavedPassword";
    String LOGIN_ERR_USER_NAME              = "Login_ErrorMessage_UserId";
    String LOGIN_ERR_USER_PASSWORD          = "Login_ErrorMessage_UserPassword";

    String REGISTRATION_SAVED_USER_NAME     = "Registration_SavedName";
    String REGISTRATION_SAVED_USER_PASSWORD = "Registration_SavedPassword";
    String REGISTRATION_SAVED_EMAIL         = "Registration_SavedEmail";
    String REGISTRATION_ERR_USER_NAME       = "Registration_ErrorMessage_UserId";
    String REGISTRATION_ERR_USER_PASSWORD   = "Registration_ErrorMessage_UserPassword";
    String REGISTRATION_ERR_EMAIL           = "Registration_ErrorMessage_UserEmail";
    String REGISTRATION_ERR_AVATAR_SIZE     = "Registration_ErrorMessage_Avatar";

    String MAIN_MENU_USER_NAME              = "userName";
    String MAIN_MENU_USER_ROLE              = "userRole";
    String MAIN_MENU_IS_ADMIN_USER_PAGE     = "isAdminPage";
    String MAIN_MENU_WELCOME_PAGE           = "isWelcomePage";
    String MAIN_MENU_IS_PROFILE_PAGE        = "isProfile";
    String MAIN_MENU_ADMIN_STATISTIC_PAGE   = "isAdminStatistic";
    String MAIN_MENU_IS_FIND_GAME           = "isFindGame";
    String MAIN_MENU_IS_STATISTIC           = "isStatisticPage";
    String MAIN_MENU_AUTH_KEY               = "authKey";

    String MYPROFILE_SAVED_USER_NAME        = "MyProfile_SavedName";
    String MYPROFILE_SAVED_USER_PASSWORD    = "MyProfile_SavedPassword";
    String MYPROFILE_SAVED_EMAIL            = "MyProfile_SavedEmail";
    String MYPROFILE_ERR_USER_PASSWORD      = "MyProfile_ErrorMessage_UserPassword";
    String MYPROFILE_ERR_EMAIL              = "MyProfile_ErrorMessage_UserEmail";
    String MYPROFILE_ERR_AVATAR             = "MyProfile_ErrorMessage_Avatar";

    String ADMIN_STATISTIC_CURRENT_PAGE     = "tableCurrentPage";
    String ADMIN_STATISTIC_RECORDS_LIST     = "admin_statistic_listRecords";
    String ADMIN_STATISTIC_SAVED_ID         = "recordId";
    String ADMIN_STATISTIC_SAVED_USER_NAME  = "Admin_Stat_Saved_UserName";
    String ADMIN_STATISTIC_SAVED_VS_NAME    = "Admin_Stat_Saved_VsUserName";
    String ADMIN_STATISTIC_SAVED_WIN        = "Admin_Stat_Saved_Win";
    String ADMIN_STATISTIC_SAVED_LOOSE      = "Admin_Stat_Saved_Loose";
    String ADMIN_STATISTIC_SAVED_EVEN       = "Admin_Stat_Saved_Even";
    String ADMIN_STATISTIC_ERR_USER_NAME    = "Admin_Stat_Error_UserName";
    String ADMIN_STATISTIC_ERR_VSUSER_NAME  = "Admin_Stat_Error_VsUserName";
    String ADMIN_STATISTIC_SHOW_ADD_MENU    = "showMenu";
    String ADMIN_STATISTIC_SHOW_EDIT_MENU   = "showEditMenu";

    String ADMIN_USER_CURRENT_PAGE          = "tableCurrentPage";
    String ADMIN_USER_LIST                  = "listUsers";
    String ADMIN_USER_SHOW_ADD_MENU         = "showMenu";
    String ADMIN_USER_SAVED_NAME            = "SavedName";
    String ADMIN_USER_ERR_USER_NAME         = "ErrorMessage_UserId";
    String ADMIN_USER_SAVED_PASSWORD        = "SavedPassword";
    String ADMIN_USER_ERR_USER_PASSWORD     = "ErrorMessage_UserPassword";
    String ADMIN_USER_SAVED_USER_ROLE       = "SavedRole";
    String ADMIN_USER_ERR_USER_ROLE         = "ErrorMessage_UserRole";
    String ADMIN_USER_SAVED_EMAIL           = "SavedEmail";
    String ADMIN_USER_ERR_EMAIL             = "ErrorMessage_UserEmail";
    String ADMIN_USER_SHOW_EDIT_MENU        = "showEditMenuX";
    String ADMIN_USER_ERR_AVATAR            = "ErrorMessage_Avatar";

    String GAME_GAME_FOUND                  = "gameFound";
    String GAME_FIELD_SIZE                  = "matrixSize";
    String GAME_MATRIX                      = "cells";
    String GAME_PLAYER_1                    = "player1";
    String GAME_PLAYER_2                    = "player2";
    String GAME_WIN                         = "youWin";
    String GAME_LOOSE                       = "youLoose";
    String GAME_EVEN                        = "youEven";
    String GAME_MESSAGE                     = "message";

    String STATISTIC_RANK                   = "rank";
    String STATISTIC_LIST                   = "statistic_listRecords";

    String TABLE_PREVIOUS                   = "previous";
    String TABLE_NEXT                       = "next";
    String TABLE_FROM_PAGE                  = "fromPage";
    String TABLE_TO_PAGE                    = "toPage";
}
