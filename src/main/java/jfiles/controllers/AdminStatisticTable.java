package jfiles.controllers;

import jfiles.Constants.Page;
import jfiles.Constants.PageService.Check;
import jfiles.Constants.PageService.Message;
import jfiles.Constants.PageService.Tag;
import jfiles.Constants.Role;
import jfiles.model.StatisticEntity;
import jfiles.service.*;
import jfiles.service.SessionService;
import jfiles.model.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

/**Controller for work with menu <i>Admin->Statistic Table</i><br>
 * Responsible for display Statistics table content, creating new records and edit existing records*/
@org.springframework.stereotype.Controller
public class AdminStatisticTable {

    //region Services declaration
    @Autowired
    private TableUtil tableUtil;

    @Autowired
    private SessionService sessionService;

    @Autowired
    private StatisticService statisticService;

    @Autowired
    private UserService userService;
    //endregion

    /**Load Statistic table based on currentPage parameter<br>
     * currentPage - page of statistic table to display<br>
     * Display table parameters are defined into Table interface and calculated via TableUtil service */
    @RequestMapping(value = "/admin/statistic", method = RequestMethod.GET)
    public String adminUsers(Model model,
                             @RequestParam int authKey,
                             @RequestParam(value = Tag.ADMIN_STATISTIC_CURRENT_PAGE, required = false) int currentPage){

        PageService page = new PageService().setModel(model);

        Session session = sessionService.getBy(authKey);
        int     role    = session.getUserRole();

        if( !(role == Role.ADMIN.id() || role == Role.SUPER_ADMIN.id())){

            page.add( Tag.ERROR_MESSAGE, Message.ERROR_ROLE);
            return Page.ERROR;
        }

        if ( session.getStatisticEntities() == null) {
            session.setStatisticEntities( statisticService.getAllRecords());
        }

        List<StatisticEntity> cachedTable = session.getStatisticEntities();

        tableUtil.setParam( currentPage, cachedTable.size());

        page.add( Tag.MAIN_MENU_USER_NAME            , session.getUserName())
            .add( Tag.MAIN_MENU_USER_ROLE            , session.getUserRole())
            .add( Tag.MAIN_MENU_ADMIN_STATISTIC_PAGE , true)
            .add( Tag.MAIN_MENU_AUTH_KEY             , authKey)

            .add( Tag.ADMIN_STATISTIC_RECORDS_LIST , tableUtil.getServiceRecords(cachedTable))
            .add( Tag.ADMIN_STATISTIC_CURRENT_PAGE , currentPage)
            .add( Tag.TABLE_FROM_PAGE              , tableUtil.getFromPage())
            .add( Tag.TABLE_TO_PAGE                , tableUtil.getToPage())
            .add( Tag.TABLE_PREVIOUS               , tableUtil.getPrev())
            .add( Tag.TABLE_NEXT                   , tableUtil.getNext());

        return Page.MAIN_MENU;
    }

