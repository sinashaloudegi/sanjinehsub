package ir.iconish.sanjinehsub.ui.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.google.firebase.FirebaseApp;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.messaging.FirebaseMessaging;

import net.jhoobin.jhub.CharkhoneSdkApp;

import java.util.Timer;
import java.util.TimerTask;

import javax.inject.Inject;

import ir.iconish.sanjinehsub.R;
import ir.iconish.sanjinehsub.bazaar.UpdateCheck;
import ir.iconish.sanjinehsub.config.AppController;
import ir.iconish.sanjinehsub.data.vm.AppConfigViewModel;
import ir.iconish.sanjinehsub.data.vm.GetScoreCharkhooneViewModel;
import ir.iconish.sanjinehsub.ui.ActivityNavigationHelper;
import ir.iconish.sanjinehsub.ui.DialogHelper;
import ir.iconish.sanjinehsub.ui.Dialoglistener;
import ir.iconish.sanjinehsub.util.AppConstants;
import ir.iconish.sanjinehsub.util.InternetAccess;

import static ir.iconish.sanjinehsub.util.AppConstants.PACKAGE_NAME;
import static ir.iconish.sanjinehsub.util.AppConstants.STORE;


public class SplashActivity extends AppCompatActivity implements Dialoglistener {


    private static final String TAG = "SplashActivity";


    @Inject
    AppConfigViewModel appConfigViewModel;

    BroadcastReceiver broadcastReceiver;
    UpdateCheck updateCheck;
    @Inject
    GetScoreCharkhooneViewModel getScoreCharkhooneViewModel;
    private FirebaseAnalytics mFirebaseAnalytics;

    public String[] getSecrets() {
        return getResources().getStringArray(R.array.secrets);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        if ("CHARKHOONE".equals(STORE)) {
            CharkhoneSdkApp.initSdk(this, getSecrets());

        }


        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        initFirebase();

        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        StrictMode.setThreadPolicy(policy);
        ((AppController) getApplication()).getAppComponent().inject(this);

        attachViewModel();

        appConfigViewModel.callAppConfigViewModel();

      /*  getScoreCharkhooneViewModel.callGetScoreCharkhooneViewModel(null, null, null);
        getScoreViewModelObserver();*/
    }

    private void getScoreViewModelObserver() {
        getScoreCharkhooneViewModel.getApiSuccessLiveDataResponse().observe(this, creditScorePreProcess -> {
                    Log.d(TAG, "getApiSuccessLiveDataResponse: 2");

                }
        );

        getScoreCharkhooneViewModel.getApiAuthFailureErrorLiveData().observe(this, volleyError -> {

            Log.d(TAG, "getScoreCharkhooneViewModelObserver: 2");


        });
        getScoreCharkhooneViewModel.getApiErrorLiveData().observe(this, volleyError -> {

            Log.d(TAG, "getScoreCharkhooneViewModelObserver: 3");

            goToFailApiPage("ApiError");
        });
        getScoreCharkhooneViewModel.getApiServerErrorLiveData().observe(this, volleyError ->
        {
            Log.d(TAG, "getScoreCharkhooneViewModelObserver: 4");

            //goToFailApiPage("ServerError");
        });
        getScoreCharkhooneViewModel.getApiTimeOutErrorLiveData().observe(this, volleyError ->
                {
                    Log.d(TAG, "getScoreCharkhooneViewModelObserver: 5");

                    goToFailApiPage("TimeOutError");
                }
        );
        getScoreCharkhooneViewModel.getApiClientNetworkErrorLiveData().observe(this, volleyError -> {
            Log.d(TAG, "getScoreCharkhooneViewModelObserver: 6");

            goToFailApiPage("ClientNetworkError");
        });

        getScoreCharkhooneViewModel.getApiForbiden403ErrorLiveData().observe(this, volleyError -> {
            Log.d(TAG, "getScoreCharkhooneViewModelObserver: 7");

        });
        getScoreCharkhooneViewModel.getApiValidation422ErrorLiveData().observe(this, volleyError -> {
            Log.d(TAG, "getScoreViewModelObserver: 8");

        });
    }


    private void attachViewModel() {
        Log.d(TAG, "attachViewModel: ");
        appConfigViewModel.getApiSuccessLiveDataResponse().observe(this, appConfig -> startTimer());

        appConfigViewModel.getApiAuthFailureErrorLiveData().observe(this, volleyError -> {
        });

        appConfigViewModel.getApiErrorLiveData().observe(this, volleyError -> goToFailApiPage("ApiError"));
        appConfigViewModel.getApiServerErrorLiveData().observe(this, volleyError -> goToFailApiPage("ServerError"));
        appConfigViewModel.getApiTimeOutErrorLiveData().observe(this, volleyError -> goToFailApiPage("TimeOutError"));
        appConfigViewModel.getApiClientNetworkErrorLiveData().observe(this, volleyError ->
                {
                    Log.d(TAG, "ClientNetworkError: " + "appConfigViewModel");
                    goToFailApiPage("ClientNetworkError");

                }
        );
        appConfigViewModel.getApiForbiden403ErrorLiveData().observe(this, volleyError -> {
        });
        appConfigViewModel.getApiValidation422ErrorLiveData().observe(this, volleyError -> {
        });

    }

    private void startTimer() {
        Log.d(TAG, "startTimer: ");
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                if (InternetAccess.isInternetAvailable()) {
                    if (AppConstants.STORE.equals("CAFEBAZAAR")) {
                        messageReciver();
                        updateCheck = new UpdateCheck(getApplicationContext());
                        updateCheck.initService();
                    } else if (AppConstants.STORE.equals("CHARKHOONE")) {
                        //TODO: 11/12/2019 check if user has charkhoone and singed up in charkhoone
                        navigateToApp();
                    }

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
/*
    public void runIntro() {
        appConfigViewModel.setFirstRun(true);
        Log.i("Test", " splash runIntro ");
        FrgIntro frg6 = new FrgIntro();
        FragmentManager fm6 = getFragmentManager();
        if (fm6 != null) {
            FragmentTransaction ft6 = fm6.beginTransaction();
            ft6.replace(R.id.frg_holder, frg6);
            if (ft6 != null) {
                ((MainActivity) getActivity()).commitFragment(ft6);
            }
        }


    }*/

    private void navigateToApp() {
        Log.d(TAG, "navigateToApp: ");
        // TODO: 11/12/2019 for charkhoone there are 3 possibilites, 1- goto intro 2-goto mainActivity 3-goto subscribePage
        if (AppConstants.STORE.equals("CHARKHOONE")) {
            Log.d(TAG, "navigateToApp: charkhoone");


            if (null == appConfigViewModel.getUserPassword()) {


                if (appConfigViewModel.isFirstRun()) {
                    Toast.makeText(this, "IS FIRST RUN", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "IS NOT FIRST RUN", Toast.LENGTH_SHORT).show();

                    startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                }

            } else {
                startActivity(new Intent(SplashActivity.this, MainActivity.class));

            }

        } else if (AppConstants.STORE.equals("CAFEBAZAAR")) {

            if (null == appConfigViewModel.getUserPassword()) {
                startActivity(new Intent(SplashActivity.this, LoginActivity.class));

            } else {
                startActivity(new Intent(SplashActivity.this, MainActivity.class));

            }


        }
        finish();
    }


    private void goToFailApiPage(String failCause) {
        Log.d(TAG, "goToFailApiPage: failCause" + failCause);
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
