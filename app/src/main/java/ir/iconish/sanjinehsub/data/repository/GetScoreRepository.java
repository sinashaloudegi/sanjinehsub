package ir.iconish.sanjinehsub.data.repository;


import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.crashlytics.android.Crashlytics;

import javax.inject.Inject;

import ir.iconish.sanjinehsub.data.source.api.CreditScorePreProcessApi;
import ir.iconish.sanjinehsub.data.source.api.VolleyCallback;
import ir.iconish.sanjinehsub.data.source.local.SharedPreferencesManager;
import ir.iconish.sanjinehsub.util.Purchase;


public class GetScoreRepository {
    private static final String TAG = "_score";

    CreditScorePreProcessApi getScoreApi;


    SharedPreferencesManager sharedPreferencesManager;

    @Inject
    public GetScoreRepository(CreditScorePreProcessApi getScoreApi, SharedPreferencesManager sharedPreferencesManager) {

        this.getScoreApi = getScoreApi;
        this.sharedPreferencesManager = sharedPreferencesManager;
    }

    public void callGetScoreRepository(String ownerMobile, String ntCode, int persontypeid, int personalitytypeId, int paymenttypeid, int channelId, int verifycode, Purchase purchase, @NonNull final VolleyCallback volleyCallback) {

        Log.d(TAG, "callGetScoreRepository: ____");
        String mobileNumber = sharedPreferencesManager.getMobileNumberValue();
        Crashlytics.setString("MobileNumber", mobileNumber);
        if (persontypeid == 1) {
            Log.d(TAG, "Before: callGetScoreRepository: " + ntCode);
            ntCode = sharedPreferencesManager.getNationalCodeValue();
            Log.d(TAG, "callGetScoreRepository: " + ntCode);
        }
        Log.d(TAG, "callGetScoreRepository: PersnTypeId: " + persontypeid);
        Log.d(TAG, "callGetScoreRepository: ntcode: " + ntCode);

        String token = sharedPreferencesManager.getTokenValue();
        Log.d(TAG, "callGetScoreRepository: Now lets call getScoreApi and ntcod=" + ntCode);
        getScoreApi.callGetScoreApi(mobileNumber, ntCode, persontypeid, personalitytypeId, paymenttypeid, channelId, token, verifycode, ownerMobile, purchase, new VolleyCallback() {
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


    @Nullable
    public String getMarketKey() {
        return sharedPreferencesManager.getMarketKeyValue();
    }

}
