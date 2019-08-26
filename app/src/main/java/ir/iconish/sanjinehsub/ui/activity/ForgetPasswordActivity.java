package ir.iconish.sanjinehsub.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ir.iconish.sanjinehsub.R;
import ir.iconish.sanjinehsub.config.AppController;
import ir.iconish.sanjinehsub.data.vm.ForgetPasswordViewModel;
import ir.iconish.sanjinehsub.ui.ActivityNavigationHelper;
import ir.iconish.sanjinehsub.util.ButtonHelper;

public class ForgetPasswordActivity extends AppCompatActivity {
    @Nullable
    @BindView(R.id.btnSendPassword)
    AppCompatButton btnSendPassword;

    @Nullable
    @BindView(R.id.prg)
    ProgressBar prg;


    @Nullable
    @BindView(R.id.txtAlert)
    TextView txtAlert;


    @Nullable
    @BindView(R.id.edtPhone)
    TextView edtPhone;


    @Inject
    ForgetPasswordViewModel forgetPasswordViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        ButterKnife.bind(this);
        ((AppController) getApplication()).getAppComponent().inject(this);
        edtPhone.setText(forgetPasswordViewModel.getMobileNumber());
        attachViewModel();

    }


    private void attachViewModel() {
        forgetPasswordViewModel.getApiSuccessLiveDataResponse().observe(this, passwordValidationResponse -> {
                    stopWating();


                    if (passwordValidationResponse.getRespobseStatusCode() == 1000) {

                        ActivityNavigationHelper.navigateToActivityWithData(this, ForgetPassworReciverActivity.class, true, "mobileNumber", edtPhone.getText().toString());
                    } else {

                        txtAlert.setText(passwordValidationResponse.getDescryptions());
                        txtAlert.setVisibility(View.VISIBLE);
                    }

//if 1010 go to enter pass -- if 1011 go to otp

                }
        );

        forgetPasswordViewModel.getApiAuthFailureErrorLiveData().observe(this, volleyError -> {
        });

        forgetPasswordViewModel.getApiErrorLiveData().observe(this, volleyError -> {
            goToFailApiPage("ApiError");

        });
        forgetPasswordViewModel.getApiServerErrorLiveData().observe(this, volleyError ->

        {
            goToFailApiPage("ServerError");

        });
        forgetPasswordViewModel.getApiTimeOutErrorLiveData().observe(this, volleyError ->
                {
                    goToFailApiPage("TimeOutError");
                }

        );
        forgetPasswordViewModel.getApiClientNetworkErrorLiveData().observe(this, volleyError -> {
            goToFailApiPage("ClientNetworkError");


        });


        forgetPasswordViewModel.getApiForbiden403ErrorLiveData().observe(this, volleyError -> {
        });
        forgetPasswordViewModel.getApiValidation422ErrorLiveData().observe(this, volleyError -> {
        });

    }


    private void showWating() {
        prg.setVisibility(View.VISIBLE);
        ButtonHelper.toggleAppCompatButtonStatus(btnSendPassword, false);
    }

    private void stopWating() {
        prg.setVisibility(View.INVISIBLE);
        ButtonHelper.toggleAppCompatButtonStatus(btnSendPassword, true);
    }

    private void goToFailApiPage(String failCause) {

        Intent intent = new Intent(this, FailApiActivity.class);
        intent.putExtra("failCause", failCause);
        startActivity(intent);
        finish();

    }

    private void forgetPassword(String mobilePhone) {
        showWating();
        forgetPasswordViewModel.callForgetPasswordViewModel(mobilePhone);
    }

    @OnClick(R.id.btnSendPassword)
    public void btnSendPasswordAction() {
        txtAlert.setVisibility(View.INVISIBLE);
        String mobileNumber = edtPhone.getText().toString();
        if (!mobileNumber.startsWith("09")) {
            txtAlert.setText(getString(R.string.enter_correct_mobile_phone));
            txtAlert.setVisibility(View.VISIBLE);
            return;
        }


        if (mobileNumber.length() < 11) {
            txtAlert.setText(getString(R.string.enter_correct_mobile_phone));
            txtAlert.setVisibility(View.VISIBLE);
            return;
        }

        forgetPassword(mobileNumber);

    }

}
