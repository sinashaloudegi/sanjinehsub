package ir.iconish.sanjinehsub.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class WebViewConfiguration {
    WebView webView;
    Context context;
    String url;
    private View mCustomView;
    private WebChromeClient.CustomViewCallback customViewCallback;


    private FrameLayout customViewContainer;


    public WebViewConfiguration(Context context,FrameLayout customViewContainer,WebView webView,String url) {
        this.webView=webView;
        this.url=url;
        this.context=context;
        this.customViewContainer=customViewContainer;
    }

    @SuppressLint("JavascriptInterface")
    public void initWebView(){

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

        settings.setDomStorageEnabled(true);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webView.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        webView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
        webView.setScrollbarFadingEnabled(false);


        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.ECLAIR) {
            try {

                Method m1 = WebSettings.class.getMethod("setDomStorageEnabled", new Class[]{Boolean.TYPE});
                m1.invoke(settings, Boolean.TRUE);

                Method m2 = WebSettings.class.getMethod("setDatabaseEnabled", new Class[]{Boolean.TYPE});
                m2.invoke(settings, Boolean.TRUE);

                Method m3 = WebSettings.class.getMethod("setDatabasePath", new Class[]{String.class});
                m3.invoke(settings, "/data/data/" + context.getPackageName() + "/databases/");

                Method m4 = WebSettings.class.getMethod("setAppCacheMaxSize", new Class[]{Long.TYPE});
                m4.invoke(settings, 1024*1024*8);

                Method m5 = WebSettings.class.getMethod("setAppCachePath", new Class[]{String.class});
                m5.invoke(settings, "/data/data/" +context. getPackageName() + "/cache/");

                Method m6 = WebSettings.class.getMethod("setAppCacheEnabled", new Class[]{Boolean.TYPE});
                m6.invoke(settings, Boolean.TRUE);

               // Log.e("TAG", "Enabled HTML5-Features");
            }
            catch (NoSuchMethodException e) {
               // Log.e("TAG", "Reflection fail", e);
            }
            catch (InvocationTargetException e) {
               // Log.e("TAG", "Reflection fail", e);
            }
            catch (IllegalAccessException e) {
                //Log.e("TAG", "Reflection fail", e);
            }
        }


        if (Build.VERSION.SDK_INT >= 19) {
            webView.setWebChromeClient(new MyWebChromeClient());
            webView.setWebViewClient(new MyWebClient());
        } else {
            webView.setWebViewClient(new MyWebClient());
        }








        webView.addJavascriptInterface(this, "Android");

        webView.loadUrl(url);
    }
    public class MyWebClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if (url.endsWith(".pdf")) {
                context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url.trim())));
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
                        context.startActivity(mail);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                    return true;
                } else {
                    mail.putExtra(Intent.EXTRA_EMAIL, new String[]{url});
                    Log.i("Email", "url :  " + url);
                    mail.putExtra(Intent.EXTRA_SUBJECT, "");
                    mail.putExtra(Intent.EXTRA_TEXT, body);
                   context. startActivity(mail);
                    return true;
                }

            }

            if (url.startsWith("tel:")) {
                //Handle telephony Urls
                context.startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse(url)));
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
    public class MyWebChromeClient extends WebChromeClient {

        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            super.onProgressChanged(view, newProgress);
//      if (newProgress < 100 && progress.getVisibility() == ProgressBar.GONE) {
//        progress.setVisibility(ProgressBar.VISIBLE);
//      }
//
//      progress.setProgress(newProgress);
//      if (newProgress == 100) {
//        progress.setVisibility(ProgressBar.GONE);
//      }
            // Your custom code.
        }

        private Bitmap mDefaultVideoPoster;
        private View mVideoProgressView;

        @Override
        public void onShowCustomView(View view, int requestedOrientation, CustomViewCallback callback) {
            onShowCustomView(view, callback);    //To change body of overridden methods use File | Settings | File Templates.
        }

        @Override
        public void onShowCustomView(View view, CustomViewCallback callback) {

            // if a view already exists then immediately terminate the new one
            if (mCustomView != null) {
                callback.onCustomViewHidden();
                return;
            }
            mCustomView = view;
            webView.setVisibility(View.GONE);
            customViewContainer.setVisibility(View.VISIBLE);
            customViewContainer.addView(view);
            customViewCallback = callback;
        }

        @Override
        public View getVideoLoadingProgressView() {

            if (mVideoProgressView == null) {
                LayoutInflater inflater = LayoutInflater.from(context);
                //mVideoProgressView = inflater.inflate(R.layout.video_progress, null);
            }
            return mVideoProgressView;
        }

        @Override
        public void onHideCustomView() {
            super.onHideCustomView();
            if (mCustomView == null)
                return;

            webView.setVisibility(View.VISIBLE);
            customViewContainer.setVisibility(View.GONE);

            // Hide the custom view.
            mCustomView.setVisibility(View.GONE);

            // Remove the custom view from its container.
            customViewContainer.removeView(mCustomView);
            customViewCallback.onCustomViewHidden();

            mCustomView = null;
        }

    }

}
