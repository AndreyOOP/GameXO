package jfiles.controllers.loaders;

import jfiles.service.BlobStoreGAE;
import jfiles.service.Game.GamePool;
import jfiles.service.Game.GameSession;
import jfiles.service.SessionLogin.LoginSession;
import jfiles.service.SessionLogin.Session;
import jfiles.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class BlobLoader {

    @Autowired
    private LoginSession loginSession;

    @Autowired
    private UserService userService;

    @RequestMapping("/blob/{authKey}")
    public void blob(@PathVariable("authKey") int authKey, HttpServletResponse res) throws IOException {

        Session session = loginSession.getSession(authKey);

        String blobKey = session.getBlobKey();

        BlobStoreGAE.serveBlob(blobKey, res);
    }

    @RequestMapping("/blobVsPlayer/{name}")
    public void blob(@PathVariable("name") String name, HttpServletResponse res) throws IOException {

//        String blobKey = userService.getUserByName(name).getBlobKey();

        BlobStoreGAE.serveBlob(name, res);
    }

}
