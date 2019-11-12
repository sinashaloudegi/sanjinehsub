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
import ir.iconish.sanjinehsub.data.model.AppConfig;
import ir.iconish.sanjinehsub.util.AppConstants;

public class AppConfigApi {


    AppController
            appController;


    @Inject
    public AppConfigApi(AppController appController) {
        this.appController = appController;
    }


    public void callConfigApi(@NonNull final VolleyCallback volleyCallback) {


        String url = ConstantUrl.BASE_MARKET + ConstantUrl.CAFEBAZAAR_CONFIG;
        Log.d("callConfigApi", "callConfigApi: " + url);

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                url, null,
                response -> {


                    if (response != null) {

                        AppConfig appConfig = parseJson(response);
                        volleyCallback.onSuccess(appConfig);

                    }


                }, error -> {
            Log.d("callConfigApi:", "callConfigApi: " + error.toString());
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

            }


        }

        ) {
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

    @NonNull
    public AppConfig parseJson(@NonNull JSONObject jsonObject) {
        AppConfig appConfig = new AppConfig();

        try {
            int marketEnumId = jsonObject.getInt("marketEnumId");
            int timerDuration = jsonObject.getInt("timerDuration");
            String marketKey = jsonObject.getString("marketKey");

            appConfig.setMarketEnumId(marketEnumId);
            appConfig.setTimerDuration(timerDuration);
            appConfig.setMarketKey(marketKey);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return appConfig;
    }
}
