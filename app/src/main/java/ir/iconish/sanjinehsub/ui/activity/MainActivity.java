package ir.iconish.sanjinehsub.ui.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.FirebaseApp;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ir.iconish.sanjinehsub.R;
import ir.iconish.sanjinehsub.adapter.NavigationAdapter;
import ir.iconish.sanjinehsub.adapter.listener.RecyclerIemListener;
import ir.iconish.sanjinehsub.config.AppController;
import ir.iconish.sanjinehsub.data.model.NavigationItem;
import ir.iconish.sanjinehsub.data.vm.LogoutViewModel;
import ir.iconish.sanjinehsub.ui.ActivityNavigationHelper;
import ir.iconish.sanjinehsub.ui.DialogHelper;
import ir.iconish.sanjinehsub.util.AppConstants;

public class MainActivity extends AppCompatActivity implements RecyclerIemListener {

    @Nullable
    @BindView(R.id.drawerLayout)
    DrawerLayout drawerLayout;

    @Nullable
    @BindView(R.id.navView)
    NavigationView navigationView;


    @Nullable
    @BindView(R.id.recNavigation)
    RecyclerView recyclerNavigation;


    @Nullable
    @BindView(R.id.imgNavMenu)
    ImageView imgNavMenu;


    private static final String TAG = "MainActivity";
    @Inject
    LogoutViewModel logoutViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "onCreate: Splash");
        Toast.makeText(this, "New Sanjineh", Toast.LENGTH_SHORT).show();
        FirebaseApp.initializeApp(this);

        FirebaseMessaging.getInstance().subscribeToTopic(AppConstants.CHANNEL_ID_NOTIFICATON).addOnSuccessListener(aVoid -> {
            // Toast.makeText(getApplicationContext(),"Success",Toast.LENGTH_LONG).show();
        });


        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        ButterKnife.bind(this);
        ((AppController) getApplication()).getAppComponent().inject(this);

        initNavigation();

