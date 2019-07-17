package ir.iconish.sanjinehsub.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.adpdigital.push.AdpPushClient;
import com.crashlytics.android.Crashlytics;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ir.iconish.sanjinehsub.R;
import ir.iconish.sanjinehsub.config.AppController;
import ir.iconish.sanjinehsub.data.model.LoginStatusEnum;
import ir.iconish.sanjinehsub.data.vm.LoginViewModel;
import ir.iconish.sanjinehsub.ui.ActivityNavigationHelper;
import ir.iconish.sanjinehsub.ui.DialogHelper;
import ir.iconish.sanjinehsub.util.ButtonHelper;

public class LoginActivity extends AppCompatActivity {


    private static final String TAG = "LoginActivity";
    @Nullable
    @BindView(R.id.mobile_number_login_edit_text)
    EditText mobileNumberLoginEditText;

    @Nullable
    @BindView(R.id.prgLogin)
    ProgressBar prgLogin;

    @Nullable
    @BindView(R.id.enter_login_button)
    Button enterLoginButton;
    @Inject
    LoginViewModel loginViewModel;
    private String mobileNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        ButterKnife.bind(this);
        ((AppController) getApplication()).getAppComponent().inject(this);
        attachViewModel();
        mobileNumberLoginEditTextChangeListener();

    }

    private void mobileNumberLoginEditTextChangeListener() {
        mobileNumberLoginEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                if (isMobileNumberValid(charSequence.toString())) {
                    enterLoginButton.setEnabled(true);
                    mobileNumber = mobileNumberLoginEditText.getText().toString();
                    mobileNumberLoginEditText.setError("Eshtebahe dadash");
                } else {
                    enterLoginButton.setEnabled(false);
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }


    @OnClick(R.id.enter_login_button)
    public void enterLoginButtonClick() {

        showWating();
        if (enterLoginButton.isEnabled() && isMobileNumberValid((mobileNumberLoginEditText.getText().toString()))) {
            mobileNumber = mobileNumberLoginEditText.getText().toString();

        } else {
            Toast.makeText(this, "Please Enter Correct Mobile Number", Toast.LENGTH_SHORT).show();
        }


        //TODO: also check if the rule is checked and after that you need to make an api call
        loginViewModel.callLoginViewModel(mobileNumber);
   /*     if (!mobileNumber.startsWith("09")) {
            txtAlertPhone.setText(getString(R.string.enter_correct_mobile_phone));
            txtAlertPhone.setVisibility(View.VISIBLE);
            return;
        }


        if (mobileNumber.length() < 11) {
            txtAlertPhone.setText(getString(R.string.enter_correct_mobile_phone));
            txtAlertPhone.setVisibility(View.VISIBLE);
            return;
        }*/

    }


    private void attachViewModel() {
        loginViewModel.getApiSuccessLiveDataResponse().observe(this, user -> {
                    stopWating();

            String userId = AdpPushClient.get().getUserId();

            if (userId != null && !userId.isEmpty()) {
                AdpPushClient.get().register(userId);
            } else {

                //If user is not registered verify the user and
                //call AdpPushClient.get().register("USER_ID") method at login page
                AdpPushClient.get().register(user.getMobileNumber() + "");
            }
                    if (user.getResponseCodeEnum().getValue() == LoginStatusEnum.USER_EXIST.getValue()) {
                        ActivityNavigationHelper.navigateToActivity(this, CheckPasswordActivity.class, true);
                        //ActivityNavigationHelper.navigateToActivity(this, MainActivity.class,true);
                    } else if (user.getResponseCodeEnum().getValue() == LoginStatusEnum.USERISNEW.getValue()) {
                        ActivityNavigationHelper.navigateToActivity(this, VerifyRegisterOtpActivity.class, true);

                    }


                }
        );

        loginViewModel.getApiAuthFailureErrorLiveData().observe(this, volleyError -> {
        });

        loginViewModel.getApiErrorLiveData().observe(this, volleyError -> {
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


        loginViewModel.getApiForbiden403ErrorLiveData().observe(this, volleyError -> {
        });
        loginViewModel.getApiValidation422ErrorLiveData().observe(this, volleyError -> {
        });

    }


    private void goToFailApiPage(String failCause) {

        Intent intent = new Intent(this, FailApiActivity.class);
        Crashlytics.setString("failCause", failCause);
        intent.putExtra("failCause", failCause);
        startActivity(intent);
        finish();

    }


    @Override
    public void onBackPressed() {
        DialogHelper.sureExit(this);
    }


    private void showWating() {
        prgLogin.setVisibility(View.VISIBLE);
        ButtonHelper.toggleAppCompatButtonStatus(enterLoginButton, false);
    }

    private void stopWating() {
        prgLogin.setVisibility(View.INVISIBLE);
        ButtonHelper.toggleAppCompatButtonStatus(enterLoginButton, true);
    }

    private boolean isMobileNumberValid(String mobileNumber) {


        return mobileNumber.length() > 10;

    }

}
