package ir.iconish.sanjinehsub.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.Timer;
import java.util.TimerTask;

import javax.inject.Inject;

import ir.iconish.sanjinehsub.R;
import ir.iconish.sanjinehsub.config.AppController;
import ir.iconish.sanjinehsub.data.vm.SplashViewModel;
import ir.iconish.sanjinehsub.util.IabHelper;
import ir.iconish.sanjinehsub.util.IabResult;
import ir.iconish.sanjinehsub.util.Inventory;
import ir.iconish.sanjinehsub.util.Purchase;

public class SplashActivity extends AppCompatActivity{
@Inject
SplashViewModel splashViewModel;

    public static IabHelper mHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

      ((AppController) getApplication()).getAppComponent().inject(this);

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


//new CheckCafeBazaarLogin(this).initService();
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
              //  startActivity(new Intent(SplashActivity.this, MainActivity.class));

                if(splashViewModel.getUserId()==-1){
                startActivity(new Intent(SplashActivity.this, LoginActivity.class));}
                else {
                    Log.e("splashViewModel.getUserId()",splashViewModel.getUserId()+"");
                    startActivity(new Intent(SplashActivity.this, MainActivity.class));
                }
                finish();
            }
        }, 3000);

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
