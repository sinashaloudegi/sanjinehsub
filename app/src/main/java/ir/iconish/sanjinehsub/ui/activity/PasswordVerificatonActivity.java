package ir.iconish.sanjinehsub.ui.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ir.iconish.sanjinehsub.R;

public class PasswordVerificatonActivity extends AppCompatActivity{

    @BindView(R.id.txtTimer)
    TextView txtTimer;
    CountDownTimer countDownTimer;

    @BindView(R.id.btnEnter)
    AppCompatButton btnEnter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_verfication);
        ButterKnife.bind(this);

        startTimer();





    }

    private void startTimer() {
       // int timerDuration=checkPasswordViewModel.getTimerDuration();

        countDownTimer=    new CountDownTimer(200000, 1000) {

            public void onTick(long millisUntilFinished) {
                if(millisUntilFinished<10000){
                    txtTimer.setTextColor(Color.RED);
                }
                txtTimer.setText( millisUntilFinished / 1000+"" );

            }

            public void onFinish() {
                txtTimer.setText( "0");
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

        startActivity(new Intent(this,MainActivity.class));
        finish();
    }
}
