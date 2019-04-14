package ir.iconish.sanjinehsub.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ir.iconish.sanjinehsub.R;
import ir.iconish.sanjinehsub.config.AppController;
import ir.iconish.sanjinehsub.data.vm.GetScoreViewModel;
import ir.iconish.sanjinehsub.util.ButtonHelper;
import ir.iconish.sanjinehsub.util.Purchase;

public class GetScoreActivity extends AppCompatActivity {

  @BindView(R.id.edtNtcode)
  EditText edtNtcode;
  @BindView(R.id.btnGetScore)
  AppCompatButton btnGetScore;
  @BindView(R.id.prgGetScore)
  ProgressBar prgGetScore;
  @BindView(R.id.imgBack)
  ImageView imgBack;


  @Inject
  GetScoreViewModel getScoreViewModel;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_get_score);
    ButterKnife.bind(this);
    ((AppController) getApplication()).getAppComponent().inject(this);

    attachViewModel();

  }


  @OnClick(R.id.btnGetScore)
  public void btnGetScoreAction() {


    showWating();
    Purchase purchase = null ;
    getScoreViewModel.callGetScoreViewModel(purchase);
    // startActivity(new Intent(this,LoginVerificatonActivity.class));
    //finish();
  }

  @OnClick(R.id.imgBack)
  public void imgBackAction() {
     onBackPressed();
  }



    private void attachViewModel() {
    getScoreViewModel.getApiSuccessLiveDataResponse().observe(this, services -> {
        stopWating();
        //if 1010 go to enter pass -- if 1011 go to otp
        Log.e("success", "in activity");
      }
    );

    getScoreViewModel.getApiAuthFailureErrorLiveData().observe(this, volleyError -> {
    });

    getScoreViewModel.getApiErrorLiveData().observe(this, volleyError -> {
      goToFailApiPage("ApiError");

    });
    getScoreViewModel.getApiServerErrorLiveData().observe(this, volleyError ->

    {
      goToFailApiPage("ServerError");

    });
    getScoreViewModel.getApiTimeOutErrorLiveData().observe(this, volleyError ->
      {
        goToFailApiPage("TimeOutError");
      }

    );
    getScoreViewModel.getApiClientNetworkErrorLiveData().observe(this, volleyError -> {
      goToFailApiPage("ClientNetworkError");


    });


    getScoreViewModel.getApiForbiden403ErrorLiveData().observe(this, volleyError -> {
    });
    getScoreViewModel.getApiValidation422ErrorLiveData().observe(this, volleyError -> {
    });

  }


  private void goToFailApiPage(String failCause) {

    Intent intent = new Intent(this, FailApiActivity.class);
    intent.putExtra("failCause", failCause);
    startActivity(intent);
    finish();

  }


//  @Override
//  public void onBackPressed() {
//  }


  private void showWating() {
    prgGetScore.setVisibility(View.VISIBLE);
    ButtonHelper.toggleAppCompatButtonStatus(btnGetScore, false);
  }

  private void stopWating() {
    prgGetScore.setVisibility(View.INVISIBLE);
    ButtonHelper.toggleAppCompatButtonStatus(btnGetScore, true);
  }


}
