package ir.iconish.sanjinehsub.data.source.api;

import android.util.Log;

import androidx.annotation.NonNull;

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
import ir.iconish.sanjinehsub.data.model.PasswordValidationResponse;
import ir.iconish.sanjinehsub.util.AppConstants;

public class ForgetPasswordApi {


    AppController
            appController;


    @Inject
    public ForgetPasswordApi(AppController appController) {
        this.appController=appController;
    }


    @NonNull
    public PasswordValidationResponse parseJson(@NonNull JSONObject jsonObject) {
        PasswordValidationResponse passwordValidationResponse=new PasswordValidationResponse();

      try {
            JSONObject jsonObjectRoot=jsonObject.getJSONObject("responseStatus");
          int statusCode=  jsonObjectRoot.getInt("value");
                String descr=jsonObjectRoot.getString("descr");


          passwordValidationResponse.setRespobseStatusCode(statusCode);
          passwordValidationResponse.setDescryptions(descr);


             /*   else if (statusCode==1011){

            }*/
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return passwordValidationResponse;
    }

    public void callForgetPasswordApi(String mobileNumer, @NonNull final VolleyCallback volleyCallback) {


        String   url=ConstantUrl.BASE+ConstantUrl.FORGET_PASSWORD;
JSONObject jsonObject=new JSONObject();

        try {

            jsonObject.put("mobilenumber",mobileNumer);
            jsonObject.put("lang","Fa");
            jsonObject.put("channel",AppConstants.CHANNEL);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                url,jsonObject,
                response -> {



                    if (response!=null){

                        PasswordValidationResponse passwordValidationResponse=     parseJson(response);
                   volleyCallback.onSuccess(passwordValidationResponse);

                       // volleyCallback.onSuccess(visits);
                    }





                }, error -> {
                    Log.e("api error=",error.toString());
                    if ((error instanceof NetworkError) || (error instanceof NoConnectionError) ) {

                        volleyCallback.onClientNetworkError();



                        return;
                    }
                    if (error instanceof TimeoutError){

                        volleyCallback.onTimeOutError();


                        return;
                    }




                    if ((error instanceof ServerError)){


                        volleyCallback.onServerError();

                        return;
                    }



                    int statusCode=error.networkResponse.statusCode;

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

        )

        {
            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }

            @NonNull
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> params = new HashMap<String, String>();

                params.put("appid", AppConstants.APP_ID);


                return params;
            }


        };




        jsonObjReq.setRetryPolicy(new DefaultRetryPolicy(
                AppConstants.CLIENT_TIMEOUT,
                1,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        String tag_json_arry = "checkPasswordApi";
        appController.addToRequestQueue(jsonObjReq, tag_json_arry);






    }


}
