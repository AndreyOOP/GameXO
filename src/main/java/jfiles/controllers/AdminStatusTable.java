package jfiles.controllers;

import jfiles.Constants.Page;
import jfiles.Constants.PageService.Tag;

import jfiles.Constants.Roles;
import jfiles.model.StatisticEntity;
import jfiles.service.Game.GamePool;
import jfiles.service.Game.GameSession;
import jfiles.service.PageService;
import jfiles.service.SessionLogin.LoginSession;
import jfiles.service.SessionLogin.Session;
import jfiles.service.StatusTable;
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

    private PageService page = new PageService();

    @Autowired
    private LoginSession loginSession;

    @Autowired
    private GamePool gamePool;
    //endregion

    @RequestMapping(value = "/admin/status", method = RequestMethod.GET)
    public String adminStatus(Model model,
                              @RequestParam int authKey,
                              @RequestParam(value = Tag.ADMIN_STATISTIC_CURRENT_PAGE, required = false) int currentPage){

        Session session = loginSession.getSession(authKey);
        int role = session.getUserRole();

        if( !(role == Roles.ADMIN.id() || role == Roles.SUPER_ADMIN.id()))
            return Page.ERROR; //todo change to auth error

        //prepare list user name <-> status (online/looking for game)
        StatusTable statusTable = new StatusTable();

        for(Session s: loginSession.getLoggedUsers().values()){
            statusTable.addRecord( s.getUserName(), "online");
        }

        for(GameSession gs: gamePool.getGameSessions()){

            try {
                String name = gs.getPlayer1();
                statusTable.setStatusForRecord( name, "in game");
            } catch (Exception e) {}

            try {
                String name = gs.getPlayer2();
                statusTable.setStatusForRecord( name, "in game");
            } catch (Exception e) {}
        }


        tableUtil.setParam( currentPage, statusTable.size()); //todo update


        page.setModel(model)

                .add( Tag.MAIN_MENU_USER_NAME           , session.getUserName())
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
    public String killLoginSession(RedirectAttributes redirectAttributes,
                               @RequestParam int authKey,
                               @RequestParam("recordId") String deleteRecordId,
                               @RequestParam("tableCurrentPage") String tableCurrentPage){

        Session session = loginSession.getSession(authKey);
        int role = session.getUserRole();

        if( !(role == Roles.ADMIN.id() || role == Roles.SUPER_ADMIN.id()))
            return Page.ERROR; //todo change to auth error

        loginSession.removeUserByName( deleteRecordId);

        page.setRedirectAttributes( redirectAttributes);

        page.addRedirect( Tag.MAIN_MENU_AUTH_KEY        , authKey)
            .addRedirect( Tag.ADMIN_STATUS_CURRENT_PAGE , tableCurrentPage);

        return "redirect:" + Page.ADMIN_STATUS_MENU;
    }


    @RequestMapping(value = "/killgamesession", method = RequestMethod.GET)
    public String killGameSession(RedirectAttributes redirectAttributes,
                               @RequestParam int authKey,
                               @RequestParam("recordId") String deleteRecordId,
                               @RequestParam("tableCurrentPage") String tableCurrentPage){

        Session session = loginSession.getSession(authKey);
        int role = session.getUserRole();

        if( !(role == Roles.ADMIN.id() || role == Roles.SUPER_ADMIN.id()))
            return Page.ERROR; //todo change to auth error

        try {
            gamePool.removeUser( deleteRecordId);
        } catch (Exception e) {
            e.printStackTrace();
        }

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
