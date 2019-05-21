package ir.iconish.sanjinehsub.data.source.local;


import android.content.SharedPreferences;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class SharedPreferencesManager {

    public static final String APP_SETTINGS = "VISEO_APP_SETTINGS";
    private static final String MOBILE_NUMBER_VALUE = "MOBILE_NUMBER_VALUE";
    private static final String EMAIL_VALUE = "EMAIL_VALUE";
    private static final String FIRST_NAME_VALUE = "FIRSt_NAME_VALUE";
    private static final String LAST_NAME_VALUE = "LAST_NAME_VALUE";
    private static final String RESPONSE_STATUS_CODE_VALUE = "RESPONSE_STATUS_CODE_VALUE";
    private static final String USER_ID_VALUE = "RESPONSE_STATUS_CODE_VALUE";
    private static final String PASSWORD_VALUE = "PASSWORD_VALUE";
    private static final String NATIONAL_CODE = "NAIONAL_CODE";
    private static final String MARKET_KEY = "MARKET_KEY";
    private static final String TIMER_DURATION = "TIMER_DURATION";
    private static final String TOKEN = "TOKEN";




    private SharedPreferences sharedPreferences;

    @Inject
    public SharedPreferencesManager(SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
    }



    public  String getMobileNumberValue() {
        return sharedPreferences.getString(MOBILE_NUMBER_VALUE , null);
    }

    public  void setMobileNumberValue( String newValue) {
        final SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(MOBILE_NUMBER_VALUE , newValue);
        editor.commit();
    }


    public  String getEmailValue() {
        return sharedPreferences.getString(EMAIL_VALUE , null);
    }

    public  void setEmailValue( String newValue) {
        final SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(EMAIL_VALUE , newValue);
        editor.commit();
    }


    public  String getMarketKeyValue() {
        return sharedPreferences.getString(MARKET_KEY , null);
    }




    public  void setMarketKeyValue( String newValue) {
        final SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(MARKET_KEY , newValue);
        editor.commit();
    }




    public  int getTimerDurationValue() {
        return sharedPreferences.getInt(TIMER_DURATION , 200000);
    }




    public  void setTimerDurationValue( int newValue) {
        final SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(TIMER_DURATION , newValue);
        editor.commit();
    }





    public  String getTokenValue() {
        return sharedPreferences.getString(TOKEN , null);
    }




    public  void setTokenValue( String newValue) {
        final SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(TOKEN , newValue);
        editor.commit();
    }













      public  String getFirsNameValue() {
        return sharedPreferences.getString(FIRST_NAME_VALUE , null);
    }




    public  void setFirstNameValue( String newValue) {
        final SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(FIRST_NAME_VALUE , newValue);
        editor.commit();
    }







    public  String getLastNameValue() {
        return sharedPreferences.getString(LAST_NAME_VALUE , null);
    }

    public  void setLastNameValue( String newValue) {
        final SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(LAST_NAME_VALUE , newValue);
        editor.commit();
    }






      public  void setNationalCodeValue( String newValue) {
        final SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(NATIONAL_CODE , newValue);
        editor.commit();
    }

    public  String getNationalCodeValue() {
        return sharedPreferences.getString(NATIONAL_CODE , null);
    }





      public  int getResponseStatusCodeValue() {
        return sharedPreferences.getInt(RESPONSE_STATUS_CODE_VALUE , -1);
    }

    public  void setResponseStatusCodeValue( int newValue) {
        final SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(RESPONSE_STATUS_CODE_VALUE , newValue);
        editor.commit();
    }


  public  long getUserIdValue() {
        return sharedPreferences.getLong(USER_ID_VALUE , -1);
    }

    public  void setUserIdValue( long newValue) {
        final SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putLong(USER_ID_VALUE , newValue);
        editor.commit();
    }



  public  String getPasswordValue() {
        return sharedPreferences.getString(PASSWORD_VALUE,null);
    }

    public  void setPasswordValue( String newValue) {
        final SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(PASSWORD_VALUE , newValue);
        editor.commit();
    }


public void clearAll(){

        String marketKey=getMarketKeyValue();
        int  timmerDuration=getTimerDurationValue();
        sharedPreferences.edit().clear().commit();
        setTimerDurationValue(timmerDuration);
        setMarketKeyValue(marketKey);



}


public void clearPassword(){

    SharedPreferences.Editor editor = sharedPreferences.edit();
    editor.remove(PASSWORD_VALUE);
    editor.apply();

}



}
