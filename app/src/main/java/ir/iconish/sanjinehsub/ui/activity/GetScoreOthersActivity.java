package ir.iconish.sanjinehsub.ui.activity;

import android.app.Activity;
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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.crashlytics.android.Crashlytics;

import java.util.UUID;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ir.iconish.sanjinehsub.R;
import ir.iconish.sanjinehsub.bazaar.CheckCafeBazaarLogin;
import ir.iconish.sanjinehsub.config.AppController;
import ir.iconish.sanjinehsub.data.model.CafeBazaarPaymentTypeEnum;
import ir.iconish.sanjinehsub.data.model.CreditResponseEnum;
import ir.iconish.sanjinehsub.data.model.NumberOfSanjineh;
import ir.iconish.sanjinehsub.data.model.ReportStateEnum;
import ir.iconish.sanjinehsub.data.vm.GetScoreViewModel;
import ir.iconish.sanjinehsub.data.vm.SendVerifyCodeViewModel;
import ir.iconish.sanjinehsub.data.vm.UserNumberOfSanjinehViewModel;
import ir.iconish.sanjinehsub.ui.ActivityNavigationHelper;
import ir.iconish.sanjinehsub.ui.DialogHelper;
import ir.iconish.sanjinehsub.ui.Dialoglistener;
import ir.iconish.sanjinehsub.util.AppConstants;
import ir.iconish.sanjinehsub.util.ButtonHelper;
import ir.iconish.sanjinehsub.util.Helper;
import ir.iconish.sanjinehsub.util.IabHelper;
import ir.iconish.sanjinehsub.util.IabResult;
import ir.iconish.sanjinehsub.util.Inventory;
import ir.iconish.sanjinehsub.util.Purchase;
import ir.iconish.sanjinehsub.util.ToastHelper;

public class GetScoreOthersActivity extends AppCompatActivity implements Dialoglistener {

