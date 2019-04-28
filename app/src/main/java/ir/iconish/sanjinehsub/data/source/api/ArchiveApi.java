package ir.iconish.sanjinehsub.data.source.api;

import android.util.Log;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.toolbox.JsonArrayRequest;
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
import ir.iconish.sanjinehsub.data.model.Archive;
import ir.iconish.sanjinehsub.data.model.PasswordValidationResponse;
import ir.iconish.sanjinehsub.util.AppConstants;

public class ArchiveApi {


    AppController
            appController;


    @Inject
    public ArchiveApi(AppController appController) {
        this.appController=appController;
    }



    public List<Archive> parseJson(JSONArray jsonArray){
int jsonLength=jsonArray.length();
List<Archive> archiveList=new ArrayList<>(jsonLength);
for (int i=0;i<jsonLength;i++){
    try {
        JSONObject jsonObject=jsonArray.getJSONObject(i);
        String date=jsonObject.getString("reportDate");
        String token=jsonObject.getString("token");
        String downloadURL="http://creditscore.iconish.ir/icredit/getcreditscorepdf/"+token+"/param";
        String viewLink="https://www.sanjineh.ir/report/"+token;
        Archive archive=new Archive();
        archive.setDownloadLink(downloadURL);
        archive.setViewLink(viewLink);
        archive.setReportDate(date);
        archive.setReportToken(token);
        archiveList.add(i,archive);
    } catch (JSONException e) {
        Log.e("error",e.toString());
        e.printStackTrace();
    }
}

        return archiveList;
    }







    public void callArchivedApi(String mobileNumber,final String token,final VolleyCallback volleyCallback){


        String   url=ConstantUrl.BASE_CREDIT+ConstantUrl.ARCHIVE+mobileNumber;
Log.e("urlCheckPassword=",url);
Log.e("token=",token);



        JsonArrayRequest jsonObjReq = new JsonArrayRequest(Request.Method.POST,
                url,null,
                response -> {


                    Log.e("Server response setPassword",response.toString());

                    if (response!=null){

                        List<Archive> archiveList=     parseJson(response);
                   volleyCallback.onSuccess(archiveList);

                       // volleyCallback.onSuccess(visits);
                    }





                }, error -> {
                    Log.e("api error=",error.toString());
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



                    int statusCode=error.networkResponse.statusCode;

                   // String message=new String(error.networkResponse.data);
                 /*   String errorMessage=ApiErrorHelper.parseError(message);
                    if (statusCode==401){
                        volleyCallback.onAuthFailureError401(errorMessage);
                        return;
                    }
                    if (statusCode==403){
                        volleyCallback.onForbiden403(errorMessage);
                        return;
                    }
                    if (statusCode==422){
                        volleyCallback.onValidationError422(errorMessage);
                        return;
                    }


                    volleyCallback.onFail(errorMessage);*/

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
                Log.e("tokenH=",token);
                params.put("appid", AppConstants.APP_ID);
                params.put("Authorization", token);


                return params;
            }


        };




        jsonObjReq.setRetryPolicy(new DefaultRetryPolicy(
                AppConstants.CLIENT_TIMEOUT,
                0,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        String tag_json_arry = "setPasswordApi";
        appController.addToRequestQueue(jsonObjReq, tag_json_arry);






    }


}
