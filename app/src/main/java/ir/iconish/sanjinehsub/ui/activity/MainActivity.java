package ir.iconish.sanjinehsub.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ir.iconish.sanjinehsub.R;
import ir.iconish.sanjinehsub.adapter.NavigationAdapter;
import ir.iconish.sanjinehsub.adapter.listener.RecyclerIemListener;
import ir.iconish.sanjinehsub.bazaar.CheckCafeBazaarLogin;
import ir.iconish.sanjinehsub.data.model.NavigationItem;

public class MainActivity extends AppCompatActivity  implements NavigationView.OnNavigationItemSelectedListener , RecyclerIemListener {

    @BindView(R.id.drawerLayout)
     DrawerLayout drawerLayout;

    @BindView(R.id.navView)
    NavigationView navigationView;


    @BindView(R.id.recNavigation)
    RecyclerView recyclerNavigation;


    @BindView(R.id.imgNavMenu)
    ImageView imgNavMenu;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        navigationView.setNavigationItemSelectedListener(this);
        initNavigation();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onBackPressed() {

        if (drawerLayout.isDrawerOpen(GravityCompat.END)) {
            drawerLayout.closeDrawer(GravityCompat.END);
        } else {
            super.onBackPressed();
        }
    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        menuItem.setChecked(true);
        // close drawer when item is tapped
        drawerLayout.closeDrawers();
Class cls=null;
        Fragment fragment = null;

        int id = menuItem.getItemId();

        if (id == R.id.nav_menu1) {
            // Handle the camera action
            Toast.makeText(this, "men1", Toast.LENGTH_SHORT).show();

        } else if (id == R.id.nav_menu2) {
            Toast.makeText(this, "men2", Toast.LENGTH_SHORT).show();


        } else if (id == R.id.nav_menu3) {
            Toast.makeText(this, "men3", Toast.LENGTH_SHORT).show();

        }
      //  navigateToActivity(cls);
        //  drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    private void navigateToActivity(Class cls){
        startActivity(new Intent(this,cls));
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
      n1.setDrawbleId(R.drawable.unknown_profile);
      n1.setId(1);
      navigationItems.add(0,n1);


      NavigationItem n2=new NavigationItem();
      n2.setTitle(getString(R.string.nav_profile));
      n2.setDrawbleId(R.drawable.unknown_profile);
        n2.setId(2);
      navigationItems.add(1,n2);






      NavigationItem n3=new NavigationItem();
      n3.setTitle(getString(R.string.nav_archive));
      n3.setDrawbleId(R.drawable.unknown_profile);
        n3.setId(3);
      navigationItems.add(2,n3);



      NavigationItem n4=new NavigationItem();
      n4.setTitle(getString(R.string.nav_about));
      n4.setDrawbleId(R.drawable.unknown_profile);
        n4.setId(4);
      navigationItems.add(3,n4);


      NavigationItem n5=new NavigationItem();
      n5.setTitle(getString(R.string.nav_report));
      n5.setDrawbleId(R.drawable.unknown_profile);
        n5.setId(5);
      navigationItems.add(4,n5);


      NavigationItem n6=new NavigationItem();
      n6.setTitle(getString(R.string.nav_rules));
      n6.setDrawbleId(R.drawable.unknown_profile);
        n6.setId(6);
      navigationItems.add(5,n6);




      NavigationItem n7=new NavigationItem();
      n7.setTitle(getString(R.string.nav_exit_account));
      n7.setDrawbleId(R.drawable.unknown_profile);
        n7.setId(7);
      navigationItems.add(6,n7);





      NavigationItem n8=new NavigationItem();
      n8.setTitle(getString(R.string.nav_exit_app));
      n8.setDrawbleId(R.drawable.unknown_profile);
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

        Toast.makeText(this, "You selected "+navigationItem.getId(), Toast.LENGTH_SHORT).show();
switch (navigationItem.getId()){

    case 1:
        break;



    case 2:
        break;



    case 3:
        break;



    case 4:
        break;



    case 5:
        break;



    case 6:
        break;



    case 7:
        break;



    case 8:
        break;



    case 9:
        break;



}
    }
}
