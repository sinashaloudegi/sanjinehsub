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
import ir.iconish.sanjinehsub.data.model.RegisterPurchaseInfoResultDto;
import ir.iconish.sanjinehsub.util.AppConstants;
import ir.iconish.sanjinehsub.util.Purchase;

public class GetScoreApi {


  AppController appController;


  @Inject
  public GetScoreApi(AppController appController) {
    this.appController = appController;
  }


  public RegisterPurchaseInfoResultDto parseJson(JSONObject jsonObject) {
    RegisterPurchaseInfoResultDto registerPurchaseInfoResult = new RegisterPurchaseInfoResultDto();
    RegisterPurchaseInfoResultDto.MarketResultDto marketResultDto = new RegisterPurchaseInfoResultDto.MarketResultDto();


    try {
      registerPurchaseInfoResult.setReqToken(jsonObject.getString("reqToken"));
      JSONObject marketResultDtoJsonObject = jsonObject.getJSONObject("marketResultDto");
      marketResultDto.setMarketResultEnumId(marketResultDtoJsonObject.getInt("marketResultEnumId"));
      marketResultDto.setMarketResultEnumName(marketResultDtoJsonObject.getString("marketResultEnumName"));
      registerPurchaseInfoResult.setMarketResultDto(marketResultDto);


    } catch (JSONException e) {
      Log.e("err", e.toString());
      e.printStackTrace();
    }

    return registerPurchaseInfoResult;
  }


  public void callGetScoreApi(String mobilephone,String ntcode,int persontypeid, int personalitytypeId,int paymenttypeid,int channelId,String token,int verifyCode, String ownerMobile ,Purchase purchase ,final VolleyCallback volleyCallback) {

   // http://192.168.110.54:8085/cafebazaar/registerpurchaseinfo?ntcode=0011049693&mobilephone=09124065593&persontypeid=1&personalitytypeId=1&paymenttypeid=5&channelId=1

    String url = ConstantUrl.BASE_MARKET + ConstantUrl.REGISTER_PURCHASEINFO ;
    Log.e("url=", url);
    Log.e("token=", token);
    Uri.Builder builder = Uri.parse( url).buildUpon();
    builder.appendQueryParameter("ntcode", ntcode);
    builder.appendQueryParameter("mobilephone", mobilephone);
    builder.appendQueryParameter("ownermobile", ownerMobile);
    builder.appendQueryParameter("persontypeid", String.valueOf(persontypeid));
    builder.appendQueryParameter("personalitytypeId", String.valueOf(personalitytypeId));
    builder.appendQueryParameter("paymenttypeid", String.valueOf(paymenttypeid));
    builder.appendQueryParameter("channelId",  String.valueOf(channelId));
    builder.appendQueryParameter("verifycode",  String.valueOf(verifyCode));


    String finalUrl=builder.build().toString();
Log.e("final url=",finalUrl);
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
      jsonObject.put("msisdn",mobilephone);

      Log.e("buy",jsonObject.toString());
    } catch (JSONException e) {
      e.printStackTrace();
    }


    // JsonArrayRequest

    JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST, finalUrl, jsonObject,
      response -> {
        Log.e("Server response", response.toString());
        if (response != null) {
          RegisterPurchaseInfoResultDto registerPurchaseInfoResultDto = parseJson(response);
          volleyCallback.onSuccess(registerPurchaseInfoResultDto);
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


        int statusCode = error.networkResponse.statusCode;

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


    jsonObjReq.setRetryPolicy(new DefaultRetryPolicy(AppConstants.CLIENT_TIMEOUT, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    String tag_json_arry = "getScoreApi";
    appController.addToRequestQueue(jsonObjReq, tag_json_arry);


  }


}
