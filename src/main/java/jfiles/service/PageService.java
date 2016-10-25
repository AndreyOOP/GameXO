package jfiles.service;

import jfiles.Constants.PageService.Check;
import jfiles.Constants.Role;
import jfiles.model.UserEntity;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**Service checks the data provided by user (it check Login, Registration, Admin menu forms)*/
@Service("pageService")
public class PageService<T> {

    private UserService userService;

    private Model model;
    private RedirectAttributes redirectAttributes;
    private HttpServletRequest req;

    private String formUserName;
    private String formUserPassword;
    private String formUserEmail;
    private String formUserRole;
    private String formVsUserName;

    private Boolean isUserOrEmailCheckDone = false;

    private UserEntity userInDBandPassword;
    private UserEntity editEntity;

    private List<UserEntity> nameOrEmail;
    private List<UserEntity> bothInDatabase;

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
                return formUserName.length() < 3 || formUserName.length() > 20;

            case Check.USER_CONTAIN_SPACE:
                return formUserName.contains(" ");

            case Check.USER_MISSING_IN_DATABASE:
                return userService.getUserByName(formUserName) == null;

            case Check.USER_IN_DATABASE_AND_PASSWORD_USER:{

                if(userInDBandPassword == null)
                    userInDBandPassword = userService.getUserByName(formUserName);

                return userInDBandPassword == null;
            }

            case Check.USER_IN_DATABASE_AND_PASSWORD_PASS:{

                if(userInDBandPassword == null)
                    userInDBandPassword = userService.getUserByName(formUserName);

                return !userInDBandPassword.getPassword().contentEquals(formUserPassword);
            }

            case Check.USER_OR_EMAIL_EXIST_USER:{

                if( !isUserOrEmailCheckDone){
                    nameOrEmail = userService.getRecordsWithUserNameOrEmail(formUserName, formUserEmail);
                    isUserOrEmailCheckDone = true;
                }

                if(nameOrEmail.size() == 0) return false;

                if(nameOrEmail.size() == 2) return true;

                return nameOrEmail.get(0).getName().contentEquals(formUserName);
            }

            case Check.USER_OR_EMAIL_EXIST_EMAIL:{

                if( !isUserOrEmailCheckDone){
                    nameOrEmail = userService.getRecordsWithUserNameOrEmail(formUserName, formUserEmail);
                    isUserOrEmailCheckDone = true;
                }

                if(nameOrEmail.size() == 0) return false;

                if(nameOrEmail.size() == 2) return true;

//                if(nameOrEmail.size() == 1)
                return nameOrEmail.get(0).getEmail().contentEquals(formUserEmail);
            }


            case Check.BOTH_IN_DB_USER:{

                if(bothInDatabase == null)
                    bothInDatabase = userService.getBoth(formUserName, formVsUserName);

                if(bothInDatabase.size() == 0) return true;

                if(bothInDatabase.size() == 2) return false;

                return !bothInDatabase.get(0).getName().contentEquals(formUserName);
            }

            case Check.BOTH_IN_DB_VS_USER:{

                if(bothInDatabase == null)
                    bothInDatabase = userService.getBoth(formUserName, formVsUserName);

                if(bothInDatabase.size() == 0) return true;

                if(bothInDatabase.size() == 2) return false;

                return !bothInDatabase.get(0).getName().contentEquals(formVsUserName);
            }

            case Check.VS_USER_MISSING_IN_DB:
                return userService.getUserByName(formVsUserName) == null;

            case Check.USER_ROLE:
                return !(formUserRole.contentEquals(Role.USER.text()) ||
                         formUserRole.contentEquals(Role.ADMIN.text()) ||
                         formUserRole.contentEquals(Role.SUPER_ADMIN.text()));

            case Check.PASSWORD_BLANK:
                return formUserPassword.length() == 0;

            case Check.PASSWORD_LENGTH:
                return formUserPassword.length() <= 3 || formUserPassword.length() > 20;

            case Check.PASSWORD_SYNTAX:
                return !(formUserPassword.matches(".*[0123456789].*") &&
                         formUserPassword.matches(".*[abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ].*"));

            case Check.EMAIL_IN_DATABASE:
                return userService.isEmailInDatabase(formUserEmail);

            case Check.EMAIL_MISSING:
               return formUserEmail.isEmpty();

            case Check.EMAIL_LENGTH:
                return formUserEmail.length() > 20;

            case Check.PASSWORD_MATCH:{

                UserEntity userEntity = userService.getUserByName(formUserName);

                String password = userEntity.getPassword();

                return !password.contentEquals(formUserPassword);
            }

            case Check.AVATAR_SIZE:
                return BlobStoreGAE.isSizeTooBig(req);

            default: return false;
        }
    }

    /**Based on checkType checks if field has new value vs stored in database
     * checkType is stored into <i>Check</i> interface*/
    public Boolean isFieldUpdated(int checkType){

        switch (checkType){

            case Check.NEW_AVATAR:
                return BlobStoreGAE.isNewFile(req);

            case Check.NEW_PASSWORD:
                return !editEntity.getPassword().contentEquals( formUserPassword);

            case Check.NEW_EMAIL:
                return !editEntity.getEmail().contentEquals( formUserEmail);

            default: return false;
        }
    }

    public UserEntity getUserInDBandPassword() {
        return userInDBandPassword;
    }

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

    public PageService setHttpServletRequest(HttpServletRequest req){
        this.req = req;
        return this;
    }

    public PageService setEditEntity(UserEntity editEntity) {
        this.editEntity = editEntity;
        return this;
    }

    public PageService setUserService(UserService userService) {
        this.userService = userService;
        return this;
    }

}
