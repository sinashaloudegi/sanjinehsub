package ir.iconish.sanjinehsub.data.repository;


import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import javax.inject.Inject;

import ir.iconish.sanjinehsub.data.model.AppConfig;
import ir.iconish.sanjinehsub.data.source.api.AppConfigApi;
import ir.iconish.sanjinehsub.data.source.api.VolleyCallback;
import ir.iconish.sanjinehsub.data.source.local.SharedPreferencesManager;


public class AppConfigRepository {
    AppConfigApi appConfigApi;

    SharedPreferencesManager sharedPreferencesManager;

    @Inject
    public AppConfigRepository(AppConfigApi appConfigApi, SharedPreferencesManager sharedPreferencesManager) {

        this.appConfigApi = appConfigApi;
        this.sharedPreferencesManager = sharedPreferencesManager;
    }

    public void callAppConfigRepository(@NonNull final VolleyCallback volleyCallback) {
        Log.d("callAppConfigRepository", "callAppConfigRepository: ");
        appConfigApi.callConfigApi(new VolleyCallback() {
            @Override
            public void onSuccess(Object o) {

                AppConfig appConfig = (AppConfig) o;
                sharedPreferencesManager.setMarketKeyValue(appConfig.getMarketKey());
                sharedPreferencesManager.setTimerDurationValue(appConfig.getTimerDuration());

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
    public String getUserPassword() {
        return sharedPreferencesManager.getPasswordValue();
    }

    public boolean isFirstRun() {
        return sharedPreferencesManager.isFirstRun();
    }


    public void setFirstRun(boolean b) {
        sharedPreferencesManager.setFirstRun(b);
    }
}
