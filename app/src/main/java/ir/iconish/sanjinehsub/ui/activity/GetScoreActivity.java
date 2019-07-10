package ir.iconish.sanjinehsub.ui.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.adpdigital.push.AdpPushClient;
import com.crashlytics.android.Crashlytics;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.UUID;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ir.iconish.sanjinehsub.R;
import ir.iconish.sanjinehsub.bazaar.CheckCafeBazaarLogin;
import ir.iconish.sanjinehsub.config.AppController;
import ir.iconish.sanjinehsub.data.model.NumberOfSanjineh;
import ir.iconish.sanjinehsub.data.vm.GetScoreViewModel;
import ir.iconish.sanjinehsub.data.vm.UserNumberOfSanjinehViewModel;
import ir.iconish.sanjinehsub.ui.ActivityNavigationHelper;
import ir.iconish.sanjinehsub.ui.DialogHelper;
import ir.iconish.sanjinehsub.ui.Dialoglistener;
import ir.iconish.sanjinehsub.util.AppConstants;
import ir.iconish.sanjinehsub.util.ButtonHelper;
import ir.iconish.sanjinehsub.util.CreditStatusManager;
import ir.iconish.sanjinehsub.util.IabHelper;
import ir.iconish.sanjinehsub.util.IabResult;
import ir.iconish.sanjinehsub.util.Inventory;
import ir.iconish.sanjinehsub.util.Purchase;
import ir.iconish.sanjinehsub.util.ToastHelper;

public class GetScoreActivity extends AppCompatActivity implements Dialoglistener {

    private static final String TAG = "_SCORE";
    @Nullable
    public static IabHelper mHelper;
    @Nullable
    @BindView(R.id.btnGetScore)
    AppCompatButton btnGetScore;
    @Nullable
    @BindView(R.id.prgGetScore)
    ProgressBar prgGetScore;
    @Nullable
    @BindView(R.id.imgBack)
    ImageView imgBack;
    @Nullable
    @BindView(R.id.txtAlert)
    TextView txtAlert;
    @Nullable
    @BindView(R.id.checkboxRules)
    CheckBox checkboxRules;
    BroadcastReceiver broadcastReceiver;

    @Inject
    UserNumberOfSanjinehViewModel getUserHasSanjinehViewModel;
    NumberOfSanjineh numberOfSanjineh;


    @Inject
    GetScoreViewModel getScoreViewModel;

