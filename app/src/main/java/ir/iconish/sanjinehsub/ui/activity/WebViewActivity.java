package ir.iconish.sanjinehsub.ui.activity;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.design.widget.NavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebView;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ir.iconish.sanjinehsub.R;
import ir.iconish.sanjinehsub.util.ToastHelper;

public class WebViewActivity extends AppCompatActivity {


    @BindView(R.id.webView)
    WebView webView;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);


        ButterKnife.bind(this);
initWebView();
    }

private void initWebView(){

    webView.setBackgroundColor(Color.TRANSPARENT);
//        webView.setBackgroundResource(R.drawable.splash);
   // if (isNetworkConnected()) {
        webView.clearCache(false);
    //}
    webView.clearHistory();
    webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
    webView.getSettings().setJavaScriptEnabled(true);
    webView.getSettings().setLoadsImagesAutomatically(true);
    webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
    webView.getSettings().setAppCacheEnabled(false);
    webView.getSettings().setLoadWithOverviewMode(true);
    webView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
    webView.setScrollbarFadingEnabled(false);
    webView.getSettings().setAllowFileAccess(true);
    webView.getSettings().setSupportMultipleWindows(false);


    webView.getSettings().setJavaScriptEnabled(true);



    String url=getIntent().getStringExtra("url");
    Log.e("url",url);
    webView.loadUrl(url);
}

}