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
import android.widget.Toast;

import java.util.UUID;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ir.iconish.sanjinehsub.R;
import ir.iconish.sanjinehsub.bazaar.CheckCafeBazaarLogin;
import ir.iconish.sanjinehsub.config.AppController;
import ir.iconish.sanjinehsub.data.vm.BazaarKeyViewModel;
import ir.iconish.sanjinehsub.data.vm.CheckReportViewModel;
import ir.iconish.sanjinehsub.data.vm.ConfirmVerifyCodeViewModel;
import ir.iconish.sanjinehsub.data.vm.GetScoreViewModel;
import ir.iconish.sanjinehsub.ui.ActivityNavigationHelper;
import ir.iconish.sanjinehsub.util.ButtonHelper;
import ir.iconish.sanjinehsub.util.IabHelper;
import ir.iconish.sanjinehsub.util.IabResult;
import ir.iconish.sanjinehsub.util.Inventory;
import ir.iconish.sanjinehsub.util.Purchase;

public class VerifyCodeOthersActivity extends AppCompatActivity {

  @BindView(R.id.edtNtcode)
  EditText edtNtcode;
  @BindView(R.id.btnGetScore)
  AppCompatButton btnGetScore;
  @BindView(R.id.prgGetScore)
  ProgressBar prgGetScore;
  @BindView(R.id.imgBack)
  ImageView imgBack;

  public static IabHelper mHelper;
  BroadcastReceiver broadcastReceiver;



  @Inject
  GetScoreViewModel getScoreViewModel;
  @Inject
  CheckReportViewModel checkReportViewModel;
  @Inject
  BazaarKeyViewModel bazaarKeyViewModel;

  @Inject
  ConfirmVerifyCodeViewModel confirmVerifyCodeViewModel;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_get_score);
    btnGetScore.setEnabled(false);
    ButterKnife.bind(this);
    ((AppController) getApplication()).getAppComponent().inject(this);

    attachViewModel();
    Intent intent = getIntent();
    String msisdn = intent.getStringExtra("msisdn");
    confirmVerifyCodeViewModel.callConfirmVerifyCodeViewModel(msisdn,"code");

  }


  @OnClick(R.id.btnGetScore)
  public void btnGetScoreAction() {
    if (!(edtNtcode.getText().toString().trim().length() > 0 )) {
      Toast.makeText(this, R.string.force_enter_ntcode, Toast.LENGTH_SHORT).show();
      return;
    }

    String ntcode = edtNtcode.getText().toString();
    showWating();
    checkReportViewModel.callCheckReportViewModel(ntcode.trim());

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
      if (reportStateEnumId == 12){}
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




    bazaarKeyViewModel.getApiSuccessLiveDataResponse().observe(this, bazaarKey -> {
      bazaarSetup(bazaarKey);
      btnGetScore.setEnabled(true);
      }
    );
    bazaarKeyViewModel.getApiAuthFailureErrorLiveData().observe(this, volleyError -> {
    });
    bazaarKeyViewModel.getApiErrorLiveData().observe(this, volleyError -> {
      goToFailApiPage("ApiError");
    });
    bazaarKeyViewModel.getApiServerErrorLiveData().observe(this, volleyError ->
    {
      goToFailApiPage("ServerError");
    });
    bazaarKeyViewModel.getApiTimeOutErrorLiveData().observe(this, volleyError ->
      {
        goToFailApiPage("TimeOutError");
      }
    );
    bazaarKeyViewModel.getApiClientNetworkErrorLiveData().observe(this, volleyError -> {
      goToFailApiPage("ClientNetworkError");
    });

    bazaarKeyViewModel.getApiForbiden403ErrorLiveData().observe(this, volleyError -> {
    });
    bazaarKeyViewModel.getApiValidation422ErrorLiveData().observe(this, volleyError -> {
    });




    checkReportViewModel.getApiSuccessLiveDataResponse().observe(this, services -> {
      if (services.getAvailable()== true && services.getValidMobile()== true){
         new CheckCafeBazaarLogin(VerifyCodeOthersActivity.this).initService();
      }
      else if (services.getAvailable() == true && services.getValidMobile() == false){
        Toast.makeText(this,"کد ملی وارد شده دارای گزارش می باشد اما شماره موبایل وارد شده با این کد ملی مطابقت ندارد.",Toast.LENGTH_SHORT).show();
      }
      else {
        Toast.makeText(this, "کد ملی وارد شده دارای گزارش نمی باشد.", Toast.LENGTH_SHORT).show();
      }
        Log.e("success", "in activity");
      }
    );
    checkReportViewModel.getApiAuthFailureErrorLiveData().observe(this, volleyError -> {
    });
    checkReportViewModel.getApiErrorLiveData().observe(this, volleyError -> {
      goToFailApiPage("ApiError");
    });
    checkReportViewModel.getApiServerErrorLiveData().observe(this, volleyError ->
    {
      goToFailApiPage("ServerError");
    });
    checkReportViewModel.getApiTimeOutErrorLiveData().observe(this, volleyError ->
      {
        goToFailApiPage("TimeOutError");
      }
    );
    checkReportViewModel.getApiClientNetworkErrorLiveData().observe(this, volleyError -> {
      goToFailApiPage("ClientNetworkError");
    });

    checkReportViewModel.getApiForbiden403ErrorLiveData().observe(this, volleyError -> {
    });
    checkReportViewModel.getApiValidation422ErrorLiveData().observe(this, volleyError -> {
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
