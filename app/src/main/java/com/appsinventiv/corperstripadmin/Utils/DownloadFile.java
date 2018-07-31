package com.appsinventiv.corperstripadmin.Utils;

import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.os.Environment;



/**
 * Created by AliAh on 03/05/2018.
 */

public class DownloadFile {

    private DownloadFile() {
    }
    public static void fromUrl(String Url,String filename){
//        String string = Long.toHexString(Double.doubleToLongBits(Math.random()));

        DownloadManager downloadManager = (DownloadManager) ApplicationClass.getInstance().getApplicationContext().getSystemService(Context.DOWNLOAD_SERVICE);
        Uri uri = Uri.parse(Url);
        DownloadManager.Request request = new DownloadManager.Request(uri);
//        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, filename);
        Long referene = downloadManager.enqueue(request);
    }

}
