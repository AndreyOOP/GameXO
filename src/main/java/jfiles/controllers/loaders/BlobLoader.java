package jfiles.controllers.loaders;

import jfiles.service.BlobStoreGAE;
import jfiles.service.SessionLogin.LoginSession;
import jfiles.service.SessionLogin.Session;
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

    @RequestMapping("/blob/{authKey}")
    public void blob(@PathVariable("authKey") int authKey, HttpServletResponse res) throws IOException {

        Session session = loginSession.getSession(authKey);

        String blobKey = session.getBlobKey();

        BlobStoreGAE.serveBlob(blobKey, res);
    }

}
