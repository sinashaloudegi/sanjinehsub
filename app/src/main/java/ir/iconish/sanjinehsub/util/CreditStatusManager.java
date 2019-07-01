package ir.iconish.sanjinehsub.util;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import ir.iconish.sanjinehsub.R;
import ir.iconish.sanjinehsub.data.model.CreditResponseEnum;
import ir.iconish.sanjinehsub.data.model.CreditScorePreProcess;
import ir.iconish.sanjinehsub.ui.ActivityNavigationHelper;
import ir.iconish.sanjinehsub.ui.DialogHelper;
import ir.iconish.sanjinehsub.ui.Dialoglistener;
import ir.iconish.sanjinehsub.ui.activity.ReportActivity;
import ir.iconish.sanjinehsub.ui.activity.WebViewActivity;

public class CreditStatusManager implements Dialoglistener {
    Activity activity;

    private static final String TAG = "_SCORE";

    public CreditStatusManager(Activity activity) {
        this.activity = activity;
    }

    public void handleReportStatus(CreditScorePreProcess creditScorePreProcess, TextView textView) {

        //int status=creditScorePreProcess.getStatus();

        //   if(status==200) {
        if (creditScorePreProcess.getReportStateId() == CreditResponseEnum.SUCCESS.getId() || creditScorePreProcess.getReportStateId() == CreditResponseEnum.NO_REPORT.getId()) {

            getScoreAtion(creditScorePreProcess.getReqToken(), activity);
            // goToNativeReportActivity(creditScorePreProcess.getReqToken());
        } else {
            showErrorCase("", creditScorePreProcess.getReportDescryption(), textView);
        }

       /* else {

showErrorCase(activity.getString(R.string.error)+" "+status,activity.getString(R.string.contact_support),textView);

        }*/
    }

    private void getScoreAtion(String reqToken, Activity activity) {
        String url = "https://www.sanjineh.ir/report/" + reqToken + "?from=android_cafebazar";
        Log.d(TAG, "getScoreAtion:URL: ");
        ActivityNavigationHelper.navigateToWebView(url, activity, WebViewActivity.class);
        activity.finish();
    }

    @Override
    public void onDialogSubmitEvent(Object object) {

    }

    @Override
    public void onDialogCancelEvent(Object object) {

    }

    private void showErrorCase(String dialogTitle, String dialogBody, TextView textView) {

        DialogHelper.showDialog(dialogTitle, dialogBody, activity.getString(R.string.submit), null, activity, this);
        textView.setText(dialogBody);
        textView.setVisibility(View.VISIBLE);
    }

    private void goToNativeReportActivity(String reqToken) {
        Intent intent = new Intent(activity, ReportActivity.class);
        intent.putExtra("reqToken", reqToken);
        activity.startActivity(intent);
        activity.finish();


    }

}

