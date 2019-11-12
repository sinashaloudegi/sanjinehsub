package ir.iconish.sanjinehsub.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;

import androidx.appcompat.app.AppCompatActivity;

import butterknife.BindView;
import ir.iconish.sanjinehsub.R;

public class ReportWeb extends AppCompatActivity {


    @BindView(R.id.report_webview)
    WebView reportWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_web);


        Intent intent = getIntent();
        String url = intent.getExtras().getString("url");

        reportWebView.loadUrl(url);

    }
}
