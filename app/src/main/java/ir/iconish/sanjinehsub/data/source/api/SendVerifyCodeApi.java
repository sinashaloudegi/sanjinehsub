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
import ir.iconish.sanjinehsub.data.model.VerifyCodeOthersResponse;
import ir.iconish.sanjinehsub.util.AppConstants;

public class SendVerifyCodeApi {


  AppController appController;


  @Inject
  public SendVerifyCodeApi(AppController appController) {
    this.appController = appController;
  }


  public VerifyCodeOthersResponse parseJson(JSONObject jsonObject) {
    VerifyCodeOthersResponse verifyCodeOthersResponse = new VerifyCodeOthersResponse();
    try {
      verifyCodeOthersResponse.setStatusCode(jsonObject.getInt("reportStateId"));
      verifyCodeOthersResponse.setDescription(jsonObject.getString("reportStateValue"));

    } catch (JSONException e) {
      Log.e("err", e.toString());
      e.printStackTrace();
    }

    return verifyCodeOthersResponse;
  }


  public void callSendVerifyCodeApi(String ntcode, String ownermobile, String mobile, final VolleyCallback volleyCallback) {


    String url = ConstantUrl.BASE_CREDIT + ConstantUrl.SEND_VERIFYCODE + ntcode + "/" + ownermobile + "/" + mobile ;
    //https://creditscore.iconish.ir/icredit/sendVerifyCode/{ntcode}/{ownermobile}/{mobile}
    Log.e("url=", url);


    // JsonArrayRequest

    JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET, url, null,
      response -> {
        Log.e("Server response", response.toString());
        if (response != null) {
          VerifyCodeOthersResponse verifyCodeOthersResponse = parseJson(response);
          volleyCallback.onSuccess(verifyCodeOthersResponse);
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
    String tag_json_arry = "sendVerifySmsApi";
    appController.addToRequestQueue(jsonObjReq, tag_json_arry);


  }


}
