package ir.iconish.sanjinehsub.data.repository;


import androidx.annotation.NonNull;

import javax.inject.Inject;

import ir.iconish.sanjinehsub.data.model.LoginStatusEnum;
import ir.iconish.sanjinehsub.data.model.PasswordValidationResponse;
import ir.iconish.sanjinehsub.data.source.api.VerifyRegisterOtpApi;
import ir.iconish.sanjinehsub.data.source.api.VolleyCallback;
import ir.iconish.sanjinehsub.data.source.local.SharedPreferencesManager;


public class VerifyRegisterOtpRepository {
    VerifyRegisterOtpApi verifyRegisterOtpApi;


    SharedPreferencesManager sharedPreferencesManager;

    @Inject
    public VerifyRegisterOtpRepository(VerifyRegisterOtpApi verifyRegisterOtpApi, SharedPreferencesManager sharedPreferencesManager) {

        this.verifyRegisterOtpApi = verifyRegisterOtpApi;
        this.sharedPreferencesManager = sharedPreferencesManager;
    }

    public void callVerifyRegisterOtpRepository(String otp, @NonNull final VolleyCallback volleyCallback) {
        verifyRegisterOtpApi.callVerifyRegisterOtpApi(otp, sharedPreferencesManager.getMobileNumberValue(), new VolleyCallback() {
            @Override
            public void onSuccess(Object o) {

                PasswordValidationResponse passwordValidationResponse = (PasswordValidationResponse) o;

                if (passwordValidationResponse.getRespobseStatusCode() == LoginStatusEnum.VERIFY_SUCCESS_AND_NEW.getValue()) {
                    String token = passwordValidationResponse.getToken();

                    sharedPreferencesManager.setPasswordValue(otp);
                    sharedPreferencesManager.setTokenValue(token);
                }
                volleyCallback.onSuccess(o);
            }

            @Override
            public void onFail(String volleyError) {
                volleyCallback.onFail(volleyError);
            }

            @Override
            public void onServerError() {
                volleyCallback.onServerError();
            }

            @Override
            public void onClientNetworkError() {
                volleyCallback.onClientNetworkError();
            }

            @Override
            public void onTimeOutError() {
                volleyCallback.onTimeOutError();
            }

            @Override
            public void onForbiden403(String volleyError) {
                volleyCallback.onForbiden403(volleyError);
            }

            @Override
            public void on404(String volleyError) {
                volleyCallback.on404(volleyError);
            }

            @Override
            public void onValidationError422(String volleyError) {
                volleyCallback.onValidationError422(volleyError);
            }

            @Override
            public void onAuthFailureError401(String volleyError) {
                volleyCallback.onAuthFailureError401(volleyError);
            }
        });


    }

    public int getTimerDuration() {
        return sharedPreferencesManager.getTimerDurationValue();
    }

}