    /**Proceed POST request of add form of Statistics table<br>
     * Make checks are UserName & VsUserName not blanks and exist in database<br>
     * Depend on check add record to database or show error message    */
    @RequestMapping(value = "/addrecord", method = RequestMethod.POST)
    public String addNewUser(RedirectAttributes redirectAttributes, Model model,
                             @RequestParam String formUserName,
                             @RequestParam String vsUserName,
                             @RequestParam(required = false) int win,
                             @RequestParam(required = false) int loose,
                             @RequestParam(required = false) int even,
                             @RequestParam int tableCurrentPage,
                             @RequestParam int authKey){

        PageService page = new PageService().setModel(model);

        Session session = sessionService.getBy(authKey);

        if(session.getUserRole() != Role.SUPER_ADMIN.id()){

            page.add( Tag.ERROR_MESSAGE, Message.ERROR_ROLE);
            return Page.ERROR;
        }

        List<StatisticEntity> cachedTable = session.getStatisticEntities();

        tableUtil.setParam(tableCurrentPage, cachedTable.size());

        page.setModel(model)
            .setFormUserName(formUserName)
            .setFormVsUserName(vsUserName)
            .setRedirectAttributes(redirectAttributes)
            .setUserService(userService)

            .add( Tag.MAIN_MENU_AUTH_KEY            , authKey)
            .add( Tag.MAIN_MENU_USER_NAME           , session.getUserName())
            .add( Tag.MAIN_MENU_USER_ROLE           , session.getUserRole())
            .add( Tag.MAIN_MENU_ADMIN_STATISTIC_PAGE, true)

            .add( Tag.ADMIN_STATISTIC_RECORDS_LIST , tableUtil.getServiceRecords(cachedTable))
            .add( Tag.ADMIN_STATISTIC_CURRENT_PAGE , tableCurrentPage)
            .add( Tag.ADMIN_STATISTIC_SHOW_ADD_MENU, true)
            .add( Tag.TABLE_FROM_PAGE              , tableUtil.getFromPage())
            .add( Tag.TABLE_TO_PAGE                , tableUtil.getToPage())
            .add( Tag.TABLE_PREVIOUS               , tableUtil.getPrev())
            .add( Tag.TABLE_NEXT                   , tableUtil.getNext())

            .add( Tag.ADMIN_STATISTIC_SAVED_USER_NAME  , formUserName)
            .add( Tag.ADMIN_STATISTIC_SAVED_VS_NAME    , vsUserName)
            .add( Tag.ADMIN_STATISTIC_SAVED_WIN        , win)
            .add( Tag.ADMIN_STATISTIC_SAVED_LOOSE      , loose)
            .add( Tag.ADMIN_STATISTIC_SAVED_EVEN       , even);


        if ( page.makeCheck( Check.USER_NAME_BLANK)){

            page.add( Tag.ADMIN_STATISTIC_ERR_USER_NAME, Message.ADD_RECORD_FIELD_BLANK);

            return Page.MAIN_MENU;
        }

        if( page.makeCheck( Check.VS_USER_NAME_BLANK)){

            page.add( Tag.ADMIN_STATISTIC_ERR_VSUSER_NAME, Message.ADD_RECORD_FIELD_BLANK);

            return Page.MAIN_MENU;
        }

        //todo check negative values

//        if( page.makeCheck(Check.USER_MISSING_IN_DATABASE_2)){
        if( page.makeCheck(Check.BOTH_IN_DB_USER)){

            page.add( Tag.ADMIN_STATISTIC_ERR_USER_NAME, Message.USER_NAME_MISSING);

            return Page.MAIN_MENU;
        }

//        if( page.makeCheck( Check.VS_USER_MISSING_IN_DB)){
        if( page.makeCheck( Check.BOTH_IN_DB_VS_USER)){

            page.add( Tag.ADMIN_STATISTIC_ERR_VSUSER_NAME, Message.USER_NAME_MISSING);

            return Page.MAIN_MENU;
        }

        statisticService.addRecord(formUserName, vsUserName, win, loose, even);

        session.setStatisticEntities(null); //todo think how to define id, the table will be reloaded

        page.addRedirect( Tag.MAIN_MENU_AUTH_KEY           , authKey)
            .addRedirect( Tag.ADMIN_STATISTIC_CURRENT_PAGE , tableCurrentPage);

        return "redirect:" + Page.ADMIN_STATISTIC_MENU;
    }

