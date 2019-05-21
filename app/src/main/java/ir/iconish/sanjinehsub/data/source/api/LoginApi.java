package ir.iconish.sanjinehsub.data.source.api;

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
import ir.iconish.sanjinehsub.data.model.LoginStatusEnum;
import ir.iconish.sanjinehsub.data.model.User;
import ir.iconish.sanjinehsub.util.AppConstants;

public class LoginApi {


    AppController
            appController;


    @Inject
    public LoginApi(AppController appController) {
        this.appController=appController;
    }



    public User parseJson(JSONObject jsonObject){
        User user=new User();

      try {
            JSONObject jsonObjectRoot=jsonObject.getJSONObject("responseStatus");
          int statusCode=  jsonObjectRoot.getInt("value");

          user.setResponseCodeEnum(LoginStatusEnum.fromValue(statusCode));
            if(statusCode==1010){
JSONObject jsonObjectUser=jsonObject.getJSONObject("accountInfo").getJSONObject("user");
                String firstName=jsonObjectUser.getString("firstname");
                String lastName=jsonObjectUser.getString("family");
                String email=jsonObjectUser.getString("email");
                String mobileNumber=jsonObjectUser.getString("mobile");
                long userId=jsonObjectUser.getLong("userid");
user.setFirstName(firstName);
user.setLastName(lastName);
user.setEmail(email);
user.setMobileNumber(mobileNumber);
user.setUserId(userId);


            }

        } catch (JSONException e) {
            //Log.e("err",e.toString());
            e.printStackTrace();
        }

        return user;
    }







    public void callLoginApi(String mobileNumer,final VolleyCallback volleyCallback){


        String   url=ConstantUrl.BASE+ConstantUrl.LOGIN;

JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("appid",AppConstants.APP_ID);
            jsonObject.put("mobile",mobileNumer);
            jsonObject.put("vasSubscribeId",AppConstants.VAS_SUBSCRIB);
        } catch (JSONException e) {
            e.printStackTrace();
        }



        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                url,jsonObject,
                response -> {


                    //Log.e("Server response",response.toString());

                    if (response!=null){

                   User user=     parseJson(response);
                   volleyCallback.onSuccess(user);

                    }





                }, error -> {
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





                }

        )

        {


            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> params = new HashMap<String, String>();

                return params;
            }


        };




        jsonObjReq.setRetryPolicy(new DefaultRetryPolicy(
                AppConstants.CLIENT_TIMEOUT,
                0,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        String tag_json_arry = "loginApi";
        appController.addToRequestQueue(jsonObjReq, tag_json_arry);






    }


}
