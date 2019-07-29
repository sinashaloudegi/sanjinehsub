package ir.iconish.sanjinehsub.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.AppCompatButton;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Optional;
import ir.iconish.sanjinehsub.R;
import ir.iconish.sanjinehsub.config.AppController;
import ir.iconish.sanjinehsub.data.vm.CheckPasswordViewModel;
import ir.iconish.sanjinehsub.ui.ActivityNavigationHelper;
import ir.iconish.sanjinehsub.util.ButtonHelper;

public class CheckPasswordActivity extends AppCompatActivity {


    @Nullable
    @BindView(R.id.txt_forget_password)
    TextView txtForgetPassword;

    @Nullable
    @BindView(R.id.btn_enter_login)
    AppCompatButton btnEnter;

    @Nullable
    @BindView(R.id.prg_login)
    ProgressBar prg;


    @Nullable
    @BindView(R.id.txt_alert)
    TextView txtAlert;


    @Nullable
    @BindView(R.id.edt_txt_get_password)
    TextView editTextGetPassword;


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


        coloredAndClickableText();
    }

    private void coloredAndClickableText() {
        SpannableString spannableString = new SpannableString(txtForgetPassword.getText().toString());
        ForegroundColorSpan red = new ForegroundColorSpan(getResources().getColor(R.color.main_color));
        spannableString.setSpan(red, 33, 38, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        ClickableSpan onRuleClicked = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View view) {
                clearPassword();
                startActivity(new Intent(CheckPasswordActivity.this, ForgetPasswordActivity.class));
                finish();
            }
        };
        spannableString.setSpan(red, 33, 38, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(onRuleClicked, 33, 38, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        txtForgetPassword.setText(spannableString);
        txtForgetPassword.setMovementMethod(LinkMovementMethod.getInstance());
    }


    @Optional
    @OnClick(R.id.btn_enter_login)
    public void navMenuAction() {


        txtAlert.setVisibility(View.INVISIBLE);
        String password = editTextGetPassword.getText().toString();

        if (password.length() < 4) {
            txtAlert.setText(getString(R.string.password_max_min));
            txtAlert.setVisibility(View.VISIBLE);
            return;
        }

        checkPassword(password);

    }


    private void attachViewModel() {
        checkPasswordViewModel.getApiSuccessLiveDataResponse().observe(this, passwordValidationResponse -> {
                    stopWating();


                    if (passwordValidationResponse.getRespobseStatusCode() == 1012 || passwordValidationResponse.getRespobseStatusCode() == 1013 || passwordValidationResponse.getRespobseStatusCode() == 1000) {

                        ActivityNavigationHelper.navigateToActivity(this, MainActivity.class, true);
                    } else {
                        txtAlert.setText(passwordValidationResponse.getDescryptions());
                        txtAlert.setVisibility(View.VISIBLE);
                    }


//if 1010 go to enter pass -- if 1011 go to otp


                }
        );

        checkPasswordViewModel.getApiAuthFailureErrorLiveData().observe(this, volleyError -> {
        });

        checkPasswordViewModel.getApiErrorLiveData().observe(this, volleyError -> goToFailApiPage("ApiError"));
        checkPasswordViewModel.getApiServerErrorLiveData().observe(this, volleyError ->

        {
            goToFailApiPage("ServerError");

        });
        checkPasswordViewModel.getApiTimeOutErrorLiveData().observe(this, volleyError ->
                goToFailApiPage("TimeOutError")

        );
        checkPasswordViewModel.getApiClientNetworkErrorLiveData().observe(this, volleyError -> goToFailApiPage("ClientNetworkError"));


        checkPasswordViewModel.getApiForbiden403ErrorLiveData().observe(this, volleyError -> {
        });
        checkPasswordViewModel.getApiValidation422ErrorLiveData().observe(this, volleyError -> {
        });

    }


    private void showWating() {
        prg.setVisibility(View.VISIBLE);
        ButtonHelper.toggleAppCompatButtonStatus(btnEnter, false);
    }

    private void stopWating() {
        prg.setVisibility(View.INVISIBLE);
        ButtonHelper.toggleAppCompatButtonStatus(btnEnter, true);
    }

    private void goToFailApiPage(String failCause) {

        Intent intent = new Intent(this, FailApiActivity.class);
        intent.putExtra("failCause", failCause);
        startActivity(intent);
        finish();

    }

    private void checkPassword(String password) {
        showWating();
        checkPasswordViewModel.callCheckPasswordViewModel(password);
    }

    private void clearPassword() {
        checkPasswordViewModel.clearPassword();
    }

}
