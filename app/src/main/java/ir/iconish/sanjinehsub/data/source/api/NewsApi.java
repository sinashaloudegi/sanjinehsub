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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import ir.iconish.sanjinehsub.config.AppController;
import ir.iconish.sanjinehsub.data.model.NewsItem;
import ir.iconish.sanjinehsub.util.AppConstants;

/**
 * @author s.shaloudegi
 * @date 8/24/2019
 */
public class NewsApi {


    AppController
            appController;

    private static final String TAG = "NewsApi";

    @Inject
    public NewsApi(AppController appController) {
        this.appController = appController;
    }


    public void callNewsApi(int arttypeid, String token, @NonNull final VolleyCallback volleyCallback) {


        String url = ConstantUrl.BASE_CONTENT + ConstantUrl.CONTENT;

        Log.d(TAG, "callNewsApi: url: " + url);
        JSONObject jsonObject = new JSONObject();


        try {
            jsonObject.put("appid", AppConstants.APP_ID);
            jsonObject.put("arttypeid", arttypeid + "");
            jsonObject.put("pageNumber", 0);
            jsonObject.put("pageSize", 3);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                url, jsonObject,
                response -> {
                    Log.d(TAG, "responseLen: " + response.length());

                    if (response != null) {

                        List<NewsItem> newsItems = null;
                        try {
                            newsItems = parseJson(response);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        volleyCallback.onSuccess(newsItems);

                    }


                }, error -> {
            Log.d(TAG, "callNewsApi: ERROR" + error.networkResponse.statusCode + "");
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
                Map<String, String> params = new HashMap<>();
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
        String tag_json_arry = "NewsApi";
        appController.addToRequestQueue(jsonObjReq, tag_json_arry);


    }

    @NonNull
    public List<NewsItem> parseJson(@NonNull JSONObject jsonObject) throws JSONException {
        JSONArray jsonArray = jsonObject.getJSONArray("lstArticle");


        Log.d(TAG, "parseJson: " + jsonArray.length());
        //    int jsonLength = jsonArray.length();
        List<NewsItem> newsItems = new ArrayList<>(3);

        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                //JSONObject jsonObject = jsonArray.getJSONObject(i);
                String articleId = jsonArray.getJSONObject(i).getString("articleid");
                String articleTitle = jsonArray.getJSONObject(i).getString("articletitle");
                String articlecontent = jsonArray.getJSONObject(i).getString("articlecontent");
                String fileUrl = jsonArray.getJSONObject(i).getJSONArray("medias").getJSONObject(0).getJSONObject("fileDocument").getString("fileurl");


                //String fileurl = jsonObject.getString("fileurl");
                NewsItem newsItem = new NewsItem();
                newsItem.setTitle(articleTitle);
                newsItem.setImgUrl(fileUrl);
                newsItem.setDecribtion(articlecontent);
                newsItem.setId(Integer.valueOf(articleId));
                newsItems.add(i, newsItem);
            } catch (JSONException e) {
                //Log.e("error",e.toString());
                e.printStackTrace();
            }
        }
        return newsItems;
    }
}
