//package ir.iconish.sanjinehsub.data.repository;
//
//
//import javax.inject.Inject;
//
//import ir.iconish.sanjinehsub.data.source.api.BazaarKeyApi;
//import ir.iconish.sanjinehsub.data.source.api.VolleyCallback;
//
//
//public class BazaarKeyRepository {
//  BazaarKeyApi bazaarKeyApi;
//
//
//
//  @Inject
//  public BazaarKeyRepository(BazaarKeyApi bazaarKeyApi) {
//
//    this.bazaarKeyApi = bazaarKeyApi;
//  }
//
//  public void callBazaarKeyRepository(final VolleyCallback volleyCallback) {
//    bazaarKeyApi.callBazaarKeyApi(new VolleyCallback() {
//      @Override
//      public void onSuccess(Object o) {
//        volleyCallback.onSuccess(o);
//      }
//
//      @Override
//      public void onFail(String volleyError) {
//        volleyCallback.onFail(volleyError);
//      }
//
//      @Override
//      public void onServerError() {
//        volleyCallback.onServerError();
//      }
//
//      @Override
//      public void onClientNetworkError() {
//        volleyCallback.onClientNetworkError();
//      }
//
//      @Override
//      public void onTimeOutError() {
//        volleyCallback.onTimeOutError();
//      }
//
//      @Override
//      public void onForbiden403(String volleyError) {
//        volleyCallback.onForbiden403(volleyError);
//      }
//
//      @Override
//      public void on404(String volleyError) {
//        volleyCallback.on404(volleyError);
//      }
//
//      @Override
//      public void onValidationError422(String volleyError) {
//        volleyCallback.onValidationError422(volleyError);
//      }
//
//      @Override
//      public void onAuthFailureError401(String volleyError) {
//        volleyCallback.onAuthFailureError401(volleyError);
//      }
//    });
//
//
//  }
//
//
//}
