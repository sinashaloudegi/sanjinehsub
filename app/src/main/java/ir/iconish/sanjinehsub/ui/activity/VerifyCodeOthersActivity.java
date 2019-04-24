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
import ir.iconish.sanjinehsub.util.ButtonHelper;
import ir.iconish.sanjinehsub.util.IabHelper;
import ir.iconish.sanjinehsub.util.IabResult;
import ir.iconish.sanjinehsub.util.Inventory;
import ir.iconish.sanjinehsub.util.Purchase;

public class VerifyCodeOthersActivity extends AppCompatActivity {


  @BindView(R.id.btnEnterVerifyCodeOthers)
  AppCompatButton btnEnterVerifyCodeOthers;
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


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_verfiy_code_others);
    ButterKnife.bind(this);
    ((AppController) getApplication()).getAppComponent().inject(this);

    bazaarSetup("");
    attachViewModel();

  }


  @OnClick(R.id.btnGetScore)
  public void btnGetScoreAction() {
    Intent intent = getIntent();
    String msisdn = intent.getStringExtra("msisdn");
    confirmVerifyCodeViewModel.callConfirmVerifyCodeViewModel(msisdn,edtVerifyCodeOthers.getText().toString());

  }

  @OnClick(R.id.imgBack)
  public void imgBackAction() {
    onBackPressed();
  }

  @Override
  public void onBackPressed() {
    startActivity();
  }

  private void  bazaarSetup(String bazaarKey){
    String base64EncodedPublicKey = bazaarKey ;
    // You can find it in your Bazaar console, in the Dealers section.
    // It is recommended to add more security than just pasting it in your source code;
    mHelper = new IabHelper(this, base64EncodedPublicKey);

    Log.i("Test", "Starting setup.");
    mHelper.startSetup(new IabHelper.OnIabSetupFinishedListener() {
      public void onIabSetupFinished(IabResult result) {
        Log.i("Test", "Setup finished.");

        if (!result.isSuccess()) {
          // Oh noes, there was a problem.
          Log.i("Test", "Problem setting up In-app Billing: " + result);

        }
        // Hooray, IAB is fully set up!
        mHelper.queryInventoryAsync(mGotInventoryListener);
      }
    });
//      mHelper.startSetup(result -> {
//        Log.i("Test", "Setup finished.");
//
//        if (!result.isSuccess()) {
//          // Oh noes, there was a problem.
//          Log.i("Test", "Problem setting up In-app Billing: " + result);
//        }
//        // Hooray, IAB is fully set up!
//        mHelper.queryInventoryAsync(mGotInventoryListener);
//      });
  }

  private void attachViewModel() {

    confirmVerifyCodeViewModel.getApiSuccessLiveDataResponse().observe(this, reportStateEnumId -> {
      if (reportStateEnumId == 12){

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




    getScoreViewModel.getApiSuccessLiveDataResponse().observe(this, services -> {
        stopWating();
        //go webview
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
        Log.i("Test", "Failed to query inventory: " + result);
        return;
      }

      Purchase gasPurchase = inventory.getPurchase("sanj01");
      if (gasPurchase != null && verifyDeveloperPayload(gasPurchase)) {
        Log.i("Test", "We have sanj01. Consuming it.");
        mHelper.consumeAsync(inventory.getPurchase("sanj01"), mConsumeFinishedListener);
        return;
      }

      Log.i("Test", "Initial inventory query finished; enabling main UI.");
    }
  };



  private void messageReciver(){
    IntentFilter filter = new IntentFilter();

    filter.addAction("versionCode");

    broadcastReceiver= new BroadcastReceiver() {
      @Override
      public void onReceive(Context context, Intent intent) {

        String action=intent.getAction();

        switch (action) {


          case "checkbazaarlogin":
            boolean cafeBazarLogin = intent.getBooleanExtra("checkbazaarlogin",false);
            if (cafeBazarLogin){

              UUID uuid = UUID.randomUUID();
              String randomUUIDString = uuid.toString();
              Log.i("Test", "randomUUIDString : " + randomUUIDString);
              //"bGoa+V7g/yqDXvKRqq+JTFn4uQZbPiQJo4pf9RzJ"
              mHelper.launchPurchaseFlow(VerifyCodeOthersActivity.this, "sanj01", 10001, mPurchaseFinishedListener, randomUUIDString);

            }
            else {
              Intent intentLogin = new Intent(Intent.ACTION_VIEW);
              intentLogin.setData(Uri.parse("bazaar://login"));
              intentLogin.setPackage("com.farsitel.bazaar");
              VerifyCodeOthersActivity.this.startActivity(intentLogin);
            }

            break;

        }


      }
    };
    registerReceiver(broadcastReceiver, filter);
  }



  @Override
  public void onDestroy() {
    if (this != null)
      this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_USER);
    super.onDestroy();

    new CheckCafeBazaarLogin(this).releaseService();

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
              getScoreViewModel.callGetScoreViewModel(purchase);

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


  private void startActivity(){
    ActivityNavigationHelper.navigateToActivity(this,GetScoreOthersActivity.class,true);

  }


}
