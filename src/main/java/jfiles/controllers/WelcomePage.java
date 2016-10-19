package jfiles.controllers;

import jfiles.Constants.Page;
import jfiles.Constants.PageService.Tag;
import jfiles.service.Game.GamePool;
import jfiles.service.PageService;
import jfiles.service.SessionLogin.LoginSession;
import jfiles.service.SessionLogin.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**Controller is responsible for welcome menu display*/ //todo add remove game session
@org.springframework.stereotype.Controller
public class WelcomePage {

    //region Services declaration
    private PageService pageService = new PageService();

    @Autowired
    private GamePool gamePool;

    @Autowired
    private LoginSession loginSession;
    //endregion

    /**Load welcome page*/
    @RequestMapping(value = "/welcome/{authKey}", method = RequestMethod.GET)
    public String welcome(Model model, @PathVariable int authKey){

        Session session = loginSession.getSession(authKey);

        if(session == null)
            return Page.ERROR;

        try {
            gamePool.removeUser( session.getUserName()); //todo add try
        } catch (Exception e) {
            e.printStackTrace();
        }

        pageService.setModel(model)

                .add( Tag.MAIN_MENU_USER_NAME    , session.getUserName())
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
