package jfiles.controllers;

import jfiles.service.SessionLogin.LoginSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**Controller is responsible for user logout*/
@org.springframework.stereotype.Controller
public class Logout {

    @Autowired
    private LoginSession loginSession;

    /**Remove user from logged in user list, redirect to login page*/
    @RequestMapping(value = "/logout/{authKey}", method = RequestMethod.GET)
    public String logout(@PathVariable int authKey){

        loginSession.removeSession(authKey);

        return "redirect:/";
    }

}
