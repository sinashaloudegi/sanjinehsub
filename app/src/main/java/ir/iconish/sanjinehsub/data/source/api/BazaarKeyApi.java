package ir.iconish.sanjinehsub.data.source.api;

import android.util.Log;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.toolbox.StringRequest;

import javax.inject.Inject;

import ir.iconish.sanjinehsub.config.AppController;
import ir.iconish.sanjinehsub.util.AppConstants;

public class BazaarKeyApi {


  AppController appController;


  @Inject
  public BazaarKeyApi(AppController appController) {
    this.appController = appController;
  }



  public void   callBazaarKeyApi( final VolleyCallback volleyCallback) {


    String url = ConstantUrl.BASE + ConstantUrl.POST_BAZAAR_KEY ;
    Log.e("url=", url);
    // JsonArrayRequest

    StringRequest jsonObjReq = new StringRequest(Request.Method.POST, url,
      response -> {
        Log.e("Server response", response);
        if (response != null) {
          volleyCallback.onSuccess(response);
          // volleyCallback.onSuccess(visits);
        }


      }, error -> {
        Log.e("api error=", error.toString());
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

    ) ;


    jsonObjReq.setRetryPolicy(new DefaultRetryPolicy(AppConstants.CLIENT_TIMEOUT, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    String tag_json_arry = "bazaarKeyApi";
    appController.addToRequestQueue(jsonObjReq, tag_json_arry);


  }


}
