package com.visit24.therapist.util;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Build;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.visit24.therapist.R;


public class DialogHelper {

    public static void sureExit(Activity activity) {


        AlertDialog.Builder alert = new AlertDialog.Builder(activity);

        View view = activity.getLayoutInflater().inflate(R.layout.exit_dialoug, null);

        Button btnYes = view.findViewById(R.id.btn_yes);
        Button btnCancel = view.findViewById(R.id.btn_cancel);
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


    public static void turnOnGps(Activity activity) {


        AlertDialog.Builder alert = new AlertDialog.Builder(activity);

        View view = activity.getLayoutInflater().inflate(R.layout.exit_dialoug, null);

        Button btnTurnOn = view.findViewById(R.id.btn_yes);
        TextView txtTop = view.findViewById(R.id.txt_top);

        TextView txtBottom = view.findViewById(R.id.txt);
        txtBottom.setText(activity.getString(R.string.turn_on_gps));

        txtTop.setText(activity.getString(R.string.activision));
        Button btnCancel = view.findViewById(R.id.btn_cancel);
        alert.setView(view);
        final AlertDialog alertDialog = alert.create();
        btnTurnOn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //  activity.finish();
                activity.startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                alertDialog.dismiss();

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


    public static void setlocationAccuracy(Activity activity) {


        AlertDialog.Builder alert = new AlertDialog.Builder(activity);

        View view = activity.getLayoutInflater().inflate(R.layout.exit_dialoug, null);

        Button btnTurnOn = view.findViewById(R.id.btn_yes);
        TextView txtTop = view.findViewById(R.id.txt_top);

        TextView txtBottom = view.findViewById(R.id.txt);
        txtBottom.setText(activity.getString(R.string.set_accurcy));

        txtTop.setText(activity.getString(R.string.set));
        Button btnCancel = view.findViewById(R.id.btn_cancel);
        alert.setView(view);
        final AlertDialog alertDialog = alert.create();
        btnTurnOn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //  activity.finish();
                activity.startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));

                alertDialog.dismiss();

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


    public static void showDialog(String dialogTitleText, String dialogBodyText, String submitButtonTitle, String cancelButtonTitle, Activity activity, Dialoglistener dialoglistener) {


        AlertDialog.Builder alert = new AlertDialog.Builder(activity);

        View view = activity.getLayoutInflater().inflate(R.layout.exit_dialoug, null);

        Button btnSubmit = view.findViewById(R.id.btn_yes);
        TextView txtTop = view.findViewById(R.id.txt_top);

        TextView txtBottom = view.findViewById(R.id.txt);
        txtBottom.setText(dialogBodyText);

        txtTop.setText(dialogTitleText);
        Button btnCancel = view.findViewById(R.id.btn_cancel);


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


        alertDialog.show();


    }


}

