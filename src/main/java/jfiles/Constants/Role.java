package jfiles.Constants;

/**Role value and role name<br>
 * There are 3 types of access<br>
 * USER - without access to Admin menu<br>
 * ADMIN - limited access to Admin menu (he is able to reset password for any user and view or download User and Statistics tables<br>
 * SUPER_ADMIN - is able to edit User and Statistics tables (Developer role)    */
public interface Role {

    int USER        = 101;
    int ADMIN       = 202;
    int SUPER_ADMIN = 303;

    String USER_NAME        = "User";
    String ADMIN_NAME       = "Admin";
    String SUPER_ADMIN_NAME = "Super_Admin";
}
