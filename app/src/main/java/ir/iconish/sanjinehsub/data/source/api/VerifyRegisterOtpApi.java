package ir.iconish.sanjinehsub.data.source.api;

import android.util.Log;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import ir.iconish.sanjinehsub.config.AppController;
import ir.iconish.sanjinehsub.data.model.PasswordValidationResponse;
import ir.iconish.sanjinehsub.data.model.ResponseCodeEnum;
import ir.iconish.sanjinehsub.util.AppConstants;

public class VerifyRegisterOtpApi {


    AppController
            appController;


    @Inject
    public VerifyRegisterOtpApi(AppController appController) {
        this.appController=appController;
    }



    public PasswordValidationResponse parseJson(JSONObject jsonObject){
        PasswordValidationResponse passwordValidationResponse=new PasswordValidationResponse();
Log.e("verif",jsonObject.toString());
      try {
            JSONObject jsonObjectRoot=jsonObject.getJSONObject("responseStatus");
          int statusCode=  jsonObjectRoot.getInt("value");
                String descr=jsonObjectRoot.getString("descr");
                if(statusCode== ResponseCodeEnum.VERIFY_SUCCESS_AND_NEW.getValue()) {
                    String token = jsonObject.getString("token");
                    passwordValidationResponse.setToken(token);
                }
          passwordValidationResponse.setRespobseStatusCode(statusCode);
          passwordValidationResponse.setDescryptions(descr);



             /*   else if (statusCode==1011){

            }*/
        } catch (JSONException e) {
            Log.e("err",e.toString());
            e.printStackTrace();
        }

        return passwordValidationResponse;
    }







    public void callVerifyRegisterOtpApi(String otp,String mobileNumber,final VolleyCallback volleyCallback){
Log.e("phone",mobileNumber);

        String   url=ConstantUrl.BASE+ConstantUrl.CONFIRM_REGISTER;

JSONObject jsonObject=new JSONObject();

        try {

            jsonObject.put("msisdn",mobileNumber);
            jsonObject.put("otp",otp);
            jsonObject.put("channel",AppConstants.CHANNEL);
            jsonObject.put("vasSub",AppConstants.VAS_SUBSCRIB);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                url,jsonObject,
                response -> {

Log.e("res server otp",response.toString());


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

            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> params = new HashMap<String, String>();

                params.put("appid", AppConstants.APP_ID);


                return params;
            }


        };




        jsonObjReq.setRetryPolicy(new DefaultRetryPolicy(
                AppConstants.CLIENT_TIMEOUT,
                0,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        String tag_json_arry = "checkPasswordApi";
        appController.addToRequestQueue(jsonObjReq, tag_json_arry);






    }


}
