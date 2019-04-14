package ir.iconish.sanjinehsub.ui.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

import javax.inject.Inject;

import ir.iconish.sanjinehsub.R;
import ir.iconish.sanjinehsub.bazaar.UpdateCheck;
import ir.iconish.sanjinehsub.config.AppController;
import ir.iconish.sanjinehsub.data.vm.SplashViewModel;
import ir.iconish.sanjinehsub.ui.ActivityNavigationHelper;
import ir.iconish.sanjinehsub.ui.DialogHelper;
import ir.iconish.sanjinehsub.ui.Dialoglistener;
import ir.iconish.sanjinehsub.util.InternetAccess;

import static ir.iconish.sanjinehsub.util.AppConstants.PACKAGE_NAME;

public class SplashActivity extends AppCompatActivity implements Dialoglistener {
@Inject
SplashViewModel splashViewModel;
    BroadcastReceiver broadcastReceiver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

        StrictMode.setThreadPolicy(policy);
      ((AppController) getApplication()).getAppComponent().inject(this);


        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
if(InternetAccess.isInternetAvailable()){

                messageReciver();


                new UpdateCheck(getApplicationContext()).initService();
            }
            else {
    ActivityNavigationHelper.navigateToActivity(SplashActivity.this,NoInternetActivity.class,true);
}
            }


        }, 2000);

    }








    @Override
    public void onDestroy() {
        super.onDestroy();

      try {
          unregisterReceiver(broadcastReceiver);
      }
      catch (Exception e){

      }

    }
    private void messageReciver(){
        IntentFilter filter = new IntentFilter();

        filter.addAction("versionCode");






        broadcastReceiver= new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                String action=intent.getAction();

                switch (action) {


                    case "versionCode":
                        long cafebazarVersionCode = intent.getLongExtra("versionCode",-1);
                        chekheckRequired(cafebazarVersionCode);

                        break;

                }


            }
        };
        registerReceiver(broadcastReceiver, filter);
    }
    private long getVersionCode() {
        long appVersion =-1;
        try {
            PackageInfo pInfo = this.getPackageManager().getPackageInfo(getPackageName(), 0);


            if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                appVersion =  pInfo.getLongVersionCode(); // avoid huge version numbers and you will be ok
            } else {
                //noinspection deprecation
                appVersion = pInfo.versionCode;
            }









        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        return appVersion;
    }

    private  void chekheckRequired(long cafebazarVesionCode){
        long appVersion=getVersionCode();
        if(cafebazarVesionCode>appVersion){
            DialogHelper.showDialog(getString(R.string.new_version),getString(R.string.download_new_version),getString(R.string.download),getString(R.string.ignore),this,this);
        }
        else {
            navigateToApp();
        }
    }

    private void goToCafebazarPage(){
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("bazaar://details?id=" + PACKAGE_NAME));
        intent.setPackage("com.farsitel.bazaar");
        startActivity(intent);
        finish();
    }

    @Override
    public void onDialogSubmitEvent(Object object) {
        goToCafebazarPage();
    }

    @Override
    public void onDialogCancelEvent(Object object) {
    navigateToApp();
    }
    private void navigateToApp(){
        if(splashViewModel.getUserId()==-1){
            startActivity(new Intent(SplashActivity.this, LoginActivity.class));}
        else {
            Log.e("splashViewModel.getUserId()",splashViewModel.getUserId()+"");
            startActivity(new Intent(SplashActivity.this, MainActivity.class));
        }
        finish();
    }
}
