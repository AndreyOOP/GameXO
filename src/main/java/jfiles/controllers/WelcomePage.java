package jfiles.controllers;

import jfiles.Constants.Page;
import jfiles.Constants.PageService.Message;
import jfiles.Constants.PageService.Tag;
import jfiles.service.PageService;
import jfiles.service.SessionService;
import jfiles.model.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**Controller is responsible for welcome menu display*/ //todo add remove game session
@org.springframework.stereotype.Controller
public class WelcomePage {

    //region Services declaration
    @Autowired
    private SessionService sessionService;
    //endregion

    /**Load welcome page*/
    @RequestMapping(value = "/welcome/{authKey}", method = RequestMethod.GET)
    public String welcome(Model model, @PathVariable int authKey){

        PageService pageService = new PageService();

        Session session = sessionService.getBy(authKey);

        pageService.setModel(model);

        if(session == null){
            pageService.add( Tag.ERROR_NO_LOGIN_SESSION, Message.ERROR_NO_LOGIN_SESSION);
            return Page.ERROR;
        }

        pageService.add( Tag.MAIN_MENU_USER_NAME    , session.getUserName())
                   .add( Tag.MAIN_MENU_USER_ROLE    , session.getUserRole())
                   .add( Tag.MAIN_MENU_WELCOME_PAGE , true)
                   .add( Tag.MAIN_MENU_AUTH_KEY     , authKey);

        return Page.MAIN_MENU;
    }

    /**Load welcome.jsp used into mainmenu.jsp*/
    @RequestMapping(value = "/welcomepagecontent")
    public String loadWelcomePageContent(){

        return Page.WELCOME;
    }

}
