package jfiles.Constants;

/**Constants related to Email service<br>
 * There are 3 types of email could be generated - after registration, in case of password reset (by admin), in case user updates his information*/
public interface Email {

    int WELCOME        = 0;
    int PASSWORD_RESET = 1;
    int UPDATE         = 2;

//    String FROM = "xo.pass.service@gmail.com";
    String FROM = "gamexo-gae@appspot.gserviceaccount.com";
//    gamexo-gae@appspot.gserviceaccount.com
}
