package jfiles.controllers;

import jfiles.Constants.*;
import jfiles.Constants.PageService.Check;
import jfiles.Constants.PageService.Message;
import jfiles.Constants.PageService.Tag;
import jfiles.model.UserEntity;
import jfiles.service.*;
import jfiles.service.SessionService;
import jfiles.model.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**Controller for work with menu <i>Admin->Users Table</i><br>
 * Responsible for display User table content, creating new records and edit existing records*/
@org.springframework.stereotype.Controller
public class AdminUserTable {

    //region Services declaration
    @Autowired
    private TableUtil         tableUtil;

    @Autowired
    private UserService       userService;

    @Autowired
    private HTMLMail          htmlMail;

    @Autowired
    private PasswordGenerator passwordGenerator;

    @Autowired
    private SessionService sessionService;
    //endregion

    /**Load User table based on tableCurrentPage parameter<br>
     * tableCurrentPage - page of User table to display<br>
     * Display table parameters are defined into Table interface and calculated via TableUtil service */
    @RequestMapping(value = "/admin/users", method = RequestMethod.GET)
    public String adminUsers(Model model,
                             @RequestParam int authKey,
                             @RequestParam int tableCurrentPage){

        PageService page = new PageService().setModel(model);

        Session session = sessionService.getBy(authKey);
        int     role    = session.getUserRole();

        if( !(role == Role.ADMIN.id() || role == Role.SUPER_ADMIN.id())){
            page.add( Tag.ERROR_MESSAGE, Message.ERROR_ROLE);
            return Page.ERROR;
        }

        if ( session.getUserEntities() == null) {
            session.setUserEntities( userService.getAllUsers());
        }

        List<UserEntity> cachedTable = session.getUserEntities();

        tableUtil.setParam( tableCurrentPage, cachedTable.size());

        page.setUserService(userService)

            .add( Tag.MAIN_MENU_AUTH_KEY           , authKey)
            .add( Tag.MAIN_MENU_USER_NAME          , session.getUserName())
            .add( Tag.MAIN_MENU_USER_ROLE          , session.getUserRole())
            .add( Tag.MAIN_MENU_IS_ADMIN_USER_PAGE , true)

            .add( Tag.ADMIN_USER_CURRENT_PAGE , tableCurrentPage)
            .add( Tag.ADMIN_USER_LIST         , tableUtil.getUserRecords( cachedTable))
            .add( Tag.TABLE_FROM_PAGE         , tableUtil.getFromPage())
            .add( Tag.TABLE_TO_PAGE           , tableUtil.getToPage())
            .add( Tag.TABLE_PREVIOUS          , tableUtil.getPrev())
            .add( Tag.TABLE_NEXT              , tableUtil.getNext());

        return Page.MAIN_MENU;
    }

    /**Delete record based on deleteUserName parameter<br>*/
    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public String removeUser(RedirectAttributes redirectAttributes,
                             Model model,
                             @RequestParam int authKey,
                             @RequestParam String deleteUser,
                             @RequestParam int tableCurrentPage){

        PageService page = new PageService().setModel(model);

        Session session = sessionService.getBy(authKey);

        if( session.getUserRole() != Role.SUPER_ADMIN.id()){

            page.add( Tag.ERROR_MESSAGE, Message.ERROR_ROLE);
            return Page.ERROR;
        }

        if( !deleteUser.contentEquals( session.getUserName())){ //to avoid yourself deletion

            List<UserEntity> cachedTable = session.getUserEntities();

            userService.remove( deleteUser);
            tableUtil.remove(deleteUser, cachedTable);
        }else{
            page.add( Tag.ERROR_MESSAGE, Message.ERROR_REMOVE_YOURSELF);
            return Page.ERROR;
        }

        page.setRedirectAttributes(redirectAttributes)
                .addRedirect( Tag.MAIN_MENU_AUTH_KEY      , authKey)
                .addRedirect( Tag.ADMIN_USER_CURRENT_PAGE , tableCurrentPage);

        return "redirect:" + Page.ADMIN_USER_MENU;
    }

