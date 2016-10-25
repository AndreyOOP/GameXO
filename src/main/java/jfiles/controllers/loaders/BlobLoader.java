package jfiles.controllers.loaders;

import jfiles.service.BlobStoreGAE;
import jfiles.service.SessionService;
import jfiles.model.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class BlobLoader {

    @Autowired
    private SessionService sessionService;

    @RequestMapping("/blob/{authKey}")
    public void blob(@PathVariable("authKey") int authKey, HttpServletResponse res) throws IOException {

        Session session = sessionService.getBy(authKey);

        String blobKey = session.getBlobKey();

        BlobStoreGAE.serveBlob(blobKey, res);
    }

    @RequestMapping("/blobVsPlayer/{key}")
    public void blob(@PathVariable("key") String key, HttpServletResponse res) throws IOException {

        BlobStoreGAE.serveBlob(key, res);
    }

}
