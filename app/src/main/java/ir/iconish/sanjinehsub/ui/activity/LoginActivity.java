package ir.iconish.sanjinehsub.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
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
    @BindView(R.id.edt_txt_mobile_number_login)
    EditText mobileNumberLoginEditText;

    @Nullable
    @BindView(R.id.prg_login)
    ProgressBar prgLogin;


    @Nullable
    @BindView(R.id.txt_number_error)
    TextView txtNumberError;

    @Nullable
    @BindView(R.id.btn_enter_login)
    Button enterLoginButton;


    @Nullable
    @BindView(R.id.check_box_blank)
    ImageView checkBoxBlank;

    @Nullable
    @BindView(R.id.layout_check_rule)
    FrameLayout layoutCheckRule;


    @Nullable
    @BindView(R.id.check_box)
    ImageView checkBox;

    @Inject
    LoginViewModel loginViewModel;
    private String mobileNumber;

    boolean ruleIsChecked = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        ButterKnife.bind(this);
        ((AppController) getApplication()).getAppComponent().inject(this);

        attachViewModel();
        mobileNumberLoginEditTextOnChangeListener();

    }

    private void toggleCheckRule() {

        if (ruleIsChecked) {
            ruleIsChecked = false;
            checkBoxBlank.setVisibility(View.VISIBLE);
            checkBox.setVisibility(View.GONE);
        } else {
            ruleIsChecked = true;
            checkBoxBlank.setVisibility(View.GONE);
            checkBox.setVisibility(View.VISIBLE);

        }

    }

    private void mobileNumberLoginEditTextOnChangeListener() {
        mobileNumberLoginEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                txtNumberError.setVisibility(View.INVISIBLE);
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

    @OnClick(R.id.layout_check_rule)
    public void onRuleCheckBoxListener() {
        toggleCheckRule();
    }

    @OnClick(R.id.btn_enter_login)
    public void enterLoginButtonClick() {
        showWating();
        if (enterLoginButton.isEnabled() && isMobileNumberValid((mobileNumberLoginEditText.getText().toString()))) {
         /*   if (checkBoxCheckRules.isSelected()) {
                loginViewModel.callLoginViewModel(mobileNumber);

            } else {
                txtNumberError.setVisibility(View.VISIBLE);
            }*/

            mobileNumber = mobileNumberLoginEditText.getText().toString();

        } else {
            Toast.makeText(this, "Please Enter Correct Mobile Number", Toast.LENGTH_LONG).show();
        }


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

        return mobileNumber.startsWith("09") && mobileNumber.length() >= 11;

    }

}
