package ir.iconish.sanjinehsub.bazaar;

import android.app.Application;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.util.Log;

import com.farsitel.bazaar.ILoginCheckService;

import javax.inject.Inject;

import ir.iconish.sanjinehsub.config.AppController;
import ir.iconish.sanjinehsub.data.source.local.SharedPreferencesManager;


public class CheckCafeBazaarLogin {


  ILoginCheckService service;

  LoginCheckServiceConnection connection;

  SharedPreferences pref = null ;
  SharedPreferences.Editor editor = null ;

  private static final String TAG = "LoginCheck";

Context context;
  public CheckCafeBazaarLogin(Context context){

this.context=context;

  }





  public void initService() {
    connection = new LoginCheckServiceConnection();
    Intent i = new Intent("com.farsitel.bazaar.service.LoginCheckService.BIND");
    i.setPackage("com.farsitel.bazaar");
    boolean ret = context.bindService(i, connection, Context.BIND_AUTO_CREATE);
  }


  public void releaseService() {
    context.unbindService(connection);
    connection = null;
  }


  public class LoginCheckServiceConnection implements ServiceConnection {

    private static final String TAG = "LoginCheck";

    public void onServiceConnected(ComponentName name, IBinder boundService) {
      service = ILoginCheckService.Stub.asInterface((IBinder) boundService);

      try {
        boolean isLoggedIn = service.isLoggedIn();
        Log.i("Test","isLoggedIn" + isLoggedIn);
        Log.e("Test","isLoggedIn" + isLoggedIn);


      } catch (Exception e) {
        e.printStackTrace();
      }
      Log.e("Test", "onServiceConnected(): Connected");
    }

    public void onServiceDisconnected(ComponentName name) {
      service = null;
      Log.e("Test", "onServiceDisconnected(): Disconnected");
    }
  }
}
