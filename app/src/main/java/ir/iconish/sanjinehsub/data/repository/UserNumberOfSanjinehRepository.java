package ir.iconish.sanjinehsub.data.repository;

import androidx.annotation.NonNull;

import javax.inject.Inject;

import ir.iconish.sanjinehsub.data.source.api.UserNumberOfSanjinehApi;
import ir.iconish.sanjinehsub.data.source.api.VolleyCallback;
import ir.iconish.sanjinehsub.data.source.local.SharedPreferencesManager;

/**
 * @author s.shaloudegi
 * @date 7/3/2019
 */
public class UserNumberOfSanjinehRepository {

    private static final String TAG = "_score";

    UserNumberOfSanjinehApi mUserNumberOfSanjinehApi;


    SharedPreferencesManager sharedPreferencesManager;

    @Inject
    public UserNumberOfSanjinehRepository(UserNumberOfSanjinehApi mUserNumberOfSanjinehApi, SharedPreferencesManager sharedPreferencesManager) {

        this.mUserNumberOfSanjinehApi = mUserNumberOfSanjinehApi;
        this.sharedPreferencesManager = sharedPreferencesManager;
    }

    public void callGetNumberOfSanjinehRepository(@NonNull final VolleyCallback volleyCallback) {

        String mobileNumber = sharedPreferencesManager.getMobileNumberValue();


        mUserNumberOfSanjinehApi.callGetNumberOfSanjinehApi(mobileNumber, new VolleyCallback() {
            @Override
            public void onSuccess(Object o) {
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


    public String geMobileNumber() {
        return sharedPreferencesManager.getMobileNumberValue();
    }
}
