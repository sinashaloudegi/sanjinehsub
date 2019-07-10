package ir.iconish.sanjinehsub.util;

import android.app.Activity;
import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;

import es.dmoral.toasty.Toasty;

public class ToastHelper {
    public static void showSuccessMessage(@NonNull Activity activity, @NonNull String message) {

        Toasty.success(activity, message, Toast.LENGTH_LONG, true).show();

    }

    public static void showErrorMessage(@NonNull Activity ctx, @NonNull String message) {

        Toasty.error(ctx, message, Toast.LENGTH_LONG, true).show();

    }

    public static void showErrorMessage(@NonNull Context ctx, @NonNull String message) {

        Toasty.error(ctx, message, Toast.LENGTH_LONG, true).show();

    }


    public static void showInfoMessage(@NonNull Activity ctx, @NonNull String message) {

        Toasty.info(ctx, message, Toast.LENGTH_LONG, true).show();

    }

    public static void showWarningMessage(@NonNull Activity activity, @NonNull String message) {

        Toasty.warning(activity, message, Toast.LENGTH_LONG, true).show();

    }




}
