package ir.iconish.sanjinehsub.util.push;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.widget.RemoteViews;


import ir.iconish.sanjinehsub.R;
import ir.iconish.sanjinehsub.util.AppConstants;

import static android.content.Context.NOTIFICATION_SERVICE;


public class Notification {




    public static void sendFirebaseNotification(String title, String message,Context context) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        //If on Oreo then notification required a notification channel.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_MIN;




            NotificationChannel channel = new NotificationChannel(AppConstants.CHANNEL_ID_NOTIFICATON, AppConstants.CHANNEL_NAME, importance);
            //NotificationChannel mChannel = new NotificationChannel("1", "one", importance);
            channel.setDescription(AppConstants.CHANNEL_DESCRIPTION);
           // NotificationChannel channel = new NotificationChannel("default", "Default", importance);
           notificationManager.createNotificationChannel(channel);
        }

        RemoteViews contentView = new RemoteViews(context.getPackageName(), R.layout.custom_push);
        contentView.setImageViewResource(R.id.image, R.mipmap.ic_launcher);
        contentView.setTextViewText(R.id.title, title);
        contentView.setTextViewText(R.id.text, message);
        NotificationCompat.Builder notification = new NotificationCompat.Builder(context, AppConstants.CHANNEL_ID_NOTIFICATON)
                .setCustomBigContentView(contentView)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setVibrate(new long[]{10,100,10,100,10,100,10,100})
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .setContent(contentView);


        notificationManager.notify(0, notification.build());
    }






}
