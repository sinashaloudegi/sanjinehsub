package ir.iconish.sanjinehsub.ui.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.google.firebase.FirebaseApp;
import com.google.firebase.analytics.FirebaseAnalytics;
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


    private static final String TAG = "SplashActivity";

    @Inject
    AppConfigViewModel appConfigViewModel;

    BroadcastReceiver broadcastReceiver;
    UpdateCheck updateCheck;

    private FirebaseAnalytics mFirebaseAnalytics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        initFirebase();

/*

        String reqToken = "eyJhbGciOiJIUzI1NiJ9   .eyJzdWIiOiIxOTExMDUwODA2NDEzMjI1IiwiaWNvbkNyZWRpdCI6IjE5MTEwNTA4MDY0MTMyMjUiLCJpYXQiOjE1NTc1NDU4MDF9.DEFEycDW90S9J5ZobgPGAnVZ-NR_A3FYoO7obsLziNw";
        goToNativeReportActivity(reqToken);*/


        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        StrictMode.setThreadPolicy(policy);
        ((AppController) getApplication()).getAppComponent().inject(this);

        attachViewModel();
        appConfigViewModel.callAppConfigViewModel();


    }

    private void startTimer() {
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                if (InternetAccess.isInternetAvailable()) {
                    messageReciver();
                    updateCheck = new UpdateCheck(getApplicationContext());
                    updateCheck.initService();
                } else {
                    ActivityNavigationHelper.navigateToActivity(SplashActivity.this, NoInternetActivity.class, true);
                }
            }


        }, 1000);
    }


    private void messageReciver() {

        IntentFilter filter = new IntentFilter();

        filter.addAction("versionCode");

        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, @NonNull Intent intent) {

                String action = intent.getAction();

                if ("versionCode".equals(action)) {
                    long cafebazarVersionCode = intent.getLongExtra("versionCode", -1);
                    checkRequired(cafebazarVersionCode);
                }


            }
        };
        registerReceiver(broadcastReceiver, filter);
    }

    private void checkRequired(long cafebazarVesionCode) {

        if (cafebazarVesionCode > -1) {
            DialogHelper.showDialog(getString(R.string.new_version), getString(R.string.download_new_version), getString(R.string.download), getString(R.string.ignore), this, this);
        } else {

            navigateToApp();
        }
    }

    private void goToCafebazarPage() {
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


    private void navigateToApp() {
        if (null == appConfigViewModel.getUserPassword()) {
            startActivity(new Intent(SplashActivity.this, LoginActivity.class));

        } else {
            startActivity(new Intent(SplashActivity.this, MainActivity.class));

        }
        finish();
    }


    private void attachViewModel() {
        appConfigViewModel.getApiSuccessLiveDataResponse().observe(this, appConfig -> startTimer());

        appConfigViewModel.getApiAuthFailureErrorLiveData().observe(this, volleyError -> {
        });

        appConfigViewModel.getApiErrorLiveData().observe(this, volleyError -> goToFailApiPage("ApiError"));
        appConfigViewModel.getApiServerErrorLiveData().observe(this, volleyError -> goToFailApiPage("ServerError"));
        appConfigViewModel.getApiTimeOutErrorLiveData().observe(this, volleyError -> goToFailApiPage("TimeOutError"));
        appConfigViewModel.getApiClientNetworkErrorLiveData().observe(this, volleyError -> goToFailApiPage("ClientNetworkError"));
        appConfigViewModel.getApiForbiden403ErrorLiveData().observe(this, volleyError -> {
        });
        appConfigViewModel.getApiValidation422ErrorLiveData().observe(this, volleyError -> {
        });

    }

    private void goToFailApiPage(String failCause) {

        Intent intent = new Intent(this, FailApiActivity.class);
        intent.putExtra("failCause", failCause);
        startActivity(intent);
        finish();

    }

    private void initFirebase() {
        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "String");
        bundle.putString(FirebaseAnalytics.Param.ITEM_ID, "TESTid");
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);

        FirebaseApp.initializeApp(this);

        FirebaseMessaging.getInstance().subscribeToTopic(AppConstants.TOPIC)
                .addOnCompleteListener(task -> {

                });


    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        try {
            updateCheck.releaseService();
            unregisterReceiver(broadcastReceiver);
        } catch (Exception ignored) {

        }

    }


}
