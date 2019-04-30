package ir.iconish.sanjinehsub.ui;

import android.app.Activity;
import android.content.Intent;

import java.util.Iterator;
import java.util.Map;

public class ActivityNavigationHelper {

    public static  void navigateToWebView(String url, Activity activity ,Class cls){

        Intent intent=new Intent(activity,cls);
        intent.putExtra("url",url);
        activity.startActivity(intent);
    }





     public static  void navigateToActivity( Activity activity ,Class cls,boolean finishCurrentActiviry){

        Intent intent=new Intent(activity,cls);

        activity.startActivity(intent);

        if (finishCurrentActiviry){
            activity.finish();
        }
    }




     public static  void navigateToActivityWithData(Activity activity , Class cls, boolean finishCurrentActiviry, String key,String value){

        Intent intent=new Intent(activity,cls);

intent.putExtra(key,value);


        activity.startActivity(intent);

        if (finishCurrentActiviry){
            activity.finish();
        }
    }








}