    CheckCafeBazaarLogin checkCafeBazaarLogin;
    @NonNull
    IabHelper.OnConsumeFinishedListener mConsumeFinishedListener = new IabHelper.OnConsumeFinishedListener() {
        @Override
        public void onConsumeFinished(Purchase purchase, @NonNull IabResult result) {
            Log.i(TAG, "Consumption finished. Purchase: " + purchase + ", result: " + result);
            if (mHelper == null) {
                return;
            }
            if (result.isSuccess()) {
                //Log.e("Test", "Consumption successful. Provisioning.");
            } else {
                //Log.e("Test", "Error while consuming: " + result);
            }
            //Log.e("Test",  "End consumption flow.");
        }
    };
    @NonNull
    public IabHelper.QueryInventoryFinishedListener mGotInventoryListener = new IabHelper.QueryInventoryFinishedListener() {
        @Override
        public void onQueryInventoryFinished(@NonNull IabResult result, @Nullable Inventory inventory) {
            //Log.e("Test", "Query inventory finished.");
            if (mHelper == null) {
                return;
            }

            if (result.isFailure()) {
                Log.i("Test", "Failed to query invenreturn;tory: " + result);

            }

            if (inventory != null) {
                Purchase gasPurchase = inventory.getPurchase(AppConstants.BAZAAR_SKU);
                if (gasPurchase != null && verifyDeveloperPayload(gasPurchase)) {
                    //Log.e("Test", "We have sanj01. Consuming it.");
                    mHelper.consumeAsync(inventory.getPurchase(AppConstants.BAZAAR_SKU), mConsumeFinishedListener);
                    return;
                }
            }


            Log.i("Test", "Initial inventory query finished; enabling main UI.");
        }
    };
    @NonNull
    IabHelper.OnIabPurchaseFinishedListener mPurchaseFinishedListener = new IabHelper.OnIabPurchaseFinishedListener() {
        @Override
        public void onIabPurchaseFinished(@NonNull IabResult result, @NonNull Purchase purchase) {
            Log.i(TAG, "Purchase finished: " + result + ", purchase: " + purchase);


            if (mHelper == null) {
                return;
            }

            if (result.isFailure()) {
                return;
            }
            if (!verifyDeveloperPayload(purchase)) {
                Log.i("Test", "Error purchasing. Authenticity verification failed.");
                return;
            }
            Log.i("Test", "Purchase successful.");
            showWating();

            Log.d(TAG, "onIabPurchaseFinished: now we are going to call callGetScoreViewModel and purchase token is" + purchase.getToken());

            JSONObject data = new JSONObject();
            try {
                data.put("bazaarToken", purchase.getToken());
            } catch (JSONException e) {
                e.printStackTrace();
            }

            AdpPushClient.get().track("add-to-card", data);


            Crashlytics.setString("purchaseFinished", "finished!");
            Crashlytics.setString("purchaseToken", purchase.getToken());
            getScoreViewModel.callGetScoreViewModel(null, null, 1, 1, AppConstants.PAYMENT_TYPE, AppConstants.CHANNEL_ID, -1, purchase);

            if (purchase.getSku().equals("sanj01")) {
                //Log.e("Test", "Purchase is gas. Starting sanj02 consumption.");
                mHelper.consumeAsync(purchase, mConsumeFinishedListener);
            }
        }
    };
    private boolean alreadyBazaarInited = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_score);

        ButterKnife.bind(this);
        ((AppController) getApplication()).getAppComponent().inject(this);
        checkCafeBazaarLogin = new CheckCafeBazaarLogin(GetScoreActivity.this);
        messageReciver();
        showWating();
        bazaarSetup(getScoreViewModel.getMarketKey());
        attachViewModel();
        Log.d(TAG, "onCreate: GetScoreActivity");
        getUserHasSanjinehViewModel.callGetUserSanjinehViewModel();
    }

    @OnClick(R.id.btnGetScore)
    public void btnGetScoreAction() {
        Log.d(TAG, "btnGetScoreAction: ");
        if (!checkboxRules.isChecked()) {


            txtAlert.setText(getString(R.string.accept_nt_code_mobile));
            txtAlert.setVisibility(View.VISIBLE);
            return;

        }


        if (!alreadyBazaarInited) {
            checkCafeBazaarLogin.initService();
        } else {

            //"bGoa+V7g/yqDXvKRqq+JTFn4uQZbPiQJo4pf9RzJ"
            Log.d(TAG, "btnGetScoreAction: Starting launchPurchaseFlow");

            choosePaymentType();


        }
    }

    private void choosePaymentType() {

        if (userHasSanjineh()) {
            int sanjinehValue = (this.numberOfSanjineh.getBalance()) / (this.numberOfSanjineh.getUnitValue());
            String toastMesasge = " شما دارای " + sanjinehValue + " سنجینه در کیف پول خود هستید ";
            Toast.makeText(this, toastMesasge, Toast.LENGTH_LONG).show();
            DialogHelper.showDialog("نحوه پرداخت", "پرداخت از کیف پول یا کافه بازار؟", "کیف پول", "کافه بازار", this, this);

        } else {
            startPurchase();
        }

    }

    private void startPurchase() {
        UUID uuid = UUID.randomUUID();
        String randomUUIDString = uuid.toString();
        if (mHelper != null) {
            Crashlytics.setString("flagEndAsync", "mHelper.flagEndAsync();");
            mHelper.flagEndAsync();
        }
        mHelper.launchPurchaseFlow(GetScoreActivity.this, AppConstants.BAZAAR_SKU, 10001, mPurchaseFinishedListener, randomUUIDString);
    }

    @OnClick(R.id.imgBack)
    public void imgBackAction() {
        onBackPressed();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private void bazaarSetup(String bazaarKey) {
        String base64EncodedPublicKey = bazaarKey;
        // You can find it in your Bazaar console, in the Dealers section.
        // It is recommended to add more security than just pasting it in your source code;
        mHelper = new IabHelper(GetScoreActivity.this, base64EncodedPublicKey);

        Log.i("Test", "Starting setup.");

        mHelper.startSetup(result -> {
            Log.i("Test", "Setup finished.");

            if (!result.isSuccess()) {
                ToastHelper.showErrorMessage(this, getString(R.string.bazar_setup_fail));
                txtAlert.setText(getString(R.string.bazar_setup_fail));
                txtAlert.setVisibility(View.VISIBLE);
                // Oh noes, there was a problem.
                Log.i("Test", "Problem setting up In-app Billing: " + result);
            } else {
                btnGetScore.setVisibility(View.VISIBLE);
                prgGetScore.setVisibility(View.INVISIBLE);
            }
            // Hooray, IAB is fully set up!
            mHelper.queryInventoryAsync(mGotInventoryListener);
        });
    }

    private void attachViewModel() {

        getUserHasSanjinehViewModel.getApiSuccessLiveDataResponse().observe(this, numberOfSanjineh -> {

                    stopWating();
                    this.numberOfSanjineh = numberOfSanjineh;
                    Crashlytics.setString("NumberOfSanjineh", numberOfSanjineh.toString());
                    Log.d(TAG, "this.numberOfSanjineh: " + numberOfSanjineh);
                }
        );

        getUserHasSanjinehViewModel.getApiAuthFailureErrorLiveData().observe(this, volleyError -> {
        });
        getUserHasSanjinehViewModel.getApiErrorLiveData().observe(this, volleyError -> {
            goToFailApiPage("ApiError");
        });
        getUserHasSanjinehViewModel.getApiServerErrorLiveData().observe(this, volleyError ->
        {
            //goToFailApiPage("ServerError");
        });
        getUserHasSanjinehViewModel.getApiTimeOutErrorLiveData().observe(this, volleyError ->
                {
                    goToFailApiPage("TimeOutError");
                }
        );
        getUserHasSanjinehViewModel.getApiClientNetworkErrorLiveData().observe(this, volleyError -> {
            goToFailApiPage("ClientNetworkError");
        });

        getUserHasSanjinehViewModel.getApiForbiden403ErrorLiveData().observe(this, volleyError -> {
        });
        getUserHasSanjinehViewModel.getApiValidation422ErrorLiveData().observe(this, volleyError -> {
        });


        getScoreViewModel.getApiSuccessLiveDataResponse().observe(this, creditScorePreProcess -> {
                    // Log.i("Test registerPurchaseInfoResultDto : " , registerPurchaseInfoResultDto.toString());
                    stopWating();

                    new CreditStatusManager(this).handleReportStatus(creditScorePreProcess, txtAlert);
                }
        );

        getScoreViewModel.getApiAuthFailureErrorLiveData().observe(this, volleyError -> {
        });
        getScoreViewModel.getApiErrorLiveData().observe(this, volleyError -> {
            goToFailApiPage("ApiError");
        });
        getScoreViewModel.getApiServerErrorLiveData().observe(this, volleyError ->
        {
            //goToFailApiPage("ServerError");
        });
        getScoreViewModel.getApiTimeOutErrorLiveData().observe(this, volleyError ->
                {
                    goToFailApiPage("TimeOutError");
                }
        );
        getScoreViewModel.getApiClientNetworkErrorLiveData().observe(this, volleyError -> {
            goToFailApiPage("ClientNetworkError");
        });

        getScoreViewModel.getApiForbiden403ErrorLiveData().observe(this, volleyError -> {
        });
        getScoreViewModel.getApiValidation422ErrorLiveData().observe(this, volleyError -> {
        });

    }

    private void goToFailApiPage(String failCause) {

        Intent intent = new Intent(this, FailApiActivity.class);
        intent.putExtra("failCause", failCause);
        startActivity(intent);
        finish();

    }

    private void showWating() {
        prgGetScore.setVisibility(View.VISIBLE);
        ButtonHelper.toggleAppCompatButtonStatus(btnGetScore, false);
    }

    private void stopWating() {
        prgGetScore.setVisibility(View.INVISIBLE);
        ButtonHelper.toggleAppCompatButtonStatus(btnGetScore, true);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.i("Test", "onActivityResult(" + requestCode + "," + resultCode + "," + data);
        if (mHelper == null) {
            return;
        }

        // Pass on the activity result to the helper for handling
        if (!mHelper.handleActivityResult(requestCode, resultCode, data)) {
            super.onActivityResult(requestCode, resultCode, data);
        } else {
            Log.i("Test", "onActivityResult handled by IABUtil.");
        }
    }

    private void messageReciver() {
        IntentFilter filter = new IntentFilter();

        filter.addAction("checkbazaarlogin");

        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, @NonNull Intent intent) {

                String action = intent.getAction();

                switch (action) {


                    case "checkbazaarlogin":
                        boolean cafeBazarLogin = intent.getBooleanExtra("checkbazaarlogin", false);
                        if (cafeBazarLogin) {
                            alreadyBazaarInited = true;
                            UUID uuid = UUID.randomUUID();
                            String randomUUIDString = uuid.toString();
                            Log.i("Test", "randomUUIDString : " + randomUUIDString);
                            //"bGoa+V7g/yqDXvKRqq+JTFn4uQZbPiQJo4pf9RzJ"

                            choosePaymentType();

                        } else {
                            alreadyBazaarInited = false;
                            Intent intentLogin = new Intent(Intent.ACTION_VIEW);
                            intentLogin.setData(Uri.parse("bazaar://login"));
                            intentLogin.setPackage("com.farsitel.bazaar");
                            GetScoreActivity.this.startActivity(intentLogin);
                        }

                        break;

                }


            }
        };
        registerReceiver(broadcastReceiver, filter);
    }

    @Override
    public void onDestroy() {
        if (GetScoreActivity.this != null) {
            GetScoreActivity.this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_USER);
        }
        super.onDestroy();


        if (broadcastReceiver != null) {
            unregisterReceiver(broadcastReceiver);
        }
        try {
            checkCafeBazaarLogin.releaseService();
        } catch (Exception e) {

        }

        Log.i("Test", "Destroying helper.");
        if (mHelper != null) {
            mHelper.dispose();
        }
        mHelper = null;

    }

    boolean verifyDeveloperPayload(@NonNull Purchase p) {
        String payload = p.getDeveloperPayload();
        return true;
    }

    private void getScoreAtion(String reqToken) {
        String url = "https://www.sanjineh.ir/report/" + reqToken;
        ActivityNavigationHelper.navigateToWebView(url, GetScoreActivity.this, WebViewActivity.class);
        finish();
    }

    private void goToNativeReportActivity(String reqToken) {
        Intent intent = new Intent(this, ReportActivity.class);
        intent.putExtra("reqToken", reqToken);

    }

    private boolean userHasSanjineh() {
        boolean hasSanjineh = false;
        if (numberOfSanjineh == null || this.numberOfSanjineh.getUnitValue() <= 0 || this.numberOfSanjineh.getBalance() <= 0) {
            Crashlytics.setBool("userHasSanjineh", hasSanjineh);
            return hasSanjineh;
        }
        int sanjinehValue = (this.numberOfSanjineh.getBalance()) / (this.numberOfSanjineh.getUnitValue());

        hasSanjineh = sanjinehValue >= 1;

        Crashlytics.setBool("userHasSanjineh", hasSanjineh);
        return hasSanjineh;
    }

    @Override
    public void onDialogSubmitEvent(Object object) {
        Log.d(TAG, "onDialogSubmitEvent: ");
        showWating();

        getScoreViewModel.callGetScoreViewModel(null, null, 1, 1, AppConstants.PAYMENT_TYPE, AppConstants.CHANNEL_ID, -1, null);
    }

    @Override
    public void onDialogCancelEvent(Object object) {
        Log.d(TAG, "onDialogCancelEvent: ");
        if (mHelper != null) {
            mHelper.flagEndAsync();
        }
        startPurchase();
    }
}
