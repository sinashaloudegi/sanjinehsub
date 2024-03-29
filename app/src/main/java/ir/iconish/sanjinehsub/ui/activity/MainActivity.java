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
import com.android.billingclient.util.IabHelper;
import com.crashlytics.android.Crashlytics;
import com.github.mikephil.charting.charts.LineChart;
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
import ir.iconish.sanjinehsub.adapter.NavigationAdapter;
import ir.iconish.sanjinehsub.adapter.NewsAdapter;
import ir.iconish.sanjinehsub.adapter.OtherServiceAdapter;
import ir.iconish.sanjinehsub.adapter.SearchResultAdapter;
import ir.iconish.sanjinehsub.adapter.TeachBourseAdapter;
import ir.iconish.sanjinehsub.adapter.listener.RecyclerIemListener;
import ir.iconish.sanjinehsub.bazaar.CheckCafeBazaarLogin;
import ir.iconish.sanjinehsub.config.AppController;
import ir.iconish.sanjinehsub.data.model.IrancellPurchaseDTO;
import ir.iconish.sanjinehsub.data.model.IrancellReportDTO;
import ir.iconish.sanjinehsub.data.model.IrancellSubDTO;
import ir.iconish.sanjinehsub.data.model.NavigationItem;
import ir.iconish.sanjinehsub.data.model.NewsItem;
import ir.iconish.sanjinehsub.data.model.NumberOfSanjineh;
import ir.iconish.sanjinehsub.data.model.OtherServiceItem;
import ir.iconish.sanjinehsub.data.model.SearchResult;
import ir.iconish.sanjinehsub.data.model.TeachBourseItem;
import ir.iconish.sanjinehsub.data.model.Voucher;
import ir.iconish.sanjinehsub.data.vm.GetScoreCharkhooneViewModel;
import ir.iconish.sanjinehsub.data.vm.GetScoreViewModel;
import ir.iconish.sanjinehsub.data.vm.LogoutViewModel;
import ir.iconish.sanjinehsub.data.vm.NewsViewModel;
import ir.iconish.sanjinehsub.data.vm.SendVerifyCodeViewModel;
import ir.iconish.sanjinehsub.data.vm.UserNumberOfSanjinehViewModel;
import ir.iconish.sanjinehsub.data.vm.VoucherListViewModel;
import ir.iconish.sanjinehsub.ui.ActivityNavigationHelper;
import ir.iconish.sanjinehsub.ui.DialogHelper;
import ir.iconish.sanjinehsub.ui.Dialoglistener;
import ir.iconish.sanjinehsub.util.AppConstants;
import ir.iconish.sanjinehsub.util.ButtonHelper;
import ir.iconish.sanjinehsub.util.CafeIabHelper;
import ir.iconish.sanjinehsub.util.Helper;
import ir.iconish.sanjinehsub.util.IabResult;
import ir.iconish.sanjinehsub.util.Inventory;
import ir.iconish.sanjinehsub.util.Purchase;
import ir.iconish.sanjinehsub.util.ToastHelper;

