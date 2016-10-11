package jfiles.service;

import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@Service("BlobStore")
public class BlobStoreGAE {

    private static int maxUploadSize;

    private static BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();

    public static void serveBlob(String key, HttpServletResponse res) throws IOException {

        blobstoreService.serve(new BlobKey(key) , res);
    }

    public static String getBlobKey(HttpServletRequest req){

        try {

            Map<String, List<BlobKey>> blobs = blobstoreService.getUploads(req);
            return blobs.get("avatarFile").get(0).getKeyString();

        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

//    public static byte[] getAvatarFileInBytes(HttpServletRequest req){
//
//        try {
//
//            Map<String, List<BlobKey>> blobs = blobstoreService.getUploads(req);
//            BlobKey blobKey = blobs.get("avatarFile").get(0);
//
//            return blobstoreService.fetchData(blobKey, 0, maxUploadSize);
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            return new byte[] {0};
//        }
//    }

    /*public static Boolean isNewFile(HttpServletRequest req){

        Map<String, List<BlobKey>> blobs = blobstoreService.getUploads(req);

        return blobs.size() > 0;
    }*/

    public static void setMaxUploadSize(int maxUploadSize) {
        BlobStoreGAE.maxUploadSize = maxUploadSize;
    }

}
