package ir.iconish.sanjinehsub.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.AppCompatButton;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ir.iconish.sanjinehsub.R;
import ir.iconish.sanjinehsub.config.AppController;
import ir.iconish.sanjinehsub.data.vm.SetPasswordViewModel;
import ir.iconish.sanjinehsub.ui.ActivityNavigationHelper;
import ir.iconish.sanjinehsub.util.ButtonHelper;

public class SetPasswordActivity extends AppCompatActivity {

    @Nullable
    @BindView(R.id.btnSendPassword)
    AppCompatButton btnSavePassword;

    @Nullable
    @BindView(R.id.prg)
    ProgressBar prg;


    @Nullable
    @BindView(R.id.txtAlert)
    TextView txtAlert;


    @Nullable
    @BindView(R.id.edtPassword)
    EditText edtPassword;


    @Nullable
    @BindView(R.id.edtConfirmPassword)
    EditText edtConfirmPassword;


    @Inject
    SetPasswordViewModel setPasswordViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_password);
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        ButterKnife.bind(this);
        ((AppController) getApplication()).getAppComponent().inject(this);

        attachViewModel();
    }

    @OnClick(R.id.btnSendPassword)
    public void btnSendPasswordAction() {

        txtAlert.setVisibility(View.INVISIBLE);
        String password = edtPassword.getText().toString();
        String confirmPassword = edtConfirmPassword.getText().toString();

        if (password.length() < 4) {
            txtAlert.setText(getString(R.string.password_max_min));
            txtAlert.setVisibility(View.VISIBLE);
            return;
        }


        if (!password.equals(confirmPassword)) {
            txtAlert.setText(getString(R.string.password_not_match));
            txtAlert.setVisibility(View.VISIBLE);
            return;
        }


        setPassword(password);

    }


    private void attachViewModel() {
        setPasswordViewModel.getApiSuccessLiveDataResponse().observe(this, passwordValidationResponse -> {
                    stopWating();


                    if (passwordValidationResponse.getRespobseStatusCode() == 9999) {
                        String token = passwordValidationResponse.getToken();


                        // ActivityNavigationHelper.navigateToActivity(this,MainActivity.class,true);
                        ActivityNavigationHelper.navigateToActivityWithData(this, MainActivity.class, true, "token", token);
                    } else {

                        txtAlert.setText(passwordValidationResponse.getDescryptions());
                        txtAlert.setVisibility(View.VISIBLE);
                    }

//if 1010 go to enter pass -- if 1011 go to otp

                    // Log.e("success","in check password activity");
                }
        );

        setPasswordViewModel.getApiAuthFailureErrorLiveData().observe(this, volleyError -> {
        });

        setPasswordViewModel.getApiErrorLiveData().observe(this, volleyError -> {
            goToFailApiPage("ApiError");

        });
        setPasswordViewModel.getApiServerErrorLiveData().observe(this, volleyError ->

        {
            goToFailApiPage("ServerError");

        });
        setPasswordViewModel.getApiTimeOutErrorLiveData().observe(this, volleyError ->
                {
                    goToFailApiPage("TimeOutError");
                }

        );
        setPasswordViewModel.getApiClientNetworkErrorLiveData().observe(this, volleyError -> {
            goToFailApiPage("ClientNetworkError");


        });


        setPasswordViewModel.getApiForbiden403ErrorLiveData().observe(this, volleyError -> {
        });
        setPasswordViewModel.getApiValidation422ErrorLiveData().observe(this, volleyError -> {
        });

    }


    private void showWating() {
        prg.setVisibility(View.VISIBLE);
        ButtonHelper.toggleAppCompatButtonStatus(btnSavePassword, false);
    }

    private void stopWating() {
        prg.setVisibility(View.INVISIBLE);
        ButtonHelper.toggleAppCompatButtonStatus(btnSavePassword, true);
    }

    private void goToFailApiPage(String failCause) {

        Intent intent = new Intent(this, FailApiActivity.class);
        intent.putExtra("failCause", failCause);
        startActivity(intent);
        finish();

    }

    private void setPassword(String password) {
        showWating();
        setPasswordViewModel.callSetPasswordViewModel(password);
    }

}
