package jfiles.controllers.loaders;

import jfiles.Constants.Role;
import jfiles.Constants.Roles;
import jfiles.Constants.Table;
import jfiles.service.SessionLogin.LoginSession;
import jfiles.service.TableUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;


/**Controller is resposible for User or Statistics .csv table download*/
@Controller
public class TableLoader {

    @Autowired
    private LoginSession loginSession;

    @Autowired
    private TableUtil tableUtil;

    @RequestMapping(value = "download", method = RequestMethod.GET)
    public void downloadFile(  @RequestParam int authKey,
                               @RequestParam int fileId,
                               HttpServletResponse response){

        int userRole = loginSession.getSession(authKey).getUserRole();

        if ( userRole == Roles.SUPER_ADMIN.id() || userRole == Roles.ADMIN.id() ){

            try {

                String fileName = "";

                response.setContentType("text/xml");

                if( fileId == Table.STATISCTIC)
                    fileName = Table.STATISTIC_FILE_NAME;

                if( fileId == Table.USER)
                    fileName = Table.USER_FILE_NAME;

                response.setHeader( "Content-Disposition", "attachment; filename=" + fileName);

                OutputStream os = response.getOutputStream();
                os.write( tableUtil.prepareTable(fileId));

                response.flushBuffer();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
