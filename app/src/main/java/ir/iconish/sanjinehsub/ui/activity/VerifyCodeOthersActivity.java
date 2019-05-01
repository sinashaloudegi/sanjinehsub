package ir.iconish.sanjinehsub.ui.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.UUID;

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
import ir.iconish.sanjinehsub.util.CreditStatusManager;
import ir.iconish.sanjinehsub.util.IabHelper;
import ir.iconish.sanjinehsub.util.IabResult;
import ir.iconish.sanjinehsub.util.Inventory;
import ir.iconish.sanjinehsub.util.Purchase;
import ir.iconish.sanjinehsub.util.ToastHelper;

public class VerifyCodeOthersActivity extends AppCompatActivity {

  @BindView(R.id.btnEnterVerifyCodeOthers)
  AppCompatButton btnEnterVerifyCodeOthers;



  @BindView(R.id.txtAlert)
  TextView txtAlert;





  @BindView(R.id.edtVerifyCodeOthers)
  EditText edtVerifyCodeOthers;
  @BindView(R.id.prgVerifyCodeOthers)
  ProgressBar prgVerifyCodeOthers;
  @BindView(R.id.imgBack)
  ImageView imgBack;


  public static IabHelper mHelper;
  BroadcastReceiver broadcastReceiver;




  @Inject
  ConfirmVerifyCodeViewModel confirmVerifyCodeViewModel;

  @Inject
  GetScoreViewModel getScoreViewModel;

  CheckCafeBazaarLogin checkCafeBazaarLogin;
  private boolean alreadyBazaarInited = false;
int reportStatus;
  String msisdn = null;
  String ntcode = null;
  Purchase purchase = null;


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_verfiy_code_others);
    ButterKnife.bind(this);
    ((AppController) getApplication()).getAppComponent().inject(this);
    Intent intent = getIntent();
    msisdn = intent.getStringExtra("otherMobile");
    reportStatus = intent.getIntExtra("reportStatus",0);
    ntcode = intent.getStringExtra("otherNtCode");
    purchase= (Purchase) intent.getSerializableExtra("purchase");
    checkCafeBazaarLogin = new CheckCafeBazaarLogin(VerifyCodeOthersActivity.this);
    //messageReciver();
    attachViewModel();

  }


  @OnClick(R.id.btnEnterVerifyCodeOthers)
  public void btnGetScoreAction() {

    if(edtVerifyCodeOthers.length()==4) {
      showWating();

      int verifyCode=Integer.parseInt(edtVerifyCodeOthers.getText().toString());
      getScoreViewModel.callGetScoreViewModel(msisdn, ntcode, 2, 1, AppConstants.PAYMENT_TYPE, AppConstants.CHANNEL_ID,verifyCode, purchase);

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
             // Log.i("Test registerPurchaseInfoResultDto : " , registerPurchaseInfoResultDto.toString());


              stopWating();

      new CreditStatusManager(this).handleReportStatus(creditScorePreProcess,txtAlert);

           /*   if(registerPurchaseInfoResultDto.getMarketResultDto().getMarketResultEnumId()== ReportStateEnum.SEND_VERIFY_CODE.getId() ||registerPurchaseInfoResultDto.getMarketResultDto().getMarketResultEnumId()== CreditResponseEnum.SUCCESS.getId()){
                getScoreAtion(registerPurchaseInfoResultDto.getReqToken());}
              else {
                String error=CreditResponseEnum.fromValue(new Long(registerPurchaseInfoResultDto.getMarketResultDto().getMarketResultEnumId())).getValue();
                ToastHelper.showErrorMessage(VerifyCodeOthersActivity.this,error);
              //  txtAlert.setText(registerPurchaseInfoResultDto.getMarketResultDto().getMarketResultEnumId()+":"+error);
               // txtAlert.setVisibility(View.VISIBLE);
              }*/
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









   /* confirmVerifyCodeViewModel.getApiSuccessLiveDataResponse().observe(this, reportStateEnumId -> {
      if (reportStateEnumId == 12){
        if (!alreadyBazaarInited) {
          checkCafeBazaarLogin.initService();
        }
        else {
          UUID uuid = UUID.randomUUID();
          String randomUUIDString = uuid.toString();
          Log.i("Test", "randomUUIDString : " + randomUUIDString);
          //"bGoa+V7g/yqDXvKRqq+JTFn4uQZbPiQJo4pf9RzJ"
          mHelper.launchPurchaseFlow(VerifyCodeOthersActivity.this, "sanj01", 10001, mPurchaseFinishedListener, randomUUIDString);

        }
      }
      else {
        Toast.makeText(VerifyCodeOthersActivity.this, R.string.retry_others_otp, Toast.LENGTH_LONG).show();
      }
      }
    );
    confirmVerifyCodeViewModel.getApiAuthFailureErrorLiveData().observe(this, volleyError -> {
    });
    confirmVerifyCodeViewModel.getApiErrorLiveData().observe(this, volleyError -> {
      goToFailApiPage("ApiError");
    });
    confirmVerifyCodeViewModel.getApiServerErrorLiveData().observe(this, volleyError ->
    {
      goToFailApiPage("ServerError");
    });
    confirmVerifyCodeViewModel.getApiTimeOutErrorLiveData().observe(this, volleyError ->
      {
        goToFailApiPage("TimeOutError");
      }
    );
    confirmVerifyCodeViewModel.getApiClientNetworkErrorLiveData().observe(this, volleyError -> {
      goToFailApiPage("ClientNetworkError");
    });

    confirmVerifyCodeViewModel.getApiForbiden403ErrorLiveData().observe(this, volleyError -> {
    });
    confirmVerifyCodeViewModel.getApiValidation422ErrorLiveData().observe(this, volleyError -> {
    });




    getScoreViewModel.getApiSuccessLiveDataResponse().observe(this, registerPurchaseInfoResultDto -> {
        Log.i("Test registerPurchaseInfoResultDto : " , registerPurchaseInfoResultDto.toString());
        stopWating();
        getScoreAtion(registerPurchaseInfoResultDto.getReqToken(), registerPurchaseInfoResultDto.getMarketResultDto().getMarketResultEnumId());
        Log.e("success", "in activity");
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
    prgVerifyCodeOthers.setVisibility(View.VISIBLE);
    ButtonHelper.toggleAppCompatButtonStatus(btnEnterVerifyCodeOthers, false);
  }

  private void stopWating() {
    prgVerifyCodeOthers.setVisibility(View.INVISIBLE);
    ButtonHelper.toggleAppCompatButtonStatus(btnEnterVerifyCodeOthers, true);
  }







  @Override
  public void onDestroy() {
    if (this != null)
      this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_USER);
    super.onDestroy();




  }



  boolean verifyDeveloperPayload(Purchase p) {
    String payload = p.getDeveloperPayload();
    return true;
  }


  private void startActivity(){
    ActivityNavigationHelper.navigateToActivity(this,GetScoreOthersActivity.class,true);

  }

  private void getScoreAtion(String reqToken){
    String url="https://www.sanjineh.ir/report/" + reqToken;
    if(reportStatus==22){
     // url=
    }

    ActivityNavigationHelper.navigateToWebView(url, VerifyCodeOthersActivity.this, WebViewActivity.class);
    finish();
  }



}
