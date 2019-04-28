package ir.iconish.sanjinehsub.util;

import android.app.DownloadManager;
import android.content.Context;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Environment;

import java.io.File;

import ir.iconish.sanjinehsub.R;

public class DownloadHelper {

    public static void reportDownload(String downloadLink,Context context){





        DownloadManager mgr = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);

        Uri downloadUri = Uri.parse(downloadLink);
        DownloadManager.Request request = new DownloadManager.Request(
                downloadUri);








        request.setAllowedNetworkTypes(
                DownloadManager.Request.NETWORK_WIFI
                        | DownloadManager.Request.NETWORK_MOBILE)
                .setAllowedOverRoaming(false).setTitle(context.getString(R.string.download_report))


                .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);



        long refId = mgr.enqueue(request);

    }

}

