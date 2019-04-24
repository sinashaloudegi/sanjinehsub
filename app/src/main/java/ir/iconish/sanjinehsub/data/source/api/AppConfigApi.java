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
import ir.iconish.sanjinehsub.data.model.AppConfig;
import ir.iconish.sanjinehsub.data.model.PasswordValidationResponse;
import ir.iconish.sanjinehsub.util.AppConstants;

public class AppConfigApi {


    AppController
            appController;


    @Inject
    public AppConfigApi(AppController appController) {
        this.appController=appController;
    }



    public AppConfig parseJson(JSONObject jsonObject){
        AppConfig appConfig=new AppConfig();

        try {
        int marketEnumId=    jsonObject.getInt("marketEnumId");
        int timerDuration=    jsonObject.getInt("timerDuration");
        String marketKey=    jsonObject.getString("marketKey");
        appConfig.setMarketEnumId(marketEnumId);
        appConfig.setTimerDuration(timerDuration);
        appConfig.setMarketKey(marketKey);

        } catch (JSONException e) {
            e.printStackTrace();
        }
//marketEnumId -marketKey-timerDuration
        return appConfig;
    }







    public void callConfigApi(int appId,final VolleyCallback volleyCallback){


        String   url=ConstantUrl.BASE_MARKET+ConstantUrl.APP_CONFIG+appId;
Log.e("callConfigApi=",url);


        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                url,null,
                response -> {


                    Log.e("Server response",response.toString());

                    if (response!=null){

                        AppConfig appConfig=     parseJson(response);
                   volleyCallback.onSuccess(appConfig);

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
