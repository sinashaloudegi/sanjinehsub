package ir.iconish.sanjinehsub.ui.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ImageView;
import android.widget.ProgressBar;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ir.iconish.sanjinehsub.R;
import ir.iconish.sanjinehsub.adapter.ArchiveAdapter;
import ir.iconish.sanjinehsub.adapter.listener.ArchiveRecyclerListener;
import ir.iconish.sanjinehsub.bazaar.UpdateCheck;
import ir.iconish.sanjinehsub.config.AppController;
import ir.iconish.sanjinehsub.data.model.Archive;
import ir.iconish.sanjinehsub.data.repository.ArchiveRepository;
import ir.iconish.sanjinehsub.data.vm.AppConfigViewModel;
import ir.iconish.sanjinehsub.data.vm.ArchiveViewModel;
import ir.iconish.sanjinehsub.ui.ActivityNavigationHelper;
import ir.iconish.sanjinehsub.ui.DialogHelper;
import ir.iconish.sanjinehsub.ui.Dialoglistener;
import ir.iconish.sanjinehsub.util.AnimationHelper;
import ir.iconish.sanjinehsub.util.DownloadHelper;
import ir.iconish.sanjinehsub.util.InternetAccess;

import static ir.iconish.sanjinehsub.util.AppConstants.PACKAGE_NAME;

public class ArchiveActivity extends AppCompatActivity implements ArchiveRecyclerListener {
@Inject
ArchiveViewModel archiveViewModel;



    @BindView(R.id.swip)
    SwipeRefreshLayout swipeRefreshLayout;






    @BindView(R.id.recyclerArchive)
    RecyclerView recyclerView;







    @BindView(R.id.imgBack)
    ImageView imgBack;


















    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_archive);
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        ButterKnife.bind(this);

      ((AppController) getApplication()).getAppComponent().inject(this);
attachViewModel();


        swipeRefreshLayout.setColorSchemeResources(
                R.color.colorPrimary,
                R.color.colorAccent
        );
        LinearLayoutManager   mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);





swipeRefreshLayout.setRefreshing(true);
        archiveViewModel.callArchiveViewModel();
      //  startTimer();




    }




    @Override
    public void onDestroy() {
        super.onDestroy();



    }



private void setDataOnRecycler(List<Archive> archiveList){

    ArchiveAdapter archiveAdapter=new ArchiveAdapter(archiveList,this);
    recyclerView.setAdapter(archiveAdapter);
    archiveAdapter.notifyDataSetChanged();


    swipeRefreshLayout.setEnabled(false);


}



    private void attachViewModel() {
        archiveViewModel.getApiSuccessLiveDataResponse().observe(this, archives -> {
setDataOnRecycler(archives);

            swipeRefreshLayout.setRefreshing(false);
            Log.e("list",archives.toString());
                }
        );

        archiveViewModel.getApiAuthFailureErrorLiveData().observe(this, volleyError -> {});

        archiveViewModel.getApiErrorLiveData().observe(this, volleyError ->{
            goToFailApiPage("ApiError");

        });
        archiveViewModel.getApiServerErrorLiveData().observe(this, volleyError ->

        {
            goToFailApiPage("ServerError");

        });
        archiveViewModel.getApiTimeOutErrorLiveData().observe(this, volleyError ->
                {
                    goToFailApiPage("TimeOutError");
                }

        );
        archiveViewModel.getApiClientNetworkErrorLiveData().observe(this, volleyError -> {
            goToFailApiPage("ClientNetworkError");


        });


        archiveViewModel.getApiForbiden403ErrorLiveData().observe(this, volleyError ->{} );
        archiveViewModel.getApiValidation422ErrorLiveData().observe(this, volleyError ->{} );

    }
    private void goToFailApiPage(String failCause){

        Intent intent=new Intent(this,FailApiActivity.class);
        intent.putExtra("failCause",failCause);
        startActivity(intent);
        finish();

    }

    @Override
    public void onDownloadTap(Archive archive) {
        DownloadHelper.reportDownload(archive.getDownloadLink(),this);
    }

    @Override
    public void onVisitTap(Archive archive) {
ActivityNavigationHelper.navigateToWebView(archive.getViewLink(),this,WebViewActivity.class);
    }


    @OnClick(R.id.imgBack)
    public void imgBackAction() {
        AnimationHelper.fadInFadeout(imgBack);

        finish();

    }

}
