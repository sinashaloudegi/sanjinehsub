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

import java.util.UUID;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ir.iconish.sanjinehsub.R;
import ir.iconish.sanjinehsub.bazaar.CheckCafeBazaarLogin;
import ir.iconish.sanjinehsub.config.AppController;
import ir.iconish.sanjinehsub.data.model.CreditResponseEnum;
import ir.iconish.sanjinehsub.data.vm.GetScoreViewModel;
import ir.iconish.sanjinehsub.data.vm.SendVerifyCodeViewModel;
import ir.iconish.sanjinehsub.ui.ActivityNavigationHelper;
import ir.iconish.sanjinehsub.util.AppConstants;
import ir.iconish.sanjinehsub.util.ButtonHelper;
import ir.iconish.sanjinehsub.util.IabHelper;
import ir.iconish.sanjinehsub.util.IabResult;
import ir.iconish.sanjinehsub.util.Inventory;
import ir.iconish.sanjinehsub.util.Purchase;
import ir.iconish.sanjinehsub.util.ToastHelper;

public class GetScoreOthersActivity extends AppCompatActivity {

  @BindView(R.id.edtNtcodeOthers)
  EditText edtNtcodeOthers;
  @BindView(R.id.edtMsisdnOthers)
  EditText edtMsisdnOthers;
  @BindView(R.id.btnGetScoreOthers)
  AppCompatButton btnGetScoreOthers;
  @BindView(R.id.prgGetScoreOthers)
  ProgressBar prgGetScoreOthers;
  @BindView(R.id.imgBack)
  ImageView imgBack;

  @BindView(R.id.txtAlert)
  TextView txtAlert;




  @Inject
  GetScoreViewModel getScoreViewModel;





  Purchase purchase;



  CheckCafeBazaarLogin checkCafeBazaarLogin;
  public static IabHelper mHelper;
  @Inject
  SendVerifyCodeViewModel sendVerifyCodeViewModel;
  BroadcastReceiver broadcastReceiver;

