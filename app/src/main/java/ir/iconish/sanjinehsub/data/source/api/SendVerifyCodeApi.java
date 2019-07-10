package ir.iconish.sanjinehsub.data.source.api;

import android.net.Uri;
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

import javax.inject.Inject;

import ir.iconish.sanjinehsub.config.AppController;
import ir.iconish.sanjinehsub.data.model.VerifyCodeOthersResponse;
import ir.iconish.sanjinehsub.util.AppConstants;

public class SendVerifyCodeApi {


    private static final String TAG = "_SCORE";
    AppController appController;


    @Inject
    public SendVerifyCodeApi(AppController appController) {
        this.appController = appController;
    }

    @NonNull
    public VerifyCodeOthersResponse parseJson(@NonNull JSONObject jsonObject) {
        VerifyCodeOthersResponse verifyCodeOthersResponse = new VerifyCodeOthersResponse();
        try {
            verifyCodeOthersResponse.setStatusCode(jsonObject.getInt("reportStateId"));
            verifyCodeOthersResponse.setDescription(jsonObject.getString("reportDescryption"));
            verifyCodeOthersResponse.setNoReportReqToken(jsonObject.getString("reportDescryption"));

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return verifyCodeOthersResponse;
    }

    public void callSendVerifyCodeApi(String cafePaymentType, String token, String ntcode, String ownermobile, String mobile, @NonNull final VolleyCallback volleyCallback) {
        String url = ConstantUrl.BASE_MARKET + ConstantUrl.SEND_VERIFYCODE;
        //https://creditscore.iconi.ir/icredit/sendVerifyCode/{ntcode}/{ownermobile}/{mobile}
        Log.e("url=", url);
        Log.d(TAG, "url is: " + url);

        Uri.Builder builder = Uri.parse(url).buildUpon();
        builder.appendQueryParameter("cafePaymentType", cafePaymentType);
        String finalUrl = builder.build().toString();

        JSONObject body = new JSONObject();
        try {
            Log.d(TAG, "nid:" + ntcode + " otherMobileNumber: " + ownermobile + " ownerMobileNumber: " + mobile);

            body.put("nid", ntcode);
            body.put("otherMobileNumber", ownermobile);
            body.put("ownerMobileNumber", mobile);

    /*        Log.d(TAG, "token: " + "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxOTI0MDYwNDM3MDQ3NTAzIiwiaWNvbkNyZWRpdCI6IjE5MjQwNjA0MzcwNDc1MDMiLCJpYXQiOjE1NjEzNzgwMjR9.i4OfH_KSc--5ScATB6gzI7S5Xk7s_uKC8i__JSq6jL4");

            body.put("reqToken", "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxOTI0MDYwNDM3MDQ3NTAzIiwiaWNvbkNyZWRpdCI6IjE5MjQwNjA0MzcwNDc1MDMiLCJpYXQiOjE1NjEzNzgwMjR9.i4OfH_KSc--5ScATB6gzI7S5Xk7s_uKC8i__JSq6jL4");
*/
        } catch (Exception e) {

        }

        // JsonArrayRequest

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST, finalUrl, body,
                response -> {
                    Log.d(TAG, "callSendVerifyCodeApi: Response: " + response.toString());
                    if (response != null) {
                        VerifyCodeOthersResponse verifyCodeOthersResponse = parseJson(response);
                        volleyCallback.onSuccess(verifyCodeOthersResponse);
                        // volleyCallback.onSuccess(visits);
                    }


                }, error -> {
            Log.d(TAG, "callSendVerifyCodeApi: error= " + error.toString());
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
            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }

          /*  @Override
        public Map<String, String> getHeaders() {
            Map<String, String> params = new HashMap<String, String>();
            params.put("Authorization", token);
            Log.d(TAG, "getHeaders: token:" + token);
            return params;
        }*/
        };


        jsonObjReq.setRetryPolicy(new DefaultRetryPolicy(AppConstants.CLIENT_TIMEOUT, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        String tag_json_arry = "sendVerifySmsApi";
        appController.addToRequestQueue(jsonObjReq, tag_json_arry);


    }


}
