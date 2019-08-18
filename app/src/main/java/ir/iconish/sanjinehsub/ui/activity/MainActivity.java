package ir.iconish.sanjinehsub.ui.activity;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.adpdigital.push.AdpPushClient;
import com.crashlytics.android.Crashlytics;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.FirebaseApp;
import com.google.firebase.messaging.FirebaseMessaging;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ir.iconish.sanjinehsub.R;
import ir.iconish.sanjinehsub.adapter.CoinAdapter;
import ir.iconish.sanjinehsub.adapter.NavigationAdapter;
import ir.iconish.sanjinehsub.adapter.listener.RecyclerIemListener;
import ir.iconish.sanjinehsub.bazaar.CheckCafeBazaarLogin;
import ir.iconish.sanjinehsub.config.AppController;
import ir.iconish.sanjinehsub.data.model.CafeBazaarPaymentTypeEnum;
import ir.iconish.sanjinehsub.data.model.CoinPrice;
import ir.iconish.sanjinehsub.data.model.CreditResponseEnum;
import ir.iconish.sanjinehsub.data.model.NavigationItem;
import ir.iconish.sanjinehsub.data.model.NumberOfSanjineh;
import ir.iconish.sanjinehsub.data.model.ReportStateEnum;
import ir.iconish.sanjinehsub.data.vm.GetScoreViewModel;
import ir.iconish.sanjinehsub.data.vm.LogoutViewModel;
import ir.iconish.sanjinehsub.data.vm.SendVerifyCodeViewModel;
import ir.iconish.sanjinehsub.data.vm.UserNumberOfSanjinehViewModel;
import ir.iconish.sanjinehsub.ui.ActivityNavigationHelper;
import ir.iconish.sanjinehsub.ui.DialogHelper;
import ir.iconish.sanjinehsub.ui.Dialoglistener;
import ir.iconish.sanjinehsub.util.AppConstants;
import ir.iconish.sanjinehsub.util.ButtonHelper;
import ir.iconish.sanjinehsub.util.CreditStatusManager;
import ir.iconish.sanjinehsub.util.Helper;
import ir.iconish.sanjinehsub.util.IabHelper;
import ir.iconish.sanjinehsub.util.IabResult;
import ir.iconish.sanjinehsub.util.Inventory;
import ir.iconish.sanjinehsub.util.Purchase;
import ir.iconish.sanjinehsub.util.ToastHelper;

public class MainActivity extends AppCompatActivity implements RecyclerIemListener, Dialoglistener, AdapterView.OnItemSelectedListener {

    @Nullable
    @BindView(R.id.drawerLayout)
    DrawerLayout drawerLayout;

    @Nullable
    @BindView(R.id.navView)
    NavigationView navigationView;

    @Nullable
    @BindView(R.id.spnr_choose_self_others)
    Spinner spnrChooseSelfOthers;

    @Nullable
    @BindView(R.id.recNavigation)
    RecyclerView recyclerNavigation;

    @Nullable
    @BindView(R.id.txt_user_name)
    TextView txtUserName;
    @Nullable
    @BindView(R.id.txt_get_credit_info)
    TextView txtGetCreditInfo;

    @Nullable
    @BindView(R.id.imgNavMenu)
    ImageView imgNavMenu;

    @Nullable
    @BindView(R.id.edt_txt_ntcode_get_credit)
    EditText edtNtcodeOthers;

    @Nullable
    @BindView(R.id.edt_txt_mobile_number_get_credit)
    EditText edtMsisdnOthers;


    @Nullable
    @BindView(R.id.credit_form)
    View creditForm;

    @Nullable
    @BindView(R.id.credit_otp)
    View creditOtp;

 /*   @Nullable
    @BindView(R.id.get_credit_header)
    ExpansionHeader getCreditHeader;

    @Nullable
    @BindView(R.id.get_credit_layout)
    ExpansionLayout getCreditLayout;
    @Nullable
    @BindView(R.id.get_otp_layout)
    ExpansionLayout getOtpLayout;*/


