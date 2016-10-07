package jfiles.service;

import jfiles.Constants.PageService.Check;
import jfiles.Constants.Role;
import jfiles.model.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**Service checks the data provided by user (it check Login, Registration, Admin menu forms)*/
@Service("pageService")
public class PageService<T> {

    @Autowired
    private UserService userService;

    private Model model;
    private RedirectAttributes redirectAttributes;

    private String userName;
    private String formUserName;
    private String formUserPassword;
    private String formUserEmail;
    private String formUserRole;
    private String formVsUserName;

    private MultipartFile avatarFile;

    /**Method adds required attribute to JSP page*/
    public PageService add(String attribute, T value){

        model.addAttribute(attribute, value);

        return this;
    }

    /**Method adds redirect attribute to RedirectAttributes*/
    public PageService addRedirect(String attribute, T value){

        redirectAttributes.addAttribute(attribute, value);

        return this;
    }

    /**Based on checkType checks data of field from user input form (checks like user name or password syntax etc)
     * checkType is stored into <i>Check</i> interface*/
    public Boolean makeCheck(int checkType){

        switch (checkType){

            case Check.USER_NAME_BLANK:
                return formUserName.length() == 0;

            case Check.VS_USER_NAME_BLANK:
                return formVsUserName.length() == 0;

            case Check.USER_LENGTH:
                return formUserName.length() < 3;

            case Check.USER_CONTAIN_SPACE:
                return formUserName.contains(" ");

            case Check.USER_MISSING_IN_DATABASE:
                return userService.getUserByName(formUserName) == null;

            case Check.VS_USER_MISSING_IN_DB:
                return userService.getUserByName(formVsUserName) == null;

            case Check.USER_ALREADY_REGISTERED:
                return userService.getUserByName(formUserName) != null;

            case Check.USER_ROLE:
                return !(formUserRole.contentEquals( Role.USER_NAME) ||
                        formUserRole.contentEquals( Role.ADMIN_NAME) ||
                        formUserRole.contentEquals( Role.SUPER_ADMIN_NAME));

            case Check.PASSWORD_BLANK:
                return formUserPassword.length() == 0;

            case Check.PASSWORD_LENGTH:
                return formUserPassword.length() <= 3;

            case Check.PASSWORD_SYNTAX:
                return !(formUserPassword.matches(".*[0123456789].*") &&
                         formUserPassword.matches(".*[abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ].*"));

            case Check.EMAIL_IN_DATABASE:
                return userService.isEmailInDatabase(formUserEmail);

            case Check.PASSWORD_MATCH:

                UserEntity userEntity = userService.getUserByName(formUserName);

                String password       = userEntity.getPassword();

                return !password.contentEquals(formUserPassword);

            default: return false;
        }
    }

    /**Based on checkType checks if field has new value vs stored in database
     * checkType is stored into <i>Check</i> interface*/
    public Boolean isFieldUpdated(int checkType){

        switch (checkType){

            case Check.NEW_PASSWORD:{

                String currentPassword  = userService.getUserByName( userName).getPassword();

                return !currentPassword.contentEquals( formUserPassword);
            }

            case Check.NEW_EMAIL:{

                String currentEmail = userService.getUserByName( userName).getEmail();

                if( currentEmail == null) currentEmail = "";

                return !currentEmail.contentEquals( formUserEmail);
            }

            case Check.NEW_AVATAR:{

                return !avatarFile.getOriginalFilename().isEmpty();
            }

            default: return false;
        }
    }


    /*@Autowired
    @Qualifier(value = "UserServiceBean")
    public void setUserService(UserService userService) {
        this.userService = userService;
    }*/

    public PageService setModel(Model model) {
        this.model = model;
        return this;
    }

    public PageService setFormUserName(String formUserName) {
        this.formUserName = formUserName;
        return this;
    }

    public PageService setFormUserPassword(String formUserPassword) {
        this.formUserPassword = formUserPassword;
        return this;
    }

    public PageService setFormUserEmail(String formUserEmail) {
        this.formUserEmail = formUserEmail;
        return this;
    }

    public PageService setUserName(String userName) {
        this.userName = userName;
        return this;
    }

    public PageService setAvatarFile(MultipartFile avatarFile) {
        this.avatarFile = avatarFile;
        return this;
    }

    public PageService setFormUserRole(String formUserRole) {
        this.formUserRole = formUserRole;
        return this;
    }

    public PageService setFormVsUserName(String formVsUserName) {
        this.formVsUserName = formVsUserName;
        return this;
    }

    public PageService setRedirectAttributes(RedirectAttributes redirectAttributes){
        this.redirectAttributes = redirectAttributes;
        return this;
    }
}