    /**Method loads edit form based on record id which should be changed<br>
     * as well add to Model parameters required for Admin->Statistic menu display*/
    @RequestMapping(value = "/editrecord", method = RequestMethod.GET)
    public String editRecordGet(Model model,
                                @RequestParam int authKey,
                                @RequestParam int recordId,
                                @RequestParam int tableCurrentPage){

        PageService page = new PageService().setModel(model);

        Session session = sessionService.getBy(authKey);

        if(session.getUserRole() != Role.SUPER_ADMIN.id()){

            page.add( Tag.ERROR_MESSAGE, Message.ERROR_ROLE);
            return Page.ERROR;
        }

        List<StatisticEntity> cachedTable = session.getStatisticEntities();

        tableUtil.setParam( tableCurrentPage, cachedTable.size());

        StatisticEntity record = tableUtil.getById(recordId, cachedTable);  // getRecordById(recordId, cachedTable);

        page.add( Tag.MAIN_MENU_USER_NAME            , session.getUserName())
            .add( Tag.MAIN_MENU_USER_ROLE            , session.getUserRole())
            .add( Tag.MAIN_MENU_ADMIN_STATISTIC_PAGE , true)
            .add( Tag.MAIN_MENU_AUTH_KEY             , authKey)

            .add( Tag.ADMIN_STATISTIC_RECORDS_LIST , tableUtil.getServiceRecords(cachedTable))
            .add( Tag.ADMIN_STATISTIC_CURRENT_PAGE , tableCurrentPage)
            .add( Tag.TABLE_FROM_PAGE              , tableUtil.getFromPage())
            .add( Tag.TABLE_TO_PAGE                , tableUtil.getToPage())
            .add( Tag.TABLE_PREVIOUS               , tableUtil.getPrev())
            .add( Tag.TABLE_NEXT                   , tableUtil.getNext())

            .add( Tag.ADMIN_STATISTIC_SAVED_USER_NAME   , record.getUser())
            .add( Tag.ADMIN_STATISTIC_SAVED_VS_NAME     , record.getVsUser())
            .add( Tag.ADMIN_STATISTIC_SAVED_WIN         , record.getWin())
            .add( Tag.ADMIN_STATISTIC_SAVED_LOOSE       , record.getLoose())
            .add( Tag.ADMIN_STATISTIC_SAVED_EVEN        , record.getEven())
            .add( Tag.ADMIN_STATISTIC_SHOW_EDIT_MENU    , true)
            .add( Tag.ADMIN_STATISTIC_SAVED_ID          , recordId);

        return Page.MAIN_MENU;
    }

