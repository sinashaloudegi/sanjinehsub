package ir.iconish.sanjinehsub.data.repository;

import android.util.Log;

import androidx.annotation.NonNull;

import javax.inject.Inject;

import ir.iconish.sanjinehsub.data.source.api.NewsApi;
import ir.iconish.sanjinehsub.data.source.api.VolleyCallback;
import ir.iconish.sanjinehsub.data.source.local.SharedPreferencesManager;

/**
 * @author s.shaloudegi
 * @date 8/24/2019
 */
public class NewsRepository {
    NewsApi mNewsApi;
    private static final String TAG = "NewsRepository";

    SharedPreferencesManager sharedPreferencesManager;

    @Inject
    public NewsRepository(NewsApi newsApi, SharedPreferencesManager sharedPreferencesManager) {

        this.mNewsApi = newsApi;
        this.sharedPreferencesManager = sharedPreferencesManager;
    }

    public void callNewsRepository(int arttypeid, @NonNull final VolleyCallback volleyCallback) {
        mNewsApi.callNewsApi(arttypeid, sharedPreferencesManager.getTokenValue(), new VolleyCallback() {
            @Override
            public void onSuccess(Object o) {

                Log.d(TAG, "onSuccess: " + o.toString());
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


}
