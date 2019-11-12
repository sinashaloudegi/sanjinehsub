package ir.iconish.sanjinehsub.ui;

import android.app.Activity;
import android.content.Intent;

import androidx.annotation.NonNull;

public class ActivityNavigationHelper {

    public static void navigateToWebView(String url, @NonNull Activity activity, Class cls, String bundle) {

        Intent intent = new Intent(activity, cls);
        intent.putExtra("url", url);
        intent.putExtra("bundle", bundle);
        activity.startActivity(intent);
    }

    public static void navigateToWebView(String url, @NonNull Activity activity, Class cls) {

        Intent intent = new Intent(activity, cls);
        intent.putExtra("url", url);
        activity.startActivity(intent);
    }

    public static void navigateToActivity(@NonNull Activity activity, Class cls, boolean finishCurrentActiviry) {

        Intent intent = new Intent(activity, cls);

        activity.startActivity(intent);

        if (finishCurrentActiviry) {
            activity.finish();
        }
    }


    public static void navigateToActivityWithData(@NonNull Activity activity, Class cls, boolean finishCurrentActiviry, String key, String value) {

        Intent intent = new Intent(activity, cls);

        intent.putExtra(key, value);


        activity.startActivity(intent);

        if (finishCurrentActiviry) {
            activity.finish();
        }
    }


}
