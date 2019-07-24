package ir.iconish.sanjinehsub.bazaar;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.os.IBinder;

import androidx.annotation.Nullable;

import com.farsitel.bazaar.ILoginCheckService;


public class CheckCafeBazaarLogin {


    @Nullable
    ILoginCheckService service;

    @Nullable
    LoginCheckServiceConnection connection;

    private static final String TAG = "LoginCheck";
    @Nullable
    SharedPreferences pref = null;
    @Nullable
    SharedPreferences.Editor editor = null;
    Context context;

    public CheckCafeBazaarLogin(Context context) {

        this.context = context;

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

    private void broadCastCheckBazaarLogin(boolean isLogin) {

        Intent intent = new Intent("checkbazaarlogin");
        intent.putExtra("checkbazaarlogin", isLogin);
        context.sendBroadcast(intent);

    }

    public class LoginCheckServiceConnection implements ServiceConnection {

        private static final String TAG = "LoginCheck";

        @Override
        public void onServiceConnected(ComponentName name, IBinder boundService) {
            service = ILoginCheckService.Stub.asInterface(boundService);

            try {
                boolean isLoggedIn = service.isLoggedIn();
                //Log.e("Test","isLoggedIn" + isLoggedIn);

                broadCastCheckBazaarLogin(isLoggedIn);


            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            service = null;
            //Log.e("Test", "onServiceDisconnected(): Disconnected");
        }
    }

}
