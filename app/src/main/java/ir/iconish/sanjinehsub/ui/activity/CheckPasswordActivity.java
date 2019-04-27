package ir.iconish.sanjinehsub.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ir.iconish.sanjinehsub.R;
import ir.iconish.sanjinehsub.config.AppController;
import ir.iconish.sanjinehsub.data.model.ResponseCodeEnum;
import ir.iconish.sanjinehsub.data.vm.CheckPasswordViewModel;
import ir.iconish.sanjinehsub.ui.ActivityNavigationHelper;
import ir.iconish.sanjinehsub.util.ButtonHelper;

public class CheckPasswordActivity extends AppCompatActivity{

    @BindView(R.id.btnEnter)
    AppCompatButton btnEnter;

    @BindView(R.id.prg)
    ProgressBar prg;


    @BindView(R.id.txtAlert)
    TextView txtAlert;


    @BindView(R.id.edtPassword)
    TextView edtPassword;


    @Inject
    CheckPasswordViewModel checkPasswordViewModel;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_password);
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        ButterKnife.bind(this);
        ((AppController) getApplication()).getAppComponent().inject(this);


        attachViewModel();
    }

    @OnClick(R.id.imgBack)
    public void imgBackAction() {
        ActivityNavigationHelper.navigateToActivity(this,LoginActivity.class,true);

    }

    @OnClick(R.id.btnEnter)
    public void navMenuAction() {






        txtAlert.setVisibility(View.INVISIBLE);
        String password=  edtPassword.getText().toString()  ;

        if(password.length()<4){
            txtAlert.setText(getString(R.string.password_max_min));
            txtAlert.setVisibility(View.VISIBLE);
            return;
        }


        /*startActivity(new Intent(this,MainActivity.class));
        finish();*/
        checkPassword(password);

}



    @OnClick(R.id.txtForgetPassword)
    public void txtForgetPasswordAction() {
        clearPassword();
        startActivity(new Intent(this,ForgetPasswordActivity.class));
        finish();
}




    private void attachViewModel() {
        checkPasswordViewModel.getApiSuccessLiveDataResponse().observe(this, passwordValidationResponse -> {
                    stopWating();



            if(passwordValidationResponse.getRespobseStatusCode()==1012||passwordValidationResponse.getRespobseStatusCode()==1013||passwordValidationResponse.getRespobseStatusCode()==1000) {

//ActivityNavigationHelper.navigateToActivity(this,SetPasswordActivity.class,true);
                ActivityNavigationHelper.navigateToActivity(this, MainActivity.class, true);
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

private void clearPassword(){
    checkPasswordViewModel.clearPassword();
}

}