    /**Proceed POST request of edit form of Statistics table<br>
     * Make checks is VsUserName not blank and exist in database (userName is not changable)<br>
     * Depend on check update record in database or show error message    */
    @RequestMapping(value = "/editrecord", method = RequestMethod.POST)
    public String editRecord(RedirectAttributes redirectAttributes, Model model,
                             @RequestParam int recordId,
                             @RequestParam String formUserName,
                             @RequestParam String vsUserName,
                             @RequestParam int win,
                             @RequestParam int loose,
                             @RequestParam int even,
                             @RequestParam int tableCurrentPage,
                             @RequestParam int authKey){

        PageService page = new PageService().setModel(model);

        Session session = sessionService.getBy(authKey);

        List<StatisticEntity> cachedTable = session.getStatisticEntities();

        if(session.getUserRole() != Role.SUPER_ADMIN.id()){

            page.add( Tag.ERROR_MESSAGE, Message.ERROR_ROLE);
            return Page.ERROR;
        }

        tableUtil.setParam(tableCurrentPage, cachedTable.size());

        page.setRedirectAttributes(redirectAttributes)
            .setFormVsUserName(vsUserName)
            .setUserService(userService)

            .add( Tag.MAIN_MENU_AUTH_KEY             , authKey)
            .add( Tag.MAIN_MENU_USER_NAME            , session.getUserName())
            .add( Tag.MAIN_MENU_USER_ROLE            , session.getUserRole())
            .add( Tag.MAIN_MENU_ADMIN_STATISTIC_PAGE , true)

            .add( Tag.ADMIN_STATISTIC_RECORDS_LIST   , tableUtil.getServiceRecords(cachedTable))
            .add( Tag.ADMIN_STATISTIC_CURRENT_PAGE   , tableCurrentPage)
            .add( Tag.ADMIN_STATISTIC_SHOW_EDIT_MENU , true)
            .add( Tag.TABLE_FROM_PAGE                , tableUtil.getFromPage())
            .add( Tag.TABLE_TO_PAGE                  , tableUtil.getToPage())
            .add( Tag.TABLE_PREVIOUS                 , tableUtil.getPrev())
            .add( Tag.TABLE_NEXT                     , tableUtil.getNext())

            .add( Tag.ADMIN_STATISTIC_SAVED_ID         , recordId)
            .add( Tag.ADMIN_STATISTIC_SAVED_USER_NAME  , formUserName)
            .add( Tag.ADMIN_STATISTIC_SAVED_VS_NAME    , vsUserName)
            .add( Tag.ADMIN_STATISTIC_SAVED_WIN        , win)
            .add( Tag.ADMIN_STATISTIC_SAVED_LOOSE      , loose)
            .add( Tag.ADMIN_STATISTIC_SAVED_EVEN       , even);


        if( page.makeCheck( Check.VS_USER_NAME_BLANK)){

            page.add( Tag.ADMIN_STATISTIC_ERR_VSUSER_NAME, Message.ADD_RECORD_FIELD_BLANK);

            return Page.MAIN_MENU;
        }

        //todo add negative checks

        if( page.makeCheck( Check.VS_USER_MISSING_IN_DB)){

            page.add( Tag.ADMIN_STATISTIC_ERR_VSUSER_NAME, Message.USER_NAME_MISSING);

            return Page.MAIN_MENU;
        }

        statisticService.updateRecord(recordId, vsUserName, win, loose, even);
        tableUtil.updateById(recordId, vsUserName, win, loose, even, cachedTable);

        page.addRedirect( Tag.MAIN_MENU_AUTH_KEY, authKey)
            .addRedirect(Tag.ADMIN_STATISTIC_CURRENT_PAGE, tableCurrentPage);

        return "redirect:" + Page.ADMIN_STATISTIC_MENU;
    }

    /**Delete record based on Id parameter<br>*/
    @RequestMapping(value = "/deleterecord", method = RequestMethod.GET)
    public String deleteRecord(RedirectAttributes redirectAttributes, Model model,
                               @RequestParam int authKey,
                               @RequestParam("deleteRecordId") int deleteRecordId,
                               @RequestParam("tablePage") String tablePage){

        PageService page = new PageService().setModel(model);

        Session session = sessionService.getBy(authKey);

        if( session.getUserRole() != Role.SUPER_ADMIN.id()){

            page.add( Tag.ERROR_MESSAGE, Message.ERROR_ROLE);
            return Page.ERROR;
        }

        List<StatisticEntity> cachedTable = session.getStatisticEntities();

        StatisticEntity remove = tableUtil.getById(deleteRecordId, cachedTable);

        statisticService.deleteRecord( deleteRecordId);
        cachedTable.remove( remove);

        page.setRedirectAttributes( redirectAttributes);

        page.addRedirect( Tag.MAIN_MENU_AUTH_KEY           , authKey)
            .addRedirect( Tag.ADMIN_STATISTIC_CURRENT_PAGE , tablePage);

        return "redirect:" + Page.ADMIN_STATISTIC_MENU;
    }

    /**Load adminstatistic.jsp used into mainmenu.jsp*/
    @RequestMapping(value = "/adminstatisticpagecontent")
    public String loadAdminStatisticJSP(){

        return Page.ADMIN_STATISTIC;
    }

    /**Load editrecord.jsp used into adminstatistic.jsp*/
    @RequestMapping(value = "/editrecordpagecontent")
    public String loadAdminStatisticEditJSP(){

        return Page.ADMIN_STATISTIC_EDIT;
    }

    /**Load addrecord.jsp used into adminstatistic.jsp*/
    @RequestMapping(value = "/addrecordpagecontent")
    public String loadAddRecordJSP(){

        return Page.ADMIN_STATISTIC_ADD;
    }

}
