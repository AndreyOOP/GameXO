package jfiles.controllers;

import jfiles.Constants.Page;
import jfiles.Constants.PageService.Message;
import jfiles.Constants.PageService.Tag;

import jfiles.Constants.Role;
import jfiles.Constants.XO;
import jfiles.service.Game.GamePool;
import jfiles.service.Game.GameSession2;
import jfiles.service.PageService;
import jfiles.service.SessionLogin.LoginSession;
import jfiles.service.SessionLogin.Session;
import jfiles.model.StatusTable.StatusTable;
import jfiles.service.TableUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@org.springframework.stereotype.Controller
public class AdminStatusTable {

    //region Services declaration
    @Autowired
    private TableUtil tableUtil;

    @Autowired
    private LoginSession loginSession;
    //endregion

    @RequestMapping(value = "/admin/status", method = RequestMethod.GET)
    public String adminStatus(Model model,
                              @RequestParam int authKey,
                              @RequestParam("tableCurrentPage") int currentPage){

        PageService page = new PageService().setModel(model);

        Session session = loginSession.getSession(authKey);
        int role = session.getUserRole();

        if( !(role == Role.ADMIN.id() || role == Role.SUPER_ADMIN.id())){

            page.add( Tag.ERROR_MESSAGE, Message.ERROR_ROLE);
            return Page.ERROR;
        }

        StatusTable statusTable = tableUtil.createStatusTable(loginSession);

        tableUtil.setParam( currentPage, statusTable.size());

        page.add( Tag.MAIN_MENU_USER_NAME           , session.getUserName())
            .add( Tag.MAIN_MENU_USER_ROLE           , session.getUserRole())
            .add( Tag.MAIN_MENU_ADMIN_STATUS_PAGE   , true)
            .add( Tag.MAIN_MENU_AUTH_KEY            , authKey)

            .add( Tag.ADMIN_STATUS_RECORDS_LIST    , statusTable.list())
            .add( Tag.ADMIN_STATUS_CURRENT_PAGE    , currentPage)
            .add( Tag.TABLE_FROM_PAGE              , tableUtil.getFromPage())
            .add( Tag.TABLE_TO_PAGE                , tableUtil.getToPage())
            .add( Tag.TABLE_PREVIOUS               , tableUtil.getPrev())
            .add( Tag.TABLE_NEXT                   , tableUtil.getNext());

        return Page.MAIN_MENU;
    }


    @RequestMapping(value = "/killloginsession", method = RequestMethod.GET)
    public String killLoginSession(RedirectAttributes redirectAttributes, Model model,
                               @RequestParam int authKey,
                               @RequestParam("removeUser") String removeUser,
                               @RequestParam("tableCurrentPage") String tableCurrentPage){

        PageService page = new PageService().setModel(model);

        Session session = loginSession.getSession(authKey);
        int role = session.getUserRole();

        if( !(role == Role.ADMIN.id() || role == Role.SUPER_ADMIN.id())){

            page.add( Tag.ERROR_MESSAGE, Message.ERROR_ROLE);
            return Page.ERROR;
        }

        if( !session.getUserName().contentEquals(removeUser)){ //to avoid yourself removal

            //get game session of removed user, set that is game over & that database is updated
            Session session2 = loginSession.getSessionByUserName( removeUser); //todo rewrite!

            GameSession2 gameSession = session2.getGameSession();

            gameSession.setGameOver(true);

            gameSession.getPlayer1().setUpdatedDB(true); //no need of DB update
            gameSession.getPlayer2().setUpdatedDB(true);

            gameSession.getPlayer1().setGameStatus(XO.EVEN);
            gameSession.getPlayer2().setGameStatus(XO.EVEN);

            loginSession.removeUserByName(removeUser);
        }

        page.setRedirectAttributes( redirectAttributes);

        page.addRedirect( Tag.MAIN_MENU_AUTH_KEY        , authKey)
            .addRedirect( Tag.ADMIN_STATUS_CURRENT_PAGE , tableCurrentPage);

        return "redirect:" + Page.ADMIN_STATUS_MENU;
    }


    @RequestMapping(value = "/killgamesession", method = RequestMethod.GET)
    public String killGameSession(RedirectAttributes redirectAttributes, Model model,
                               @RequestParam int authKey,
                               @RequestParam("removeUser") String removeUser,
                               @RequestParam("tableCurrentPage") String tableCurrentPage){

        PageService page = new PageService().setModel(model);

        Session session = loginSession.getSession(authKey);
        int role = session.getUserRole();

        if( !(role == Role.ADMIN.id() || role == Role.SUPER_ADMIN.id())){

            page.add( Tag.ERROR_MESSAGE, Message.ERROR_ROLE);
            return Page.ERROR;
        }

        Session session2 = loginSession.getSessionByUserName( removeUser);

        GameSession2 gameSession = session2.getGameSession();

        gameSession.setGameOver(true);

        gameSession.getPlayer1().setUpdatedDB(true); //no need of DB update
        gameSession.getPlayer2().setUpdatedDB(true);

        gameSession.getPlayer1().setGameStatus(XO.EVEN);
        gameSession.getPlayer2().setGameStatus(XO.EVEN);

        page.setRedirectAttributes( redirectAttributes);

        page.addRedirect( Tag.MAIN_MENU_AUTH_KEY        , authKey)
            .addRedirect( Tag.ADMIN_STATUS_CURRENT_PAGE , tableCurrentPage);

        return "redirect:" + Page.ADMIN_STATUS_MENU;
    }

    @RequestMapping(value = "/adminstatuspagecontent")
    public String loadAdminStatisticJSP(){

        return Page.ADMIN_STATUS;
    }

}
