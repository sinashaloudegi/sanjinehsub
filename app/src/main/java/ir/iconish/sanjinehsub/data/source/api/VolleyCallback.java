package com.visit24.therapist.data.source.api;

public interface VolleyCallback<T> {

  public void onSuccess(Object O);

  public void onFail(String volleyError);
  public void onServerError();
  public void onClientNetworkError();
  public void onTimeOutError();
  public void onForbiden403(String volleyError);
  public void on404(String volleyError);
  public void onValidationError422(String volleyError);
  public void onAuthFailureError401(String authFailureError);


}