import static androidx.recyclerview.widget.RecyclerView.HORIZONTAL;
import static ir.iconish.sanjinehsub.util.AppConstants.STORE;

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
    @BindView(R.id.other_services_recycler_view)
    RecyclerView otherServiceRecyclerView;


    @Nullable
    @BindView(R.id.teach_bourse_recycler_view)
    RecyclerView teachBourseRecyclerView;


    @Nullable
    @BindView(R.id.news_recycler_view)
    RecyclerView newsRecyclerView;


    @Nullable
    @BindView(R.id.coin_recycler_view)
    RecyclerView coinRecycleView;

    @Nullable
    @BindView(R.id.chart)
    LineChart chart;

    @Nullable
    @BindView(R.id.txt_user_name)
    TextView txtUserName;

    @Nullable
    @BindView(R.id.txt_get_credit_info)
    TextView txtGetCreditInfo;


    @Nullable
    @BindView(R.id.price_voucher_1_off)
    TextView priceVoucher1Off;


    @Nullable
    @BindView(R.id.price_voucher_2_off)
    TextView priceVoucher2Off;


    @Nullable
    @BindView(R.id.price_voucher_3_off)
    TextView priceVoucher3Off;


    @Nullable
    @BindView(R.id.price_voucher_1_original)
    TextView priceVoucher1Original;


    @Nullable
    @BindView(R.id.price_voucher_2_original)
    TextView priceVoucher2Original;


    @Nullable
    @BindView(R.id.price_voucher_3_original)
    TextView priceVoucher3Original;


    @Nullable
    @BindView(R.id.txt_sent_otp)
    TextView txtSentOtp;


    @Nullable
    @BindView(R.id.voucher_txt_1)
    TextView voucherTxt1;

    @Nullable
    @BindView(R.id.voucher_txt_2)
    TextView voucherTxt2;

    @Nullable
    @BindView(R.id.voucher_txt_3)
    TextView voucherTxt3;

    @Nullable
    @BindView(R.id.vouchers_layout)
    View vouchersLayout;


    @Nullable
    @BindView(R.id.vouchers_see_all)
    TextView voucherSeeAll;


    @Nullable
    @BindView(R.id.imgNavMenu)
    ImageView imgNavMenu;

    @Nullable
    @BindView(R.id.edit_profile)
    ImageView editProfile;


    @Nullable
    @BindView(R.id.voucher_img_1)
    ImageView voucherImg1;

    @Nullable
    @BindView(R.id.voucher_img_2)
    ImageView voucherImg2;

    @Nullable
    @BindView(R.id.voucher_img_3)
    ImageView voucherImg3;

    @Nullable
    @BindView(R.id.edt_txt_ntcode_get_credit)
    EditText edtNtcodeOthers;

    @Nullable
    @BindView(R.id.edt_txt_otp)
    EditText edtTextOtp;

  /*  @Nullable
    @BindView(R.id.edt_txt_search)
    EditText edtTextSearch;*/

    /*@Nullable
    @BindView(R.id.auto_search)
    AutoCompleteTextView autoSearch;
*/

    @Nullable
    @BindView(R.id.edt_txt_mobile_number_get_credit)
    EditText edtMsisdnOthers;


    @Nullable
    @BindView(R.id.credit_form)
    View creditForm;

    @Nullable
    @BindView(R.id.divider_voucher)
    View dividerVoucher;


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

    String mobileNumber;
    String ntcode;
    int voucherSize;
    /////////////////////////////////////////CafeBazaar Purchase Flow///////////////////////////////////////////////
    @Nullable
    CafeIabHelper mCafeIabHelper;
    IabHelper mCharkhooneIabHelper;
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

    @Inject
    VoucherListViewModel mVoucherListViewModel;

    @Inject
    GetScoreCharkhooneViewModel getScoreCharkhooneViewModel;
    Purchase purchase;
    @Inject
    SendVerifyCodeViewModel sendVerifyCodeViewModel;
    boolean isSelf = true;


    @OnClick(R.id.rootOtherScore)
    public void rootOtherScoreAction() {
        ActivityNavigationHelper.navigateToActivity(this, GetScoreOthersActivity.class, false);
    }

    @NonNull
    CafeIabHelper.OnConsumeFinishedListener mConsumeCafeFinishedListener = new CafeIabHelper.OnConsumeFinishedListener() {
        @Override
        public void onConsumeFinished(Purchase purchase, @NonNull IabResult result) {
            Log.i(TAG, "Consumption finished. Purchase: " + purchase + ", result: " + result);
            if (mCafeIabHelper == null) {
                return;
            }
            result.isSuccess();//Log.e("Test", "Consumption successful. Provisioning.");
//Log.e("Test", "Error while consuming: " + result);
//Log.e("Test",  "End consumption flow.");
        }
    };
    @NonNull
    CafeIabHelper.OnIabPurchaseFinishedListener mPurchaseFinishedListener = new CafeIabHelper.OnIabPurchaseFinishedListener() {

        @Override
        public void onIabPurchaseFinished(@NonNull IabResult result, @NonNull Purchase purchase) {
            MainActivity.this.purchase = purchase;

            Log.i(TAG, "PurchaseFinished: " + result + ", purchase: " + purchase);


            if (mCafeIabHelper == null) {
                return;
            }

            if (result.isFailure()) {
                return;
            }
            if (!verifyDeveloperPayload(purchase)) {
                Log.i("Test", "Error purchasing. Authenticity verification failed.");
                return;
            }
            Log.i("Test", " Cafe Purchase successful.");
            showWating();

            Log.d(TAG, "onIabPurchaseFinished: now we are going to call callGetScoreCafeBazaarViewModel and purchase token is" + purchase.getToken());

            JSONObject data = new JSONObject();
            try {


                if (AppConstants.STORE.equals("CHARKHOONE")) {
                    data.put("charkhooneToken", purchase.getToken());

                } else if (AppConstants.STORE.equals("CAFEBAZAAR")) {

                    data.put("bazaarToken", purchase.getToken());


                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

            AdpPushClient.get().track("add-to-card", data);


            Crashlytics.setString("purchaseFinished", "finished!");
            Crashlytics.setString("purchaseToken", purchase.getToken());

            if (isSelf) {
                getScoreViewModel.callGetScoreCafeBazaarViewModel(null, edtNtcodeOthers.getText().toString(), 1, 1, AppConstants.PAYMENT_TYPE, AppConstants.CHANNEL_ID, -1, purchase);
            } else {
                getScoreViewModel.callGetScoreCafeBazaarViewModel(edtMsisdnOthers.getText().toString(), edtNtcodeOthers.getText().toString(), 2, 1, AppConstants.PAYMENT_TYPE, AppConstants.CHANNEL_ID, -1, purchase);
            }



          /*  else {
                Log.d(TAG, "onIabPurchaseFinished: sendingVerifyCode");
                sendVerifyCodeViewModel.callSendVerifyCodeViewModel(CafeBazaarPaymentTypeEnum.CAFE_SDK.name(), edtNtcodeOthers.getText().toString(), edtMsisdnOthers.getText().toString());
            }*/


            mCafeIabHelper.consumeAsync(purchase, mConsumeCafeFinishedListener);
            String url;
            if (isSelf) {
                url = "https://creditscore.iconish.ir/report/sendotp?channel=ANDROID&mobile=" + getUserHasSanjinehViewModel.getMobileNumber() + "&nationalCode=" + ntcode;
            } else {

                url = "https://creditscore.iconish.ir/report/sendotp?channel=ANDROID&mobile=" + getUserHasSanjinehViewModel.getMobileNumber() + "&nationalCode=" + ntcode + "&otherMobile=" + mobileNumber;
            }
            loadURL(url, "report");
        }
    };

//

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

    @Inject
    NewsViewModel mNewsViewModel;
    long voucherId1;

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
    long voucherId2;
    long voucherId3;
    private ArrayList<SearchResult> searchResultArrayList;
    private SearchResultAdapter searchResultAdapter;

    NumberOfSanjineh numberOfSanjineh;
    @NonNull
    public CafeIabHelper.QueryInventoryFinishedListener mGotInventoryListener = new CafeIabHelper.QueryInventoryFinishedListener() {
        @Override
        public void onQueryInventoryFinished(@NonNull IabResult result, @Nullable Inventory inventory) {
            //Log.e("Test", "Query inventory finished.");
            if (mCafeIabHelper == null) {
                return;
            }

            if (result.isFailure()) {
                Log.i("Test", "Failed to query invenreturn;tory: " + result);

            }

            if (inventory != null) {
                Purchase gasPurchase = inventory.getPurchase(AppConstants.BAZAAR_SKU);
                if (gasPurchase != null && verifyDeveloperPayload(gasPurchase)) {
                    //Log.e("Test", "We have sanj01. Consuming it.");
                    mCafeIabHelper.consumeAsync(inventory.getPurchase(AppConstants.BAZAAR_SKU), mConsumeCafeFinishedListener);
                    return;
                }
            }


            Log.i("Test", "Initial inventory query finished; enabling main UI.");
        }
    };
    @NonNull
    IabHelper.OnConsumeFinishedListener mConsumeCharkhooneFinishedListener = new IabHelper.OnConsumeFinishedListener() {

        @Override
        public void onConsumeFinished(com.android.billingclient.util.Purchase purchase, com.android.billingclient.util.IabResult iabResult) {
            Log.i(TAG, "Consumption finished. Purchase: " + purchase + ", result: " + iabResult);
            if (mCafeIabHelper == null) {
                return;
            }
            iabResult.isSuccess();//Log.e("Test", "Consumption successful. Provisioning.");
        }
    };
    //
    private IabHelper.OnIabPurchaseFinishedListener mCharkhoonePurchaseFinishListener = new IabHelper.OnIabPurchaseFinishedListener() {
        public void onIabPurchaseFinished(com.android.billingclient.util.IabResult result, com.android.billingclient.util.Purchase purchase) {
            Log.d("Test", " Purchase finished: " + result + ", purchase: " + purchase);

            // if we were disposed of in the meantime, quit.
            if (mCharkhooneIabHelper == null)
                return;

            if (purchase != null) {
                if (purchase.getSku() != null && purchase.getSku().equalsIgnoreCase(AppConstants.CHARKHOONE_SKU)) {
                    //Successful Payment
                    Log.i("Test", " Purchase purchase.getSku() : " + purchase.getSku());

                    IrancellPurchaseDTO irancellPurchaseDTO = new IrancellPurchaseDTO();
                    irancellPurchaseDTO.setAutoRenewing(purchase.isAutoRenewing());
                    irancellPurchaseDTO.setDeveloperPayload(purchase.getDeveloperPayload());
                    irancellPurchaseDTO.setItemType(purchase.getItemType());
                    irancellPurchaseDTO.setOrderId(purchase.getOrderId());
                    irancellPurchaseDTO.setOriginalJson(purchase.getOriginalJson());
                    irancellPurchaseDTO.setPurchaseState(purchase.getPurchaseState());
                    irancellPurchaseDTO.setPurchaseTime(purchase.getPurchaseTime());
                    irancellPurchaseDTO.setSignature(purchase.getSignature());
                    irancellPurchaseDTO.setToken(purchase.getToken());

                    IrancellSubDTO irancellSubDTO = new IrancellSubDTO();

                    irancellSubDTO.setAppId(AppConstants.APP_ID_INT);
                    irancellSubDTO.setChannelId(AppConstants.CHANNEL_ID);
                  /*  irancellSubDTO.setVersionCode(AppConstants.versionCode);
                    irancellSubDTO.setVersionName(AppConstants.);*/

                    IrancellReportDTO irancellReportDTO = new IrancellReportDTO();
                    irancellReportDTO.setOtherMobile(edtMsisdnOthers.getText().toString());
                    irancellReportDTO.setNtCode(edtNtcodeOthers.getText().toString());
                    irancellReportDTO.setPersonTypeId(isSelf ? 1 : 2);


                    // TODO: 11/16/2019 cafe_charkhoone after success payment, this should be uncommented
                    /*getScoreCharkhooneViewModel.callGetScoreCharkhooneViewModel(irancellPurchaseDTO, irancellSubDTO, irancellReportDTO);*/

                    // Log.i("Test registerPurchaseInfoResultDto : " , registerPurchaseInfoResultDto.toString());
                    Log.d(TAG, "getScoreViewModelObserver: success");
                    stopWating();
                    // TODO: 11/4/2019 go to web  load url
                    String url;
                    if (isSelf) {
                        url = "https://creditscore.iconish.ir/report/sendotp?channel=ANDROID&mobile=" + getUserHasSanjinehViewModel.getMobileNumber() + "&nationalCode=" + ntcode;
                    } else {

                        url = "https://creditscore.iconish.ir/report/sendotp?channel=ANDROID&mobile=" + getUserHasSanjinehViewModel.getMobileNumber() + "&nationalCode=" + ntcode + "&otherMobile=" + mobileNumber;
                    }
                    loadURL(url, "report");
                    /* new CreditStatusManager(this).handleReportStatus(creditScorePreProcess, txtGetCreditInfo);*/


                }
            }
            try {
                mCharkhooneIabHelper.consumeAsync(purchase, mConsumeCharkhooneFinishedListener);
            } catch (IabHelper.IabAsyncInProgressException e) {
                e.printStackTrace();
            }

            if (result.isFailure()) {
                Log.i("Test", " Error purchasing. ");
                return;
            }


            Log.i("Test", "Purchase successful.");


        }
    };

    @OnClick(R.id.edit_profile)
    public void setEditProfile() {

        ActivityNavigationHelper.navigateToWebView("https://www.sanjineh.ir/profile/edit_profile&from=android_cafebazar", this, WebViewActivity.class);
    }

    private boolean alreadyBazaarInited = false;

    private void initNavigation() {
        List<NavigationItem> navigationItems = new ArrayList<>();


        NavigationItem homeNavigation = new NavigationItem();
        homeNavigation.setTitle("خانه");
        homeNavigation.setDrawbleId(R.drawable.home);
        homeNavigation.setId(0);
        navigationItems.add(0, homeNavigation);


        NavigationItem khoshhesabanClub = new NavigationItem();
        khoshhesabanClub.setTitle("پروفایل");
        khoshhesabanClub.setDrawbleId(R.drawable.khoshhesaban_gray);
        khoshhesabanClub.setId(1);
        navigationItems.add(1, khoshhesabanClub);


        /*NavigationItem archive = new NavigationItem();
        archive.setTitle("آرشیو گزارش ها");
        archive.setDrawbleId(R.drawable.archive);
        archive.setId(2);
        navigationItems.add(2, archive);
*/

        NavigationItem teach = new NavigationItem();
        teach.setTitle("آموزش بهبود رتبه اعتباری");
        teach.setDrawbleId(R.drawable.help_credit_score);
        teach.setId(3);
        navigationItems.add(2, teach);


        NavigationItem about = new NavigationItem();
        about.setTitle("درباره ما");
        about.setId(4);
        navigationItems.add(3, about);


        NavigationItem rules = new NavigationItem();
        rules.setTitle("قوانین و مقررات");
        rules.setId(5);
        navigationItems.add(4, rules);


        NavigationItem exitAccount = new NavigationItem();
        exitAccount.setTitle(getString(R.string.nav_exit_account));
        exitAccount.setId(6);
        navigationItems.add(5, exitAccount);


        NavigationItem exitApp = new NavigationItem();
        exitApp.setTitle(getString(R.string.exit_app));
        exitApp.setId(7);
        navigationItems.add(6, exitApp);


        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);

        recyclerNavigation.setLayoutManager(layoutManager);
        recyclerNavigation.setHasFixedSize(true);

        NavigationAdapter navigationAdapter = new NavigationAdapter(navigationItems, this);
        recyclerNavigation.setAdapter(navigationAdapter);


    }

    @OnClick(R.id.voucher_card_1)
    public void onVoucherCard1Click() {
        ActivityNavigationHelper.navigateToWebView("https://www.sanjineh.ir/profile/product-detail/" + voucherId3 + "?from=android_cafebazar", MainActivity.this, WebViewActivity.class);
        Log.d(TAG, "https://www.sanjineh.ir/product-detail/" + voucherId3 + "&from=android_cafebazar");
    }

    @OnClick(R.id.voucher_card_2)
    public void onVoucherCard2Click() {
        ActivityNavigationHelper.navigateToWebView("https://www.sanjineh.ir/profile/product-detail/" + voucherId2 + "?from=android_cafebazar", MainActivity.this, WebViewActivity.class);

        Log.d(TAG, "https://www.sanjineh.ir/product-detail/" + voucherId2 + "&from=android_cafebazar");
    }

    @OnClick(R.id.voucher_card_3)
    public void onVoucherCard3Click() {
        ActivityNavigationHelper.navigateToWebView("https://www.sanjineh.ir/profile/product-detail/" + voucherId1 + "?from=android_cafebazar", MainActivity.this, WebViewActivity.class);

        Log.d(TAG, "https://www.sanjineh.ir/product-detail/" + voucherId1 + "&from=android_cafebazar");

    }

    @Override
    public void onTap(Object obj) {

        if (obj instanceof NavigationItem) {
            NavigationItem navigationItem = (NavigationItem) obj;


            switch (navigationItem.getId()) {

                case 0:
                    ActivityNavigationHelper.navigateToActivity(MainActivity.this, MainActivity.class, true);
                    break;


                case 1:


                    ActivityNavigationHelper.navigateToWebView("https://www.sanjineh.ir/profile/" + logoutViewModel.getToken() + "?from=android_cafebazar", this, WebViewActivity.class);

                    break;


                case 2:

                    ActivityNavigationHelper.navigateToActivity(MainActivity.this, ArchiveActivity.class, false);

                    break;


                case 3:
                    ActivityNavigationHelper.navigateToWebView("https://www.sanjineh.ir/news?id=48&from=android_cafebazar", MainActivity.this, WebViewActivity.class);
                    break;


                case 4:

                    ActivityNavigationHelper.navigateToWebView("https://www.sanjineh.ir/contactus?from=android_cafebazar", MainActivity.this, WebViewActivity.class);

                    break;


                case 5:
                    ActivityNavigationHelper.navigateToWebView("https://www.sanjineh.ir/terms?from=android_cafebazar", MainActivity.this, WebViewActivity.class);
                    break;


                case 6:

                    logoutViewModel.logout();

                    ActivityNavigationHelper.navigateToActivity(this, LoginActivity.class, true);

                    break;

                case 7:

                    DialogHelper.sureExit(this);

                    break;




          /*  case 9:
                Toast.makeText(this, "You Clicked on Wallet", Toast.LENGTH_SHORT).show();
                break;
*/
            }
        }

        if (obj instanceof OtherServiceItem) {
            OtherServiceItem otherServiceItem = (OtherServiceItem) obj;

            switch (otherServiceItem.getId()) {

                case 0:
                    ActivityNavigationHelper.navigateToWebView("https://www.sanjineh.ir/news?id=0&from=android_cafebazar", this, WebViewActivity.class);

                    break;


                case 1:

                    ActivityNavigationHelper.navigateToWebView("https://www.sanjineh.ir/news?id=1&from=android_cafebazar", this, WebViewActivity.class);

                    break;


                case 2:
                    ActivityNavigationHelper.navigateToWebView("https://www.sanjineh.ir/news?id=2&from=android_cafebazar", this, WebViewActivity.class);

                    break;

            }
        }

        if (obj instanceof TeachBourseItem) {
            TeachBourseItem teachBourseItem = (TeachBourseItem) obj;

            switch (teachBourseItem.getId()) {

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

        if (obj instanceof NewsItem) {
            NewsItem newsItem = (NewsItem) obj;
            ActivityNavigationHelper.navigateToWebView("https://www.sanjineh.ir/details?id=37&articleId=" + newsItem.getId() + "&from=android_cafebazar", this, WebViewActivity.class);


        }

    }

/*    public void performSearch(View view) {
        String searchTerm = edtTextSearch.getText().toString().trim();
        String searchUrl = "https://www.sanjineh.ir/details?id=37&articleId=37833";
        Toast.makeText(this, searchTerm, Toast.LENGTH_SHORT).show();
        // TODO: 8/21/2019 make a call to farshad with the data
        ActivityNavigationHelper.navigateToWebView(searchUrl, this, WebViewActivity.class);

    }*/


    private void setUpNewsRecycler(List<NewsItem> newsItems) {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, true);

        newsRecyclerView.setLayoutManager(layoutManager);
        newsRecyclerView.setHasFixedSize(true);

        NewsAdapter newsAdapter = new NewsAdapter(this, newsItems, this);
        newsRecyclerView.setAdapter(newsAdapter);
    }

    private void setUpTeachBourseRecycler() {

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, HORIZONTAL, true);

        teachBourseRecyclerView.setLayoutManager(layoutManager);
        teachBourseRecyclerView.setHasFixedSize(true);
        List<TeachBourseItem> teachBourseItems = new ArrayList<>();
        TeachBourseItem ot = new TeachBourseItem();
        TeachBourseItem ot1 = new TeachBourseItem();
        TeachBourseItem ot2 = new TeachBourseItem();
        ot.setTitle("آموزش و آشنایی مقدمات");
        ot.setDrawbleId(R.drawable.bourse_learn);

        ot1.setTitle("راهنمای مختصر ورود و فعالیت");
        ot1.setDrawbleId(R.drawable.bourse_guide);

        ot2.setTitle("درخواست و صدور  مجوز");
        ot2.setDrawbleId(R.drawable.bourse_license);


        teachBourseItems.add(ot);
        teachBourseItems.add(ot1);
        teachBourseItems.add(ot2);

        teachBourseItems.add(ot);
        teachBourseItems.add(ot1);
        teachBourseItems.add(ot2);

        teachBourseItems.add(ot);
        teachBourseItems.add(ot1);
        teachBourseItems.add(ot2);

        TeachBourseAdapter teachBourseAdapter = new TeachBourseAdapter(teachBourseItems, this);
        teachBourseRecyclerView.setAdapter(teachBourseAdapter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FirebaseApp.initializeApp(this);

        Log.d(TAG, "onCreate: created");

        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        ButterKnife.bind(this);
        ((AppController) getApplication()).getAppComponent().inject(this);


        FirebaseMessaging.getInstance().subscribeToTopic(AppConstants.CHANNEL_ID_NOTIFICATON).addOnSuccessListener(aVoid -> {
        });

        setUpOtherServicesRecycler();
        setUpTeachBourseRecycler();
        setUpSpinner();
        initNavigation();

        if (AppConstants.STORE.equals("CAFEBAZAAR")) {
            checkCafeBazaarLogin = new CheckCafeBazaarLogin(MainActivity.this);
            messageReciver();
            bazaarSetup(getScoreViewModel.getMarketKey());
        } else if (AppConstants.STORE.equals("CHARKHOONE")) {
            mCharkhooneIabHelper = new IabHelper(this, AppConstants.base64EncodedPublicKey);

            mCharkhooneIabHelper.startSetup(result -> {
                Log.i("Test", "Setup finished.");

                if (!result.isSuccess()) {
                    return;
                }
                if (mCharkhooneIabHelper == null)
                    return;

            });

        }

        showWating();
        txtUserName.setText(getUserHasSanjinehViewModel.getMobileNumber());
        creditForm.setVisibility(View.VISIBLE);
        creditOtp.setVisibility(View.INVISIBLE);

        if (isSelf) {
            edtMsisdnOthers.setText(getUserHasSanjinehViewModel.getMobileNumber());
        }


        attachViewModel();
        mVoucherListViewModel.callVoucherListViewModel();

       /* if (voucherSize == 0) {
            vouchersLayout.setVisibility(View.GONE);
            dividerVoucher.setVisibility(View.GONE);
        }
*/

        mNewsViewModel.callNewsViewModel();

        Log.d(TAG, "onCreate: GetScoreActivity");
        //  getUserHasSanjinehViewModel.callGetUserSanjinehViewModel();
/*
        edtTextSearch.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                performSearch(null);
                return true;
            }
            return false;
        });
*/

    }

    private void codeSentStringBuilder() {
        String str1 = "کد فعالسازی برای شماره";
        String str2 = "پیامک شده است";
        String result = String.format("%1$s %2$s \n %3$s", str1, mobileNumber, str2);
        txtSentOtp.setText(result);
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
        //  ButtonHelper.toggleAppCompatButtonStatus(btnGetCredit, false);
    }

    private void autoSearch() {
        searchResultArrayList = new ArrayList<>();


        searchResultArrayList.add(new SearchResult(1, "سینا"));
        searchResultArrayList.add(new SearchResult(2, "سنجینه"));
        searchResultArrayList.add(new SearchResult(3, "باران"));
        searchResultArrayList.add(new SearchResult(4, "اخبار"));


        searchResultAdapter = new SearchResultAdapter(this, R.layout.search_result_item_row, searchResultArrayList);

/*
        autoSearch.setAdapter(searchResultAdapter);
*/
    }


    private void choosePaymentType() {
        Log.d(TAG, "choosePaymentType: checking user has sanjineh");
        getUserHasSanjinehViewModel.callGetUserSanjinehViewModel();

    }

/*
    private void setUpCoinRecycler() {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, HORIZONTAL, true);

        coinRecycleView.setLayoutManager(layoutManager);
        coinRecycleView.setHasFixedSize(true);
        List<CoinPrice> coinPriceItems = new ArrayList<>();
        CoinPrice ot = new CoinPrice();
        CoinPrice ot1 = new CoinPrice();


        int[] x = {10, 20, 30, 40, 50, 60, 71, 80, 90, 91};
        int[] y = {100, 200, 300, 400, 500, 600, 701, 800, 900, 910};
        ot.setName("قیمت یورو در این لحظه");
        ot.setDate(x);
        ot.setRate("کاهش 2100 (%1.39)");
        ot.setPrice(y);




*/
/*
        ot1.setName("یورو");
        ot1.setDate(new int[] {10,20,30,40,50,60,71,80,90,91});
        ot1.setRate("20%");
        ot1.setPrice(new int[] {10,20,30,40,50,60,71,80,90,91});*//*



        coinPriceItems.add(ot);
        coinPriceItems.add(ot);
        coinPriceItems.add(ot);
        //coinPriceItems.add(ot1);
*/
/*

        coinPriceItems.add(ot);
        //coinPriceItems.add(ot1);

        coinPriceItems.add(ot);
        //coinPriceItems.add(ot1);
*//*


        CoinAdapter coinAdapter = new CoinAdapter(coinPriceItems, this);
        coinRecycleView.setAdapter(coinAdapter);
    }
*/

    private void setUpOtherServicesRecycler() {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, HORIZONTAL, true);

        otherServiceRecyclerView.setLayoutManager(layoutManager);
        otherServiceRecyclerView.setHasFixedSize(true);
        List<OtherServiceItem> otherServiceItems = new ArrayList<>();
        OtherServiceItem ot = new OtherServiceItem();
        OtherServiceItem ot1 = new OtherServiceItem();
        OtherServiceItem ot2 = new OtherServiceItem();
        ot.setTitle("ساختمان و مسکن");
        ot.setDrawbleId(R.drawable.services_housing);

        ot1.setTitle("حمل و نقل");
        ot1.setDrawbleId(R.drawable.services_transportation);

        ot2.setTitle("شاخص هوا");
        ot2.setDrawbleId(R.drawable.services_geography);


        otherServiceItems.add(ot);
        otherServiceItems.add(ot1);
        otherServiceItems.add(ot2);


        OtherServiceAdapter otherServiceAdapter = new OtherServiceAdapter(otherServiceItems, this);
        otherServiceRecyclerView.setAdapter(otherServiceAdapter);
    }

    @OnClick(R.id.btn_get_credit)
    public void btnGetCreditAction() {
        Log.d(TAG, "btnGetCreditAction: Clicked!");
        String msisdn = edtMsisdnOthers.getText().toString().trim();
        String ntCode = edtNtcodeOthers.getText().toString().trim();
        Log.d(TAG, "btnGetCreditAction: Clicked! msisdn " + msisdn + " ntcoe= " + ntCode);

        if (!(ntCode.length() > 0)) {

            edtNtcodeOthers.setError("کد ملی به درستی وارد نشده است");
            return;
        }

        if (!(msisdn.trim().length() > 0) && !msisdn.startsWith("09")) {

            edtMsisdnOthers.setError("شماره موبایل به درستی وارد نشده است");

            return;

        }
        if (msisdn.length() != 11) {

            edtMsisdnOthers.setError("شماره موبایل به درستی وارد نشده است");

            return;
        }


        if (!Helper.validationNationalCode(ntCode)) {
        /*    txtAlert.setText(getString(R.string.enter_correct_national_code));
            txtAlert.setVisibility(View.VISIBLE);*/
            edtNtcodeOthers.setError("کد ملی به درستی وارد نشده است");
            return;
        }

        Log.d(TAG, "btnGetCreditAction: ");
        if (!ruleIsChecked) {
/*            txtAlert.setText(getString(R.string.accept_nt_code_mobile));
            txtAlert.setVisibility(View.VISIBLE);*/
            Toast.makeText(MainActivity.this, "بخض قوانین و مقررات را تایید کنید", Toast.LENGTH_LONG).show();

            return;
        }

        this.ntcode = ntCode;
        this.mobileNumber = msisdn;

        if (AppConstants.STORE.equals("CAFEBAZAAR") && !alreadyBazaarInited) {
            checkCafeBazaarLogin.initService();
        } else {

            //"bGoa+V7g/yqDXvKRqq+JTFn4uQZbPiQJo4pf9RzJ"
            Log.d(TAG, "btnGetCreditAction: Starting launchPurchaseFlow");

            choosePaymentType();


        }
    }

    private void attachViewModel() {

        userHasSanjinehViewModelObserver();

        newsViewModelObserver();


        getScoreViewModelObserver();

        voucherListViewmodelObserver();
        getScoreCharkhooneViewModelObserver();

    }

    private void voucherListViewmodelObserver() {
        mVoucherListViewModel.getApiSuccessLiveDataResponse().observe(this, this::setUpVouvhers

        );

        mVoucherListViewModel.getApiAuthFailureErrorLiveData().observe(this, volleyError -> {
            Toast.makeText(this, "۱", Toast.LENGTH_SHORT).show();
        });

        mVoucherListViewModel.getApiErrorLiveData().observe(this, volleyError -> {
            goToFailApiPage("ApiError");

        });
        mVoucherListViewModel.getApiServerErrorLiveData().observe(this, volleyError ->

        {
            goToFailApiPage("ServerError");

        });
        mVoucherListViewModel.getApiTimeOutErrorLiveData().observe(this, volleyError ->
                goToFailApiPage("TimeOutError")

        );
        mVoucherListViewModel.getApiClientNetworkErrorLiveData().observe(this, volleyError -> {
            goToFailApiPage("ClientNetworkError");


        });


        mVoucherListViewModel.getApiForbiden403ErrorLiveData().observe(this, volleyError -> {
        });
        mVoucherListViewModel.getApiValidation422ErrorLiveData().observe(this, volleyError -> {
        });
    }

    private void getScoreCharkhooneViewModelObserver() {
        getScoreCharkhooneViewModel.getApiSuccessLiveDataResponse().observe(this, creditScorePreProcess -> {
                    // Log.i("Test registerPurchaseInfoResultDto : " , registerPurchaseInfoResultDto.toString());
                    Log.d(TAG, "getScoreViewModelObserver: success");
                    stopWating();
                    // TODO: 11/4/2019 go to web  load url
                    String url;
                    if (isSelf) {
                        url = "https://creditscore.iconish.ir/report/sendotp?channel=ANDROID&mobile=" + getUserHasSanjinehViewModel.getMobileNumber() + "&nationalCode=" + ntcode;
                    } else {

                        url = "https://creditscore.iconish.ir/report/sendotp?channel=ANDROID&mobile=" + getUserHasSanjinehViewModel.getMobileNumber() + "&nationalCode=" + ntcode + "&otherMobile=" + mobileNumber;
                    }
                    loadURL(url, "report");
                    /* new CreditStatusManager(this).handleReportStatus(creditScorePreProcess, txtGetCreditInfo);*/
                }
        );

        getScoreCharkhooneViewModel.getApiAuthFailureErrorLiveData().observe(this, volleyError -> {
            Toast.makeText(this, "ntcode:" + ntcode + " getApiAuthFailureErrorLiveData: " + mobileNumber, Toast.LENGTH_SHORT).show();
            Log.d(TAG, "getScoreCharkhooneViewModelObserver: 2");


        });
        getScoreCharkhooneViewModel.getApiErrorLiveData().observe(this, volleyError -> {
            Toast.makeText(this, "ntcode:" + ntcode + " getApiErrorLiveData: " + mobileNumber, Toast.LENGTH_SHORT).show();
            Log.d(TAG, "getScoreCharkhooneViewModelObserver: 3");

            goToFailApiPage("ApiError");
        });
        getScoreCharkhooneViewModel.getApiServerErrorLiveData().observe(this, volleyError ->
        {
            Log.d(TAG, "getScoreCharkhooneViewModelObserver: 4");

            Toast.makeText(this, "ntcode:" + ntcode + " getApiServerErrorLiveData: " + mobileNumber, Toast.LENGTH_SHORT).show();
            //goToFailApiPage("ServerError");
        });
        getScoreCharkhooneViewModel.getApiTimeOutErrorLiveData().observe(this, volleyError ->
                {
                    Toast.makeText(this, "ntcode:" + ntcode + " getApiTimeOutErrorLiveData: " + mobileNumber, Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "getScoreCharkhooneViewModelObserver: 5");

                    goToFailApiPage("TimeOutError");
                }
        );
        getScoreCharkhooneViewModel.getApiClientNetworkErrorLiveData().observe(this, volleyError -> {
            Toast.makeText(this, "ntcode:" + ntcode + " getApiClientNetworkErrorLiveData: " + mobileNumber, Toast.LENGTH_SHORT).show();
            Log.d(TAG, "getScoreCharkhooneViewModelObserver: 6");

            goToFailApiPage("ClientNetworkError");
        });

        getScoreCharkhooneViewModel.getApiForbiden403ErrorLiveData().observe(this, volleyError -> {
            Toast.makeText(this, "ntcode:" + ntcode + " getApiForbiden403ErrorLiveData: " + mobileNumber, Toast.LENGTH_SHORT).show();
            Log.d(TAG, "getScoreCharkhooneViewModelObserver: 7");

        });
        getScoreCharkhooneViewModel.getApiValidation422ErrorLiveData().observe(this, volleyError -> {
            Toast.makeText(this, "ntcode:" + ntcode + " getApiValidation422ErrorLiveData: " + mobileNumber, Toast.LENGTH_SHORT).show();
            Log.d(TAG, "getScoreViewModelObserver: 8");

        });
    }


    private void getScoreViewModelObserver() {
        getScoreViewModel.getApiSuccessLiveDataResponse().observe(this, creditScorePreProcess -> {
                    // Log.i("Test registerPurchaseInfoResultDto : " , registerPurchaseInfoResultDto.toString());
                    Log.d(TAG, "getScoreViewModelObserver: success");
                    stopWating();
                    // TODO: 11/4/2019 go to web  load url
                    String url;
                    if (isSelf) {
                        url = "https://creditscore.iconish.ir/report/sendotp?channel=ANDROID&mobile=" + getUserHasSanjinehViewModel.getMobileNumber() + "&nationalCode=" + ntcode;
                    } else {

                        url = "https://creditscore.iconish.ir/report/sendotp?channel=ANDROID&mobile=" + getUserHasSanjinehViewModel.getMobileNumber() + "&nationalCode=" + ntcode + "&otherMobile=" + mobileNumber;
                    }
                    loadURL(url, "report");
                    /* new CreditStatusManager(this).handleReportStatus(creditScorePreProcess, txtGetCreditInfo);*/
                }
        );

        getScoreViewModel.getApiAuthFailureErrorLiveData().observe(this, volleyError -> {
            Toast.makeText(this, "ntcode:" + ntcode + " getApiAuthFailureErrorLiveData: " + mobileNumber, Toast.LENGTH_SHORT).show();
            Log.d(TAG, "getScoreViewModelObserver: 2");


        });
        getScoreViewModel.getApiErrorLiveData().observe(this, volleyError -> {
            Toast.makeText(this, "ntcode:" + ntcode + " getApiErrorLiveData: " + mobileNumber, Toast.LENGTH_SHORT).show();
            Log.d(TAG, "getScoreViewModelObserver: 3");

            goToFailApiPage("ApiError");
        });
        getScoreViewModel.getApiServerErrorLiveData().observe(this, volleyError ->
        {
            Log.d(TAG, "getScoreViewModelObserver: 4");

            Toast.makeText(this, "ntcode:" + ntcode + " getApiServerErrorLiveData: " + mobileNumber, Toast.LENGTH_SHORT).show();
            //goToFailApiPage("ServerError");
        });
        getScoreViewModel.getApiTimeOutErrorLiveData().observe(this, volleyError ->
                {
                    Toast.makeText(this, "ntcode:" + ntcode + " getApiTimeOutErrorLiveData: " + mobileNumber, Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "getScoreViewModelObserver: 5");

                    goToFailApiPage("TimeOutError");
                }
        );
        getScoreViewModel.getApiClientNetworkErrorLiveData().observe(this, volleyError -> {
            Toast.makeText(this, "ntcode:" + ntcode + " getApiClientNetworkErrorLiveData: " + mobileNumber, Toast.LENGTH_SHORT).show();
            Log.d(TAG, "getScoreViewModelObserver: 6");

            goToFailApiPage("ClientNetworkError");
        });

        getScoreViewModel.getApiForbiden403ErrorLiveData().observe(this, volleyError -> {
            Toast.makeText(this, "ntcode:" + ntcode + " getApiForbiden403ErrorLiveData: " + mobileNumber, Toast.LENGTH_SHORT).show();
            Log.d(TAG, "getScoreViewModelObserver: 7");

        });
        getScoreViewModel.getApiValidation422ErrorLiveData().observe(this, volleyError -> {
            Toast.makeText(this, "ntcode:" + ntcode + " getApiValidation422ErrorLiveData: " + mobileNumber, Toast.LENGTH_SHORT).show();
            Log.d(TAG, "getScoreViewModelObserver: 8");

        });
    }

    private void newsViewModelObserver() {
        mNewsViewModel.getApiSuccessLiveDataResponse().observe(this, newsItems -> {
                    setUpNewsRecycler(newsItems);
                    stopWating();
                }
        );

        mNewsViewModel.getApiAuthFailureErrorLiveData().observe(this, volleyError -> {

        });
        mNewsViewModel.getApiErrorLiveData().observe(this, volleyError -> {
            goToFailApiPage("ApiError");
        });
        mNewsViewModel.getApiServerErrorLiveData().observe(this, volleyError ->
        {
            //goToFailApiPage("ServerError");
        });
        mNewsViewModel.getApiTimeOutErrorLiveData().observe(this, volleyError ->
                {
                    goToFailApiPage("TimeOutError");
                }
        );
        mNewsViewModel.getApiClientNetworkErrorLiveData().observe(this, volleyError -> {
            goToFailApiPage("ClientNetworkError");
        });

        mNewsViewModel.getApiForbiden403ErrorLiveData().observe(this, volleyError -> {
        });
    }

    private void userHasSanjinehViewModelObserver() {
        getUserHasSanjinehViewModel.getApiSuccessLiveDataResponse().observe(this, numberOfSanjineh -> {

                    stopWating();
                    this.numberOfSanjineh = numberOfSanjineh;
                    if (userHasSanjineh()) {
                        int sanjinehValue = (this.numberOfSanjineh.getBalance()) / (this.numberOfSanjineh.getUnitValue());
                        String toastMesasge = " شما دارای " + sanjinehValue + " سنجینه در کیف پول خود هستید ";
                        Toast.makeText(this, toastMesasge, Toast.LENGTH_LONG).show();

                        String purcahseStoreText = "";
                        String purcahseStoreQuestionText = "";


                        if ("CHARKHOONE".equals(STORE)) {
                            purcahseStoreText = "چارخونه";
                            purcahseStoreQuestionText = "پرداخت از کیف پول یا چارخونه؟";
                        } else if ("CAFEBAZAAR".equals(STORE)) {
                            purcahseStoreText = "کافه بازار";
                            purcahseStoreQuestionText = "پرداخت از کیف پول یا کافه بازار؟";
                        }


                        DialogHelper.showDialog("نحوه پرداخت", purcahseStoreQuestionText, "کیف پول", purcahseStoreText, this, this);


                    } else {
                        Log.d(TAG, "choosePaymentType: USER HAS NO SANJINEH");
                        startPurchase();
                    }
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
        getUserHasSanjinehViewModel.getApiClientNetworkErrorLiveData().observe(this, volleyError -> goToFailApiPage("ClientNetworkError"));

        getUserHasSanjinehViewModel.getApiForbiden403ErrorLiveData().observe(this, volleyError -> {
        });
        getUserHasSanjinehViewModel.getApiValidation422ErrorLiveData().observe(this, volleyError -> {
        });

        getUserHasSanjinehViewModel.getApiValidation422ErrorLiveData().observe(this, volleyError -> {
        });
    }


    private void loadURL(String url, String bundle) {

        Log.d(TAG, "loadURL: " + bundle);

        ActivityNavigationHelper.navigateToWebView(url, MainActivity.this, WebViewActivity.class, bundle);
    }


    private void setUpVouvhers(List<Voucher> vouchers) {

        voucherSize = vouchers.size();
        if (vouchers.size() == 0) {
            return;
        }


        for (int i = 0; i < vouchers.size(); i++) {
            voucherId1 = vouchers.get(i).getId();
            voucherTxt1.setText(vouchers.get(i).getDescription());

        }
      /*  voucherId1 = vouchers.get(2).getId();
        voucherId2 = vouchers.get(1).getId();
        voucherId3 = vouchers.get(0).getId();

        voucherTxt1.setText(vouchers.get(2).getDescription());
        voucherTxt2.setText(vouchers.get(1).getDescription());
        voucherTxt3.setText(vouchers.get(0).getDescription());
*/
/*

        long price1 = Long.valueOf(vouchers.get(0).getPrice());
        long price2 = Long.valueOf(vouchers.get(1).getPrice());
        long price3 = Long.valueOf(vouchers.get(2).getPrice());

        long off1 = Long.valueOf(vouchers.get(0).getDiscount());
        long off2 = Long.valueOf(vouchers.get(1).getDiscount());
        long off3 = Long.valueOf(vouchers.get(2).getDiscount());

        if (off1 == 0) off1 = 1;
        if (off2 == 0) off2 = 1;
        if (off3 == 0) off3 = 1;


        priceVoucher1Original.setText(price1 + "");
        priceVoucher2Original.setText(price2 + "");
        priceVoucher3Original.setText(price3 + "");

        priceVoucher1Off.setText(price1 / off1 + "");
        priceVoucher2Off.setText(price2 / off2 + "");
        priceVoucher3Off.setText(price3 / off3 + "");

        priceVoucher1Original.setPaintFlags(priceVoucher1Original.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        priceVoucher2Original.setPaintFlags(priceVoucher2Original.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        priceVoucher3Original.setPaintFlags(priceVoucher3Original.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
*/

     /*   Glide.with(this).load(vouchers.get(0).getImageUrl()).into(voucherImg1);
        Glide.with(this).load(vouchers.get(1).getImageUrl()).into(voucherImg2);
        Glide.with(this).load(vouchers.get(2).getImageUrl()).into(voucherImg3);*/


    }

    @Override
    protected void onResume() {
        super.onResume();
        codeSentStringBuilder();
        creditForm.setVisibility(View.VISIBLE);
        creditOtp.setVisibility(View.INVISIBLE);

        edtTextOtp.setText("");
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
        if (mCafeIabHelper != null) {
            Crashlytics.setString("flagEndAsync", "mCafeIabHelper.flagEndAsync();");
            mCafeIabHelper.flagEndAsync();
        }
        Log.d(TAG, "startPurchase: starting launchepurchaseflow");
        //   Toast.makeText(MainActivity.this, "startPurchase" + mCafeIabHelper.toString(), Toast.LENGTH_SHORT).show();


        if ("CAFEBAZAAR".equals(STORE)) {

            mCafeIabHelper.launchPurchaseFlow(this, AppConstants.BAZAAR_SKU, 10001, mPurchaseFinishedListener, randomUUIDString);

        } else if ("CHARKHOONE".equals(STORE)) {
            try {
                mCharkhooneIabHelper.launchPurchaseFlow(this, AppConstants.CHARKHOONE_SKU, 10001, mCharkhoonePurchaseFinishListener, randomUUIDString);
            } catch (IabHelper.IabAsyncInProgressException e) {
                e.printStackTrace();
            }
        }


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if ("CHARKHOONE".equals(STORE)) {
            Log.i("Test", "onActivityResult(" + requestCode + "," + resultCode + "," + data);
            if (mCharkhooneIabHelper == null) return;

            // Pass on the activity result to the helper for handling
            if (!mCharkhooneIabHelper.handleActivityResult(requestCode, resultCode, data)) {
                // not handled, so handle it ourselves (here's where you'd
                // perform any handling of activity results not related to in-app
                // billing...
                super.onActivityResult(requestCode, resultCode, data);
            } else {
                Log.i("Test", "onActivityResult handled by IABUtil.");
            }
        } else if ("CAFEBAZAAR".equals(STORE)) {
            Log.i("Test", "onActivityResult(" + requestCode + "," + resultCode + "," + data);
            if (mCafeIabHelper == null) {
                return;
            }

            // Pass on the activity result to the helper for handling
            if (!mCafeIabHelper.handleActivityResult(requestCode, resultCode, data)) {
                super.onActivityResult(requestCode, resultCode, data);
            } else {
                Log.i("Test", "onActivityResult handled by IABUtil.");
            }

        }


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
/*

        getScoreViewModel.callGetScoreCafeBazaarViewModel(null, edtNtcodeOthers.getText().toString(), isSelf ? 1 : 2, 1, AppConstants.PAYMENT_TYPE, AppConstants.CHANNEL_ID, -1, purchase);
*/
        String url;
        if (isSelf) {
            url = "https://creditscore.iconish.ir/report/sendotp?channel=ANDROID&mobile=" + getUserHasSanjinehViewModel.getMobileNumber() + "&nationalCode=" + ntcode;
        } else {

            url = "https://creditscore.iconish.ir/report/sendotp?channel=ANDROID&mobile=" + getUserHasSanjinehViewModel.getMobileNumber() + "&nationalCode=" + ntcode + "&otherMobile=" + mobileNumber;
        }


        loadURL(url, "report");

    }


    //GoCafebazaar
    @Override
    public void onDialogCancelEvent(Object object) {
        Log.d(TAG, "onDialogCancelEvent: ");
        if (mCafeIabHelper != null) {
            mCafeIabHelper.flagEndAsync();
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

                if ("checkbazaarlogin".equals(action)) {
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
                }


            }
        };
        registerReceiver(broadcastReceiver, filter);
    }


    @OnClick(R.id.btn_otp_submit)
    public void setBtnOtpSubmitClick() {
        Log.d(TAG, "setBtnOtpSubmitClick: " + edtTextOtp.getText());
        if (edtTextOtp.length() == 4) {
            showWating();

            int verifyCode = Integer.parseInt(edtTextOtp.getText().toString());
//            getScoreViewModel.callGetScoreCafeBazaarViewModel(mobileNumber, ntcode, 2, 1, AppConstants.PAYMENT_TYPE, AppConstants.CHANNEL_ID, verifyCode, purchase);
            Log.d(TAG, "btnGetScoreAction: VerifyCode" + verifyCode);

            //confirmVerifyCodeViewModel.callConfirmVerifyCodeViewModel(msisdn,edtVerifyCodeOthers.getText().toString());
        }
    }

    private void bazaarSetup(String bazaarKey) {

        // You can find it in your Bazaar console, in the Dealers section.
        // It is recommended to add more security than just pasting it in your source code;
        mCafeIabHelper = new CafeIabHelper(this, bazaarKey);

        Log.i("Test", "Starting setup.");

        mCafeIabHelper.startSetup(result -> {
            Log.d("Test", "Setup finished.");

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

            Log.d("Test", "Hooray, IAB is fully set up" + result);

            // Hooray, IAB is fully set up!
            mCafeIabHelper.queryInventoryAsync(mGotInventoryListener);
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
            edtMsisdnOthers.setEnabled(false);

            edtMsisdnOthers.setText(getUserHasSanjinehViewModel.getMobileNumber());

        } else if (i == 1) {
            isSelf = false;
            edtMsisdnOthers.setEnabled(true);
            edtMsisdnOthers.setText("");
        } else {
            Toast.makeText(this, "خطای ناشناخته", Toast.LENGTH_SHORT).show();
        }
        // Showing selected spinner item

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }


    @OnClick(R.id.vouchers_see_all)
    public void onVoucherSeeAllClick() {
        ActivityNavigationHelper.navigateToWebView("https://www.sanjineh.ir/news?id=3&from=android_cafebazar", this, WebViewActivity.class);
    }


    @OnClick(R.id.teach_see_all)
    public void onTeachBourseSeeAllClick() {
        /* ActivityNavigationHelper.navigateToWebView("https://www.sanjineh.ir/profile/edit_profile&from=android_cafebazar", this, WebViewActivity.class);*/
    }

    @OnClick(R.id.news_see_all)
    public void onNewsSeeAllClick() {
        ActivityNavigationHelper.navigateToWebView("https://www.sanjineh.ir/news?id=37&from=android_cafebazar", this, WebViewActivity.class);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mCafeIabHelper != null) mCafeIabHelper.dispose();
        mCafeIabHelper = null;
    }
}
