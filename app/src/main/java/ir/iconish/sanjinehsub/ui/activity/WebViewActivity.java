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

@SuppressLint("JavascriptInterface")
private void initWebView(){

    webView.setBackgroundColor(Color.TRANSPARENT);
//        webView.setBackgroundResource(R.drawable.splash);
   // if (isNetworkConnected()) {
        webView.clearCache(false);
    //}
    webView.clearHistory();
    WebSettings settings = webView.getSettings();
    settings.setAllowFileAccess(true);

    settings.setDomStorageEnabled(true);
   settings.setJavaScriptCanOpenWindowsAutomatically(true);
   settings.setLoadsImagesAutomatically(true);
   settings.setCacheMode(WebSettings.LOAD_NO_CACHE);
   settings.setAppCacheEnabled(false);
   settings.setLoadWithOverviewMode(true);
    settings.setJavaScriptEnabled(true);


    webView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
    webView.setScrollbarFadingEnabled(false);


    if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.ECLAIR) {
        try {

            Method m1 = WebSettings.class.getMethod("setDomStorageEnabled", new Class[]{Boolean.TYPE});
            m1.invoke(settings, Boolean.TRUE);

            Method m2 = WebSettings.class.getMethod("setDatabaseEnabled", new Class[]{Boolean.TYPE});
            m2.invoke(settings, Boolean.TRUE);

            Method m3 = WebSettings.class.getMethod("setDatabasePath", new Class[]{String.class});
            m3.invoke(settings, "/data/data/" + getPackageName() + "/databases/");

            Method m4 = WebSettings.class.getMethod("setAppCacheMaxSize", new Class[]{Long.TYPE});
            m4.invoke(settings, 1024*1024*8);

            Method m5 = WebSettings.class.getMethod("setAppCachePath", new Class[]{String.class});
            m5.invoke(settings, "/data/data/" + getPackageName() + "/cache/");

            Method m6 = WebSettings.class.getMethod("setAppCacheEnabled", new Class[]{Boolean.TYPE});
            m6.invoke(settings, Boolean.TRUE);

            Log.e("TAG", "Enabled HTML5-Features");
        }
        catch (NoSuchMethodException e) {
            Log.e("TAG", "Reflection fail", e);
        }
        catch (InvocationTargetException e) {
            Log.e("TAG", "Reflection fail", e);
        }
        catch (IllegalAccessException e) {
            Log.e("TAG", "Reflection fail", e);
        }
    }

    if (Build.VERSION.SDK_INT >= 19) {

        webView.setWebViewClient(new MyWebClient());
    } else {
        webView.setWebViewClient(new MyWebClient());
    }
    webView.addJavascriptInterface(this, "Android");


    String url=getIntent().getStringExtra("url");
    Log.e("url",url);
    webView.loadUrl(url);
}
    public class MyWebClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if (url.endsWith(".pdf")) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url.trim())));
                // if want to download pdf manually create AsyncTask here
                // and download file
                return true;
            }

            if (url.startsWith("mailto:")) {
                url = url.substring(7);
                String body = "";
                Intent mail = new Intent(Intent.ACTION_SEND);
                mail.setType("application/octet-stream");

                String[] paramStrings = url.split("\\?");
                if (paramStrings.length > 1) {
                    Log.i("Email", "paramStrings :  " + paramStrings);
                    Log.i("Email", "paramStrings[0] :  " + paramStrings[0]);
                    String[] parts = paramStrings[1].split("\\&");
                    Log.i("Email", "parts :  " + parts);
                    String[] subjects = parts[0].split("=");
                    Log.i("Email", "subjects :  " + subjects);
                    String subject = subjects[1];
                    Log.i("Email", "subject :  " + subject);
                    String[] bodies = parts[1].split("=");
                    Log.i("Email", "bodies :  " + bodies);
                    String emailBody = bodies[1];
                    Log.i("Email", "emailBody :  " + emailBody);

                    String resultBody = "";
                    try {
                        resultBody = java.net.URLDecoder.decode(emailBody, "UTF-8");
                        Log.i("Email", "resultBody :  " + resultBody);
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }

                    mail.putExtra(Intent.EXTRA_EMAIL, new String[]{paramStrings[0]});
                    mail.putExtra(Intent.EXTRA_SUBJECT, subject);
                    mail.putExtra(Intent.EXTRA_TEXT, resultBody);
                    try {
                        startActivity(mail);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                    return true;
                } else {
                    mail.putExtra(Intent.EXTRA_EMAIL, new String[]{url});
                    Log.i("Email", "url :  " + url);
                    mail.putExtra(Intent.EXTRA_SUBJECT, "");
                    mail.putExtra(Intent.EXTRA_TEXT, body);
                    startActivity(mail);
                    return true;
                }

            }

            if (url.startsWith("tel:")) {
                //Handle telephony Urls
                startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse(url)));
                return true;


            }

            view.loadUrl(url);
            return false;
        }


        @Override
        public void onPageStarted(final WebView view, final String url, final Bitmap favicon) {
//      progress.setVisibility(View.VISIBLE);
            //progress.show();

            super.onPageStarted(view, url, favicon);
        }

        @Override
        public void onPageFinished(WebView view, String url) {


                super.onPageFinished(view, url);

        }
    }

}