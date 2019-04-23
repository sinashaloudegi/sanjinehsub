package ir.iconish.sanjinehsub.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ir.iconish.sanjinehsub.R;
import ir.iconish.sanjinehsub.config.AppController;
import ir.iconish.sanjinehsub.data.model.ResponseCodeEnum;
import ir.iconish.sanjinehsub.data.vm.LoginViewModel;
import ir.iconish.sanjinehsub.ui.ActivityNavigationHelper;
import ir.iconish.sanjinehsub.util.ButtonHelper;
import ir.iconish.sanjinehsub.ui.DialogHelper;

public class LoginActivity extends AppCompatActivity{

    @BindView(R.id.btnCheckPhone)
    AppCompatButton btnCheckPhone;

@BindView(R.id.prgLogin)
ProgressBar prgLogin;




    @BindView(R.id.edtMobileNumber)
    EditText edtMobileNumber;




    @BindView(R.id.txtAlert)
    TextView txtAlert;




    @BindView(R.id.checkboxRules)
    CheckBox checkBoxRules;





    @Inject
    LoginViewModel loginViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        ((AppController) getApplication()).getAppComponent().inject(this);

attachViewModel();

    }



    @OnClick(R.id.txtRegisterRules)
    public void txtRegisterRulesAction() {

        ActivityNavigationHelper.navigateToWebView(getString(R.string.rules_url),this,WebViewActivity.class);

    }




    @OnClick(R.id.btnCheckPhone)
    public void btnCheckPhoneAction() {
        txtAlert.setVisibility(View.INVISIBLE);
      String mobileNumber=  edtMobileNumber.getText().toString()  ;

        if (!mobileNumber.startsWith("09")){
            txtAlert.setText(getString(R.string.enter_correct_mobile_phone));
            txtAlert.setVisibility(View.VISIBLE);
            return;
        }



        if(mobileNumber.length()<11){
            txtAlert.setText(getString(R.string.enter_correct_mobile_phone));
            txtAlert.setVisibility(View.VISIBLE);
            return;
        }



        if(!checkBoxRules.isChecked()){
            txtAlert.setText(getString(R.string.accept_rules));
            txtAlert.setVisibility(View.VISIBLE);
            return;
        }






showWating();
loginViewModel.callLoginViewModel(edtMobileNumber.getText().toString());
       // startActivity(new Intent(this,LoginVerificatonActivity.class));
        //finish();
}



    private void attachViewModel() {
        loginViewModel.getApiSuccessLiveDataResponse().observe(this, user -> {
stopWating();


if(user.getResponseCodeEnum().getValue()== ResponseCodeEnum.USER_EXIST.getValue()){
    ActivityNavigationHelper.navigateToActivity(this, CheckPasswordActivity.class,true);
}

else if (user.getResponseCodeEnum().getValue()== ResponseCodeEnum.USERISNEW.getValue()){
    ActivityNavigationHelper.navigateToActivity(this,VerifyRegisterOtpActivity.class,true);

}


//if 1010 go to enter pass -- if 1011 go to otp

                    Log.e("success","in activity");
        }
        );

        loginViewModel.getApiAuthFailureErrorLiveData().observe(this, volleyError -> {});

        loginViewModel.getApiErrorLiveData().observe(this, volleyError ->{
            goToFailApiPage("ApiError");

        });
        loginViewModel.getApiServerErrorLiveData().observe(this, volleyError ->

        {
            goToFailApiPage("ServerError");

        });
        loginViewModel.getApiTimeOutErrorLiveData().observe(this, volleyError ->
                {
                    goToFailApiPage("TimeOutError");
                }

        );
        loginViewModel.getApiClientNetworkErrorLiveData().observe(this, volleyError -> {
            goToFailApiPage("ClientNetworkError");


        });


      loginViewModel.getApiForbiden403ErrorLiveData().observe(this, volleyError ->{} );
        loginViewModel.getApiValidation422ErrorLiveData().observe(this, volleyError ->{} );

    }



    private void goToFailApiPage(String failCause){

        Intent intent=new Intent(this,FailApiActivity.class);
        intent.putExtra("failCause",failCause);
        startActivity(intent);
        finish();

    }




    @Override
    public void onBackPressed() {
        DialogHelper.sureExit(this);
    }



    private void showWating(){
        prgLogin.setVisibility(View.VISIBLE);
        ButtonHelper.toggleAppCompatButtonStatus(btnCheckPhone,false);
    }
      private void stopWating(){
        prgLogin.setVisibility(View.INVISIBLE);
        ButtonHelper.toggleAppCompatButtonStatus(btnCheckPhone,true);
    }




}