package ir.iconish.sanjinehsub.bazaar;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.farsitel.bazaar.IUpdateCheckService;

import javax.inject.Inject;


import ir.iconish.sanjinehsub.config.AppController;

public class CheckCafeBazaarUpdate  {
Context context;
  public CheckCafeBazaarUpdate(Context context) {

    this.context=context;
  }

  IUpdateCheckService service;
  UpdateServiceConnection connection;
  private static final String TAG = "UpdateCheck";
  @Inject
  AppController appController;
  class UpdateServiceConnection implements ServiceConnection {
    public void onServiceConnected(ComponentName name, IBinder boundService) {
      service = IUpdateCheckService.Stub
        .asInterface((IBinder) boundService);
      try {
        long vCode = service.getVersionCode("your.app.packagename");
        Toast.makeText(appController, "Version Code:" + vCode,
          Toast.LENGTH_LONG).show();
      } catch (Exception e) {
        e.printStackTrace();
      }
      Log.d(TAG, "onServiceConnected(): Connected");
    }

    public void onServiceDisconnected(ComponentName name) {
      service = null;
      Log.d(TAG, "onServiceDisconnected(): Disconnected");
    }
  }



  private void initService() {
    Log.i(TAG, "initService()");
    connection = new UpdateServiceConnection();
    Intent i = new Intent(
      "com.farsitel.bazaar.service.UpdateCheckService.BIND");
    i.setPackage("com.farsitel.bazaar");
    boolean ret = context.bindService(i, connection, Context.BIND_AUTO_CREATE);
    Log.d(TAG, "initService() bound value: " + ret);
  }

  private void releaseService() {
    context.unbindService(connection);
    connection = null;
    Log.d(TAG, "releaseService(): unbound.");
  }

}
