package ir.iconish.sanjinehsub.ui.activity;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.design.widget.NavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.InetSocketAddress;
import java.net.Socket;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ir.iconish.sanjinehsub.R;
import ir.iconish.sanjinehsub.ui.WebViewConfiguration;
import ir.iconish.sanjinehsub.util.ToastHelper;

public class WebViewActivity extends AppCompatActivity {


    @BindView(R.id.webView)
    WebView webView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);


        ButterKnife.bind(this);


        String url = getIntent().getStringExtra("url");

        webView.loadUrl(url);
        new WebViewConfiguration(this,webView,url).initWebView();
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        if(webView.canGoBack()){

            webView.goBack();
        }
        else {
            finish();
        }
    }
}