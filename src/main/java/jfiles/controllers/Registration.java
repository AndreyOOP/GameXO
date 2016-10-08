package jfiles.controllers;

import jfiles.Constants.*;
import jfiles.Constants.PageService.Check;
import jfiles.Constants.PageService.Message;
import jfiles.Constants.PageService.Tag;
import jfiles.service.HTMLMail;
import jfiles.service.PageService;
import jfiles.service.SessionLogin.LoginSession;
import jfiles.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

/**Controller is responsible for registration menu*/
@org.springframework.stereotype.Controller
public class Registration {

    //region Services declaration
    @Autowired
    private PageService pageService;

    @Autowired
//    @Qualifier(value = "UserServiceBean")
    private UserService userService;

//    @Autowired
//    private HTMLMail    htmlMail;

    @Autowired
    private LoginSession loginSession;
    //endregion

    //todo solve upload problem (it is related to google GAE)
    /**Make checks of input data<br>
     * Add User record to database<br>
     * Send welcome email<br>
     * Generate authorization key and include registered user to <i>Logged Users</i> list*/
    @RequestMapping(value = "/registration", method = RequestMethod.POST) //seems without required = false error with userName parameter
    public String registration(Model model,
                               @RequestParam String userName,
                               @RequestParam String userPassword,
                               @RequestParam String userEmail,
                               @RequestParam(value = "avatarFile", required = false) MultipartFile avatarFile){


        pageService.setModel(model)
                   .setFormUserName(userName)
                   .setFormUserPassword(userPassword)
                   .setFormUserEmail(userEmail)

                   .add( Tag.REGISTRATION_SAVED_USER_NAME     , userName)
                   .add( Tag.REGISTRATION_SAVED_USER_PASSWORD , userPassword)
                   .add( Tag.REGISTRATION_SAVED_EMAIL         , userEmail);


        //region Check Form Input
        if( pageService.makeCheck( Check.USER_NAME_BLANK)){

            pageService.add( Tag.REGISTRATION_ERR_USER_NAME, Message.USER_NAME_BLANK);
            return Page.REGISTRATION;
        }

        if( pageService.makeCheck( Check.USER_LENGTH))    {

            pageService.add( Tag.REGISTRATION_ERR_USER_NAME, Message.USER_NAME_TOO_SHORT);
            return Page.REGISTRATION;
        }

        if( pageService.makeCheck( Check.USER_CONTAIN_SPACE)){

            pageService.add( Tag.REGISTRATION_ERR_USER_NAME, Message.USER_NAME_CONTAIN_SPACES);
            return Page.REGISTRATION;
        }

        if( pageService.makeCheck( Check.PASSWORD_BLANK)){

            pageService.add( Tag.REGISTRATION_ERR_USER_PASSWORD, Message.PASSWORD_BLANK);
            return Page.REGISTRATION;
        }

        if( pageService.makeCheck( Check.PASSWORD_LENGTH)){

            pageService.add( Tag.REGISTRATION_ERR_USER_PASSWORD, Message.PASSWORD_LENGTH);
            return Page.REGISTRATION;
        }

        if( pageService.makeCheck( Check.PASSWORD_SYNTAX)){

            pageService.add( Tag.REGISTRATION_ERR_USER_PASSWORD, Message.PASSWORD_SYNTAX);
            return Page.REGISTRATION;
        }

        if( pageService.makeCheck( Check.USER_ALREADY_REGISTERED)){

            pageService.add( Tag.REGISTRATION_ERR_USER_NAME, Message.USER_ALREADY_REGISTERED);
            return Page.REGISTRATION;
        }

        if( pageService.makeCheck( Check.EMAIL_IN_DATABASE)){

            pageService.add( Tag.REGISTRATION_ERR_EMAIL, Message.EMAIL_ALREADY_REGISTERED);
            return Page.REGISTRATION;
        }
        //endregion


//        userService.addUser(userName, userPassword, Role.USER, userEmail, avatarFile);
        userService.addUser(userName, userPassword, Role.USER, userEmail, avatarFile);

//        htmlMail.sendEmail( userName, userPassword, userEmail, Email.WELCOME); //todo add to separate thread, 5-10 sec pending during registration

        if( loginSession.isUserAlreadyLoggedIn(userName))
            return Page.ERROR;

        int authKey = loginSession.generateAuthorizationKey();

        loginSession.addUser(authKey, userName);

        return "redirect:/welcome/" + authKey;
    }

    /**Load registration.jsp used into mainmenu.jsp*/
    @RequestMapping(value = "/registration", method = RequestMethod.GET)
    public String openRegistrationPage(){

        return Page.REGISTRATION;
    }

}