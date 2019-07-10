package ir.iconish.sanjinehsub.data.repository;


import androidx.annotation.NonNull;

import javax.inject.Inject;

import ir.iconish.sanjinehsub.data.model.PasswordValidationResponse;
import ir.iconish.sanjinehsub.data.source.api.SetPasswordApi;
import ir.iconish.sanjinehsub.data.source.api.VolleyCallback;
import ir.iconish.sanjinehsub.data.source.local.SharedPreferencesManager;


public class SetPasswordRepository {
   SetPasswordApi setPasswordApi;


SharedPreferencesManager sharedPreferencesManager;

@Inject
    public SetPasswordRepository(SetPasswordApi setPasswordApi, SharedPreferencesManager sharedPreferencesManager) {

this.setPasswordApi=setPasswordApi;
this.sharedPreferencesManager=sharedPreferencesManager;
    }

    public void callSetPasswordRepository(String password, @NonNull final VolleyCallback volleyCallback) {
        setPasswordApi.callSetPasswordApi(password,sharedPreferencesManager.getMobileNumberValue(),new VolleyCallback() {
        @Override
        public   void onSuccess(Object o) {
            PasswordValidationResponse passwordValidationResponse= (PasswordValidationResponse) o;

if(passwordValidationResponse.getRespobseStatusCode()==9999){
    sharedPreferencesManager.setPasswordValue(password);
}
            volleyCallback.onSuccess(o);
        }
        @Override
        public void onFail(String volleyError) {
            volleyCallback.onFail(volleyError);
        }

        @Override
        public void onServerError( ) {
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


}
