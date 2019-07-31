package ir.iconish.sanjinehsub.ui.activity;


import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

import butterknife.ButterKnife;
import butterknife.OnClick;
import ir.iconish.sanjinehsub.R;
import ir.iconish.sanjinehsub.util.ToastHelper;

public class NoInternetActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activty_no_internet);
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

        StrictMode.setThreadPolicy(policy);
        ButterKnife.bind(this);

    }

    @OnClick(R.id.retryOnline)
    public void retryOnline() {
        retryAccessNetwork();
    }


    @OnClick(R.id.icnConnection)
    public void checkConnection() {


        retryAccessNetwork();


    }


    private void retryAccessNetwork() {
        if (isInternetAvailable()) {


            startActivity(new Intent(this, SplashActivity.class));
            finish();
        } else {
            showErrorAlert("InternetNotAvailable");
        }
    }

    public boolean isInternetAvailable() {


        String host = "www.google.com";
        int port = 80;
        Socket socket = new Socket();

        try {
            socket.connect(new InetSocketAddress(host, port), 10000);
            socket.close();
            return true;
        } catch (IOException e) {
            try {
                socket.close();
            } catch (IOException es) {
            }
            return false;
        }
    }

    public void showErrorAlert(String error) {

        ToastHelper.showErrorMessage(NoInternetActivity.this, getString(R.string.no_internet));
    }
}