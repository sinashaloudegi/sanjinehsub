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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import ir.iconish.sanjinehsub.config.AppController;
import ir.iconish.sanjinehsub.data.model.Voucher;
import ir.iconish.sanjinehsub.util.AppConstants;

/**
 * @author s.shaloudegi
 * @date 7/31/2019
 */
public class VoucherListApi {
    AppController
            appController;


    @Inject
    public VoucherListApi(AppController appController) {
        this.appController = appController;
    }


    private static final String TAG = "VoucherListApi";

    public void callVoucherListApi(Long userId, @NonNull final VolleyCallback volleyCallback) {


        String url = ConstantUrl.BASE_KHOSHHESABAN + ConstantUrl.KHOSHHESABAN_PROFILE;


        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("userId", userId);


        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                url, jsonObject,
                response -> {

                    if (response != null) {

                        List<Voucher> voucherList = parseJson(response);
                        volleyCallback.onSuccess(voucherList);

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

            }


        }

        ) {


       /*     @NonNull
            @Override
            public Map<String, String> getHeaders() {

                return new HashMap<>();
            }*/


        };


        jsonObjReq.setRetryPolicy(new DefaultRetryPolicy(
                AppConstants.CLIENT_TIMEOUT,
                1,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        String tag_json_arry = "voucherListApi";
        appController.addToRequestQueue(jsonObjReq, tag_json_arry);

    }

    @NonNull
    public List<Voucher> parseJson(@NonNull JSONObject jsonObject) {
        Log.d(TAG, "    parseJson: " + jsonObject.toString());
        JSONArray jsonArray = null;
        try {
            jsonArray = jsonObject.getJSONArray("offer");
        } catch (JSONException e) {

            e.printStackTrace();
            return null;
        }
        int jsonLength = jsonArray.length();
        Log.d(TAG, "parseJson: len= " + jsonLength);
        List<Voucher> voucherList = new ArrayList<>(jsonLength);
        for (int i = 0; i < jsonLength; i++) {

            try {
                Voucher voucher = new Voucher();

                long id = Long.valueOf(jsonArray.getJSONObject(i).getString("id"));
                String name = jsonArray.getJSONObject(i).getString("name");
                String score = jsonArray.getJSONObject(i).getString("score");
                String prizeTypeId = jsonArray.getJSONObject(i).getString("prizeTypeId");
                String price = jsonArray.getJSONObject(i).getString("price");
                String unitId = jsonArray.getJSONObject(i).getString("unitId");
                String discount = jsonArray.getJSONObject(i).getString("discount");
                String imageUrl = jsonArray.getJSONObject(i).getString("imageUrl");
                String description = jsonArray.getJSONObject(i).getString("description");

                //Log.d("eeee", jsonObject.getString("description"));
                voucher.setName(name);

                voucher.setId(id);
                voucher.setDescription(description);

                voucher.setDiscount(discount);
                voucher.setImageUrl(imageUrl);
                voucher.setPrice(price);
                voucher.setPrizeTypeId(prizeTypeId);
                voucher.setUnitId(unitId);
                voucher.setScore(score);
                voucherList.add(i, voucher);

            } catch (JSONException e) {
                Log.d("err", e.toString());
                e.printStackTrace();
            }

        }

        return voucherList;
    }


}
