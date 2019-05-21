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
        return appConfig;
    }







    public void callConfigApi(final VolleyCallback volleyCallback){


        String   url=ConstantUrl.BASE_MARKET+ConstantUrl.APP_CONFIG;



        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                url,null,
                response -> {



                    if (response!=null){

                        AppConfig appConfig=     parseJson(response);
                   volleyCallback.onSuccess(appConfig);

                    }





                }, error -> {
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
