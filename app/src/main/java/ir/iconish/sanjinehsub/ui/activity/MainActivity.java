package ir.iconish.sanjinehsub.ui.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;

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

public class MainActivity extends AppCompatActivity  implements  RecyclerIemListener {

    @BindView(R.id.drawerLayout)
     DrawerLayout drawerLayout;

    @BindView(R.id.navView)
    NavigationView navigationView;


    @BindView(R.id.recNavigation)
    RecyclerView recyclerNavigation;


    @BindView(R.id.imgNavMenu)
    ImageView imgNavMenu;

@Inject
    LogoutViewModel logoutViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        ButterKnife.bind(this);
        ((AppController) getApplication()).getAppComponent().inject(this);

        initNavigation();
    }


    @Override
    public void onBackPressed() {

        if (drawerLayout.isDrawerOpen(GravityCompat.END)) {
            drawerLayout.closeDrawer(GravityCompat.END);
        } else {
            DialogHelper.sureExit(this);
            //super.onBackPressed();
        }
    }


    private void navigateToActivity(Class cls){

      startActivity(new Intent(this,cls));
    }

    @OnClick(R.id.rootOtherScore)
    public void rootOtherScoreAction() {
      ActivityNavigationHelper.navigateToActivity(this,GetScoreOthersActivity.class,false);
    }
    @OnClick(R.id.rootMyScore)
    public void rootMyScore() {
      ActivityNavigationHelper.navigateToActivity(this,GetScoreActivity.class,false);
    }


    @OnClick(R.id.rootScoreHelp)
    public void rootScoreHelpAction() {

ActivityNavigationHelper.navigateToWebView("https://www.sanjineh.ir/about/reporttype?id=40&child=45",this,WebViewActivity.class);

    }




    @OnClick(R.id.rootImprovement)
    public void rrootImprovementAction() {

ActivityNavigationHelper.navigateToWebView("https://www.sanjineh.ir/consultation/advice?id=39&child=49",this,WebViewActivity.class);

    }




    @OnClick(R.id.rootEconomicNews)
    public void rootEconomicNewsAction() {

ActivityNavigationHelper.navigateToWebView("https://www.sanjineh.ir/news?id=37",this,WebViewActivity.class);

    }




    @OnClick(R.id.rootBankService)
    public void rootBankServiceAction() {

ActivityNavigationHelper.navigateToWebView("https://www.sanjineh.ir/bankService?id=87",this,WebViewActivity.class);

    }




    @OnClick(R.id.rootBurse)
    public void rootBurseAction() {

ActivityNavigationHelper.navigateToWebView("https://www.sanjineh.ir/bourse?id=88",this,WebViewActivity.class);

    }












    @OnClick(R.id.imgNavMenu)
    public void navMenuAction() {


        if(drawerLayout.isDrawerOpen(GravityCompat.END)) {
            drawerLayout.closeDrawer(GravityCompat.END);
        }
     else {
            drawerLayout.openDrawer(GravityCompat.END);

        }

    }

    private void initNavigation(){
        List<NavigationItem> navigationItems=new ArrayList<>();
      NavigationItem n1=new NavigationItem();
      n1.setTitle(getString(R.string.nav_dpwnload_app));
      n1.setDrawbleId(R.drawable.ic_download_nav);
      n1.setId(1);
      navigationItems.add(0,n1);


      NavigationItem n2=new NavigationItem();
      n2.setTitle(getString(R.string.nav_profile));
      n2.setDrawbleId(R.drawable.ic_person_nav);
        n2.setId(2);
      navigationItems.add(1,n2);






      NavigationItem n3=new NavigationItem();
      n3.setTitle(getString(R.string.nav_archive));
      n3.setDrawbleId(R.drawable.ic_archive_nav);
        n3.setId(3);
      navigationItems.add(2,n3);



      NavigationItem n4=new NavigationItem();
      n4.setTitle(getString(R.string.nav_about));
      n4.setDrawbleId(R.drawable.ic_info_nav);
        n4.setId(4);
      navigationItems.add(3,n4);


      NavigationItem n5=new NavigationItem();
      n5.setTitle(getString(R.string.nav_report));
      n5.setDrawbleId(R.drawable.ic_erro_report);
        n5.setId(5);
      navigationItems.add(4,n5);


      NavigationItem n6=new NavigationItem();
      n6.setTitle(getString(R.string.nav_rules));
      n6.setDrawbleId(R.drawable.ic_rule_nav);
        n6.setId(6);
      navigationItems.add(5,n6);




      NavigationItem n7=new NavigationItem();
      n7.setTitle(getString(R.string.nav_exit_account));
      n7.setDrawbleId(R.drawable.ic_signout_nav);
        n7.setId(7);
      navigationItems.add(6,n7);





      NavigationItem n8=new NavigationItem();
      n8.setTitle(getString(R.string.nav_exit_app));
      n8.setDrawbleId(R.drawable.ic_exit_app_nav);
        n8.setId(8);
      navigationItems.add(7,n8);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerNavigation.setLayoutManager(layoutManager);

        NavigationAdapter navigationAdapter=new NavigationAdapter(navigationItems,this);
        recyclerNavigation.setAdapter(navigationAdapter);





    }

    @Override
    public void onTap(Object obj) {
NavigationItem navigationItem= (NavigationItem) obj;

switch (navigationItem.getId()){

    case 1:
downloadLastVersion();

        break;



    case 2:
        break;



    case 3:
        ActivityNavigationHelper.navigateToActivity(MainActivity.this,ArchiveActivity.class,false);

        break;



    case 4:
        ActivityNavigationHelper.navigateToWebView("https://www.sanjineh.ir/aboutus",MainActivity.this,WebViewActivity.class);

        break;


    case 5:
        ActivityNavigationHelper.navigateToWebView("https://www.sanjineh.ir/contactus",MainActivity.this,WebViewActivity.class);

        break;


    case 6:
        https://www.sanjineh.ir/terms
        ActivityNavigationHelper.navigateToWebView("https://www.sanjineh.ir/terms",MainActivity.this,WebViewActivity.class);

        break;



    case 7:

        logoutViewModel.logout();

        ActivityNavigationHelper.navigateToActivity(this,LoginActivity.class,true);

        break;



    case 8:
        finish();
        break;





}
    }

    private void downloadLastVersion(){
        String url = "http://dl.iconish.ir/app/SanjinehAppSub1.1.2.apk";
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);
    }

}
