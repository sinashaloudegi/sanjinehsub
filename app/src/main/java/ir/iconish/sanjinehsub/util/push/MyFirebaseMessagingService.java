package ir.iconish.sanjinehsub.util.push;

import androidx.annotation.NonNull;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

  @Override
  public void onNewToken(String s) {
    super.onNewToken(s);
  }


  @Override
  public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
    //Log.e("firebase msg",remoteMessage.getNotification().getBody());

    String title = remoteMessage.getNotification().getTitle();
   String message = remoteMessage.getNotification().getBody();


   // Notification.sendFirebaseNotification("اعلان سنجینه", message, context);
    Notification.sendFirebaseNotification(title, message, getApplicationContext());


  }

}









