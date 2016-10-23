package jfiles.controllers;

import jfiles.Constants.*;
import jfiles.Constants.PageService.Check;
import jfiles.Constants.PageService.Message;
import jfiles.Constants.PageService.Tag;
import jfiles.model.UserEntity;
import jfiles.service.HTMLMail;
import jfiles.service.PageService;
import jfiles.service.SessionLogin.LoginSession;
import jfiles.service.SessionLogin.Session;
import jfiles.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import javax.servlet.http.HttpServletRequest;
import jfiles.service.BlobStoreGAE;

/**Controller is responsible for update personal information page*/
@org.springframework.stereotype.Controller
public class UpdateProfile {

    //region Services declaration
    @Autowired
    private LoginSession loginSession;

    @Autowired
    private UserService userService;

    @Autowired
    private HTMLMail    htmlMail;
    //endregion

    /**Method responsible for displaying form with current personal information*/
    @RequestMapping(value = "/myprofile/{authKey}", method = RequestMethod.GET)
    public String openMyProfile2(Model model, @PathVariable int authKey){

        PageService pageService = new PageService().setModel(model);

        Session session = loginSession.getSession(authKey);

        if(session==null){

            pageService.add(Tag.ERROR_NO_LOGIN_SESSION, Tag.ERROR_NO_LOGIN_SESSION);
            return Page.ERROR;
        }

        pageService.add( Tag.MAIN_MENU_USER_NAME           , session.getUserName())
                   .add( Tag.MAIN_MENU_USER_ROLE           , session.getUserRole())
                   .add( Tag.MAIN_MENU_IS_PROFILE_PAGE     , true)
                   .add( Tag.MAIN_MENU_AUTH_KEY            , authKey)

                   .add( Tag.MYPROFILE_SAVED_USER_NAME     , session.getUserName())
                   .add( Tag.MYPROFILE_SAVED_USER_PASSWORD , session.getUserPassword())
                   .add( Tag.MYPROFILE_SAVED_EMAIL         , session.getUserEmail());

        return Page.MAIN_MENU;
    }

    /**Method responsible for personal information update<br>
     * It makes checks if any field has been updated, in case of update make appropriate checks for updated field<br>
     * If check successful update database record, send notification email about update*/
    @RequestMapping(value = "/myprofile", method = RequestMethod.POST)
    public String updateMyProfile(Model model,
                                  @RequestParam int authKey,
                                  @RequestParam String userName,
                                  @RequestParam String userPassword,
                                  @RequestParam String userEmail,
                                  HttpServletRequest req) {

        PageService pageService = new PageService();
        Session     session     = loginSession.getSession(authKey);
        String      blobKey     = session.getBlobKey();
        UserEntity  editEntity  = new UserEntity(session.getUserName(), session.getUserPassword(), session.getUserRole(), session.getUserEmail(), session.getBlobKey());

        Boolean updatePassword = false;
        Boolean updateEmail    = false;
        Boolean updateAvatar   = false;

        pageService.setModel(model)
                   .setFormUserName(userName)
                   .setFormUserPassword(userPassword)
                   .setFormUserEmail(userEmail)
                   .setHttpServletRequest(req)
                   .setEditEntity(editEntity)
                   .setUserService(userService)

                   .add( Tag.MAIN_MENU_USER_NAME           , userName)
                   .add( Tag.MAIN_MENU_USER_ROLE           , session.getUserRole())
                   .add( Tag.MAIN_MENU_IS_PROFILE_PAGE     , true)
                   .add( Tag.MAIN_MENU_AUTH_KEY            , authKey)

                   .add( Tag.MYPROFILE_SAVED_USER_NAME     , userName)
                   .add( Tag.MYPROFILE_SAVED_USER_PASSWORD , userPassword)
                   .add( Tag.MYPROFILE_SAVED_EMAIL         , userEmail);


        //region Form Input Check

        if( pageService.isFieldUpdated( Check.NEW_PASSWORD)){

            if( pageService.makeCheck( Check.PASSWORD_BLANK)){

                pageService.add( Tag.MYPROFILE_ERR_USER_PASSWORD, Message.PASSWORD_BLANK);
                return Page.MAIN_MENU;
            }

            if( pageService.makeCheck( Check.PASSWORD_LENGTH)){

                pageService.add( Tag.MYPROFILE_ERR_USER_PASSWORD, Message.PASSWORD_LENGTH);
                return Page.MAIN_MENU;
            }

            if( pageService.makeCheck( Check.PASSWORD_SYNTAX)){

                pageService.add( Tag.MYPROFILE_ERR_USER_PASSWORD, Message.PASSWORD_SYNTAX);
                return Page.MAIN_MENU;
            }

            updatePassword = true;
        }


        if( pageService.isFieldUpdated( Check.NEW_EMAIL)){

            if( pageService.makeCheck( Check.EMAIL_IN_DATABASE)){

                pageService.add( Tag.MYPROFILE_ERR_EMAIL, Message.EMAIL_ALREADY_REGISTERED);
                return Page.MAIN_MENU;
            }

            updateEmail = true;
        }


        if( pageService.isFieldUpdated( Check.NEW_AVATAR)){ //todo is there 3 times upload ?

            if( pageService.makeCheck( Check.AVATAR_SIZE)){

                pageService.add( Tag.MYPROFILE_ERR_AVATAR, Message.AVATAR_SIZE);
                return Page.MAIN_MENU;
            }

            blobKey = BlobStoreGAE.getBlobKey(req); //new blob key of avatar

            updateAvatar = true;
        }


        if( updatePassword || updateEmail || updateAvatar){

            userService.updateUserInDatabase( userName, userPassword, userEmail, blobKey);

            session.setUserPassword(userPassword);
            session.setUserEmail(userEmail);
            session.setBlobKey(blobKey);

            htmlMail.sendEmail( userName, userPassword, userEmail, Email.UPDATE);

            if( updatePassword)
                pageService.add( Tag.MYPROFILE_ERR_USER_PASSWORD, Message.UPDATED);

            if( updateEmail)
                pageService.add( Tag.MYPROFILE_ERR_EMAIL, Message.UPDATED);

            if( updateAvatar)
                pageService.add( Tag.MYPROFILE_ERR_AVATAR, Message.UPDATED);
        }
        else
            pageService.add( Tag.MYPROFILE_ERR_AVATAR, Message.NOTHING_UPDATE);
        //endregion

        return Page.MAIN_MENU;
    }

    /**Load myprofile.jsp used into mainmenu.jsp*/
    @RequestMapping(value = "/myprofilepagecontent")
    public String prepareMyProfileContent(){

        return Page.MYPROFILE;
    }

}
