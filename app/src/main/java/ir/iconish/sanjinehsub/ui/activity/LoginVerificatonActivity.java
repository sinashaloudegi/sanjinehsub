package ir.iconish.sanjinehsub.ui.activity;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.telephony.SmsMessage;
import android.widget.EditText;
import android.widget.TextView;

import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ir.iconish.sanjinehsub.R;
import ir.iconish.sanjinehsub.util.PermissionHelper;
import ir.iconish.sanjinehsub.util.ToastHelper;

public class LoginVerificatonActivity extends AppCompatActivity{

    @BindView(R.id.txtTimer)
    TextView txtTimer;


    @BindView(R.id.edtVerificationCode)
    EditText edtVerificationCode;




    CountDownTimer countDownTimer;

    private BroadcastReceiver broadcastReceiver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_verfication);
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        ButterKnife.bind(this);
        reciveSmsListener();
        startTimer();





    }
    private void reciveSmsListener(){


        MultiplePermissionsListener multiplePermissionsListener=new MultiplePermissionsListener() {
            @Override
            public void onPermissionsChecked(MultiplePermissionsReport report) {
                if ( report.areAllPermissionsGranted()){


                    IntentFilter intentFilter = new IntentFilter("android.provider.Telephony.SMS_RECEIVED");
                    broadcastReceiver = new BroadcastReceiver() {
                        @Override
                        public void onReceive(Context context, Intent intent) {

                            Bundle data = intent.getExtras();
                            Object[] pdus = (Object[]) data.get("pdus");

                            for (int i = 0; i < pdus.length; i++) {
                                SmsMessage smsMessage = SmsMessage.createFromPdu((byte[]) pdus[i]);
                                String sender = smsMessage.getDisplayOriginatingAddress();

                                String messageBody = smsMessage.getMessageBody();

                                receiveCodeAction(messageBody);
                            }
                        }
                    };

                    registerReceiver(broadcastReceiver, intentFilter);




                }
                else {
                    ToastHelper.showWarningMessage(LoginVerificatonActivity.this,getString(R.string.should_grant_permission));
                }
            }

            @Override
            public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                token.continuePermissionRequest();

            }


        };
        String[] permissions={Manifest.permission.RECEIVE_SMS};
        PermissionHelper.permissions(this, multiplePermissionsListener, permissions);

    }
    private void receiveCodeAction(String code) {
        try {
            code = code.replaceAll("[^0-9]", "");
            edtVerificationCode.setText(code);


        }
        catch (Exception e){
        }

    }

    private void startTimer() {
        long timerDuration=Long.parseLong(getString(R.string.timer_start_value));
        countDownTimer=    new CountDownTimer(timerDuration, 1000) {

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

        startActivity(new Intent(this, CheckPasswordActivity.class));
        finish();
    }
}
