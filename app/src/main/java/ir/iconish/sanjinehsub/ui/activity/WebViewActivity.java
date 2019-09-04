package ir.iconish.sanjinehsub.ui.activity;


import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;
import android.widget.FrameLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import ir.iconish.sanjinehsub.R;
import ir.iconish.sanjinehsub.ui.DialogHelper;
import ir.iconish.sanjinehsub.ui.WebViewConfiguration;

public class WebViewActivity extends AppCompatActivity {
    private static final String TAG = "WebViewActivity";

    @Nullable
    @BindView(R.id.webView)
    WebView webView;


    @Nullable
    @BindView(R.id.customViewContainer)
    FrameLayout customViewContainer;
    String url = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);


        ButterKnife.bind(this);


        url = getIntent().getStringExtra("url");
        Log.d(TAG, "onCreate: " + url);
        webView.loadUrl(url);
        //ToastHelper.showInfoMessage(this, url);
        new WebViewConfiguration(this, customViewContainer, webView, url).initWebView();
    }

    @Override
    public void onBackPressed() {
        if (webView.canGoBack()) {

            webView.goBack();
        } else {
            Log.d(TAG, "onBackPressed: urlweb" + url);
            if (url.contains("/report/")) {
                DialogHelper.sureBack(WebViewActivity.this);
            } else {
                finish();
            }

        }
    }
}