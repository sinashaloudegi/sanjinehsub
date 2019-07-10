package ir.iconish.sanjinehsub.data.source.api;

import androidx.annotation.NonNull;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import ir.iconish.sanjinehsub.config.AppController;
import ir.iconish.sanjinehsub.data.model.Archive;
import ir.iconish.sanjinehsub.util.AppConstants;

public class ArchiveApi {


    AppController
            appController;


    @Inject
    public ArchiveApi(AppController appController) {
        this.appController = appController;
    }


    @NonNull
    public List<Archive> parseJson(@NonNull JSONArray jsonArray) {
        int jsonLength = jsonArray.length();
        List<Archive> archiveList = new ArrayList<>(jsonLength);
        for (int i = 0; i < jsonLength; i++) {
            try {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String date = jsonObject.getString("reportDate");
                String token = jsonObject.getString("token");
                String downloadURL = "http://creditscore.iconish.ir/icredit/getcreditscorepdf/" + token + "/param";
                String viewLink = "https://www.sanjineh.ir/report/" + token + "?from=android_cafebazar";
                Archive archive = new Archive();
                archive.setDownloadLink(downloadURL);
                archive.setViewLink(viewLink);
                archive.setReportDate(date);
                archive.setReportToken(token);
                archiveList.add(i, archive);
            } catch (JSONException e) {
                //Log.e("error",e.toString());
                e.printStackTrace();
            }
        }

        return archiveList;
    }


    public void callArchivedApi(String mobileNumber, @NonNull final String token, @NonNull final VolleyCallback volleyCallback) {


        String url = ConstantUrl.BASE_CREDIT + ConstantUrl.ARCHIVE + mobileNumber;


        JsonArrayRequest jsonObjReq = new JsonArrayRequest(Request.Method.POST,
                url, null,
                response -> {


                    if (response != null) {

                        List<Archive> archiveList = parseJson(response);
                        volleyCallback.onSuccess(archiveList);

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
            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }

            @NonNull
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> params = new HashMap<String, String>();
                // Log.e("tokenH=",token);
                params.put("appid", AppConstants.APP_ID);
                params.put("Authorization", token);


                return params;
            }


        };


        jsonObjReq.setRetryPolicy(new DefaultRetryPolicy(
                AppConstants.CLIENT_TIMEOUT,
                1,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        String tag_json_arry = "setPasswordApi";
        appController.addToRequestQueue(jsonObjReq, tag_json_arry);


    }


}
