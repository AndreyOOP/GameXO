package jfiles.service;

import com.google.appengine.api.blobstore.*;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@Service("BlobStore")
public class BlobStoreGAE {

    private static int    maxUploadSize;
    private static String defaultPicture;

    private static BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();

    public static void serveBlob(String key, HttpServletResponse res) throws IOException {

        blobstoreService.serve(new BlobKey(key) , res);
    }

    public static String getBlobKey(HttpServletRequest req){

        try {

            Map<String, List<BlobKey>> blobs     = blobstoreService.getUploads(req);
            Map<String, List<FileInfo>> blobInfo = blobstoreService.getFileInfos(req);

            //*** Note ***
            //during upload form without file on google blobstore sometimes appears file containing 0, sometime nothing
            //this part checks size if it is less 10 bytes - just use default picture
            if( blobInfo.get("avatarFile").get(0).getSize() < 10){

                blobstoreService.delete( blobs.get("avatarFile").get(0)); //remove 0 blob

                return defaultPicture;
            }

            return blobs.get("avatarFile").get(0).getKeyString();

        } catch (Exception e) {
            e.printStackTrace();
            return defaultPicture;
        }
    }

    //todo how to solve? now it will anyway upload picture, if it is too big - send error message and delete it...
    public static Boolean isSizeTooBig(HttpServletRequest req){

        try {

            Map<String, List<FileInfo>> x = blobstoreService.getFileInfos(req);

            if (x.get("avatarFile").get(0).getSize() > maxUploadSize){

                Map<String, List<BlobKey>> blobs = blobstoreService.getUploads(req);

                blobstoreService.delete( blobs.get("avatarFile").get(0));

                return true;
            }
            return false;

        } catch (Exception e) {

            e.printStackTrace();
            return false;
        }
    }

    public static Boolean isNewFile(HttpServletRequest req){

        try {

            Map<String, List<BlobKey>>  blobs    = blobstoreService.getUploads(req);
            Map<String, List<FileInfo>> blobInfo = blobstoreService.getFileInfos(req);

            if( blobInfo.get("avatarFile").get(0).getSize() < 10){

                blobstoreService.delete( blobs.get("avatarFile").get(0));

                return false;
            }
            return true;

        } catch (Exception e) {

            e.printStackTrace();
            return false;
        }
    }

    public static void setMaxUploadSize(int maxUploadSize) {
        BlobStoreGAE.maxUploadSize = maxUploadSize;
    }

    public static void setDefaultPicture(String defaultPicture) {
        BlobStoreGAE.defaultPicture = defaultPicture;
    }

}
