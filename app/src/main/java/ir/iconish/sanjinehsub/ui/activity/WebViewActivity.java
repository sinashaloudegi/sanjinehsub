package ir.iconish.sanjinehsub.ui.activity;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.webkit.WebView;
import android.widget.FrameLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import ir.iconish.sanjinehsub.R;
import ir.iconish.sanjinehsub.ui.WebViewConfiguration;

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
        Log.i("Test web url : " , url);

        webView.loadUrl(url);
        new WebViewConfiguration(this,customViewContainer,webView,url).initWebView();
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