package ir.iconish.sanjinehsub.data.source.local;


import android.content.SharedPreferences;




import java.lang.reflect.Type;
import java.util.ArrayList;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class SharedPreferencesManager {

    public static final String APP_SETTINGS = "VISEO_APP_SETTINGS";
    private static final String MOBILE_NUMBER_VALUE = "MOBILE_NUMBER_VALUE";





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



}
