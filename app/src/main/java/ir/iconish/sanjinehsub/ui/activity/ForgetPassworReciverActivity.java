package ir.iconish.sanjinehsub.ui.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
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
import ir.iconish.sanjinehsub.data.vm.CheckPasswordViewModel;
import ir.iconish.sanjinehsub.data.vm.ForgetPasswordViewModel;
import ir.iconish.sanjinehsub.ui.ActivityNavigationHelper;
import ir.iconish.sanjinehsub.util.ButtonHelper;

public class ForgetPassworReciverActivity extends AppCompatActivity{

    @BindView(R.id.txtTimer)
    TextView txtTimer;


    @BindView(R.id.edtPassword)
    EditText edtPassword;







      @BindView(R.id.btnRetry)
      AppCompatButton btnRetry;



      @BindView(R.id.txtAlert)
      TextView txtAlert;


    @BindView(R.id.prg)
    ProgressBar prg;




    @Inject
    CheckPasswordViewModel checkPasswordViewModel;



    CountDownTimer countDownTimer;

    @BindView(R.id.btnEnter)
    AppCompatButton btnEnter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_reciver);
        ButterKnife.bind(this);
        ((AppController) getApplication()).getAppComponent().inject(this);

        attachViewModel();
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

        txtAlert.setVisibility(View.INVISIBLE);
        String password=  edtPassword.getText().toString()  ;

        if(password.length()<4){
            txtAlert.setText(getString(R.string.password_max_min));
            txtAlert.setVisibility(View.VISIBLE);
            return;
        }


        checkPassword(password);
    }

     @OnClick(R.id.btnRetry)
    public void btnRetryAction() {
ActivityNavigationHelper.navigateToActivity(this,ForgetPasswordActivity.class,true);
    }



    private void attachViewModel() {
        checkPasswordViewModel.getApiSuccessLiveDataResponse().observe(this, passwordValidationResponse -> {
                    stopWating();


                    if(passwordValidationResponse.getRespobseStatusCode()==1000){

                        ActivityNavigationHelper.navigateToActivity(this,MainActivity.class,true);
                    }
                    else {

                        txtAlert.setText(passwordValidationResponse.getDescryptions());
                        txtAlert.setVisibility(View.VISIBLE);
                    }

//if 1010 go to enter pass -- if 1011 go to otp

                    Log.e("success","in check password activity");
                }
        );

        checkPasswordViewModel.getApiAuthFailureErrorLiveData().observe(this, volleyError -> {});

        checkPasswordViewModel.getApiErrorLiveData().observe(this, volleyError ->{
            goToFailApiPage("ApiError");

        });
        checkPasswordViewModel.getApiServerErrorLiveData().observe(this, volleyError ->

        {
            goToFailApiPage("ServerError");

        });
        checkPasswordViewModel.getApiTimeOutErrorLiveData().observe(this, volleyError ->
                {
                    goToFailApiPage("TimeOutError");
                }

        );
        checkPasswordViewModel.getApiClientNetworkErrorLiveData().observe(this, volleyError -> {
            goToFailApiPage("ClientNetworkError");


        });


        checkPasswordViewModel.getApiForbiden403ErrorLiveData().observe(this, volleyError ->{} );
        checkPasswordViewModel.getApiValidation422ErrorLiveData().observe(this, volleyError ->{} );

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
    private void checkPassword(String password){
        showWating();
        checkPasswordViewModel.callCheckPasswordViewModel(password);
    }


}
