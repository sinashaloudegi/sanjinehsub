package ir.iconish.sanjinehsub.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Build;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;

import ir.iconish.sanjinehsub.R;


public class DialogHelper {
    public static void sureBack(@NonNull Activity activity) {


        AlertDialog.Builder alert = new AlertDialog.Builder(activity);

        View view = activity.getLayoutInflater().inflate(R.layout.back_dialog, null);

        Button btnYes = view.findViewById(R.id.btn_yes_report);
        Button btnCancel = view.findViewById(R.id.btn_cancel_report);
        alert.setView(view);
        final AlertDialog alertDialog = alert.create();
        btnYes.setOnClickListener(view1 -> {
            alertDialog.dismiss();


            activity.finish();
            //      activity.finishAffinity();
        });


        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });


        alertDialog.show();


    }

    public static void sureExit(@NonNull Activity activity) {


        AlertDialog.Builder alert = new AlertDialog.Builder(activity);

        View view = activity.getLayoutInflater().inflate(R.layout.exit_dialoug, null);

        Button btnYes = view.findViewById(R.id.btn_yes_report);
        Button btnCancel = view.findViewById(R.id.btn_cancel_report);
        alert.setView(view);
        final AlertDialog alertDialog = alert.create();
        btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();


                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    activity.finishAffinity();
                } else {
                    ActivityCompat.finishAffinity(activity);
                }
            }
        });


        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });


        alertDialog.show();


    }


    public static void showDialog(String dialogTitleText, String dialogBodyText, String submitButtonTitle, @Nullable String cancelButtonTitle, @NonNull Activity activity, @NonNull Dialoglistener dialoglistener) {


        AlertDialog.Builder alert = new AlertDialog.Builder(activity);

        View view = activity.getLayoutInflater().inflate(R.layout.exit_dialoug, null);

        Button btnSubmit = view.findViewById(R.id.btn_yes_report);
        TextView txtTop = view.findViewById(R.id.txt_top);

        TextView txtBottom = view.findViewById(R.id.txt);
        txtBottom.setText(dialogBodyText);

        txtTop.setText(dialogTitleText);
        Button btnCancel = view.findViewById(R.id.btn_cancel_report);


        if (null == cancelButtonTitle) {
            btnCancel.setVisibility(View.GONE);
        }
        btnSubmit.setText(submitButtonTitle);


        btnCancel.setText(cancelButtonTitle);
        alert.setCancelable(false);
        alert.setView(view);
        final AlertDialog alertDialog = alert.create();
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialoglistener.onDialogSubmitEvent(null);
                alertDialog.dismiss();

            }
        });


        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialoglistener.onDialogCancelEvent(null);
                alertDialog.dismiss();
            }
        });

        alertDialog.setCancelable(true);
        alertDialog.show();


    }


}

