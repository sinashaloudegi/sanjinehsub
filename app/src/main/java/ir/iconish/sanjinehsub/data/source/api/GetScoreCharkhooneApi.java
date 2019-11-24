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
import com.crashlytics.android.Crashlytics;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import ir.iconish.sanjinehsub.config.AppController;
import ir.iconish.sanjinehsub.data.model.CreditScorePreProcess;
import ir.iconish.sanjinehsub.data.model.IrancellMainDTO;
import ir.iconish.sanjinehsub.util.AppConstants;

/**
 * @author s.shaloudegi
 * @date 11/13/2019
 */
public class GetScoreCharkhooneApi {


    //
    private static final String TAG = "_SCORE";
    AppController appController;
    private int retry = 0;

    @Inject
    public GetScoreCharkhooneApi(AppController appController) {
        this.appController = appController;
    }


    @NonNull
    public CreditScorePreProcess parseJson(@NonNull JSONObject jsonObject) {
        CreditScorePreProcess creditScorePreProcess = new CreditScorePreProcess();
        try {

            try {
                String reqToken = jsonObject.getString("reqToken");
                Log.d(TAG, "reqToken: " + reqToken);
                creditScorePreProcess.setReqToken(reqToken);
            } catch (JSONException e) {
                e.printStackTrace();

            }


            String reportDescryption = jsonObject.getString("reportDescryption");
            int reportStateId = jsonObject.getInt("reportStateId");
            creditScorePreProcess.setReportDescryption(reportDescryption);
            creditScorePreProcess.setReportStateId(reportStateId);

        } catch (JSONException e) {
            Log.d(TAG, "except: " + e.toString());
            e.printStackTrace();
        }


        return creditScorePreProcess;

    }


    public void callGetScoreCharkhooneApi(IrancellMainDTO irancellMainDTO, @NonNull String token, @NonNull final VolleyCallback volleyCallback) {

        String url = ConstantUrl.BASE_SECURITY + ConstantUrl.CHARKHOONE_REGISTERPURCHASEINFO;
        Log.d(TAG, "callGetScoreApi: CALLING" + url);
        Crashlytics.setString("url", url);


        JSONObject jsonObject = new JSONObject();
        JSONObject irancellMainJson = new JSONObject();
        JSONObject irancellPurchaseJson = new JSONObject();
        JSONObject irancellSubJson = new JSONObject();
        JSONObject irancellReportJson = new JSONObject();

        try {

            irancellMainJson.put("IrancellPurchaseJson", irancellPurchaseJson);
            irancellMainJson.put("IrancellSubJson", irancellSubJson);
            irancellMainJson.put("IrancellReportJson", irancellReportJson);
            jsonObject.put("IrancellMainDTO", irancellMainJson);

            Log.d(TAG, "irancellMainDTO.toString() " + irancellMainDTO.toString());

        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d(TAG, "callGetScoreApi: finalURL:" + jsonObject.toString());


        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST, url, jsonObject,
                response -> {
                    Log.d(TAG, "callGetScoreApi: Response" + response);
                    if (response != null) {
                        CreditScorePreProcess getCreditScorePrepareProcess = parseJson(response);
                        Log.d(TAG, "callGetScoreApi: Response=" + getCreditScorePrepareProcess.toString());

                        volleyCallback.onSuccess(getCreditScorePrepareProcess);

                    }


                }, error -> {
            Log.d(TAG, "callGetScoreApi: error" + error);
            if ((error instanceof NetworkError) || (error instanceof NoConnectionError)) {
                volleyCallback.onClientNetworkError();
                if (retry < 3) {
                    retry++;
                    Log.e("rety:", retry + "");
                    // TODO: 11/13/2019 set Retry Policy

                    return;
                }
                return;
            }
            if (error instanceof TimeoutError) {
                volleyCallback.onTimeOutError();
                if (retry < 3) {
                    retry++;
                    Log.e("rety:", retry + "");
                    // TODO: 11/13/2019 set Retry Policy

                    return;
                }
                return;
            }


            if ((error instanceof ServerError)) {


                if (retry < 3) {
                    retry++;
                    Log.e("rety:", retry + "");
                    // TODO: 11/13/2019 set Retry Policy

                    return;
                }
                volleyCallback.onServerError();
                return;
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
                params.put("Authorization", token);
                return params;
            }
        };


        jsonObjReq.setRetryPolicy(new DefaultRetryPolicy(AppConstants.CLIENT_TIMEOUT_CREDIT, 1, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        String tag_json_arry = "getScoreApi";
        appController.addToRequestQueue(jsonObjReq, tag_json_arry);


    }


    //

}
