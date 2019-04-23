package ir.iconish.sanjinehsub.data.source.api;

import android.util.Log;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import javax.inject.Inject;

import ir.iconish.sanjinehsub.config.AppController;
import ir.iconish.sanjinehsub.data.model.RegisterPurchaseInfoResult;
import ir.iconish.sanjinehsub.util.AppConstants;
import ir.iconish.sanjinehsub.util.Purchase;

public class GetScoreApi {


  AppController appController;


  @Inject
  public GetScoreApi(AppController appController) {
    this.appController = appController;
  }


  public RegisterPurchaseInfoResult parseJson(JSONObject jsonObject) {
    RegisterPurchaseInfoResult registerPurchaseInfoResult = new RegisterPurchaseInfoResult();

    try {
      registerPurchaseInfoResult.setPurchaseResult(jsonObject.getBoolean("purchaseResult"));
      registerPurchaseInfoResult.setUserBalance(jsonObject.getInt("userBalance"));

    } catch (JSONException e) {
      Log.e("err", e.toString());
      e.printStackTrace();
    }

    return registerPurchaseInfoResult;
  }


  public void callGetScoreApi(String msisdn, Purchase purchase, final VolleyCallback volleyCallback) {


    String url = ConstantUrl.BASE + ConstantUrl.REGISTER_PURCHASEINFO ;
    Log.e("url=", url);

    JSONObject jsonObject = new JSONObject();
    try {
      jsonObject.put("purchaseitemtype", purchase.getItemType());
      jsonObject.put("purchasepackagename", purchase.getPackageName());
      jsonObject.put("purchasesku", purchase.getSku());
      jsonObject.put("purchasetime", purchase.getPurchaseTime());
      jsonObject.put("purchasestate", purchase.getPurchaseState());
      jsonObject.put("developerpayload", purchase.getDeveloperPayload());
      jsonObject.put("purchasetoken", purchase.getToken());
      jsonObject.put("purchaseorderid", purchase.getOrderId());
      jsonObject.put("purchaseoriginaljson", purchase.getOriginalJson());
      jsonObject.put("purchasesignature", purchase.getSignature());
      jsonObject.put("msisdn",msisdn);

    } catch (JSONException e) {
      e.printStackTrace();
    }

    // JsonArrayRequest

    JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST, url, jsonObject,
      response -> {
        Log.e("Server response", response.toString());
        if (response != null) {
          RegisterPurchaseInfoResult registerPurchaseInfoResult = parseJson(response);
          volleyCallback.onSuccess(registerPurchaseInfoResult);
          // volleyCallback.onSuccess(visits);
        }


      }, error -> {
        Log.e("api error=", error.toString());
        if ((error instanceof NetworkError) || (error instanceof NoConnectionError)) {
          volleyCallback.onClientNetworkError();
          return;
        }
        if (error instanceof TimeoutError) {
          volleyCallback.onTimeOutError();
          return;
        }


        if ((error instanceof ServerError)) {
          volleyCallback.onServerError();
          return;
        }


        int statusCode = error.networkResponse.statusCode;

        // String message=new String(error.networkResponse.data);
             /*   String errorMessage=ApiErrorHelper.parseError(message);
                if (statusCode==401){
                    volleyCallback.onAuthFailureError401(errorMessage);
                    return;
                }
                if (statusCode==403){
                    volleyCallback.onForbiden403(errorMessage);
                    return;
                }
                if (statusCode==422){
                    volleyCallback.onValidationError422(errorMessage);
                    return;
                }


                volleyCallback.onFail(errorMessage);*/

      }

    ) {
           /* @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }*/

//      @Override
//      public Map<String, String> getHeaders() {
//        Map<String, String> params = new HashMap<String, String>();
//        params.put("appid", AppConstants.APP_ID);
//        return params;
//      }
    };


    jsonObjReq.setRetryPolicy(new DefaultRetryPolicy(AppConstants.CLIENT_TIMEOUT, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    String tag_json_arry = "getScoreApi";
    appController.addToRequestQueue(jsonObjReq, tag_json_arry);


  }


}