    /**Reset password based on user name<br>
     * Generate new password via <i>PasswordGenerator</i> service<br>
     * Generate and send email with new password via <i>htmlEmail</i> service */
    @RequestMapping(value = "/reset", method = RequestMethod.GET)
    public String resetPassword(RedirectAttributes redirectAttributes,
                                Model model,
                                @RequestParam int authKey,
                                @RequestParam String resetUser,
                                @RequestParam int tableCurrentPage){

        PageService page = new PageService().setModel(model);

        Session session = sessionService.getBy(authKey);
        int     role    = session.getUserRole();

        if( !(role == Role.ADMIN.id() || role == Role.SUPER_ADMIN.id())){

            page.add( Tag.ERROR_MESSAGE, Message.ERROR_ROLE);
            return Page.ERROR;
        }

        List<UserEntity> cachedTable = session.getUserEntities();

        UserEntity entityForUpdate = tableUtil.getByName(resetUser, cachedTable);

        String email = entityForUpdate.getEmail();

        String newPassword = passwordGenerator.generate();

        entityForUpdate.setPassword(newPassword);
        userService.update(entityForUpdate);

        htmlMail.sendEmail( resetUser, newPassword, email, Email.PASSWORD_RESET);

        page.setRedirectAttributes(redirectAttributes)
                .addRedirect( Tag.MAIN_MENU_AUTH_KEY      , authKey)
                .addRedirect( Tag.ADMIN_USER_CURRENT_PAGE , tableCurrentPage);

        return "redirect:" + Page.ADMIN_USER_MENU;
    }

    /**Proceed POST request of add form of <i>User</i> table<br>
     * Make checks of entered information<br>
     * Depend on check add record to database or show error message    */
    @RequestMapping(value = "/addnewuser", method = RequestMethod.POST)
    public String addNewUser(RedirectAttributes redirectAttributes, Model model,
                             @RequestParam int authKey,
                             @RequestParam String formUserName,
                             @RequestParam String userPassword,
                             @RequestParam String userEmail,
                             @RequestParam String userRole,
                             @RequestParam int tableCurrentPage,
                             HttpServletRequest req){

        PageService page = new PageService().setModel(model);

        Session session = sessionService.getBy(authKey);

        if( session.getUserRole() != Role.SUPER_ADMIN.id()){

            page.add( Tag.ERROR_MESSAGE, Message.ERROR_ROLE);
            return Page.ERROR;
        }

        List<UserEntity> cachedTable = session.getUserEntities();

        tableUtil.setParam(tableCurrentPage, cachedTable.size());

        page.setRedirectAttributes(redirectAttributes)
            .setFormUserName(formUserName)
            .setFormUserPassword(userPassword)
            .setFormUserEmail(userEmail)
            .setFormUserRole(userRole)
            .setHttpServletRequest(req)
            .setUserService(userService)

            .add( Tag.MAIN_MENU_AUTH_KEY           , authKey)
            .add( Tag.MAIN_MENU_USER_ROLE          , session.getUserRole())
            .add( Tag.MAIN_MENU_IS_ADMIN_USER_PAGE , true)

            .add( Tag.TABLE_FROM_PAGE              , tableUtil.getFromPage())
            .add( Tag.TABLE_TO_PAGE                , tableUtil.getToPage())
            .add( Tag.TABLE_PREVIOUS               , tableUtil.getPrev())
            .add( Tag.TABLE_NEXT                   , tableUtil.getNext())

            .add( Tag.ADMIN_USER_LIST            , tableUtil.getUserRecords( cachedTable))
            .add( Tag.ADMIN_USER_SHOW_ADD_MENU   , true)
            .add( Tag.ADMIN_USER_CURRENT_PAGE    , tableCurrentPage)
            .add( Tag.ADMIN_USER_SAVED_NAME      , formUserName)
            .add( Tag.ADMIN_USER_SAVED_PASSWORD  , userPassword)
            .add( Tag.ADMIN_USER_SAVED_USER_ROLE , Role.id(userRole).id())
            .add( Tag.ADMIN_USER_SAVED_EMAIL     , userEmail);


        //region Checks
        if( page.makeCheck( Check.USER_NAME_BLANK)){

            page.add( Tag.ADMIN_USER_ERR_USER_NAME, Message.USER_NAME_BLANK);
            return Page.MAIN_MENU;
        }

        if( page.makeCheck( Check.PASSWORD_BLANK)){

            page.add( Tag.ADMIN_USER_ERR_USER_PASSWORD, Message.PASSWORD_BLANK);
            return Page.MAIN_MENU;
        }

        if( page.makeCheck( Check.USER_ROLE)) {

            page.add( Tag.ADMIN_USER_ERR_USER_ROLE, Message.USER_ROLE_NOT_FOUND);
            return Page.MAIN_MENU;
        }


//        if( page.makeCheck( Check.USER_ALREADY_REGISTERED)){
        if( page.makeCheck( Check.USER_OR_EMAIL_EXIST_USER)){

            page.add( Tag.ADMIN_USER_ERR_USER_NAME, Message.USER_ALREADY_REGISTERED);
            return Page.MAIN_MENU;
        }

//        if( page.makeCheck( Check.EMAIL_IN_DATABASE)) {
        if( page.makeCheck( Check.USER_OR_EMAIL_EXIST_EMAIL)) {

            page.add( Tag.ADMIN_USER_ERR_EMAIL, Message.EMAIL_ALREADY_REGISTERED);
            return Page.MAIN_MENU;
        }

        if( page.makeCheck( Check.AVATAR_SIZE)){

            page.add( Tag.ADMIN_USER_ERR_AVATAR, Message.AVATAR_SIZE);
            return Page.MAIN_MENU;
        }
        //endregion


        int    role    = Role.id(userRole).id(); //id role id by name
        String blobKey = BlobStoreGAE.getBlobKey(req);

        userService.addUser(formUserName, userPassword, role, userEmail, blobKey);
        cachedTable.add( new UserEntity(formUserName, userPassword, role, userEmail, blobKey));

        page.addRedirect( Tag.MAIN_MENU_AUTH_KEY      , authKey)
            .addRedirect( Tag.ADMIN_USER_CURRENT_PAGE , tableCurrentPage);

        return "redirect:" + Page.ADMIN_USER_MENU;
    }

