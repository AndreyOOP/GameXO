package jfiles.Constants.PageService;

/**Warning messages <br>
 * Will be displayed after fail of form field check*/
public interface Message {

    String USER_NAME_BLANK          = "Please enter your user id";
    String USER_NAME_LENGTH         = "User name should be at least 3 and less than 20 symbols";
    String USER_ALREADY_REGISTERED  = "User id registered already";
    String USER_NAME_CONTAIN_SPACES = "User id should not contain space";
    String USER_NAME_NOT_FOUND      = "Entered id could not be found. Please register";
    String USER_NAME_MISSING        = "User name not found";
    String USER_ROLE_NOT_FOUND      = "This role does not exist";

    String WELCOME_SESSION_REMOVED  = "Previous session have been deleted";

    String PASSWORD_INCORRECT       = "Incorrect password";
    String PASSWORD_BLANK           = "Please enter your password";
    String PASSWORD_LENGTH          = "Password should contain more than 3 characters and less than 20";
    String PASSWORD_SYNTAX          = "Password should contain alphabet characters and at least one number";

    String EMAIL_ALREADY_REGISTERED = "This email already in use";
    String EMAIL_LENGTH             = "Email is too long. (Should be less then 20 symbols)";
    String EMAIL_MISSING            = "Email is missing";

    String AVATAR_SIZE              = "Image size too big. (Should be less than 100KB)";

    String UPDATED                  = "Successfully updated";
    String NOTHING_UPDATE           = "Nothing to update";

    String ADD_RECORD_FIELD_BLANK   = "Should not be blank";

    String ERROR_NO_LOGIN_SESSION   = "Login session is missing";
    String ERROR_USER_WITH_YOUR_NAME_ONLINE   = "User with your name is online";
    String ERROR_ROLE               = "No access for your role";
    String ERROR_REMOVE_YOURSELF    = "Please do not remove yourself!";

    String GAME_OPPONENT_TURN       = "Opponent Turn";
    String GAME_YOUR_TURN           = "Your Turn";

}
