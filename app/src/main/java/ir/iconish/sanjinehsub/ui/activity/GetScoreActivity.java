package ir.iconish.sanjinehsub.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ir.iconish.sanjinehsub.R;
import ir.iconish.sanjinehsub.config.AppController;
import ir.iconish.sanjinehsub.data.vm.GetScoreViewModel;
import ir.iconish.sanjinehsub.util.ButtonHelper;
import ir.iconish.sanjinehsub.util.IabHelper;
import ir.iconish.sanjinehsub.util.IabResult;
import ir.iconish.sanjinehsub.util.Inventory;
import ir.iconish.sanjinehsub.util.Purchase;

public class GetScoreActivity extends AppCompatActivity {

  @BindView(R.id.edtNtcode)
  EditText edtNtcode;
  @BindView(R.id.btnGetScore)
  AppCompatButton btnGetScore;
  @BindView(R.id.prgGetScore)
  ProgressBar prgGetScore;
  @BindView(R.id.imgBack)
  ImageView imgBack;

  public static IabHelper mHelper;



  @Inject
  GetScoreViewModel getScoreViewModel;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_get_score);

    String base64EncodedPublicKey = "MIHNMA0GCSqGSIb3DQEBAQUAA4G7ADCBtwKBrwCmun9HxyV8hZjIX9aZGBIcM8u+vCS5/rBHQdUbrAwJpFjJ02z8ZQhngcjiFs7qCRsCYJjhnpVZgHnLG1WmKVmk3eUJDywAMszbSC7qK6HMYaBTp8trDpdHniMX9N9ftFHX/jbRhzVApl6rsfhZUX2alHpF0JQz5cm67pcdfai2eZalcgP9jUkE1PeCEeB+4AezX3q5bg2Qoe2XsdO1K211xGV7R4oullO3JAkTYQMCAwEAAQ==";
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
    ButterKnife.bind(this);
    ((AppController) getApplication()).getAppComponent().inject(this);

    attachViewModel();

  }


  @OnClick(R.id.btnGetScore)
  public void btnGetScoreAction() {


    showWating();
    Purchase purchase = null ;
    getScoreViewModel.callGetScoreViewModel(purchase);
    // startActivity(new Intent(this,LoginVerificatonActivity.class));
    //finish();
  }

  @OnClick(R.id.imgBack)
  public void imgBackAction() {
     onBackPressed();
  }



    private void attachViewModel() {
    getScoreViewModel.getApiSuccessLiveDataResponse().observe(this, services -> {
        stopWating();
        //if 1010 go to enter pass -- if 1011 go to otp
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


//  @Override
//  public void onBackPressed() {
//  }


  private void showWating() {
    prgGetScore.setVisibility(View.VISIBLE);
    ButtonHelper.toggleAppCompatButtonStatus(btnGetScore, false);
  }

  private void stopWating() {
    prgGetScore.setVisibility(View.INVISIBLE);
    ButtonHelper.toggleAppCompatButtonStatus(btnGetScore, true);
  }


  @Override
  public void onDestroy() {
    super.onDestroy();
    Log.i("Test", "Destroying helper.");
    if (mHelper != null) mHelper.dispose();
    mHelper = null;
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

  IabHelper.OnConsumeFinishedListener mConsumeFinishedListener = new IabHelper.OnConsumeFinishedListener() {
    public void onConsumeFinished(Purchase purchase, IabResult result) {
      Log.i("Test", "Consumption finished. Purchase: " + purchase + ", result: " + result);
      // if we were disposed of in the meantime, quit.
      if (mHelper == null) return;
      // We know this is the "gas" sku because it's the only one we consume,
      // so we don't check which sku was consumed. If you have more than one
      // sku, you probably should check...
      if (result.isSuccess()) {
        // successfully consumed, so we apply the effects of the item in our
        // game world's logic, which in our case means filling the gas tank a bit
        Log.i("Test", "Consumption successful. Provisioning.");
      }
      else {
        Log.i("Test", "Error while consuming: " + result);
      }
      //updateUi();
      Log.i("Test",  "End consumption flow.");
    }
  };

  boolean verifyDeveloperPayload(Purchase p) {
    String payload = p.getDeveloperPayload();
    return true;
  }


}