    /**Method loads edit form based on userName to edit<br>
     * as well add to Model parameters required for <i>Admin->User Table</i> menu display*/
    @RequestMapping(value = "/edituser", method = RequestMethod.GET)
    public String editUser(Model model,
                           @RequestParam int authKey,
                           @RequestParam String editUser,
                           @RequestParam int tableCurrentPage){

        PageService page = new PageService().setModel(model);

        Session session = sessionService.getBy(authKey);

        if(session.getUserRole() != Role.SUPER_ADMIN.id()){

            page.add( Tag.ERROR_MESSAGE, Message.ERROR_ROLE);
            return Page.ERROR;
        }

        List<UserEntity> cachedTable = session.getUserEntities();

        tableUtil.setParam(tableCurrentPage, cachedTable.size());

        UserEntity editUserEnt = tableUtil.getByName( editUser, cachedTable);

        page.add( Tag.MAIN_MENU_AUTH_KEY           , authKey)
            .add( Tag.MAIN_MENU_IS_ADMIN_USER_PAGE , true)
            .add( Tag.MAIN_MENU_USER_ROLE          , session.getUserRole())

            .add( Tag.TABLE_FROM_PAGE , tableUtil.getFromPage())
            .add( Tag.TABLE_TO_PAGE   , tableUtil.getToPage())
            .add( Tag.TABLE_PREVIOUS  , tableUtil.getPrev())
            .add( Tag.TABLE_NEXT      , tableUtil.getNext())

            .add( Tag.ADMIN_USER_LIST            , tableUtil.getUserRecords( cachedTable))
            .add( Tag.ADMIN_USER_SHOW_EDIT_MENU  , true)
            .add( Tag.ADMIN_USER_CURRENT_PAGE    , tableCurrentPage)

            .add( Tag.ADMIN_USER_SAVED_NAME      , editUser)
            .add( Tag.ADMIN_USER_SAVED_PASSWORD  , editUserEnt.getPassword())
            .add( Tag.ADMIN_USER_SAVED_USER_ROLE , editUserEnt.getRole())
            .add( Tag.ADMIN_USER_SAVED_EMAIL     , editUserEnt.getEmail());

        return Page.MAIN_MENU;
    }