  private boolean alreadyBazaarInited = false;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_get_score_others);
    ButterKnife.bind(this);
    ((AppController) getApplication()).getAppComponent().inject(this);
    checkCafeBazaarLogin = new CheckCafeBazaarLogin(this);
    messageReciver();
    bazaarSetup(getScoreViewModel.getMarketKey());
    attachViewModel();
  }
  private void  bazaarSetup(String bazaarKey){



    mHelper = new IabHelper(this, bazaarKey);

    Log.i("Test", "Starting setup.");

    mHelper.startSetup(result -> {
      Log.i("Test", "Setup finished.");

      if (!result.isSuccess()) {
        ToastHelper.showErrorMessage(this,getString(R.string.bazar_setup_fail));
        txtAlert.setText(getString(R.string.bazar_setup_fail));
        txtAlert.setVisibility(View.VISIBLE);
        // Oh noes, there was a problem.
        Log.i("Test", "Problem setting up In-app Billing: " + result);
      }
      else {
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
    if (mHelper == null) return;

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

    if(!(edtMsisdnOthers.getText().toString().trim().length() > 0) && !(edtNtcodeOthers.getText().toString().trim().length() > 0)){
      txtAlert.setText(getString(R.string.force_enter_phone_ntcode));
      txtAlert.setVisibility(View.VISIBLE);
      return;
    }
    if ((edtMsisdnOthers.getText().toString().trim().length() > 0) && !(edtNtcodeOthers.getText().toString().trim().length() > 0)) {
      txtAlert.setText(getString(R.string.force_enter_phone));
      txtAlert.setVisibility(View.VISIBLE);
      return;
    }
     if ((edtNtcodeOthers.getText().toString().trim().length() > 0) && !(edtMsisdnOthers.getText().toString().trim().length() > 0)) {
       txtAlert.setText(getString(R.string.force_enter_ntcode));
       txtAlert.setVisibility(View.VISIBLE);
      return;
    }
    if ((edtMsisdnOthers.getText().toString().trim().length() > 0) && !edtMsisdnOthers.getText().toString().startsWith("09")) {
      txtAlert.setText(getString(R.string.enter_correct_mobile_phone));
      txtAlert.setVisibility(View.VISIBLE);
      return;

    }
    if ((edtMsisdnOthers.getText().toString().trim().length() > 0) && edtMsisdnOthers.getText().toString().trim().length() != 11) {
      txtAlert.setText(getString(R.string.valid_phone));
      txtAlert.setVisibility(View.VISIBLE);
      return;
    }

     // String ntcode = edtNtcodeOthers.getText().toString();
     // String ownermobile = edtMsisdnOthers.getText().toString();

      //showWating();
     // sendVerifyCodeViewModel.callSendVerifyCodeViewModel(ntcode, ownermobile);
    if (!alreadyBazaarInited) {
      checkCafeBazaarLogin.initService();
    }
    else {
      UUID uuid = UUID.randomUUID();
      String randomUUIDString = uuid.toString();
      Log.i("Test", "randomUUIDString : " + randomUUIDString);
      //"bGoa+V7g/yqDXvKRqq+JTFn4uQZbPiQJo4pf9RzJ"
      mHelper.launchPurchaseFlow(GetScoreOthersActivity.this, "sanj01", 10001, mPurchaseFinishedListener, randomUUIDString);

    }
    }

  @Override
  public void onDestroy() {
    if (GetScoreOthersActivity.this != null)
      GetScoreOthersActivity.this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_USER);
    super.onDestroy();
if(broadcastReceiver!=null)
  unregisterReceiver(broadcastReceiver);

    try {
      checkCafeBazaarLogin.releaseService();
    }
    catch (Exception e){

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

    sendVerifyCodeViewModel.getApiSuccessLiveDataResponse().observe(this, verifyCodeOthersResponse -> {
      stopWating();
      if (verifyCodeOthersResponse.getStatusCode() == 12||verifyCodeOthersResponse.getStatusCode() == 21||verifyCodeOthersResponse.getStatusCode() == 22||verifyCodeOthersResponse.getStatusCode() == 24 ) {
        Log.i("Test", "sent otp to others");
        Intent intent = new Intent(this, VerifyCodeOthersActivity.class);
        intent.putExtra("otherMobile", edtMsisdnOthers.getText().toString());
        intent.putExtra("otherNtCode", edtNtcodeOthers.getText().toString());
        intent.putExtra("reportStatus", verifyCodeOthersResponse.getStatusCode());
        intent.putExtra("purchase", purchase);
        startActivity(intent);
        finish();
      }else {
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


  private void messageReciver(){
    IntentFilter filter = new IntentFilter();

    filter.addAction("checkbazaarlogin");

    broadcastReceiver= new BroadcastReceiver() {
      @Override
      public void onReceive(Context context, Intent intent) {

        String action=intent.getAction();

        switch (action) {


          case "checkbazaarlogin":
            boolean cafeBazarLogin = intent.getBooleanExtra("checkbazaarlogin",false);
            if (cafeBazarLogin){
              alreadyBazaarInited = true;
              UUID uuid = UUID.randomUUID();
              String randomUUIDString = uuid.toString();
              Log.i("Test", "randomUUIDString : " + randomUUIDString);
              //"bGoa+V7g/yqDXvKRqq+JTFn4uQZbPiQJo4pf9RzJ"
              mHelper.launchPurchaseFlow(GetScoreOthersActivity.this, "sanj01", 10001, mPurchaseFinishedListener, randomUUIDString);

            }
            else {
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




  IabHelper.OnIabPurchaseFinishedListener mPurchaseFinishedListener = new IabHelper.OnIabPurchaseFinishedListener() {
    public void onIabPurchaseFinished(IabResult result, Purchase purchase)
    {
      GetScoreOthersActivity.this.purchase=purchase;
      Log.e("Test", "Purchase finished: " + result + ", purchase: " + purchase);


      if (mHelper == null) {
        return;
      }

      if (result.isFailure()) {
        return;
      }
      if (!verifyDeveloperPayload(purchase)) {
        Log.e("Test","Error purchasing. Authenticity verification failed.");
        return;
      }
      Log.e("Test", "Purchase successful.");


      if (purchase.getSku().equals("sanj01")) {
        Log.e("Test", "Purchase is gas. Starting sanj01 consumption.");
        mHelper.consumeAsync(purchase, mConsumeFinishedListener);
      }
      showWating();
       sendVerifyCodeViewModel.callSendVerifyCodeViewModel(edtNtcodeOthers.getText().toString(),edtMsisdnOthers.getText().toString());

      //getScoreViewModel.callGetScoreViewModel(purchase,edtMsisdnOthers.getText().toString(),edtNtcodeOthers.getText().toString());
      //getScoreViewModel.callGetScoreViewModel(edtMsisdnOthers.getText().toString(),edtNtcodeOthers.getText().toString(),2,1, AppConstants.PAYMENT_TYPE,AppConstants.CHANNEL_ID,purchase);

    }
  };








  boolean verifyDeveloperPayload(Purchase p) {
    String payload = p.getDeveloperPayload();
    return true;
  }

  public IabHelper.QueryInventoryFinishedListener mGotInventoryListener = new IabHelper.QueryInventoryFinishedListener() {
    public void onQueryInventoryFinished(IabResult result, Inventory inventory) {
      Log.e("Test", "Query inventory finished.");
      if (mHelper == null) return;

      if (result.isFailure()) {
        Log.e("Test", "Failed to query inventory: " + result);
        return;
      }

      if (inventory != null){
        Purchase gasPurchase = inventory.getPurchase("sanj01");
        if (gasPurchase != null && verifyDeveloperPayload(gasPurchase)) {
          Log.e("Test", "We have sanj01. Consuming it.");
          mHelper.consumeAsync(inventory.getPurchase("sanj01"), mConsumeFinishedListener);
          return;
        }
      }

      Log.e("Test", "Initial inventory query finished; enabling main UI.");
    }
  };




  IabHelper.OnConsumeFinishedListener mConsumeFinishedListener = new IabHelper.OnConsumeFinishedListener() {
    public void onConsumeFinished(Purchase purchase, IabResult result) {
      Log.e("Test", "Consumption finished. Purchase: " + purchase + ", result: " + result);

      if (mHelper == null) return;
      if (result.isSuccess()) {
        Log.e("Test", "Consumption successful. Provisioning.");
      }
      else {
        Log.e("Test", "Error while consuming: " + result);
      }
      Log.e("Test",  "End consumption flow.");
    }
  };
  private void getScoreAtion(String reqToken, int statusCode){
    String url = "https://www.sanjineh.ir/report/" + reqToken;
    ActivityNavigationHelper.navigateToWebView(url, GetScoreOthersActivity.this, WebViewActivity.class);
    finish();
  }
}
