package jfiles.controllers;

import jfiles.model.TableEntity;
import jfiles.service.TabService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {

    @Autowired
    private TabService tabService;

    @RequestMapping(value = "/")
    public String index(){

        return "index";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String loginPagePost(Model model,
                                @RequestParam String login,
                                @RequestParam String password){

        TableEntity te = tabService.getRecord(login);

        if( te == null){

            model.addAttribute("message", "Login not found");
            return "loginJSP";
        }

        if( password.contentEquals( te.getPassword())){

            model.addAttribute("authorized", true);
            model.addAttribute("log"       , login);
            model.addAttribute("pass"      , password);
        } else {

            model.addAttribute("authorized", false);
        }

        return "loginJSP";
    }

}
