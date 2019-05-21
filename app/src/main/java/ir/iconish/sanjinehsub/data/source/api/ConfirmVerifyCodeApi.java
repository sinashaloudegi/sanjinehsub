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
import ir.iconish.sanjinehsub.util.AppConstants;

public class ConfirmVerifyCodeApi {


  AppController appController;


  @Inject
  public ConfirmVerifyCodeApi(AppController appController) {
    this.appController = appController;
  }


  public Integer parseJson(JSONObject jsonObject) {
    Integer reportStateEnumId = 0;
    try {
      reportStateEnumId = jsonObject.getInt("reportStateEnumId");

    } catch (JSONException e) {
      e.printStackTrace();
    }

    return reportStateEnumId;
  }


  public void callConfirmVerifyCodeApi(String msisdn, String code, final VolleyCallback volleyCallback) {


    String url = ConstantUrl.BASE_CREDIT + ConstantUrl.Confirm_VERIFYCODE + msisdn + "/" + code  ;




    JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET, url, null,
      response -> {

        if (response != null) {
          Integer reportStateEnumId = parseJson(response);
          volleyCallback.onSuccess(reportStateEnumId);
        }


      }, error -> {
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




      }

    ) {
    };


    jsonObjReq.setRetryPolicy(new DefaultRetryPolicy(AppConstants.CLIENT_TIMEOUT, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    String tag_json_arry = "confirmVerifySmsApi";
    appController.addToRequestQueue(jsonObjReq, tag_json_arry);


  }


}
