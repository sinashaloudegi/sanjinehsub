package ir.iconish.sanjinehsub.ui.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ir.iconish.sanjinehsub.R;
import ir.iconish.sanjinehsub.config.AppController;
import ir.iconish.sanjinehsub.data.vm.VerifyRegisterOtpViewModel;
import ir.iconish.sanjinehsub.ui.ActivityNavigationHelper;
import ir.iconish.sanjinehsub.util.ButtonHelper;

public class VerifyRegisterOtpActivity extends AppCompatActivity{

    @BindView(R.id.txtTimer)
    TextView txtTimer;



    @BindView(R.id.btnEnter)
    AppCompatButton btnEnter;




    @BindView(R.id.txtAlert)
    TextView txtAlert;




    @BindView(R.id.edtVerificationCode)
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

        startTimer();


attachViewModel();


    }
    @OnClick(R.id.imgBack)
    public void imgBackAction() {
        ActivityNavigationHelper.navigateToActivity(this,LoginActivity.class,true);

    }
    private void startTimer() {

        countDownTimer=    new CountDownTimer(200000, 1000) {

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


ActivityNavigationHelper.navigateToActivity(this,SetPasswordActivity.class,true);

//if 1010 go to enter pass -- if 1011 go to otp

                    Log.e("success","in check password activity");
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
