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

/**Controller is responsible for user logout*/
@org.springframework.stereotype.Controller
public class Logout {

    @Autowired
    private LoginSession loginSession;

    @Autowired
    private GamePool gamePool;

    /**Remove user from logged in user list, redirect to login page*/
    @RequestMapping(value = "/logout/{authKey}", method = RequestMethod.GET)
    public String logout(Model model, @PathVariable int authKey){

        PageService pageService = new PageService();
        pageService.setModel(model);

        Session session = loginSession.getSession(authKey);

        if(session==null){

            pageService.add(Tag.ERROR_NO_LOGIN_SESSION, Tag.ERROR_NO_LOGIN_SESSION);
            return Page.ERROR;
        }

        try {
            gamePool.removeUser(session.getUserName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        loginSession.removeSession(authKey);

        return "redirect:/";
    }

}
