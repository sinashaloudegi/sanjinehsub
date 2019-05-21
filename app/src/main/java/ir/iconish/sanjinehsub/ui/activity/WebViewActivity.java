package ir.iconish.sanjinehsub.ui.activity;


import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import ir.iconish.sanjinehsub.R;
import ir.iconish.sanjinehsub.ui.WebViewConfiguration;
import ir.iconish.sanjinehsub.util.ToastHelper;

public class WebViewActivity extends AppCompatActivity {




    @BindView(R.id.webView)
    WebView webView;




    @BindView(R.id.customViewContainer)
    FrameLayout customViewContainer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);


        ButterKnife.bind(this);


        String url = getIntent().getStringExtra("url");
        webView.loadUrl(url);
      ToastHelper.showInfoMessage(this,url);
        new WebViewConfiguration(this,customViewContainer,webView,url).initWebView();
    }

    @Override
    public void onBackPressed() {
        if(webView.canGoBack()){

            webView.goBack();
        }
        else {
            finish();
        }
    }
}