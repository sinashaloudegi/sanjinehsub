package ir.iconish.sanjinehsub.ui.activity;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.AppCompatButton;

import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ir.iconish.sanjinehsub.R;
import ir.iconish.sanjinehsub.config.AppController;
import ir.iconish.sanjinehsub.data.model.LoginStatusEnum;
import ir.iconish.sanjinehsub.data.vm.VerifyRegisterOtpViewModel;
import ir.iconish.sanjinehsub.ui.ActivityNavigationHelper;
import ir.iconish.sanjinehsub.util.ButtonHelper;
import ir.iconish.sanjinehsub.util.PermissionHelper;
import ir.iconish.sanjinehsub.util.ToastHelper;

public class VerifyRegisterOtpActivity extends AppCompatActivity {

    @BindView(R.id.txtTimer)
    TextView txtTimer;



    @BindView(R.id.btnEnter)
    AppCompatButton btnEnter;




    @BindView(R.id.txtAlert)
    TextView txtAlert;




    @BindView(R.id.edtVerifyCode)
    EditText edtVerificationCode;





    @BindView(R.id.prg)
    ProgressBar prg;





    @BindView(R.id.btnRetry)
    AppCompatButton btnRetry;






    CountDownTimer countDownTimer;





@Inject
VerifyRegisterOtpViewModel confirmRegisterViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        ButterKnife.bind(this);
        ((AppController) getApplication()).getAppComponent().inject(this);

        attachViewModel();
        startTimer();





    }
    @Override
    protected void onDestroy() {
        super.onDestroy();


    }

    private void receiveCodeAction(String code) {
        try {
            code = code.replaceAll("[^0-9]", "");
            edtVerificationCode.setText(code);


        }
        catch (Exception e){
        }

    }
    @OnClick(R.id.imgBack)
    public void imgBackAction() {
        ActivityNavigationHelper.navigateToActivity(this,LoginActivity.class,true);

    }
    private void startTimer() {
       // long timerDuration=Long.parseLong(getString(R.string.timer_start_value));
        int timerDuration=confirmRegisterViewModel.getTimerDuration();

        countDownTimer=    new CountDownTimer(timerDuration, 1000) {

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
        txtAlert.setVisibility(View.INVISIBLE);
        String otp=  edtVerificationCode.getText().toString()  ;
        if(otp.length()<1){
            txtAlert.setText(getString(R.string.enter_correct_password));
            txtAlert.setVisibility(View.VISIBLE);
            return;
        }


        verifyRegisterOtp(otp);
       /* startActivity(new Intent(this, CheckPasswordActivity.class));
        finish();*/
    }


     @OnClick(R.id.btnRetry)
    public void btnRetryAction() {
         ActivityNavigationHelper.navigateToActivity(this,LoginActivity.class,true);
    }




    private void attachViewModel() {
        confirmRegisterViewModel.getApiSuccessLiveDataResponse().observe(this, passwordValidationResponse -> {
                    stopWating();



                    if(passwordValidationResponse.getRespobseStatusCode()== LoginStatusEnum.VERIFY_SUCCESS_AND_NEW.getValue()) {

//ActivityNavigationHelper.navigateToActivity(this,SetPasswordActivity.class,true);
                        ActivityNavigationHelper.navigateToActivity(this, MainActivity.class, true);
                    }

                    else {
                        txtAlert.setText((LoginStatusEnum.fromValue(passwordValidationResponse.getRespobseStatusCode()).getDescr()));
                        txtAlert.setVisibility(View.VISIBLE);
                    }















                }
        );

        confirmRegisterViewModel.getApiAuthFailureErrorLiveData().observe(this, volleyError -> {});

        confirmRegisterViewModel.getApiErrorLiveData().observe(this, volleyError ->{
            goToFailApiPage("ApiError");

        });
        confirmRegisterViewModel.getApiServerErrorLiveData().observe(this, volleyError ->

        {
            goToFailApiPage("ServerError");

        });
        confirmRegisterViewModel.getApiTimeOutErrorLiveData().observe(this, volleyError ->
                {
                    goToFailApiPage("TimeOutError");
                }

        );
        confirmRegisterViewModel.getApiClientNetworkErrorLiveData().observe(this, volleyError -> {
            goToFailApiPage("ClientNetworkError");


        });


        confirmRegisterViewModel.getApiForbiden403ErrorLiveData().observe(this, volleyError ->{} );
        confirmRegisterViewModel.getApiValidation422ErrorLiveData().observe(this, volleyError ->{} );

    }


    private void showWating(){
        prg.setVisibility(View.VISIBLE);
        ButtonHelper.toggleAppCompatButtonStatus(btnEnter,false);
    }
    private void stopWating(){
        prg.setVisibility(View.INVISIBLE);
        ButtonHelper.toggleAppCompatButtonStatus(btnEnter,true);
    }

    private void goToFailApiPage(String failCause){

        Intent intent=new Intent(this,FailApiActivity.class);
        intent.putExtra("failCause",failCause);
        startActivity(intent);
        finish();

    }
    private void verifyRegisterOtp(String otp){
        showWating();
        confirmRegisterViewModel.callVerifyRegisterOtpViewModel(otp);
    }



}
