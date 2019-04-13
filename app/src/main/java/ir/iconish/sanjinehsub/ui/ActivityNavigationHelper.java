package ir.iconish.sanjinehsub.ui;

import android.app.Activity;
import android.content.Intent;

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









}
