package ir.iconish.sanjinehsub.util;

import android.app.Activity;
import android.content.Context;
import android.widget.Toast;

import es.dmoral.toasty.Toasty;

public class ToastHelper {
    public static void showSuccessMessage(Activity activity,String message){

        Toasty.success(activity, message, Toast.LENGTH_LONG, true).show();

    }
      public static void showErrorMessage(Activity ctx,String message){

        Toasty.error(ctx, message, Toast.LENGTH_LONG, true).show();

    }

      public static void showErrorMessage(Context ctx,String message){

        Toasty.error(ctx, message, Toast.LENGTH_LONG, true).show();

    }


    public static void showInfoMessage(Activity ctx,String message){

        Toasty.info(ctx, message, Toast.LENGTH_LONG, true).show();

    }

  public static void showWarningMessage(Activity activity, String message){

        Toasty.warning(activity, message, Toast.LENGTH_LONG, true).show();

    }




}
