package jfiles.controllers;

import jfiles.Constants.*;
import jfiles.Constants.PageService.Check;
import jfiles.Constants.PageService.Message;
import jfiles.Constants.PageService.Tag;
import jfiles.model.UserEntity;
import jfiles.service.BlobStoreGAE;
import jfiles.service.HTMLMail;
import jfiles.service.PageService;
import jfiles.service.SessionService;
import jfiles.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

/**Controller is responsible for registration menu*/
@org.springframework.stereotype.Controller
public class Registration {

    //region Services declaration
    @Autowired
    private UserService userService;

    @Autowired
    private HTMLMail    htmlMail;

    @Autowired
    private SessionService sessionService;
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
                               HttpServletRequest req){

        PageService pageService = new PageService();

        pageService.setModel(model)
                   .setFormUserName(userName)
                   .setFormUserPassword(userPassword)
                   .setFormUserEmail(userEmail)
                   .setHttpServletRequest(req)
                   .setUserService(userService)

                   .add( Tag.REGISTRATION_SAVED_USER_NAME     , userName)
                   .add( Tag.REGISTRATION_SAVED_USER_PASSWORD , userPassword)
                   .add( Tag.REGISTRATION_SAVED_EMAIL         , userEmail);


        //region Check Form Input
        if( pageService.makeCheck( Check.USER_NAME_BLANK)){

            pageService.add( Tag.REGISTRATION_ERR_USER_NAME, Message.USER_NAME_BLANK);
            return Page.REGISTRATION;
        }

        if( pageService.makeCheck( Check.USER_LENGTH))    {

            pageService.add( Tag.REGISTRATION_ERR_USER_NAME, Message.USER_NAME_LENGTH);
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

        if( pageService.makeCheck( Check.EMAIL_MISSING)){

            pageService.add( Tag.REGISTRATION_ERR_EMAIL, Message.EMAIL_MISSING);
            return Page.REGISTRATION;
        }

        if( pageService.makeCheck( Check.EMAIL_LENGTH)){

            pageService.add( Tag.REGISTRATION_ERR_EMAIL, Message.EMAIL_LENGTH);
            return Page.REGISTRATION;
        }

        if( pageService.makeCheck( Check.USER_OR_EMAIL_EXIST_USER)){

            pageService.add( Tag.REGISTRATION_ERR_USER_NAME, Message.USER_ALREADY_REGISTERED);
            return Page.REGISTRATION;
        }

        if( pageService.makeCheck( Check.USER_OR_EMAIL_EXIST_EMAIL)){

            pageService.add( Tag.REGISTRATION_ERR_EMAIL, Message.EMAIL_ALREADY_REGISTERED);
            return Page.REGISTRATION;
        }

        if ( pageService.makeCheck( Check.AVATAR_SIZE)) {

            pageService.add( Tag.REGISTRATION_ERR_AVATAR_SIZE, Message.AVATAR_SIZE);
            return Page.REGISTRATION;
        }
        //endregion

        UserEntity registeredUser = new UserEntity(userName, userPassword, Role.USER.id(), userEmail, BlobStoreGAE.getBlobKey(req));

        userService.addUser( registeredUser);

        htmlMail.sendEmail( userName, userPassword, userEmail, Email.WELCOME);

        if( sessionService.isUserAlreadyLoggedIn(userName)){
            pageService.add( Tag.ERROR_USER_WITH_YOUR_NAME_ONLINE, Message.ERROR_USER_WITH_YOUR_NAME_ONLINE);
            return Page.ERROR;
        }

        int authKey = sessionService.generateAuthorizationKey();

        sessionService.addUser(authKey, registeredUser);

        return "redirect:/welcome/" + authKey;
    }

    /**Load registration.jsp used into mainmenu.jsp*/
    @RequestMapping(value = "/registration", method = RequestMethod.GET)
    public String openRegistrationPage(){

        return Page.REGISTRATION;
    }

}
