package ir.iconish.sanjinehsub.bazaar;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

import com.farsitel.bazaar.IUpdateCheckService;


public class UpdateCheck {


    @Nullable
    IUpdateCheckService service;
    @Nullable
    UpdateServiceConnection connection;
    private static final String TAG = "UpdateCheck";
    Context context;

    public UpdateCheck(Context context) {
        this.context = context;
    }

    private boolean isPackageInstalled(Context context) {
        PackageManager pm = context.getPackageManager();
        try {
            pm.getPackageInfo("com.farsitel.bazaar", PackageManager.GET_ACTIVITIES);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;

        }
    }

    public void initService() {

        if (isPackageInstalled(context)) {
            Log.i(TAG, "initService()");
            connection = new UpdateServiceConnection();
            Intent i = new Intent(
                    "com.farsitel.bazaar.service.UpdateCheckService.BIND");
            i.setPackage("com.farsitel.bazaar");
            boolean ret = context.bindService(i, connection, Context.BIND_AUTO_CREATE);
            Log.d(TAG, "initService() bound value: " + ret);
        } else {
            //if cafeBazaar app is not installed, return -1 as version code
            broadCastVersion(-1);
        }
    }

    /**
     * This is our function to un-binds this activity from our service.
     */
    public void releaseService() {
        context.unbindService(connection);
        connection = null;
        Log.d(TAG, "releaseService(): unbound.");
    }

    private void broadCastVersion(long versionCode) {

        Intent in = new Intent("versionCode");
        in.putExtra("versionCode", versionCode);
        context.sendBroadcast(in);

    }

    class UpdateServiceConnection implements ServiceConnection {
        @Override
        public void onServiceConnected(ComponentName name, IBinder boundService) {
            service = IUpdateCheckService.Stub
                    .asInterface(boundService);
            try {
                long vCode = service.getVersionCode("ir.iconish.sanjinehsub");
             /*   Toast.makeText(context, "Version Code:" + vCode,
                        Toast.LENGTH_LONG).show();*/

                broadCastVersion(vCode);
            } catch (Exception e) {
                e.printStackTrace();
            }
            Log.d(TAG, "onServiceConnected(): Connected");
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            service = null;
            Log.d(TAG, "onServiceDisconnected(): Disconnected");
        }
    }


}
