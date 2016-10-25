package jfiles.controllers;

import jfiles.Constants.Page;
import jfiles.Constants.PageService.Message;
import jfiles.Constants.PageService.Tag;

import jfiles.Constants.Role;
import jfiles.Constants.XO;
import jfiles.model.Game.GameSession;
import jfiles.service.PageService;
import jfiles.service.SessionService;
import jfiles.model.Session;
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
    private SessionService sessionService;
    //endregion

    @RequestMapping(value = "/admin/status", method = RequestMethod.GET)
    public String adminStatus(Model model,
                              @RequestParam int authKey,
                              @RequestParam("tableCurrentPage") int currentPage){

        PageService page = new PageService().setModel(model);

        Session session = sessionService.getBy(authKey);
        int role = session.getUserRole();

        if( !(role == Role.ADMIN.id() || role == Role.SUPER_ADMIN.id())){

            page.add( Tag.ERROR_MESSAGE, Message.ERROR_ROLE);
            return Page.ERROR;
        }

        StatusTable statusTable = tableUtil.createStatusTable(sessionService);

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

        Session session = sessionService.getBy(authKey);
        int role = session.getUserRole();

        if( !(role == Role.ADMIN.id() || role == Role.SUPER_ADMIN.id())){

            page.add( Tag.ERROR_MESSAGE, Message.ERROR_ROLE);
            return Page.ERROR;
        }

        if( !session.getUserName().contentEquals(removeUser)){ //to avoid yourself removal

            Session session2 = sessionService.getBy( removeUser);

            GameSession gameSession = session2.getGameSession();
            gameEnd(gameSession);

            sessionService.removeBy(removeUser);
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

        Session session = sessionService.getBy(authKey);
        int role = session.getUserRole();

        if( !(role == Role.ADMIN.id() || role == Role.SUPER_ADMIN.id())){

            page.add( Tag.ERROR_MESSAGE, Message.ERROR_ROLE);
            return Page.ERROR;
        }

        GameSession killGameSession = sessionService.getBy( removeUser).getGameSession();
        gameEnd(killGameSession);

        page.setRedirectAttributes( redirectAttributes);

        page.addRedirect( Tag.MAIN_MENU_AUTH_KEY        , authKey)
            .addRedirect( Tag.ADMIN_STATUS_CURRENT_PAGE , tableCurrentPage);

        return "redirect:" + Page.ADMIN_STATUS_MENU;
    }

    @RequestMapping(value = "/adminstatuspagecontent")
    public String loadAdminStatisticJSP(){

        return Page.ADMIN_STATUS;
    }

    private void gameEnd(GameSession gameSession){

        if( gameSession != null){

            gameSession.setGameOver(true);

            gameSession.getPlayer1().setStatisticUpdated(true); //no need of DB update
            gameSession.getPlayer2().setStatisticUpdated(true);

            gameSession.getPlayer1().setGameStatus(XO.EVEN);
            gameSession.getPlayer2().setGameStatus(XO.EVEN);
        }
    }
}