    /**Proceed POST request of edit form of <i>User</i> table<br>
     * Make checks are email and password fields updated, if updated check them<br>
     * Based on check update record in database or show error message    */
    @RequestMapping(value = "/edituser", method = RequestMethod.POST)
    public String addNewUser(Model model, RedirectAttributes redirectAttributes,
                             @RequestParam int authKey,
                             @RequestParam int tableCurrentPage,
                             @RequestParam String editName,
                             @RequestParam String userPassword,
                             @RequestParam String userEmail,
                             @RequestParam String userRole,
                             HttpServletRequest req){

        PageService page = new PageService().setModel(model);

        Session session = sessionService.getBy(authKey);

        if( session.getUserRole() != Role.SUPER_ADMIN.id()){

            page.add( Tag.ERROR_MESSAGE, Message.ERROR_ROLE);
            return Page.ERROR;
        }

        List<UserEntity> cachedTable = session.getUserEntities();
        UserEntity       editEntity  = tableUtil.getByName(editName, cachedTable);
        String           blobKey     = editEntity.getBlobKey();

        tableUtil.setParam(tableCurrentPage, cachedTable.size());

        page.setFormUserPassword(userPassword)
            .setFormUserEmail(userEmail)
            .setRedirectAttributes(redirectAttributes)
            .setHttpServletRequest(req)
            .setEditEntity(editEntity)
            .setUserService(userService)

            .add( Tag.MAIN_MENU_AUTH_KEY           , authKey)
            .add( Tag.MAIN_MENU_IS_ADMIN_USER_PAGE , true)
            .add( Tag.MAIN_MENU_USER_ROLE          , session.getUserRole())

            .add( Tag.TABLE_FROM_PAGE , tableUtil.getFromPage())
            .add( Tag.TABLE_TO_PAGE   , tableUtil.getToPage())
            .add( Tag.TABLE_PREVIOUS  , tableUtil.getPrev())
            .add( Tag.TABLE_NEXT      , tableUtil.getNext())

            .add( Tag.ADMIN_USER_LIST            , tableUtil.getUserRecords( cachedTable))
            .add( Tag.ADMIN_USER_SHOW_EDIT_MENU  , true)
            .add( Tag.ADMIN_USER_CURRENT_PAGE    , tableCurrentPage)

            .add( Tag.ADMIN_USER_SAVED_NAME      , editName)
            .add( Tag.ADMIN_USER_SAVED_PASSWORD  , userPassword)
            .add( Tag.ADMIN_USER_SAVED_USER_ROLE , Role.id(userRole).id())
            .add( Tag.ADMIN_USER_SAVED_EMAIL     , userEmail);


        if ( page.isFieldUpdated( Check.NEW_PASSWORD)) {

            if( page.makeCheck( Check.PASSWORD_BLANK)){

                page.add( Tag.ADMIN_USER_ERR_USER_PASSWORD, Message.PASSWORD_BLANK);
                return Page.MAIN_MENU;
            }
        }

        if( page.isFieldUpdated(Check.NEW_EMAIL)){

            if( page.makeCheck( Check.EMAIL_IN_DATABASE)) {

                page.add( Tag.ADMIN_USER_ERR_EMAIL, Message.EMAIL_ALREADY_REGISTERED);
                return Page.MAIN_MENU;
            }
        }

        if ( page.makeCheck( Check.NEW_AVATAR)) {

            if( page.makeCheck( Check.AVATAR_SIZE)){

                page.add( Tag.ADMIN_USER_ERR_AVATAR, Message.AVATAR_SIZE);
                return Page.MAIN_MENU;
            }

            blobKey = BlobStoreGAE.getBlobKey(req);
        }

        int role = Role.id(userRole).id();

        userService.updateUserInDatabase(editName, userPassword, userEmail, blobKey, role);
        tableUtil.update(editName, userPassword, userEmail, blobKey, role, cachedTable);

        page.addRedirect( Tag.MAIN_MENU_AUTH_KEY      , authKey)
            .addRedirect( Tag.ADMIN_USER_CURRENT_PAGE , tableCurrentPage);

        return  "redirect:" + Page.ADMIN_USER_MENU;
    }

    /**Load adminuser.jsp used into mainmenu.jsp*/
    @RequestMapping(value = "/superadminmenupagecontent")
    public String prepareMyProfileContent(){

        return Page.ADMIN_USER;
    }

    /**Load adduser.jsp used into adminuser.jsp*/
    @RequestMapping(value = "/addnewuserpagecontent")
    public String prepareAddNewUserForm(){

        return Page.ADMIN_USER_ADD;
    }

    /**Load edituser.jsp used into adminuser.jsp*/
    @RequestMapping(value = "/edituserpagecontent")
    public String prepareEditUserForm(){

        return Page.ADMIN_USER_EDIT;
    }

}
