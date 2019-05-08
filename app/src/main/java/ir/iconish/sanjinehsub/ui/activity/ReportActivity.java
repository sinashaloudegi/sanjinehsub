package ir.iconish.sanjinehsub.ui.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.Timer;
import java.util.TimerTask;

import javax.inject.Inject;

import ir.iconish.sanjinehsub.R;
import ir.iconish.sanjinehsub.bazaar.UpdateCheck;
import ir.iconish.sanjinehsub.config.AppController;
import ir.iconish.sanjinehsub.data.vm.AppConfigViewModel;

import ir.iconish.sanjinehsub.ui.ActivityNavigationHelper;
import ir.iconish.sanjinehsub.ui.DialogHelper;
import ir.iconish.sanjinehsub.ui.Dialoglistener;
import ir.iconish.sanjinehsub.util.AppConstants;
import ir.iconish.sanjinehsub.util.InternetAccess;

import static ir.iconish.sanjinehsub.util.AppConstants.PACKAGE_NAME;

public class SplashActivity extends AppCompatActivity implements Dialoglistener {
@Inject
AppConfigViewModel appConfigViewModel;



    BroadcastReceiver broadcastReceiver;
    UpdateCheck updateCheck;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        initFirebase();





        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);

        StrictMode.setThreadPolicy(policy);
      ((AppController) getApplication()).getAppComponent().inject(this);
attachViewModel();


appConfigViewModel.callAppConfigViewModel();




    }


    private void startTimer(){
        new Timer().schedule(new TimerTask() {
        @Override
        public void run() {
            if(InternetAccess.isInternetAvailable()){

                messageReciver();


                updateCheck=   new UpdateCheck(getApplicationContext());
                updateCheck.initService();
            }
            else {
                ActivityNavigationHelper.navigateToActivity(SplashActivity.this,NoInternetActivity.class,true);
            }
        }


    }, 1000);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();

      try {
          updateCheck.releaseService();
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

    private  void chekheckRequired(long cafebazarVesionCode){

        if(cafebazarVesionCode>-1){
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
    public void onDialogCancelEvent(Object object)
    {

    navigateToApp();
    }


    private void navigateToApp(){
        if(null==appConfigViewModel.getUserPassword()){
            startActivity(new Intent(SplashActivity.this, LoginActivity.class));

        }
        else {
           // appConfigViewModel.callAppConfigViewModel(0);
            startActivity(new Intent(SplashActivity.this, MainActivity.class));

        }
        finish();
    }


    private void attachViewModel() {
        appConfigViewModel.getApiSuccessLiveDataResponse().observe(this, appConfig -> {
                    startTimer();
/*                   startActivity(new Intent(SplashActivity.this, MainActivity.class));
finish();*/

//if 1010 go to enter pass -- if 1011 go to otp

                }
        );

        appConfigViewModel.getApiAuthFailureErrorLiveData().observe(this, volleyError -> {});

        appConfigViewModel.getApiErrorLiveData().observe(this, volleyError ->{
            goToFailApiPage("ApiError");

        });
        appConfigViewModel.getApiServerErrorLiveData().observe(this, volleyError ->

        {
            goToFailApiPage("ServerError");

        });
        appConfigViewModel.getApiTimeOutErrorLiveData().observe(this, volleyError ->
                {
                    goToFailApiPage("TimeOutError");
                }

        );
        appConfigViewModel.getApiClientNetworkErrorLiveData().observe(this, volleyError -> {
            goToFailApiPage("ClientNetworkError");


        });


        appConfigViewModel.getApiForbiden403ErrorLiveData().observe(this, volleyError ->{} );
        appConfigViewModel.getApiValidation422ErrorLiveData().observe(this, volleyError ->{} );

    }
    private void goToFailApiPage(String failCause){

        Intent intent=new Intent(this,FailApiActivity.class);
        intent.putExtra("failCause",failCause);
        startActivity(intent);
        finish();

    }

    private void initFirebase(){


        FirebaseApp.initializeApp(this);

        FirebaseMessaging.getInstance().subscribeToTopic(AppConstants.TOPIC)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {


                    }
                });



    }
}
