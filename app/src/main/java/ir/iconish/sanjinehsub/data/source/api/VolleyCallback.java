package ir.iconish.sanjinehsub.data.source.api;

public interface VolleyCallback<T> {

    void onSuccess(Object O);

    void onFail(String volleyError);

    void onServerError();

    void onClientNetworkError();

    void onTimeOutError();

    void onForbiden403(String volleyError);

    void on404(String volleyError);

    void onValidationError422(String volleyError);

    void onAuthFailureError401(String authFailureError);


}
