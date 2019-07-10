package ir.iconish.sanjinehsub.data.source.api;

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
import ir.iconish.sanjinehsub.data.model.NumberOfSanjineh;
import ir.iconish.sanjinehsub.util.AppConstants;

/**
 * @author s.shaloudegi
 * @date 7/3/2019
 */
public class UserNumberOfSanjinehApi {


    private static final String TAG = "UserNumberOfSanjinehApi";
    AppController
            appController;


    @Inject
    public UserNumberOfSanjinehApi(AppController appController) {
        this.appController = appController;
    }

    @NonNull
    public NumberOfSanjineh parseJson(@NonNull JSONObject jsonObject) {
        NumberOfSanjineh numberOfSanjineh = new NumberOfSanjineh();

        try {

            int balance = jsonObject.getInt("balance");
            int unitValue = jsonObject.getInt("unitValue");

            numberOfSanjineh.setBalance(balance);
            numberOfSanjineh.setUnitValue(unitValue);


        } catch (JSONException e) {
            //Log.e("err",e.toString());
            e.printStackTrace();
        }

        return numberOfSanjineh;
    }

    public void callGetNumberOfSanjinehApi(String mobileNumer, @NonNull final VolleyCallback volleyCallback) {


        String url = ConstantUrl.BASE_MARKET + ConstantUrl.GET_BALANCE;

        url += mobileNumer;

        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("mobile", mobileNumer);
        } catch (JSONException e) {
            e.printStackTrace();
        }


        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                url, jsonObject,
                response -> {


                    //Log.e("Server response",response.toString());

                    if (response != null) {

                        NumberOfSanjineh numberOfSanjineh = parseJson(response);
                        volleyCallback.onSuccess(numberOfSanjineh);

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


            @NonNull
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> params = new HashMap<String, String>();

                return params;
            }


        };


        jsonObjReq.setRetryPolicy(new DefaultRetryPolicy(
                AppConstants.CLIENT_TIMEOUT,
                1,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        String tag_json_arry = "userNnumberOfSanjinehApi";
        appController.addToRequestQueue(jsonObjReq, tag_json_arry);


    }

}
