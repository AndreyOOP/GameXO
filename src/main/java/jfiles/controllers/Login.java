package jfiles.controllers;

import jfiles.Constants.Page;
import jfiles.Constants.PageService.Check;
import jfiles.Constants.PageService.Message;
import jfiles.Constants.PageService.Tag;
import jfiles.model.UserEntity;
import jfiles.service.PageService;
import jfiles.service.SessionLogin.LoginSession;
import jfiles.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**Controller is responsible for login<br>
 * Make user name and password checks, if successful generates authorization key */
@org.springframework.stereotype.Controller
public class Login {

    private PageService pageService = new PageService();

    @Autowired
    private UserService userService;

    @Autowired
    private LoginSession loginSession;

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


        pageService.setModel(model)
                   .setFormUserName(userName)
                   .setFormUserPassword(userPassword)

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

        UserEntity loginEntity = userService.getUserByName( userName);
        pageService.setLoginEntity( loginEntity);

        if( pageService.makeCheck( Check.USER_MISSING_IN_DATABASE)){

            pageService.add( Tag.LOGIN_ERR_USER_NAME, Message.USER_NAME_NOT_FOUND);
            return Page.LOGIN;
        }

        if( pageService.makeCheck( Check.PASSWORD_MATCH)){

            pageService.add( Tag.LOGIN_ERR_USER_PASSWORD, Message.PASSWORD_INCORRECT);
            return Page.LOGIN;
        }
        //endregion

//        if( loginSession.isUserAlreadyLoggedIn(userName)) //todo think about, if someone will not correctly logout pending...
//            return Page.ERROR;

        if( loginSession.isUserAlreadyLoggedIn(userName)){
            loginSession.removeUserByName(userName);
        }

        int authKey = loginSession.generateAuthorizationKey();

        loginSession.addUser(authKey, loginEntity);

        pageService.add( Tag.MAIN_MENU_USER_NAME    , userName)
                   .add( Tag.MAIN_MENU_USER_ROLE    , loginSession.getSession(authKey).getUserRole())
                   .add( Tag.MAIN_MENU_WELCOME_PAGE , true)
                   .add( Tag.MAIN_MENU_AUTH_KEY     , authKey);

        return Page.MAIN_MENU;
    }

}