package ir.iconish.sanjinehsub.data.repository;



import android.widget.CheckBox;

import javax.inject.Inject;

import ir.iconish.sanjinehsub.data.model.PasswordValidationResponse;
import ir.iconish.sanjinehsub.data.model.ResponseCodeEnum;
import ir.iconish.sanjinehsub.data.model.User;
import ir.iconish.sanjinehsub.data.source.api.CheckPasswordApi;
import ir.iconish.sanjinehsub.data.source.api.LoginApi;
import ir.iconish.sanjinehsub.data.source.api.VolleyCallback;
import ir.iconish.sanjinehsub.data.source.local.SharedPreferencesManager;


public class CheckPasswordRepository {
   CheckPasswordApi checkPasswordApi;


SharedPreferencesManager sharedPreferencesManager;

@Inject
    public CheckPasswordRepository(CheckPasswordApi checkPasswordApi, SharedPreferencesManager sharedPreferencesManager) {

this.checkPasswordApi=checkPasswordApi;
this.sharedPreferencesManager=sharedPreferencesManager;
    }

    public void callCheckPasswordRepository  (String password, final VolleyCallback volleyCallback) {
        checkPasswordApi.callCheckPasswordApi(password,sharedPreferencesManager.getMobileNumberValue(),new VolleyCallback() {
        @Override
        public   void onSuccess(Object o) {

            PasswordValidationResponse passwordValidationResponse= (PasswordValidationResponse) o;

           if( passwordValidationResponse.getRespobseStatusCode()==1000){
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

    public void clearPassword(){
        sharedPreferencesManager.clearPassword();
    }

    public int getTimerDuration(){
        return sharedPreferencesManager.getTimerDurationValue();
    }
}
