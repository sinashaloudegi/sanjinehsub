package ir.iconish.sanjinehsub.ui.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ir.iconish.sanjinehsub.R;

public class PasswordVerificatonActivity extends AppCompatActivity {

    @Nullable
    @BindView(R.id.txtTimer)
    TextView txtTimer;
    CountDownTimer countDownTimer;

    @Nullable
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

            @Override
            public void onTick(long millisUntilFinished) {
                if(millisUntilFinished<10000){
                    txtTimer.setTextColor(Color.RED);
                }
                txtTimer.setText( millisUntilFinished / 1000+"" );

            }

            @Override
            public void onFinish() {
                txtTimer.setText( "0");
                // pinEntry.setEnabled(false);
                //  pinEntry.setText("");
                //   bottomLayout.setVisibility(View.VISIBLE);
                try {
                    //   unregisterReceiver(broadcastReceiver);
                } catch (Exception e){}
            }

        }.start();

    }
    @OnClick(R.id.btnEnter)
    public void btnEnterAction() {

        startActivity(new Intent(this,MainActivity.class));
        finish();
    }
}
