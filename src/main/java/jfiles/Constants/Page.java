package jfiles.Constants;

/**Paths to JSP pages which are stored into WEB-INF jsp folder*/
public interface Page {

    String REGISTRATION         = "registration";

    String LOGIN                = "login";

    String MAIN_MENU            = "authorized/mainmenu";

    String WELCOME              = "authorized/menu/welcome"; //todo / is a problem incorrect mapping on GAE...

    String GAME                 = "authorized/menu/game";
    String GAME_FIND            = "findgame";

    String STATISTIC            = "authorized/menu/statistic";

    String ADMIN_STATISTIC_MENU = "admin/statistic";
    String ADMIN_USER           = "authorized/menu/adminusers/adminuser";
    String ADMIN_USER_ADD       = "authorized/menu/adminusers/adduser";
    String ADMIN_USER_EDIT      = "authorized/menu/adminusers/edituser";

    String ADMIN_USER_MENU      = "admin/users";
    String ADMIN_STATISTIC      = "authorized/menu/adminstatistics/adminstatistic";
    String ADMIN_STATISTIC_ADD  = "authorized/menu/adminstatistics/addrecord";
    String ADMIN_STATISTIC_EDIT = "authorized/menu/adminstatistics/editrecord";

    String MYPROFILE            = "authorized/menu/myprofile";

    String ERROR                = "error";
}
