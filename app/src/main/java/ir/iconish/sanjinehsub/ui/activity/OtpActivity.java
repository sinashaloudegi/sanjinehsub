/*
package ir.iconish.sanjinehsub.ui.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ir.iconish.sanjinehsub.R;
import ir.iconish.sanjinehsub.ui.ActivityNavigationHelper;

public class OtpActivity extends AppCompatActivity{

    @BindView(R.id.txtTimer)
    TextView txtTimer;

      @BindView(R.id.edtVerificationCode)
      EditText edtVerificationCode;


      @BindView(R.id.btnRetry)
      AppCompatButton btnRetry;



    CountDownTimer countDownTimer;

    @BindView(R.id.btnEnter)
    AppCompatButton btnEnter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);
        ButterKnife.bind(this);

        startTimer();





    }

    private void startTimer() {
        long timer=Long.parseLong(getString(R.string.timer_start_value));

        countDownTimer=    new CountDownTimer(timer, 1000) {

            public void onTick(long millisUntilFinished) {
                if(millisUntilFinished<10000){
                    txtTimer.setTextColor(Color.RED);
                }
                txtTimer.setText( millisUntilFinished / 1000+"" );

            }

            public void onFinish() {
                txtTimer.setText( "0");
                btnEnter.setVisibility(View.INVISIBLE);
                btnRetry.setVisibility(View.VISIBLE);
               // pinEntry.setEnabled(false);
              //  pinEntry.setText("");
             //   bottomLayout.setVisibility(View.VISIBLE);
                try {
                 //   unregisterReceiver(broadcastReceiver);
                }
                catch (Exception e){}
            }

        }.start();

    }
    @OnClick(R.id.btnEnter)
    public void btnEnterAction() {


    }

     @OnClick(R.id.btnRetry)
    public void btnRetryAction() {
         ActivityNavigationHelper.navigateToActivity(this,LoginActivity.class,true);
    }



}
*/
