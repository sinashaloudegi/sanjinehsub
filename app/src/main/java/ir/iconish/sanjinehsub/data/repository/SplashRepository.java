package ir.iconish.sanjinehsub.data.repository;



import javax.inject.Inject;

import ir.iconish.sanjinehsub.data.model.User;
import ir.iconish.sanjinehsub.data.source.api.LoginApi;
import ir.iconish.sanjinehsub.data.source.api.VolleyCallback;
import ir.iconish.sanjinehsub.data.source.local.SharedPreferencesManager;


public class LoginRepository {
   LoginApi loginApi;


SharedPreferencesManager sharedPreferencesManager;

@Inject
    public LoginRepository( LoginApi loginApi, SharedPreferencesManager sharedPreferencesManager) {

this.loginApi=loginApi;
this.sharedPreferencesManager=sharedPreferencesManager;
    }

    public void callLoginRepository  (String mobileNumer , final VolleyCallback volleyCallback) {
        loginApi.callLoginApi(mobileNumer,new VolleyCallback() {
        @Override
        public   void onSuccess(Object o) {

            User user= (User) o;

            if(user.getResponseStatusCode()==1010){
                sharedPreferencesManager.setMobileNumberValue(user.getMobileNumber());
                sharedPreferencesManager.setFirstNameValue(user.getFirstName());
                sharedPreferencesManager.setLastNameValue(user.getLastName());
                sharedPreferencesManager.setEmailValue(user.getEmail());
                sharedPreferencesManager.setResponseStatusCodeValue(user.getResponseStatusCode());
                sharedPreferencesManager.setUserIdValue(user.getUserId());

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
