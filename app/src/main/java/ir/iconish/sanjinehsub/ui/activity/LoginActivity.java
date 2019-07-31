package ir.iconish.sanjinehsub.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextWatcher;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
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
import ir.iconish.sanjinehsub.data.model.User;
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
    @BindView(R.id.txt_accept_rules)
    TextView txtAcceptRule;

    @Nullable
    @BindView(R.id.prg_login)
    ProgressBar prgLogin;


    @Nullable
    @BindView(R.id.btn_enter_login)
    Button btnEnterLogin;


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
        coloredAndClickableText();
    }


    //This method is used to make a part of a text colored and clickable
    private void coloredAndClickableText() {
        SpannableString spannableString = new SpannableString(txtAcceptRule.getText().toString());
        ForegroundColorSpan color = new ForegroundColorSpan(getResources().getColor(R.color.main_color));

        ClickableSpan onRuleClicked = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View view) {
                ActivityNavigationHelper.navigateToWebView("https://www.sanjineh.ir/terms?from=android_cafebazar", LoginActivity.this, WebViewActivity.class);
                Toast.makeText(LoginActivity.this, "Rules", Toast.LENGTH_SHORT).show();
            }
        };
        spannableString.setSpan(color, 0, 15, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(onRuleClicked, 0, 15, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);


        txtAcceptRule.setText(spannableString);
        txtAcceptRule.setMovementMethod(LinkMovementMethod.getInstance());
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
                mobileNumber = mobileNumberLoginEditText.getText().toString();

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
    public void btnEnterLoginClick() {
        if (isMobileNumberValid((mobileNumberLoginEditText.getText().toString()))) {
            if (ruleIsChecked) {
                showWating();

                mobileNumber = mobileNumberLoginEditText.getText().toString();
                loginViewModel.callLoginViewModel(mobileNumber);
            } else {
                Toast.makeText(this, getString(R.string.accept_rules), Toast.LENGTH_SHORT).show();
            }

        } else {
            mobileNumberLoginEditText.setError("شماره موبایل معتبر نیست");
        }


    }


    private void attachViewModel() {
        loginViewModel.getApiSuccessLiveDataResponse().observe(this, user -> {
                    stopWating();

            registerToChabok(user);
                    if (user.getResponseCodeEnum().getValue() == LoginStatusEnum.USER_EXIST.getValue()) {
                        ActivityNavigationHelper.navigateToActivity(this, CheckPasswordActivity.class, true);
                    } else if (user.getResponseCodeEnum().getValue() == LoginStatusEnum.USERISNEW.getValue()) {
                        ActivityNavigationHelper.navigateToActivityWithData(this, VerifyRegisterOtpActivity.class, true, "mobile", mobileNumber);

                    }


                }
        );

        loginViewModel.getApiAuthFailureErrorLiveData().observe(this, volleyError -> {
        });

        loginViewModel.getApiErrorLiveData().observe(this, volleyError -> goToFailApiPage("ApiError"));
        loginViewModel.getApiServerErrorLiveData().observe(this, volleyError ->

                goToFailApiPage("ServerError"));
        loginViewModel.getApiTimeOutErrorLiveData().observe(this, volleyError ->
                goToFailApiPage("TimeOutError")
        );
        loginViewModel.getApiClientNetworkErrorLiveData().observe(this, volleyError -> goToFailApiPage("ClientNetworkError"));


        loginViewModel.getApiForbiden403ErrorLiveData().observe(this, volleyError -> {
        });
        loginViewModel.getApiValidation422ErrorLiveData().observe(this, volleyError -> {
        });

    }

    private void registerToChabok(User user) {
        //This is for Chabok, add user mobile number as userId
        String userId = AdpPushClient.get().getUserId();
        if (userId != null && !userId.isEmpty()) {
            AdpPushClient.get().register(userId);
        } else {

            //If user is not registered verify the user and
            //call AdpPushClient.get().register("USER_ID") method at login page
            AdpPushClient.get().register(user.getMobileNumber() + "");
        }
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
        ButtonHelper.toggleAppCompatButtonStatus(btnEnterLogin, false);
    }

    private void stopWating() {
        prgLogin.setVisibility(View.INVISIBLE);
        ButtonHelper.toggleAppCompatButtonStatus(btnEnterLogin, true);
    }

    private boolean isMobileNumberValid(String mobileNumber) {


        return mobileNumber.matches("(\\+98|0)?9\\d{9}");

    }

}
