package ir.iconish.sanjinehsub.config;

import android.content.Context;
import android.content.res.Configuration;
import android.text.TextUtils;

import androidx.multidex.MultiDex;
import androidx.multidex.MultiDexApplication;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.google.firebase.FirebaseApp;

import ir.iconish.sanjinehsub.di.component.AppComponent;
import ir.iconish.sanjinehsub.di.component.DaggerAppComponent;
import ir.iconish.sanjinehsub.di.module.AppModule;
import ir.iconish.sanjinehsub.di.module.NetModule;
import ir.iconish.sanjinehsub.util.FontsOverride;


public class AppController extends MultiDexApplication {


    private AppComponent appComponent;

    public AppComponent getAppComponent() {
        return appComponent;
    }

    public RequestQueue getmRequestQueue() {
        return mRequestQueue;
    }

    public static final String TAG = AppController.class.getSimpleName();
    private RequestQueue mRequestQueue;
    private static AppController mInstance;


    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }


    @Override
    public void onCreate() {
        super.onCreate();

        FirebaseApp.initializeApp(this);


        mInstance = this;


        appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .netModule(new NetModule()).build();


        FontsOverride.setDefaultFont(this, "DEFAULT", "font/iranyekanwebregular.ttf");

        FontsOverride.setDefaultFont(this, "MONOSPACE", "font/iranyekanwebregular.ttf");
        FontsOverride.setDefaultFont(this, "SERIF", "font/iranyekanwebregular.ttf");
        FontsOverride.setDefaultFont(this, "SANS_SERIF", "font/iranyekanwebregular.ttf");
    }


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    public static synchronized AppController getInstance() {
        return mInstance;
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }
        return mRequestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req, String tag) {
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        getRequestQueue().add(req);
    }

    public <T> void addToRequestQueue(Request<T> req) {
        req.setTag(TAG);
        getRequestQueue().add(req);
    }

    public void cancelPendingRequests(Object tag) {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(tag);
        }
    }


}