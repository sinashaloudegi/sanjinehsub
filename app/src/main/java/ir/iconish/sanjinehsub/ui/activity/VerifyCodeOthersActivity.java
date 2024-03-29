package ir.iconish.sanjinehsub.ui.activity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ir.iconish.sanjinehsub.R;
import ir.iconish.sanjinehsub.bazaar.CheckCafeBazaarLogin;
import ir.iconish.sanjinehsub.config.AppController;
import ir.iconish.sanjinehsub.data.vm.ConfirmVerifyCodeViewModel;
import ir.iconish.sanjinehsub.data.vm.GetScoreViewModel;
import ir.iconish.sanjinehsub.ui.ActivityNavigationHelper;
import ir.iconish.sanjinehsub.util.AppConstants;
import ir.iconish.sanjinehsub.util.ButtonHelper;
import ir.iconish.sanjinehsub.util.CafeIabHelper;
import ir.iconish.sanjinehsub.util.CreditStatusManager;
import ir.iconish.sanjinehsub.util.Purchase;

public class VerifyCodeOthersActivity extends AppCompatActivity {

    private static final String TAG = "_SCORE";
    public static CafeIabHelper mHelper;
    @Nullable
    @BindView(R.id.btnEnterVerifyCodeOthers)
    AppCompatButton btnEnterVerifyCodeOthers;
    @Nullable
    @BindView(R.id.txtAlert)
    TextView txtAlert;
    @Nullable
    @BindView(R.id.edtVerifyCodeOthers)
    EditText edtVerifyCodeOthers;
    @Nullable
    @BindView(R.id.prgVerifyCodeOthers)
    ProgressBar prgVerifyCodeOthers;
    @Nullable
    @BindView(R.id.imgBack)
    ImageView imgBack;
    @Inject
    ConfirmVerifyCodeViewModel confirmVerifyCodeViewModel;
    @Inject
    GetScoreViewModel getScoreViewModel;
    CheckCafeBazaarLogin checkCafeBazaarLogin;
    int reportStatus;
    @Nullable
    String msisdn = null;
    @Nullable
    String ntcode = null;
    @Nullable
    Purchase purchase = null;
    private boolean alreadyBazaarInited = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verfiy_code_others);
        ButterKnife.bind(this);
        ((AppController) getApplication()).getAppComponent().inject(this);
        Intent intent = getIntent();
        msisdn = intent.getStringExtra("otherMobile");
        reportStatus = intent.getIntExtra("reportStatus", 0);
        ntcode = intent.getStringExtra("otherNtCode");
        purchase = (Purchase) intent.getSerializableExtra("purchase");
        checkCafeBazaarLogin = new CheckCafeBazaarLogin(VerifyCodeOthersActivity.this);
        //messageReciver();
        attachViewModel();

    }

    @OnClick(R.id.btnEnterVerifyCodeOthers)
    public void btnGetScoreAction() {

        if (edtVerifyCodeOthers.length() == 4) {
            showWating();

            int verifyCode = Integer.parseInt(edtVerifyCodeOthers.getText().toString());
            getScoreViewModel.callGetScoreCafeBazaarViewModel(msisdn, ntcode, 2, 1, AppConstants.PAYMENT_TYPE, AppConstants.CHANNEL_ID, verifyCode, purchase);
            Log.d(TAG, "btnGetScoreAction: VerifyCode" + verifyCode);

            //confirmVerifyCodeViewModel.callConfirmVerifyCodeViewModel(msisdn,edtVerifyCodeOthers.getText().toString());
        }
    }

    @OnClick(R.id.imgBack)
    public void imgBackAction() {
        onBackPressed();
    }

    @Override
    public void onBackPressed() {
        startActivity();
    }


    private void attachViewModel() {


        getScoreViewModel.getApiSuccessLiveDataResponse().observe(this, creditScorePreProcess -> {


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
        prgVerifyCodeOthers.setVisibility(View.VISIBLE);
        ButtonHelper.toggleAppCompatButtonStatus(btnEnterVerifyCodeOthers, false);
    }

    private void stopWating() {
        prgVerifyCodeOthers.setVisibility(View.INVISIBLE);
        ButtonHelper.toggleAppCompatButtonStatus(btnEnterVerifyCodeOthers, true);
    }


    @Override
    public void onDestroy() {
        if (this != null) {
            this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_USER);
        }
        super.onDestroy();


    }


    boolean verifyDeveloperPayload(@NonNull Purchase p) {
        String payload = p.getDeveloperPayload();
        return true;
    }


    private void startActivity() {
        ActivityNavigationHelper.navigateToActivity(this, GetScoreOthersActivity.class, true);

    }

    private void getScoreAtion(String reqToken) {
        String url = "https://www.sanjineh.ir/report/" + reqToken;
        if (reportStatus == 22) {
            // url=
        }

        ActivityNavigationHelper.navigateToWebView(url, VerifyCodeOthersActivity.this, WebViewActivity.class);
        finish();
    }


}
