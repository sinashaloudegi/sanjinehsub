package ir.iconish.sanjinehsub.ui.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

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
import ir.iconish.sanjinehsub.data.model.LoginStatusEnum;
import ir.iconish.sanjinehsub.data.vm.VerifyRegisterOtpViewModel;
import ir.iconish.sanjinehsub.ui.ActivityNavigationHelper;
import ir.iconish.sanjinehsub.util.ButtonHelper;

public class VerifyRegisterOtpActivity extends AppCompatActivity {

    @Nullable
    @BindView(R.id.txtTimer)
    TextView txtTimer;


    @Nullable
    @BindView(R.id.btnEnter)
    AppCompatButton btnEnter;


    @Nullable
    @BindView(R.id.txtAlert)
    TextView txtAlert;

    static String mobileNumber;


    @Nullable
    @BindView(R.id.edtVerifyCode)
    EditText edtVerificationCode;


    @Nullable
    @BindView(R.id.prg)
    ProgressBar prg;


    @Nullable
    @BindView(R.id.btnRetry)
    AppCompatButton btnRetry;


    CountDownTimer countDownTimer;


    @Inject
    VerifyRegisterOtpViewModel confirmRegisterViewModel;
    @Nullable
    @BindView(R.id.txt_code_sent)
    TextView txtCodeSent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        ButterKnife.bind(this);
        ((AppController) getApplication()).getAppComponent().inject(this);


        mobileNumber = this.getIntent().getExtras().getString("mobile");

        codeSentStringBuilder();
        attachViewModel();
        startTimer();


    }

    private void codeSentStringBuilder() {
        String str1 = "کد فعالسازی برای شماره";
        String str2 = "پیامک شده است";
        String result = String.format("%1$s %2$s \n %3$s", str1, mobileNumber, str2);
        txtCodeSent.setText(result);
        Toast.makeText(this, result, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();


    }

    private void receiveCodeAction(String code) {
        try {
            code = code.replaceAll("[^0-9]", "");
            edtVerificationCode.setText(code);


        } catch (Exception ignored) {
        }

    }

    @OnClick(R.id.imgBack)
    public void imgBackAction() {
        ActivityNavigationHelper.navigateToActivity(this, LoginActivity.class, true);

    }

    private void startTimer() {
        // long timerDuration=Long.parseLong(getString(R.string.timer_start_value));
        int timerDuration = confirmRegisterViewModel.getTimerDuration();

        countDownTimer = new CountDownTimer(timerDuration, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {
                if (millisUntilFinished < 10000) {
                    txtTimer.setTextColor(Color.RED);
                }
                txtTimer.setText(millisUntilFinished / 1000 + "");

            }

            @Override
            public void onFinish() {
                txtTimer.setText("0");
                btnEnter.setVisibility(View.INVISIBLE);
                btnRetry.setVisibility(View.VISIBLE);
                // pinEntry.setEnabled(false);
                //  pinEntry.setText("");
                //   bottomLayout.setVisibility(View.VISIBLE);
                try {
                    //   unregisterReceiver(broadcastReceiver);
                } catch (Exception ignored) {
                }
            }

        }.start();

    }

    @OnClick(R.id.btnEnter)
    public void btnEnterAction() {
        txtAlert.setVisibility(View.INVISIBLE);
        String otp = edtVerificationCode.getText().toString();
        if (otp.length() < 1) {
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
        ActivityNavigationHelper.navigateToActivity(this, LoginActivity.class, true);
    }


    private void attachViewModel() {
        confirmRegisterViewModel.getApiSuccessLiveDataResponse().observe(this, passwordValidationResponse -> {
                    stopWating();


                    if (passwordValidationResponse.getRespobseStatusCode() == LoginStatusEnum.VERIFY_SUCCESS_AND_NEW.getValue()) {

                        ActivityNavigationHelper.navigateToActivity(this, SetPasswordActivity.class, true);
                    } else {
                        txtAlert.setText((LoginStatusEnum.fromValue(passwordValidationResponse.getRespobseStatusCode()).getDescr()));
                        txtAlert.setVisibility(View.VISIBLE);
                    }


                }
        );

        confirmRegisterViewModel.getApiAuthFailureErrorLiveData().observe(this, volleyError -> {
        });

        confirmRegisterViewModel.getApiErrorLiveData().observe(this, volleyError -> goToFailApiPage("ApiError"));
        confirmRegisterViewModel.getApiServerErrorLiveData().observe(this, volleyError ->

                goToFailApiPage("ServerError"));
        confirmRegisterViewModel.getApiTimeOutErrorLiveData().observe(this, volleyError ->
                goToFailApiPage("TimeOutError")

        );
        confirmRegisterViewModel.getApiClientNetworkErrorLiveData().observe(this, volleyError -> goToFailApiPage("ClientNetworkError"));


        confirmRegisterViewModel.getApiForbiden403ErrorLiveData().observe(this, volleyError -> {
        });
        confirmRegisterViewModel.getApiValidation422ErrorLiveData().observe(this, volleyError -> {
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

    private void verifyRegisterOtp(String otp) {
        showWating();
        confirmRegisterViewModel.callVerifyRegisterOtpViewModel(otp);
    }


}