    private static final String TAG = "_SCORE";
    @Nullable
    public static IabHelper mHelper;
    @Nullable
    @BindView(R.id.edtNtcodeOthers)
    EditText edtNtcodeOthers;
    @Nullable
    @BindView(R.id.edtMsisdnOthers)
    EditText edtMsisdnOthers;
    @Nullable
    @BindView(R.id.btnGetScoreOthers)
    AppCompatButton btnGetScoreOthers;
    @Nullable
    @BindView(R.id.prgGetScoreOthers)
    ProgressBar prgGetScoreOthers;
    @Nullable
    @BindView(R.id.imgBack)
    ImageView imgBack;
    @Nullable
    @BindView(R.id.txtAlert)
    TextView txtAlert;
    @Inject
    GetScoreViewModel getScoreViewModel;
    @Nullable
    @BindView(R.id.checkboxRules)
    CheckBox checkboxRules;
    Purchase purchase;
    CheckCafeBazaarLogin checkCafeBazaarLogin;
    @Inject
    SendVerifyCodeViewModel sendVerifyCodeViewModel;
    BroadcastReceiver broadcastReceiver;
    NumberOfSanjineh numberOfSanjineh;
    @NonNull
    IabHelper.OnConsumeFinishedListener mConsumeFinishedListener = new IabHelper.OnConsumeFinishedListener() {
        @Override
        public void onConsumeFinished(Purchase purchase, @NonNull IabResult result) {

            if (mHelper == null) {
                return;
            }
            if (result.isSuccess()) {
                Log.e("Test", "Consumption successful. Provisioning.");
            } else {
            }
            Log.e("Test", "End consumption flow.");
        }
    };
    @NonNull
    public IabHelper.QueryInventoryFinishedListener mGotInventoryListener = new IabHelper.QueryInventoryFinishedListener() {
        @Override
        public void onQueryInventoryFinished(@NonNull IabResult result, @Nullable Inventory inventory) {
            if (mHelper == null) {
                return;
            }
            stopWating();
            if (result.isFailure()) {
                Log.e("Test", "Failed to query inventory: " + result);
                return;
            }

            if (inventory != null) {
                Purchase gasPurchase = inventory.getPurchase(AppConstants.BAZAAR_SKU);
                if (gasPurchase != null && verifyDeveloperPayload(gasPurchase)) {
                    mHelper.consumeAsync(inventory.getPurchase(AppConstants.BAZAAR_SKU), mConsumeFinishedListener);
                    return;
                }
            }

            Log.e("Test", "Initial inventory query finished; enabling main UI.");
        }
    };
    @NonNull
    IabHelper.OnIabPurchaseFinishedListener mPurchaseFinishedListener = new IabHelper.OnIabPurchaseFinishedListener() {
        @Override
        public void onIabPurchaseFinished(@NonNull IabResult result, @NonNull Purchase purchase) {
            GetScoreOthersActivity.this.purchase = purchase;
            Log.d(TAG, "onIabPurchaseFinished: Finished! ");

            if (mHelper == null) {
                return;
            }

            if (result.isFailure()) {
                return;
            }
            if (!verifyDeveloperPayload(purchase)) {
                return;
            }


            if (purchase.getSku().equals(AppConstants.BAZAAR_SKU)) {
                //Log.e("Test", "Purchase is gas. Starting sanj02 consumption.");
                mHelper.consumeAsync(purchase, mConsumeFinishedListener);
            }
            showWating();
            sendVerifyCodeViewModel.callSendVerifyCodeViewModel(CafeBazaarPaymentTypeEnum.CAFE_SDK.name(), edtNtcodeOthers.getText().toString(), edtMsisdnOthers.getText().toString());

/*1
            //getScoreViewModel.callGetScoreViewModel(purchase,edtMsisdnOthers.getText().toString(),edtNtcodeOthers.getText().toString());
            //getScoreViewModel.callGetScoreViewModel(edtMsisdnOthers.getText().toString(),edtNtcodeOthers.getText().toString(),2,1, AppConstants.PAYMENT_TYPE,AppConstants.CHANNEL_ID,purchase);
*/

        }
    };
    private boolean alreadyBazaarInited = false;
    @Inject
    UserNumberOfSanjinehViewModel getUserHasSanjinehViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_score_others);
        ButterKnife.bind(this);
        ((AppController) getApplication()).getAppComponent().inject(this);
        checkCafeBazaarLogin = new CheckCafeBazaarLogin(this);
        messageReciver();
        showWating();
        bazaarSetup(getScoreViewModel.getMarketKey());
        attachViewModel();
        getUserHasSanjinehViewModel.callGetUserSanjinehViewModel();
    }

    private void bazaarSetup(String bazaarKey) {


        mHelper = new IabHelper(this, bazaarKey);

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
                btnGetScoreOthers.setVisibility(View.VISIBLE);
                prgGetScoreOthers.setVisibility(View.INVISIBLE);
            }
            // Hooray, IAB is fully set up!
            mHelper.queryInventoryAsync(mGotInventoryListener);
        });
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

    @OnClick(R.id.btnGetScoreOthers)
    public void btnGetScoreOthersAction() {
        txtAlert.setVisibility(View.INVISIBLE);
        Log.d(TAG, "btnGetScoreOthersAction: Clicked!");

        String msisdn = edtMsisdnOthers.getText().toString().trim();
        String ntCode = edtNtcodeOthers.getText().toString().trim();
        if (!(msisdn.length() > 0) && !(ntCode.length() > 0)) {
            txtAlert.setText(getString(R.string.force_enter_phone_ntcode));
            txtAlert.setVisibility(View.VISIBLE);
            return;
        }
        if ((msisdn.length() > 0) && !(ntCode.length() > 0)) {
            txtAlert.setText(getString(R.string.force_enter_phone));
            txtAlert.setVisibility(View.VISIBLE);
            return;
        }

        if ((msisdn.trim().length() > 0) && !msisdn.startsWith("09")) {
            txtAlert.setText(getString(R.string.enter_correct_mobile_phone));
            txtAlert.setVisibility(View.VISIBLE);
            return;

        }
        if ((msisdn.length() > 0) && msisdn.length() != 11) {
            txtAlert.setText(getString(R.string.valid_phone));
            txtAlert.setVisibility(View.VISIBLE);
            return;
        }


        if (!Helper.validationNationalCode(ntCode)) {
            txtAlert.setText(getString(R.string.enter_correct_national_code));
            txtAlert.setVisibility(View.VISIBLE);
            return;
        }

        if (!checkboxRules.isChecked()) {


            txtAlert.setText(getString(R.string.accept_nt_code_mobile));
            txtAlert.setVisibility(View.VISIBLE);
            return;

        }
        // String ntcode = edtNtcodeOthers.getText().toString();
        // String ownermobile = edtMsisdnOthers.getText().toString();

        //showWating();
        // sendVerifyCodeViewModel.callSendVerifyCodeViewModel(ntcode, ownermobile);
        if (!alreadyBazaarInited) {
            checkCafeBazaarLogin.initService();
        } else {
            choosePaymentType();


        }
    }

    private boolean userHasSanjineh() {
        boolean hasSanjineh = false;
        if (this.numberOfSanjineh == null || this.numberOfSanjineh.getUnitValue() <= 0 || this.numberOfSanjineh.getBalance() <= 0) {
            Crashlytics.setBool("userHasSanjineh", hasSanjineh);
            return hasSanjineh;
        }
        int sanjinehValue = (this.numberOfSanjineh.getBalance()) / (this.numberOfSanjineh.getUnitValue());

        hasSanjineh = sanjinehValue >= 1;

        Crashlytics.setBool("userHasSanjineh", hasSanjineh);
        return hasSanjineh;
    }

    @Override
    public void onDestroy() {
        if (GetScoreOthersActivity.this != null) {
            GetScoreOthersActivity.this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_USER);
        }
        super.onDestroy();
        if (broadcastReceiver != null) {
            unregisterReceiver(broadcastReceiver);
        }

        try {
            checkCafeBazaarLogin.releaseService();
        } catch (Exception e) {

        }

        // very important:
        Log.i("Test", "Destroying helper.");
        if (mHelper != null) {
            mHelper.dispose();
            mHelper = null;
        }
    }

    @OnClick(R.id.imgBack)
    public void imgBackAction() {
        onBackPressed();
    }

    private void attachViewModel() {
        getUserHasSanjinehViewModel.getApiSuccessLiveDataResponse().observe(this, numberOfSanjineh -> {
                    this.numberOfSanjineh = numberOfSanjineh;
                    Crashlytics.setString("NumberOfSanjineh", numberOfSanjineh.toString());
                    Log.d(TAG, "this.numberOfSanjineh: " + numberOfSanjineh.toString());
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
        sendVerifyCodeViewModel.getApiSuccessLiveDataResponse().observe(this, verifyCodeOthersResponse -> {
                    Log.d(TAG, "attachViewModel:GetScoreOthersActivity getStatusCode" + verifyCodeOthersResponse.getStatusCode());
                    stopWating();

                    //verifyCodeOthersResponse.getStatusCode() == 22
                    if (verifyCodeOthersResponse.getStatusCode() == CreditResponseEnum.SUCCESS.getId()) {
                        Log.i("Test", "sent otp to others");
                        Intent intent = new Intent(this, VerifyCodeOthersActivity.class);
                        intent.putExtra("otherMobile", edtMsisdnOthers.getText().toString());
                        intent.putExtra("otherNtCode", edtNtcodeOthers.getText().toString());
                        intent.putExtra("reportStatus", verifyCodeOthersResponse.getStatusCode());
                        intent.putExtra("purchase", purchase);
                        startActivity(intent);
                        finish();
                    } else if (verifyCodeOthersResponse.getStatusCode() == ReportStateEnum.USER_NOT_HAVE_REPORT.getId()) {
                        getScoreAtion(verifyCodeOthersResponse.getNoReportReqToken(), this);


                    } else if (verifyCodeOthersResponse.getStatusCode() == CreditResponseEnum.NOT_MATCH_MOBILE.getId()) {
//                        Toast.makeText(this, verifyCodeOthersResponse.getDescription(), Toast.LENGTH_SHORT).show();
                        DialogHelper.showDialog("خطا", verifyCodeOthersResponse.getDescription(), "باشه", null, this, this);


                    } else {
                        txtAlert.setText(verifyCodeOthersResponse.getDescription());
                        txtAlert.setVisibility(View.VISIBLE);
                    }
                }
        );
        sendVerifyCodeViewModel.getApiAuthFailureErrorLiveData().observe(this, volleyError -> {
        });
        sendVerifyCodeViewModel.getApiErrorLiveData().observe(this, volleyError -> {
            goToFailApiPage("ApiError");
        });
        sendVerifyCodeViewModel.getApiServerErrorLiveData().observe(this, volleyError ->
        {
            goToFailApiPage("ServerError");
        });
        sendVerifyCodeViewModel.getApiTimeOutErrorLiveData().observe(this, volleyError ->
                {
                    Log.d(TAG, "attachViewModel: VolleyTimeOutError:" + volleyError);
                    goToFailApiPage("TimeOutError");
                }
        );
        sendVerifyCodeViewModel.getApiClientNetworkErrorLiveData().observe(this, volleyError -> {
            goToFailApiPage("ClientNetworkError");
        });

        sendVerifyCodeViewModel.getApiForbiden403ErrorLiveData().observe(this, volleyError -> {
        });
        sendVerifyCodeViewModel.getApiValidation422ErrorLiveData().observe(this, volleyError -> {
        });







/*



    getScoreViewModel.getApiSuccessLiveDataResponse().observe(this, registerPurchaseInfoResultDto -> {
              stopWating();
              if(registerPurchaseInfoResultDto.getMarketResultDto().getMarketResultEnumId()== CreditResponseEnum.SUCCESS.getId()){
                getScoreAtion(registerPurchaseInfoResultDto.getReqToken(), registerPurchaseInfoResultDto.getMarketResultDto().getMarketResultEnumId());}
              else {
                String error=CreditResponseEnum.fromValue(new Long(registerPurchaseInfoResultDto.getMarketResultDto().getMarketResultEnumId())).getValue();
                ToastHelper.showErrorMessage(GetScoreOthersActivity.this,error);
                txtAlert.setText(registerPurchaseInfoResultDto.getMarketResultDto().getMarketResultEnumId()+":"+error);

                txtAlert.setVisibility(View.VISIBLE);
              }
            }
    );
    getScoreViewModel.getApiAuthFailureErrorLiveData().observe(this, volleyError -> {
    });
    getScoreViewModel.getApiErrorLiveData().observe(this, volleyError -> {
      goToFailApiPage("ApiError");
    });
    getScoreViewModel.getApiServerErrorLiveData().observe(this, volleyError ->
    {
      goToFailApiPage("ServerError");
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

*/


    }

    private void goToFailApiPage(String failCause) {

        Intent intent = new Intent(this, FailApiActivity.class);
        intent.putExtra("failCause", failCause);
        startActivity(intent);
        finish();

    }

    private void showWating() {
        prgGetScoreOthers.setVisibility(View.VISIBLE);
        ButtonHelper.toggleAppCompatButtonStatus(btnGetScoreOthers, false);
    }

    private void stopWating() {
        prgGetScoreOthers.setVisibility(View.INVISIBLE);
        ButtonHelper.toggleAppCompatButtonStatus(btnGetScoreOthers, true);
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

                            //"bGoa+V7g/yqDXvKRqq+JTFn4uQZbPiQJo4pf9RzJ"

                            choosePaymentType();

                        } else {
                            alreadyBazaarInited = false;
                            Intent intentLogin = new Intent(Intent.ACTION_VIEW);
                            intentLogin.setData(Uri.parse("bazaar://login"));
                            intentLogin.setPackage("com.farsitel.bazaar");
                            startActivity(intentLogin);
                        }

                        break;

                }


            }
        };
        registerReceiver(broadcastReceiver, filter);
    }

    boolean verifyDeveloperPayload(@NonNull Purchase p) {
        String payload = p.getDeveloperPayload();
        return true;
    }

    private void getScoreAtion(String reqToken, @NonNull Activity activity) {
        String url = "https://www.sanjineh.ir/report/" + reqToken + "?from=android_cafebazar";
        ActivityNavigationHelper.navigateToWebView(url, activity, WebViewActivity.class);
        activity.finish();
    }

    @Override
    public void onDialogSubmitEvent(Object object) {
        Log.d(TAG, "onDialogSubmitEvent: ");
        showWating();
        sendVerifyCodeViewModel.callSendVerifyCodeViewModel(CafeBazaarPaymentTypeEnum.WALLET.name(), edtNtcodeOthers.getText().toString(), edtMsisdnOthers.getText().toString());

    }

    @Override
    public void onDialogCancelEvent(Object object) {
        Log.d(TAG, "onDialogCancelEvent: ");
        startPurchase();
    }

    private void choosePaymentType() {

        if (userHasSanjineh()) {
            int sanjinehValue = (this.numberOfSanjineh.getBalance()) / (this.numberOfSanjineh.getUnitValue());
            Crashlytics.setString("userHasSanjineh", this.numberOfSanjineh.getBalance() + "");
            String toastMesasge = "شما دارای " + sanjinehValue + "سنجینه در کیف پول خود هستید ";
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
        mHelper.launchPurchaseFlow(GetScoreOthersActivity.this, AppConstants.BAZAAR_SKU, 10001, mPurchaseFinishedListener, randomUUIDString);
    }
}
