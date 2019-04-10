package ir.iconish.sanjinehsub.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;

import java.util.Timer;
import java.util.TimerTask;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ir.iconish.sanjinehsub.R;
import ir.iconish.sanjinehsub.config.AppController;
import ir.iconish.sanjinehsub.data.vm.LoginViewModel;
import ir.iconish.sanjinehsub.util.ButtonHelper;
import ir.iconish.sanjinehsub.util.DialogHelper;
import ir.iconish.sanjinehsub.util.ToastHelper;

public class LoginActivity extends AppCompatActivity{

    @BindView(R.id.btnCheckPhone)
    AppCompatButton btnCheckPhone;

@BindView(R.id.prgLogin)
ProgressBar prgLogin;




    @BindView(R.id.edtMobileNumber)
    EditText edtMobileNumber;





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

    @OnClick(R.id.btnCheckPhone)
    public void btnCheckPhoneAction() {
showWating();
loginViewModel.callLoginViewModel(edtMobileNumber.getText().toString());
       // startActivity(new Intent(this,LoginVerificatonActivity.class));
        //finish();
}



    private void attachViewModel() {
        loginViewModel.getApiSuccessLiveDataResponse().observe(this, services -> {
stopWating();
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