    /////////////////////////////////////////CafeBazaar Purchase Flow///////////////////////////////////////////////
    @Nullable
    public static IabHelper mHelper;
    @Nullable
    @BindView(R.id.check_box_blank)
    ImageView checkBoxBlank;
    @Nullable
    @BindView(R.id.check_box)
    ImageView checkBox;

    BroadcastReceiver broadcastReceiver;
    boolean ruleIsChecked = false;
    private static final String TAG = "MainActivity";
    @Inject
    LogoutViewModel logoutViewModel;
    Purchase purchase;
    @Inject
    SendVerifyCodeViewModel sendVerifyCodeViewModel;
    boolean isSelf = true;


    private void navigateToActivity(Class cls) {

        startActivity(new Intent(this, cls));
    }


    @OnClick(R.id.rootOtherScore)
    public void rootOtherScoreAction() {
        ActivityNavigationHelper.navigateToActivity(this, GetScoreOthersActivity.class, false);
    }

    @OnClick(R.id.rootMyScore)
    public void rootMyScore() {
        ActivityNavigationHelper.navigateToActivity(this, GetScoreActivity.class, false);
    }


    @OnClick(R.id.rootScoreHelp)
    public void rootScoreHelpAction() {

        ActivityNavigationHelper.navigateToWebView("https://www.sanjineh.ir/about/reporttype?id=40&child=45&from=android_cafebazar", this, WebViewActivity.class);

    }


    @OnClick(R.id.rootImprovement)
    public void rrootImprovementAction() {

        ActivityNavigationHelper.navigateToWebView("https://www.sanjineh.ir/consultation/advice?id=39&child=49&from=android_cafebazar", this, WebViewActivity.class);

    }


    @OnClick(R.id.rootEconomicNews)
    public void rootEconomicNewsAction() {

        ActivityNavigationHelper.navigateToWebView("https://www.sanjineh.ir/news?id=37&from=android_cafebazar", this, WebViewActivity.class);

    }


    @OnClick(R.id.rootBankService)
    public void rootBankServiceAction() {

        ActivityNavigationHelper.navigateToWebView("https://www.sanjineh.ir/bankService?id=87&from=android_cafebazar", this, WebViewActivity.class);

    }


    @OnClick(R.id.rootBurse)
    public void rootBurseAction() {

        ActivityNavigationHelper.navigateToWebView("https://www.sanjineh.ir/bourse?id=88&from=android_cafebazar", this, WebViewActivity.class);

    }


    @OnClick(R.id.imgNavMenu)
    public void navMenuAction() {


        if (drawerLayout.isDrawerOpen(GravityCompat.END)) {
            drawerLayout.closeDrawer(GravityCompat.END);
        } else {
            drawerLayout.openDrawer(GravityCompat.END);

        }

    }

    private void initNavigation() {
        List<NavigationItem> navigationItems = new ArrayList<>();
    /*  NavigationItem n1=new NavigationItem();
      n1.setTitle(getString(R.string.nav_dpwnload_app));
      n1.setDrawbleId(R.drawable.ic_download_nav);
      n1.setId(1);
      navigationItems.add(0,n1);*/


        NavigationItem n2 = new NavigationItem();
        n2.setTitle("خانه");
        n2.setDrawbleId(R.drawable.home);
        n2.setId(2);
        navigationItems.add(0, n2);


        NavigationItem n3 = new NavigationItem();
        n3.setTitle("باشگاه خوش حسابان");
        n3.setDrawbleId(R.drawable.khoshhesaban_gray);
        n3.setId(3);
        navigationItems.add(1, n3);


        NavigationItem n4 = new NavigationItem();
        n4.setTitle("آرشیو گزارش ها");
        n4.setDrawbleId(R.drawable.archive);
        n4.setId(4);
        navigationItems.add(2, n4);

/*        NavigationItem n9 = new NavigationItem();
        n9.setTitle(getString(R.string.nav_profile));
        n9.setDrawbleId(R.drawable.ic_person_nav);
        n9.setId(9);
        navigationItems.add(2, n9);*/

        NavigationItem n5 = new NavigationItem();
        n5.setTitle("آموزش بهبود رتبه اعتباری");
        n5.setDrawbleId(R.drawable.help_credit_score);
        n5.setId(5);
        navigationItems.add(3, n5);


        NavigationItem n6 = new NavigationItem();
        n6.setTitle("درباره ما");
        n6.setId(6);
        navigationItems.add(4, n6);


        NavigationItem n7 = new NavigationItem();
        n7.setTitle("قوانین و مقررات");
        n7.setId(7);
        navigationItems.add(5, n7);


        NavigationItem n8 = new NavigationItem();
        n8.setTitle(getString(R.string.nav_exit_account));
        n8.setId(8);
        navigationItems.add(6, n8);


        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);

