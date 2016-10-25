package jfiles.controllers;

import jfiles.Constants.Page;
import jfiles.Constants.PageService.Check;
import jfiles.Constants.PageService.Message;
import jfiles.Constants.PageService.Tag;
import jfiles.model.UserEntity;
import jfiles.service.PageService;
import jfiles.service.SessionService;
import jfiles.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**Controller is responsible for login<br>
 * Make user name and password checks, if successful generates authorization key */
@org.springframework.stereotype.Controller
public class Login {

    @Autowired
    private UserService userService;

    @Autowired
    private SessionService sessionService;

    /**Method is mapped '/' to login page*/
    @RequestMapping(value = "/")
    public String index(){
        return Page.LOGIN;
    }

    /**Make user name and password checks, if successful generates authorization key and load main menu*/
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @Scope("request")
    public synchronized String login(Model model,
                        @RequestParam String userName,
                        @RequestParam String userPassword){

        PageService pageService = new PageService();

        pageService.setModel(model)
                   .setFormUserName(userName)
                   .setFormUserPassword(userPassword)
                   .setUserService(userService)

                   .add( Tag.LOGIN_SAVED_USER_NAME    , userName)
                   .add( Tag.LOGIN_SAVED_USER_PASSWORD, userPassword);


        //region Login Form Check
        if( pageService.makeCheck( Check.USER_NAME_BLANK)){

            pageService.add( Tag.LOGIN_ERR_USER_NAME, Message.USER_NAME_BLANK);
            return Page.LOGIN;
        }

        if( pageService.makeCheck( Check.PASSWORD_BLANK)){

            pageService.add( Tag.LOGIN_ERR_USER_PASSWORD, Message.PASSWORD_BLANK);
            return Page.LOGIN;
        }

        if( pageService.makeCheck( Check.USER_IN_DATABASE_AND_PASSWORD_USER)){

            pageService.add( Tag.LOGIN_ERR_USER_NAME, Message.USER_NAME_NOT_FOUND);
            return Page.LOGIN;
        }

        if( pageService.makeCheck( Check.USER_IN_DATABASE_AND_PASSWORD_PASS)){

            pageService.add( Tag.LOGIN_ERR_USER_PASSWORD, Message.PASSWORD_INCORRECT);
            return Page.LOGIN;
        }
        //endregion

        if( sessionService.isUserAlreadyLoggedIn(userName)){

            sessionService.removeBy(userName);

            pageService.add( Tag.WELCOME_SESSION_REMOVED, Message.WELCOME_SESSION_REMOVED);
        }


        int authKey     = sessionService.generateAuthorizationKey();
        UserEntity user = pageService.getUserInDBandPassword();

        sessionService.addUser(authKey, user);

        pageService.add( Tag.MAIN_MENU_USER_NAME    , userName)
                   .add( Tag.MAIN_MENU_USER_ROLE    , user.getRole())
                   .add( Tag.MAIN_MENU_WELCOME_PAGE , true)
                   .add( Tag.MAIN_MENU_AUTH_KEY     , authKey);

        return Page.MAIN_MENU;
    }

}