package ir.iconish.sanjinehsub.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ir.iconish.sanjinehsub.R;
import ir.iconish.sanjinehsub.adapter.ArchiveAdapter;
import ir.iconish.sanjinehsub.adapter.listener.ArchiveRecyclerListener;
import ir.iconish.sanjinehsub.config.AppController;
import ir.iconish.sanjinehsub.data.model.Archive;
import ir.iconish.sanjinehsub.data.vm.ArchiveViewModel;
import ir.iconish.sanjinehsub.ui.ActivityNavigationHelper;
import ir.iconish.sanjinehsub.util.AnimationHelper;
import ir.iconish.sanjinehsub.util.DownloadHelper;

public class ArchiveActivity extends AppCompatActivity implements ArchiveRecyclerListener {
    @Inject
    ArchiveViewModel archiveViewModel;


    @Nullable
    @BindView(R.id.swip)
    SwipeRefreshLayout swipeRefreshLayout;


    @Nullable
    @BindView(R.id.recyclerArchive)
    RecyclerView recyclerView;


    @Nullable
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
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);


        swipeRefreshLayout.setRefreshing(true);
        archiveViewModel.callArchiveViewModel();


    }


    @Override
    public void onDestroy() {
        super.onDestroy();


    }


    private void setDataOnRecycler(List<Archive> archiveList) {

        ArchiveAdapter archiveAdapter = new ArchiveAdapter(archiveList, this);
        recyclerView.setAdapter(archiveAdapter);
        archiveAdapter.notifyDataSetChanged();


        swipeRefreshLayout.setEnabled(false);


    }


    private void attachViewModel() {
        archiveViewModel.getApiSuccessLiveDataResponse().observe(this, archives -> {
            setDataOnRecycler(archives);

            swipeRefreshLayout.setRefreshing(false);
            // Log.e("list",archives.toString());
                }
        );

        archiveViewModel.getApiAuthFailureErrorLiveData().observe(this, volleyError -> {
        });

        archiveViewModel.getApiErrorLiveData().observe(this, volleyError -> {
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


        archiveViewModel.getApiForbiden403ErrorLiveData().observe(this, volleyError -> {
        });
        archiveViewModel.getApiValidation422ErrorLiveData().observe(this, volleyError -> {
        });

    }

    private void goToFailApiPage(String failCause) {

        Intent intent = new Intent(this, FailApiActivity.class);
        intent.putExtra("failCause", failCause);
        startActivity(intent);
        finish();

    }

    @Override
    public void onDownloadTap(@NonNull Archive archive) {
        DownloadHelper.reportDownload(archive.getDownloadLink(), this);
    }

    @Override
    public void onVisitTap(@NonNull Archive archive) {
        ActivityNavigationHelper.navigateToWebView(archive.getViewLink(), this, WebViewActivity.class);
    }


    @OnClick(R.id.imgBack)
    public void imgBackAction() {
        AnimationHelper.fadInFadeout(imgBack);

        finish();

    }

}
