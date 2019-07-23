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

import javax.inject.Inject;

import ir.iconish.sanjinehsub.config.AppController;
import ir.iconish.sanjinehsub.data.model.AvailableReport;
import ir.iconish.sanjinehsub.util.AppConstants;

public class CheckReportApi {


    AppController appController;


    @Inject
    public CheckReportApi(AppController appController) {
        this.appController = appController;
    }


    @NonNull
    public AvailableReport parseJson(@NonNull JSONObject jsonObject) {
        AvailableReport availableReport = new AvailableReport();
        try {
            availableReport.setValidMobile(jsonObject.getBoolean("validMobile"));
            availableReport.setAvailable(jsonObject.getBoolean("isAvailable"));

        } catch (JSONException e) {
            // Log.e("err", e.toString());
            e.printStackTrace();
        }

        return availableReport;
    }


    public void callCheckReportApi(String ntcode, String mobileNumber, @NonNull final VolleyCallback volleyCallback) {


        String url = ConstantUrl.BASE_CREDITSCORE + ConstantUrl.CHECK_REPORT + ntcode + "/" + mobileNumber;
        // Log.e("url=", url);

        // JsonArrayRequest

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET, url, null,
                response -> {
                    //Log.e("Server response", response.toString());
                    if (response != null) {
                        AvailableReport availableReport = parseJson(response);
                        volleyCallback.onSuccess(availableReport);
                        // volleyCallback.onSuccess(visits);
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

        );


        jsonObjReq.setRetryPolicy(new DefaultRetryPolicy(AppConstants.CLIENT_TIMEOUT, 1, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        String tag_json_arry = "checkReportApi";
        appController.addToRequestQueue(jsonObjReq, tag_json_arry);


    }


}