/*
        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "getInstanceId failed", task.getException());
                            return;
                        }

                        // Get new Instance ID token
                        String token = task.getResult().getToken();

                        // Log and toast
                        //  String msg = getString(R.string.msg_token_fmt, token);
                        Log.d(TAG, token);
                        Toast.makeText(MainActivity.this, token, Toast.LENGTH_SHORT).show();
                    }
                });*/

    }


    @Override
    public void onBackPressed() {

        if (drawerLayout.isDrawerOpen(GravityCompat.END)) {
            drawerLayout.closeDrawer(GravityCompat.END);
        } else {
            DialogHelper.sureExit(this);
        }
    }


    private void navigateToActivity(Class cls) {

        startActivity(new Intent(this, cls));
    }

    @OnClick(R.id.rootOtherScore)
    public void rootOtherScoreAction() {
        ActivityNavigationHelper.navigateToActivity(this, GetScoreOthersActivity.class, false);
    }

    @OnClick(R.id.rootMyScore)
    public void rootMyScore() {
        ActivityNavigationHelper.navigateToActivity(this, GetScoreActivity.class, false);
    }


    @OnClick(R.id.rootScoreHelp)
    public void rootScoreHelpAction() {

        ActivityNavigationHelper.navigateToWebView("https://www.sanjineh.ir/about/reporttype?id=40&child=45&from=android_cafebazar", this, WebViewActivity.class);

    }


    @OnClick(R.id.rootImprovement)
    public void rrootImprovementAction() {

        ActivityNavigationHelper.navigateToWebView("https://www.sanjineh.ir/consultation/advice?id=39&child=49&from=android_cafebazar", this, WebViewActivity.class);

    }


    @OnClick(R.id.rootEconomicNews)
    public void rootEconomicNewsAction() {

        ActivityNavigationHelper.navigateToWebView("https://www.sanjineh.ir/news?id=37&from=android_cafebazar", this, WebViewActivity.class);

    }


    @OnClick(R.id.rootBankService)
    public void rootBankServiceAction() {

        ActivityNavigationHelper.navigateToWebView("https://www.sanjineh.ir/bankService?id=87&from=android_cafebazar", this, WebViewActivity.class);

    }


    @OnClick(R.id.rootBurse)
    public void rootBurseAction() {

        ActivityNavigationHelper.navigateToWebView("https://www.sanjineh.ir/bourse?id=88&from=android_cafebazar", this, WebViewActivity.class);

    }


    @OnClick(R.id.imgNavMenu)
    public void navMenuAction() {


        if (drawerLayout.isDrawerOpen(GravityCompat.END)) {
            drawerLayout.closeDrawer(GravityCompat.END);
        } else {
            drawerLayout.openDrawer(GravityCompat.END);

        }

    }

    private void initNavigation() {
        List<NavigationItem> navigationItems = new ArrayList<>();
    /*  NavigationItem n1=new NavigationItem();
      n1.setTitle(getString(R.string.nav_dpwnload_app));
      n1.setDrawbleId(R.drawable.ic_download_nav);
      n1.setId(1);
      navigationItems.add(0,n1);*/


        NavigationItem n2 = new NavigationItem();
        n2.setTitle(getString(R.string.nav_profile));
        n2.setDrawbleId(R.drawable.ic_person_nav);
        n2.setId(2);
        navigationItems.add(0, n2);


        NavigationItem n3 = new NavigationItem();
        n3.setTitle(getString(R.string.nav_archive));
        n3.setDrawbleId(R.drawable.ic_archive_nav);
        n3.setId(3);
        navigationItems.add(1, n3);


        NavigationItem n4 = new NavigationItem();
        n4.setTitle(getString(R.string.nav_about));
        n4.setDrawbleId(R.drawable.ic_info_nav);
        n4.setId(4);
        navigationItems.add(2, n4);

/*        NavigationItem n9 = new NavigationItem();
        n9.setTitle(getString(R.string.nav_profile));
        n9.setDrawbleId(R.drawable.ic_person_nav);
        n9.setId(9);
        navigationItems.add(2, n9);*/

        NavigationItem n5 = new NavigationItem();
        n5.setTitle(getString(R.string.nav_report));
        n5.setDrawbleId(R.drawable.ic_erro_report);
        n5.setId(5);
        navigationItems.add(3, n5);


        NavigationItem n6 = new NavigationItem();
        n6.setTitle(getString(R.string.nav_rules));
        n6.setDrawbleId(R.drawable.ic_rule_nav);
        n6.setId(6);
        navigationItems.add(4, n6);


        NavigationItem n7 = new NavigationItem();
        n7.setTitle(getString(R.string.nav_exit_account));
        n7.setDrawbleId(R.drawable.ic_signout_nav);
        n7.setId(7);
        navigationItems.add(5, n7);


        NavigationItem n8 = new NavigationItem();
        n8.setTitle(getString(R.string.nav_exit_app));
        n8.setDrawbleId(R.drawable.ic_exit_app_nav);
        n8.setId(8);
        navigationItems.add(6, n8);


        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);

        recyclerNavigation.setLayoutManager(layoutManager);

        NavigationAdapter navigationAdapter = new NavigationAdapter(navigationItems, this);
        recyclerNavigation.setAdapter(navigationAdapter);


    }

    @Override
    public void onTap(Object obj) {
        NavigationItem navigationItem = (NavigationItem) obj;

        switch (navigationItem.getId()) {

            case 1:
//downloadLastVersion();

                break;


            case 2:

                //   https://www.sanjineh.ir/profile/%D8%B3%DB%8C%D8%AF%D9%85%D8%AD%D9%85%D8%AF%20%20%D8%B3%DB%8C%D8%AF%D9%85%D8%AD%D9%85%D8%AF%DB%8C
                ActivityNavigationHelper.navigateToWebView("https://www.sanjineh.ir/profile/" + logoutViewModel.getToken() + "?from=android_cafebazar", this, WebViewActivity.class);

                break;


            case 3:
                ActivityNavigationHelper.navigateToActivity(MainActivity.this, ArchiveActivity.class, false);

                break;


            case 4:
                ActivityNavigationHelper.navigateToWebView("https://www.sanjineh.ir/aboutus?from=android_cafebazar", MainActivity.this, WebViewActivity.class);

                break;


            case 5:
                ActivityNavigationHelper.navigateToWebView("https://www.sanjineh.ir/contactus?from=android_cafebazar", MainActivity.this, WebViewActivity.class);

                break;


            case 6:
                //www.sanjineh.ir/terms
                ActivityNavigationHelper.navigateToWebView("https://www.sanjineh.ir/terms?from=android_cafebazar", MainActivity.this, WebViewActivity.class);

                break;


            case 7:

                logoutViewModel.logout();

                ActivityNavigationHelper.navigateToActivity(this, LoginActivity.class, true);

                break;


            case 8:
                finish();
                break;


          /*  case 9:
                Toast.makeText(this, "You Clicked on Wallet", Toast.LENGTH_SHORT).show();
                break;
*/
        }
    }

    private void downloadLastVersion() {
        String url = "http://dl.iconish.ir/app/SanjinehAppSub1.1.2.apk";
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);
    }

}
