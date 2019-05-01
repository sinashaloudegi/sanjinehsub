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
import ir.iconish.sanjinehsub.data.model.AppConfig;
import ir.iconish.sanjinehsub.data.model.CreditResponseEnum;
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

public class GetScoreActivity extends AppCompatActivity {

  @BindView(R.id.btnGetScore)
  AppCompatButton btnGetScore;

  @BindView(R.id.prgGetScore)
  ProgressBar prgGetScore;
  @BindView(R.id.imgBack)
  ImageView imgBack;
  @BindView(R.id.txtAlert)
  TextView txtAlert;

  public static IabHelper mHelper;
  BroadcastReceiver broadcastReceiver;



  @Inject
  GetScoreViewModel getScoreViewModel;

  CheckCafeBazaarLogin checkCafeBazaarLogin;
  private boolean alreadyBazaarInited = false;


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_get_score);

    ButterKnife.bind(this);
    ((AppController) getApplication()).getAppComponent().inject(this);
    checkCafeBazaarLogin = new CheckCafeBazaarLogin(GetScoreActivity.this);
    messageReciver();
    bazaarSetup(getScoreViewModel.getMarketKey());
    attachViewModel();

  }


  @OnClick(R.id.btnGetScore)
  public void btnGetScoreAction() {
    if (!alreadyBazaarInited) {
      checkCafeBazaarLogin.initService();
    }
    else {
      UUID uuid = UUID.randomUUID();
      String randomUUIDString = uuid.toString();
      Log.i("Test", "randomUUIDString : " + randomUUIDString);
      //"bGoa+V7g/yqDXvKRqq+JTFn4uQZbPiQJo4pf9RzJ"
      mHelper.launchPurchaseFlow(GetScoreActivity.this, "sanj01", 10001, mPurchaseFinishedListener, randomUUIDString);

    }

  }

  @OnClick(R.id.imgBack)
  public void imgBackAction() {
    onBackPressed();
  }

  @Override
  public void onBackPressed() {
    super.onBackPressed();
  }




  private void  bazaarSetup(String bazaarKey){
    Log.i("bazaarKey : " , bazaarKey);
    String base64EncodedPublicKey = bazaarKey ;
    // You can find it in your Bazaar console, in the Dealers section.
    // It is recommended to add more security than just pasting it in your source code;
    mHelper = new IabHelper(GetScoreActivity.this, base64EncodedPublicKey);

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
          btnGetScore.setVisibility(View.VISIBLE);
          prgGetScore.setVisibility(View.INVISIBLE);
        }
        // Hooray, IAB is fully set up!
        mHelper.queryInventoryAsync(mGotInventoryListener);
      });
  }

  private void attachViewModel() {


    getScoreViewModel.getApiSuccessLiveDataResponse().observe(this, creditScorePreProcess -> {
     // Log.i("Test registerPurchaseInfoResultDto : " , registerPurchaseInfoResultDto.toString());
        stopWating();

new CreditStatusManager(this).handleReportStatus(creditScorePreProcess,txtAlert);
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
    if (mHelper == null) return;

    // Pass on the activity result to the helper for handling
    if (!mHelper.handleActivityResult(requestCode, resultCode, data)) {
      super.onActivityResult(requestCode, resultCode, data);
    } else {
      Log.i("Test", "onActivityResult handled by IABUtil.");
    }
  }


  public IabHelper.QueryInventoryFinishedListener mGotInventoryListener = new IabHelper.QueryInventoryFinishedListener() {
    public void onQueryInventoryFinished(IabResult result, Inventory inventory) {
      Log.i("Test", "Query inventory finished.");
      if (mHelper == null) return;

      if (result.isFailure()) {
        Log.i("Test", "Failed to query invenreturn;tory: " + result);

      }

      if (inventory != null){
      Purchase gasPurchase = inventory.getPurchase("sanj01");
      if (gasPurchase != null && verifyDeveloperPayload(gasPurchase)) {
        Log.i("Test", "We have sanj01. Consuming it.");
        mHelper.consumeAsync(inventory.getPurchase("sanj01"), mConsumeFinishedListener);
        return;
      }
      }

      Log.i("Test", "Initial inventory query finished; enabling main UI.");
    }
  };



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
              mHelper.launchPurchaseFlow(GetScoreActivity.this, "sanj01", 10001, mPurchaseFinishedListener, randomUUIDString);

            }
            else {
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
    if (GetScoreActivity.this != null)
      GetScoreActivity.this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_USER);
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


  IabHelper.OnIabPurchaseFinishedListener mPurchaseFinishedListener = new IabHelper.OnIabPurchaseFinishedListener() {
    public void onIabPurchaseFinished(IabResult result, Purchase purchase)
    {
      Log.i("Test", "Purchase finished: " + result + ", purchase: " + purchase);


      if (mHelper == null) {
        return;
      }

      if (result.isFailure()) {
        return;
      }
      if (!verifyDeveloperPayload(purchase)) {
        Log.i("Test","Error purchasing. Authenticity verification failed.");
        return;
      }
      Log.i("Test", "Purchase successful.");
              showWating();


      //String mobilephone,String ntcode,String persontypeid, String personalitytypeId,String paymenttypeid,int channelId,


              getScoreViewModel.callGetScoreViewModel(null,null,1,1, AppConstants.PAYMENT_TYPE,AppConstants.CHANNEL_ID,-1,purchase);

      if (purchase.getSku().equals("sanj01")) {
        Log.i("Test", "Purchase is gas. Starting sanj01 consumption.");
        mHelper.consumeAsync(purchase, mConsumeFinishedListener);
      }
    }
  };


  IabHelper.OnConsumeFinishedListener mConsumeFinishedListener = new IabHelper.OnConsumeFinishedListener() {
    public void onConsumeFinished(Purchase purchase, IabResult result) {
      Log.i("Test", "Consumption finished. Purchase: " + purchase + ", result: " + result);

      if (mHelper == null) return;
      if (result.isSuccess()) {
        Log.i("Test", "Consumption successful. Provisioning.");
      }
      else {
        Log.i("Test", "Error while consuming: " + result);
      }
      Log.i("Test",  "End consumption flow.");
    }
  };


  boolean verifyDeveloperPayload(Purchase p) {
    String payload = p.getDeveloperPayload();
    return true;
  }

  private void getScoreAtion(String reqToken){
    String url = "https://www.sanjineh.ir/report/" + reqToken;
    ActivityNavigationHelper.navigateToWebView(url, GetScoreActivity.this, WebViewActivity.class);
    finish();
  }

}
