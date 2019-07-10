package ir.iconish.sanjinehsub.data.repository;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import javax.inject.Inject;

import ir.iconish.sanjinehsub.data.source.api.ReportApi;
import ir.iconish.sanjinehsub.data.source.api.VolleyCallback;
import ir.iconish.sanjinehsub.data.source.local.SharedPreferencesManager;


public class ReportRepository {
  ReportApi reportApi;

  SharedPreferencesManager sharedPreferencesManager;

  @Inject
  public ReportRepository(ReportApi reportApi, SharedPreferencesManager sharedPreferencesManager) {

    this.reportApi = reportApi;
    this.sharedPreferencesManager=sharedPreferencesManager;
  }

    public void callReportRepository(String reqToken, @NonNull final VolleyCallback volleyCallback) {
    reportApi.callReportsApi(reqToken,new VolleyCallback() {
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
    public String getUserPassword(){
        return sharedPreferencesManager.getPasswordValue();
    }

}
