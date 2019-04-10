package ir.iconish.sanjinehsub.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.Timer;
import java.util.TimerTask;

import javax.inject.Inject;

import ir.iconish.sanjinehsub.R;
import ir.iconish.sanjinehsub.bazaar.CheckCafeBazaarLogin;
import ir.iconish.sanjinehsub.config.AppController;
import ir.iconish.sanjinehsub.data.repository.SplashRepository;
import ir.iconish.sanjinehsub.data.vm.SplashViewModel;

public class SplashActivity extends AppCompatActivity{
@Inject
SplashViewModel splashViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

      ((AppController) getApplication()).getAppComponent().inject(this);

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


}
