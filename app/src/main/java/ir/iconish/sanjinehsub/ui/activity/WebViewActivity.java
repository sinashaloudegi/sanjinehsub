package ir.iconish.sanjinehsub.ui.activity;


import android.annotation.TargetApi;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.Toast;

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
    String bundle = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);

        Toast.makeText(this, "لطفا شکیبا باشید...", Toast.LENGTH_LONG).show();

        Toast.makeText(this, "لطفا شکیبا باشید...", Toast.LENGTH_LONG).show();

        ButterKnife.bind(this);


        url = getIntent().getStringExtra("url");

        Log.d(TAG, "onCreate: " + url);
        Log.d(TAG, "onCreate: " + bundle);


        if (getIntent().hasExtra("bundle")) {
            Log.d(TAG, "onCreate: bundle: " + getIntent().hasExtra("bundle"));
            //ToastHelper.showInfoMessage(this, url);
            Toast.makeText(this, "در حال انتقال به سامانه اعتبار سنجی...", Toast.LENGTH_LONG).show();
            Toast.makeText(this, "در حال انتقال به سامانه اعتبار سنجی...", Toast.LENGTH_LONG).show();
            Toast.makeText(this, "لطفا شکیبا باشید...", Toast.LENGTH_LONG).show();
            webView.getSettings().setJavaScriptEnabled(true); // enable javascript


            webView.setWebViewClient(new WebViewClient() {
                @SuppressWarnings("deprecation")
                @Override
                public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                }

                @TargetApi(android.os.Build.VERSION_CODES.M)
                @Override
                public void onReceivedError(WebView view, WebResourceRequest req, WebResourceError rerr) {
                    // Redirect to deprecated method, so you can use it in all SDK versions
                    onReceivedError(view, rerr.getErrorCode(), rerr.getDescription().toString(), req.getUrl().toString());
                }
            });

            webView.loadUrl(url);


            //  new WebViewConfiguration(this, customViewContainer, webView, url, bundle).initWebView();
        } else {
            webView.loadUrl(url);

            new WebViewConfiguration(this, customViewContainer, webView, url, bundle).initWebView();

        }
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