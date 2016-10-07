package jfiles.controllers;

import jfiles.model.TableEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Controller
public class LoginController {

//    @Autowired
//    private EntityManagerFactory emf;

    @PersistenceContext
    private EntityManager em;

    @RequestMapping(value = "/")
    public String index(){

//        em = emf.createEntityManager();

        return "index";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String loginPagePost(Model model,
                                @RequestParam String login,
                                @RequestParam String password){

        TableEntity te = hibernateDbAccess(login);

        if( te == null)
            return "loginJSP";

        if( password.contentEquals( te.getPassword())){

            model.addAttribute("authorized", true);
            model.addAttribute("log"       , login + "x");
            model.addAttribute("pass"      , password + "x");
        } else {

            model.addAttribute("authorized", false);
        }

        return "loginJSP";
    }

    private TableEntity hibernateDbAccess(String login){

//        em.getTransaction().begin();

        TableEntity res = em.find(TableEntity.class, login);

//        em.getTransaction().commit();
        return res;
    }

}
