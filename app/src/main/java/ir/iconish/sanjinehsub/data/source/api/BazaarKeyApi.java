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

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import ir.iconish.sanjinehsub.config.AppController;
import ir.iconish.sanjinehsub.data.model.ResponseCodeEnum;
import ir.iconish.sanjinehsub.data.model.User;
import ir.iconish.sanjinehsub.util.AppConstants;
import ir.iconish.sanjinehsub.util.Purchase;

public class GetScoreApi {


  AppController appController;


  @Inject
  public GetScoreApi(AppController appController) {
    this.appController = appController;
  }


  public User parseJson(JSONObject jsonObject) {
    User user = new User();

    try {
      JSONObject jsonObjectRoot = jsonObject.getJSONObject("responseStatus");
      int statusCode = jsonObjectRoot.getInt("value");

      user.setResponseCodeEnum(ResponseCodeEnum.fromValue(statusCode));
      if (statusCode == 1010) {
        JSONObject jsonObjectUser = jsonObject.getJSONObject("accountInfo").getJSONObject("user");
        String firstName = jsonObjectUser.getString("firstname");
        String lastName = jsonObjectUser.getString("family");
        String email = jsonObjectUser.getString("email");
        String mobileNumber = jsonObjectUser.getString("mobile");
        long userId = jsonObjectUser.getLong("userid");
        //String token=jsonObject.getString("mobileNo");
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setMobileNumber(mobileNumber);
        user.setUserId(userId);


      }
             /*   else if (statusCode==1011){

            }*/
    } catch (JSONException e) {
      Log.e("err", e.toString());
      e.printStackTrace();
    }

    return user;
  }


  public void callGetScoreApi(long userId, Purchase purchase, final VolleyCallback volleyCallback) {


    String url = ConstantUrl.BASE + ConstantUrl.LOGIN + userId + "/" + AppConstants.VAS_SUBSCRIB;
    Log.e("url=", url);

    // JsonArrayRequest

    JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET, url, null,
      response -> {
        Log.e("Server response", response.toString());
        if (response != null) {
          User user = parseJson(response);
          volleyCallback.onSuccess(user);
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

      @Override
      public Map<String, String> getHeaders() {
        Map<String, String> params = new HashMap<String, String>();
        params.put("appid", AppConstants.APP_ID);
        return params;
      }
    };


    jsonObjReq.setRetryPolicy(new DefaultRetryPolicy(AppConstants.CLIENT_TIMEOUT, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    String tag_json_arry = "getScoreApi";
    appController.addToRequestQueue(jsonObjReq, tag_json_arry);


  }


}