        recyclerNavigation.setLayoutManager(layoutManager);

        NavigationAdapter navigationAdapter = new NavigationAdapter(navigationItems, this);
        recyclerNavigation.setAdapter(navigationAdapter);


    }

    @Override
    public void onTap(Object obj) {
        NavigationItem navigationItem = (NavigationItem) obj;

        switch (navigationItem.getId()) {

            case 1:
//downloadLastVersion();

                break;


            case 2:

                //   https://www.sanjineh.ir/profile/%D8%B3%DB%8C%D8%AF%D9%85%D8%AD%D9%85%D8%AF%20%20%D8%B3%DB%8C%D8%AF%D9%85%D8%AD%D9%85%D8%AF%DB%8C
                ActivityNavigationHelper.navigateToWebView("https://www.sanjineh.ir/profile/" + logoutViewModel.getToken() + "?from=android_cafebazar", this, WebViewActivity.class);

                break;


            case 3:

                break;


            case 4:
                /*  ActivityNavigationHelper.navigateToWebView("https://www.sanjineh.ir/aboutus?from=android_cafebazar", MainActivity.this, WebViewActivity.class);*/
                ActivityNavigationHelper.navigateToActivity(MainActivity.this, ArchiveActivity.class, false);

                break;


            case 5:
                ActivityNavigationHelper.navigateToWebView("https://www.sanjineh.ir/contactus?from=android_cafebazar", MainActivity.this, WebViewActivity.class);

                break;


            case 6:
                //www.sanjineh.ir/terms
                ActivityNavigationHelper.navigateToWebView("https://www.sanjineh.ir/terms?from=android_cafebazar", MainActivity.this, WebViewActivity.class);

                break;


            case 7:

                logoutViewModel.logout();

                ActivityNavigationHelper.navigateToActivity(this, LoginActivity.class, true);

                break;


            case 8:
                finish();
                break;


          /*  case 9:
                Toast.makeText(this, "You Clicked on Wallet", Toast.LENGTH_SHORT).show();
                break;
*/
        }
    }

    private void setDataOnCoinRecycler(List<CoinPrice> coinPriceList) {

        CoinAdapter coinAdapter = new CoinAdapter(coinPriceList, this);
        // recyclerView.setAdapter(coinAdapter);
        coinAdapter.notifyDataSetChanged();


    }

    private void downloadLastVersion() {
        String url = "http://dl.iconish.ir/app/SanjinehAppSub1.1.2.apk";
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);
    }

    CheckCafeBazaarLogin checkCafeBazaarLogin;
    @BindView(R.id.btn_get_credit)
    AppCompatButton btnGetCredit;
    @BindView(R.id.prgGetScore)
    ProgressBar prgGetScore;
    @Inject
    GetScoreViewModel getScoreViewModel;
    @BindView(R.id.layout_check_rule)
    FrameLayout checkboxRules;
    @Inject
    UserNumberOfSanjinehViewModel getUserHasSanjinehViewModel;
    NumberOfSanjineh numberOfSanjineh;
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
            MainActivity.this.purchase = purchase;
            Log.i(TAG, "PurchaseFinished: " + result + ", purchase: " + purchase);


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
            if (isSelf) {
                getScoreViewModel.callGetScoreViewModel(null, edtNtcodeOthers.getText().toString(), 1, 1, AppConstants.PAYMENT_TYPE, AppConstants.CHANNEL_ID, -1, purchase);
            } else {
                Log.d(TAG, "onIabPurchaseFinished: sendingVerifyCode");
                sendVerifyCodeViewModel.callSendVerifyCodeViewModel(CafeBazaarPaymentTypeEnum.CAFE_SDK.name(), edtNtcodeOthers.getText().toString(), edtMsisdnOthers.getText().toString());
            }

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
        setContentView(R.layout.activity_main);
        FirebaseApp.initializeApp(this);


        FirebaseMessaging.getInstance().subscribeToTopic(AppConstants.CHANNEL_ID_NOTIFICATON).addOnSuccessListener(aVoid -> {
        });


        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        ButterKnife.bind(this);
        ((AppController) getApplication()).getAppComponent().inject(this);

        setUpSpinner();
        initNavigation();
        checkCafeBazaarLogin = new CheckCafeBazaarLogin(MainActivity.this);
        messageReciver();
        showWating();
        txtUserName.setText("کاربر مهمان");
        creditForm.setVisibility(View.VISIBLE);
        creditOtp.setVisibility(View.INVISIBLE);

        bazaarSetup(getScoreViewModel.getMarketKey());
        if (isSelf) {
            edtMsisdnOthers.setText(getUserHasSanjinehViewModel.getMobileNumber());
        }

        attachViewModel();
        Log.d(TAG, "onCreate: GetScoreActivity");
        getUserHasSanjinehViewModel.callGetUserSanjinehViewModel();


    }

    private void setUpSpinner() {
        spnrChooseSelfOthers.setOnItemSelectedListener(this);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.spnr_self_other_choose, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnrChooseSelfOthers.setAdapter(adapter);
        spnrChooseSelfOthers.setSelection(0);
    }

    @OnClick(R.id.layout_check_rule)
    public void onRuleCheckBoxListener() {
        toggleCheckRule();
    }

    private void toggleCheckRule() {

        if (ruleIsChecked) {
            ruleIsChecked = false;
            checkBoxBlank.setVisibility(View.VISIBLE);
            checkBox.setVisibility(View.GONE);
        } else {
            ruleIsChecked = true;
            checkBoxBlank.setVisibility(View.GONE);
            checkBox.setVisibility(View.VISIBLE);

        }

    }

    boolean verifyDeveloperPayload(@NonNull Purchase p) {
        String payload = p.getDeveloperPayload();
        return true;
    }


    private void showWating() {
        prgGetScore.setVisibility(View.VISIBLE);
        ButtonHelper.toggleAppCompatButtonStatus(btnGetCredit, false);
    }


    @OnClick(R.id.btn_get_credit)
    public void btnGetCreditAction() {
        Log.d(TAG, "btnGetCreditAction: Clicked!");
        String msisdn = edtMsisdnOthers.getText().toString().trim();
        String ntCode = edtNtcodeOthers.getText().toString().trim();
        Log.d(TAG, "btnGetCreditAction: Clicked! msisdn " + msisdn + " ntcoe= " + ntCode);

        if (!(msisdn.length() > 0) && !(ntCode.length() > 0)) {
           /* txtAlert.setText(getString(R.string.force_enter_phone_ntcode));
            txtAlert.setVisibility(View.VISIBLE);*/
            return;
        }
        if ((msisdn.length() > 0) && !(ntCode.length() > 0)) {
          /*  txtAlert.setText(getString(R.string.force_enter_phone));
            txtAlert.setVisibility(View.VISIBLE);*/
            return;
        }

        if ((msisdn.trim().length() > 0) && !msisdn.startsWith("09")) {
          /*  txtAlert.setText(getString(R.string.enter_correct_mobile_phone));
            txtAlert.setVisibility(View.VISIBLE);*/
            return;

        }
        if ((msisdn.length() > 0) && msisdn.length() != 11) {
           /* txtAlert.setText(getString(R.string.valid_phone));
            txtAlert.setVisibility(View.VISIBLE);*/
            return;
        }


        if (!Helper.validationNationalCode(ntCode)) {
        /*    txtAlert.setText(getString(R.string.enter_correct_national_code));
            txtAlert.setVisibility(View.VISIBLE);*/
            return;
        }

        Log.d(TAG, "btnGetCreditAction: ");
        if (!ruleIsChecked) {
/*            txtAlert.setText(getString(R.string.accept_nt_code_mobile));
            txtAlert.setVisibility(View.VISIBLE);*/
            return;

        }


        if (!alreadyBazaarInited) {
            checkCafeBazaarLogin.initService();
        } else {

            //"bGoa+V7g/yqDXvKRqq+JTFn4uQZbPiQJo4pf9RzJ"
            Log.d(TAG, "btnGetCreditAction: Starting launchPurchaseFlow");

            choosePaymentType();


        }
    }


    private void choosePaymentType() {
        Log.d(TAG, "choosePaymentType: checking user has sanjineh");
        if (userHasSanjineh()) {
            int sanjinehValue = (this.numberOfSanjineh.getBalance()) / (this.numberOfSanjineh.getUnitValue());
            String toastMesasge = " شما دارای " + sanjinehValue + " سنجینه در کیف پول خود هستید ";
            Toast.makeText(this, toastMesasge, Toast.LENGTH_LONG).show();
            DialogHelper.showDialog("نحوه پرداخت", "پرداخت از کیف پول یا کافه بازار؟", "کیف پول", "کافه بازار", this, this);

        } else {
            Log.d(TAG, "choosePaymentType: USER HAS NO SANJINEH");
            startPurchase();
        }

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

                    new CreditStatusManager(this).handleReportStatus(creditScorePreProcess, null);
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
        sendVerifyCodeViewModel.getApiSuccessLiveDataResponse().observe(this, verifyCodeOthersResponse -> {
                    //   String s = String.format("تا دقایقی دیگر کد تایید برای شماره s%ارسال خواهد شد", getUserHasSanjinehViewModel.getMobileNumber());
                    //  txtGetCreditInfo.setText(s);

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

                        creditForm.setVisibility(View.INVISIBLE);
                        creditOtp.setVisibility(View.VISIBLE);

                        /*
                         */
                        /*startActivity(intent);
                        //TODO:show otp
                        finish();*/
                    } else if (verifyCodeOthersResponse.getStatusCode() == ReportStateEnum.USER_NOT_HAVE_REPORT.getId()) {
                        getScoreAtion(verifyCodeOthersResponse.getNoReportReqToken(), this);


                    } else if (verifyCodeOthersResponse.getStatusCode() == CreditResponseEnum.NOT_MATCH_MOBILE.getId()) {
//                        Toast.makeText(this, verifyCodeOthersResponse.getDescription(), Toast.LENGTH_SHORT).show();
                        DialogHelper.showDialog("خطا", verifyCodeOthersResponse.getDescription(), "باشه", null, this, this);


                    } else {
                        /*txtAlert.setText(verifyCodeOthersResponse.getDescription());
                        txtAlert.setVisibility(View.VISIBLE);*/
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


    }

    private boolean userHasSanjineh() {
        Log.d(TAG, "userHasSanjineh: enterd has sanjineh");
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

    private void startPurchase() {
        UUID uuid = UUID.randomUUID();
        String randomUUIDString = uuid.toString();
        if (mHelper != null) {
            Crashlytics.setString("flagEndAsync", "mHelper.flagEndAsync();");
            mHelper.flagEndAsync();
        }
        Log.d(TAG, "startPurchase: starting launchepurchaseflow");
        mHelper.launchPurchaseFlow(MainActivity.this, AppConstants.BAZAAR_SKU, 10001, mPurchaseFinishedListener, randomUUIDString);
    }

    private void goToFailApiPage(String failCause) {

        Intent intent = new Intent(this, FailApiActivity.class);
        intent.putExtra("failCause", failCause);
        startActivity(intent);
        finish();

    }

    @Override
    public void onBackPressed() {

        if (drawerLayout.isDrawerOpen(GravityCompat.END)) {
            drawerLayout.closeDrawer(GravityCompat.END);
        } else {
            DialogHelper.sureExit(this);
        }
    }

    private void stopWating() {
        prgGetScore.setVisibility(View.INVISIBLE);
        ButtonHelper.toggleAppCompatButtonStatus(btnGetCredit, true);
    }

    @Override
    public void onDialogSubmitEvent(Object object) {
        Log.d(TAG, "onDialogSubmitEvent: ");
        showWating();

        if (isSelf) {
            getScoreViewModel.callGetScoreViewModel(null, edtNtcodeOthers.getText().toString(), 1, 1, AppConstants.PAYMENT_TYPE, AppConstants.CHANNEL_ID, -1, purchase);
        } else {
            sendVerifyCodeViewModel.callSendVerifyCodeViewModel(CafeBazaarPaymentTypeEnum.CAFE_SDK.name(), edtNtcodeOthers.getText().toString(), edtMsisdnOthers.getText().toString());
        }
    }

    @Override
    public void onDialogCancelEvent(Object object) {
        Log.d(TAG, "onDialogCancelEvent: ");
        if (mHelper != null) {
            mHelper.flagEndAsync();
        }
        startPurchase();
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
                            MainActivity.this.startActivity(intentLogin);
                        }

                        break;

                }


            }
        };
        registerReceiver(broadcastReceiver, filter);
    }

    private void bazaarSetup(String bazaarKey) {

        String base64EncodedPublicKey = bazaarKey;
        // You can find it in your Bazaar console, in the Dealers section.
        // It is recommended to add more security than just pasting it in your source code;
        mHelper = new IabHelper(MainActivity.this, base64EncodedPublicKey);

        Log.i("Test", "Starting setup.");

        mHelper.startSetup(result -> {
            Log.i("Test", "Setup finished.");

            if (!result.isSuccess()) {
                ToastHelper.showErrorMessage(this, getString(R.string.bazar_setup_fail));
               /* txtAlert.setText(getString(R.string.bazar_setup_fail));
                txtAlert.setVisibility(View.VISIBLE);*/
                // Oh noes, there was a problem.
                Log.i("Test", "Problem setting up In-app Billing: " + result);
            } else {
                btnGetCredit.setVisibility(View.VISIBLE);
                prgGetScore.setVisibility(View.INVISIBLE);
            }
            // Hooray, IAB is fully set up!
            mHelper.queryInventoryAsync(mGotInventoryListener);
        });
    }

    private void getScoreAtion(String reqToken, @NonNull Activity activity) {
        String url = "https://www.sanjineh.ir/report/" + reqToken + "?from=android_cafebazar";
        ActivityNavigationHelper.navigateToWebView(url, activity, WebViewActivity.class);
        activity.finish();
    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String item = adapterView.getItemAtPosition(i).toString();
        if (i == 0) {
            isSelf = true;
            edtMsisdnOthers.setText(getUserHasSanjinehViewModel.getMobileNumber());

        } else if (i == 1) {
            isSelf = false;
            edtMsisdnOthers.setText("");
        } else {
            Toast.makeText(this, "خطای ناشناخته", Toast.LENGTH_SHORT).show();
        }
        // Showing selected spinner item
        Toast.makeText(adapterView.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    ///////////////////////////////////////////////////////////////////////////////////////

}
