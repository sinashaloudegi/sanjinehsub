package ir.iconish.sanjinehsub.data.source.api;

import android.net.Uri;
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
import ir.iconish.sanjinehsub.data.model.CreditScorePreProcess;
import ir.iconish.sanjinehsub.util.AppConstants;
import ir.iconish.sanjinehsub.util.Purchase;

public class CreditScorePreProcessApi {
    private static final String TAG = "_SCORE";
    AppController appController;
    private int retry = 0;

    @Inject
    public CreditScorePreProcessApi(AppController appController) {
        this.appController = appController;
    }


    public CreditScorePreProcess parseJson(JSONObject jsonObject) {
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


    public void callGetScoreApi(String mobilephone, String ntcode, int persontypeid, int personalitytypeId, int paymenttypeid, int channelId, String token, int verifyCode, String ownerMobile, Purchase purchase, final VolleyCallback volleyCallback) {

        String url = ConstantUrl.BASE_MARKET + ConstantUrl.REGISTER_PURCHASEINFO;
        Log.d(TAG, "callGetScoreApi: CALLING" + url);

        Uri.Builder builder = Uri.parse(url).buildUpon();
        builder.appendQueryParameter("ntcode", ntcode);
        builder.appendQueryParameter("mobilephone", mobilephone);
        builder.appendQueryParameter("ownermobile", ownerMobile);
        builder.appendQueryParameter("persontypeid", String.valueOf(persontypeid));
        builder.appendQueryParameter("personalitytypeId", String.valueOf(personalitytypeId));
        builder.appendQueryParameter("paymenttypeid", String.valueOf(paymenttypeid));
        builder.appendQueryParameter("channelId", String.valueOf(channelId));
        builder.appendQueryParameter("verifycode", String.valueOf(verifyCode));


        String finalUrl = builder.build().toString();
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("purchaseitemtype", purchase.getItemType());
            jsonObject.put("purchasepackagename", purchase.getPackageName());
            jsonObject.put("purchasesku", purchase.getSku());
            jsonObject.put("purchasetime", purchase.getPurchaseTime());
            jsonObject.put("purchasestate", purchase.getPurchaseState());
            jsonObject.put("developerpayload", purchase.getDeveloperPayload());
            jsonObject.put("purchasetoken", purchase.getToken());
            jsonObject.put("purchaseorderid", purchase.getOrderId());
            jsonObject.put("purchaseoriginaljson", purchase.getOriginalJson());
            jsonObject.put("purchasesignature", purchase.getSignature());
            jsonObject.put("msisdn", mobilephone);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.d(TAG, "callGetScoreApi: finalURL:" + finalUrl);
        Log.d(TAG, "callGetScoreApi: finalURL:" + jsonObject.toString());
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST, finalUrl, jsonObject,
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
                return;
            }
            if (error instanceof TimeoutError) {
                volleyCallback.onTimeOutError();
                return;
            }


            if ((error instanceof ServerError)) {


                if (retry <= 3) {
                    Log.e("rety:", retry + "");
                    callGetScoreApi(mobilephone, ntcode, persontypeid, personalitytypeId, paymenttypeid, channelId, token, verifyCode, ownerMobile, purchase, volleyCallback);

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

            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Authorization", token);
                return params;
            }
        };


        jsonObjReq.setRetryPolicy(new DefaultRetryPolicy(AppConstants.CLIENT_TIMEOUT_CREDIT, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        String tag_json_arry = "getScoreApi";
        appController.addToRequestQueue(jsonObjReq, tag_json_arry);


    }


}
