package jfiles.controllers;

import jfiles.Constants.Page;
import jfiles.Constants.PageService.Tag;
import jfiles.model.StatisticEntity;
import jfiles.service.PageService;
import jfiles.service.SessionService;
import jfiles.model.Session;
import jfiles.service.StatisticService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**Controller is responsible for personal statistic display*/
@org.springframework.stereotype.Controller
public class Statistic {

    //region Services declaration
    @Autowired
    private SessionService sessionService;

    @Autowired
    private StatisticService statisticService;
    //endregion

    /**Load personal statisctic page<br>
     * Calculates personal rank and load gameHistory of played games*/
    @RequestMapping(value = "/statistic", method = RequestMethod.GET)
    public String welcome(Model model,
                          @RequestParam int authKey){

        PageService page = new PageService();
        page.setModel(model);

        Session session = sessionService.getBy(authKey);

        if(session==null){

            page.add(Tag.ERROR_NO_LOGIN_SESSION, Tag.ERROR_NO_LOGIN_SESSION);
            return Page.ERROR;
        }

        List<StatisticEntity> gameHistory = statisticService.getAllRecordsWithUser( session.getUserName());

        page.add( Tag.MAIN_MENU_USER_NAME    , session.getUserName())
            .add( Tag.MAIN_MENU_USER_ROLE    , session.getUserRole())
            .add( Tag.MAIN_MENU_IS_STATISTIC , true)
            .add( Tag.MAIN_MENU_AUTH_KEY     , authKey)

            .add( Tag.STATISTIC_RANK          , rankCalculation( gameHistory))
            .add( Tag.STATISTIC_LIST          , gameHistory);

        return Page.MAIN_MENU;
    }

    /**Calculate personal rank<br>
     * It depend on win/loose/even ratio. Even weight is 0.5*/
    private int rankCalculation(List<StatisticEntity> gameHistory){

        int win   = 0;
        int loose = 0;
        int even  = 0;

        for(StatisticEntity record: gameHistory){

            win   += record.getWin();
            loose += record.getLoose();
            even  += record.getEven();
        }

        int total = win + loose + even;

        if( total == 0) return 0; //as well work without this check, double rank = NaN -> (int)NaN = 0

        double rank = 100*((win + 0.5*even - loose)/total);

        return (int)rank;
    }

    /**Load statistic.jsp used into mainmenu.jsp*/
    @RequestMapping(value = "/statisticpagecontent")
    public String loadWelcomePageContent(){

        return Page.STATISTIC;
    }

}
