package ir.iconish.sanjinehsub.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.util.Timer;
import java.util.TimerTask;

import ir.iconish.sanjinehsub.R;
import ir.iconish.sanjinehsub.bazaar.CheckCafeBazaarLogin;

public class SplashActivity extends AppCompatActivity{
;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
//new CheckCafeBazaarLogin(this).initService();
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                startActivity(new Intent(SplashActivity.this, MainActivity.class));
               // startActivity(new Intent(SplashActivity.this, LoginActivity.class));
            }
        }, 3000);

    }


}
