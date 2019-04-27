package ir.iconish.sanjinehsub.ui.activity;

import android.content.BroadcastReceiver;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ir.iconish.sanjinehsub.R;
import ir.iconish.sanjinehsub.config.AppController;
import ir.iconish.sanjinehsub.data.vm.SendVerifyCodeViewModel;
import ir.iconish.sanjinehsub.util.ButtonHelper;

public class GetScoreOthersActivity extends AppCompatActivity {

  @BindView(R.id.edtNtcodeOthers)
  EditText edtNtcodeOthers;
  @BindView(R.id.edtMsisdnOthers)
  EditText edtMsisdnOthers;
  @BindView(R.id.btnGetScoreOthers)
  AppCompatButton btnGetScoreOthers;
  @BindView(R.id.prgGetScoreOthers)
  ProgressBar prgGetScoreOthers;
  @BindView(R.id.imgBack)
  ImageView imgBack;

  @BindView(R.id.txtAlert)
  TextView txtAlert;

  BroadcastReceiver broadcastReceiver;


  @Inject
  SendVerifyCodeViewModel sendVerifyCodeViewModel;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_get_score_others);
    ButterKnife.bind(this);
    ((AppController) getApplication()).getAppComponent().inject(this);

    attachViewModel();
  }


  @OnClick(R.id.btnGetScoreOthers)
  public void btnGetScoreOthersAction() {
    txtAlert.setVisibility(View.INVISIBLE);

    if(!(edtMsisdnOthers.getText().toString().trim().length() > 0) && !(edtNtcodeOthers.getText().toString().trim().length() > 0)){
      txtAlert.setText(getString(R.string.force_enter_phone_ntcode));
      txtAlert.setVisibility(View.VISIBLE);
      return;
    }
    if ((edtMsisdnOthers.getText().toString().trim().length() > 0) && !(edtNtcodeOthers.getText().toString().trim().length() > 0)) {
      txtAlert.setText(getString(R.string.force_enter_phone));
      txtAlert.setVisibility(View.VISIBLE);
      return;
    }
     if ((edtNtcodeOthers.getText().toString().trim().length() > 0) && !(edtMsisdnOthers.getText().toString().trim().length() > 0)) {
       txtAlert.setText(getString(R.string.force_enter_ntcode));
       txtAlert.setVisibility(View.VISIBLE);
      return;
    }
    if ((edtMsisdnOthers.getText().toString().trim().length() > 0) && !edtMsisdnOthers.getText().toString().startsWith("09")) {
      txtAlert.setText(getString(R.string.enter_correct_mobile_phone));
      txtAlert.setVisibility(View.VISIBLE);
      return;

    }
    if ((edtMsisdnOthers.getText().toString().trim().length() > 0) && edtMsisdnOthers.getText().toString().trim().length() != 11) {
      txtAlert.setText(getString(R.string.valid_phone));
      txtAlert.setVisibility(View.VISIBLE);
      return;
    }

      String ntcode = edtNtcodeOthers.getText().toString();
      String ownermobile = edtMsisdnOthers.getText().toString();

      showWating();
      sendVerifyCodeViewModel.callSendVerifyCodeViewModel(ntcode, ownermobile);

    }


  @OnClick(R.id.imgBack)
  public void imgBackAction() {
    onBackPressed();
  }



  private void attachViewModel() {

    sendVerifyCodeViewModel.getApiSuccessLiveDataResponse().observe(this, verifyCodeOthersResponse -> {
      stopWating();
      if (verifyCodeOthersResponse.getStatusCode() == 12) {
        Log.i("Test", "sent otp to others");
        Intent intent = new Intent(this, VerifyCodeOthersActivity.class);
        intent.putExtra("msisdn", edtMsisdnOthers.getText().toString());
        intent.putExtra("ntcode", edtNtcodeOthers.getText().toString());
        startActivity(intent);
        finish();
      }else {
        txtAlert.setText(verifyCodeOthersResponse.getDescription());
        txtAlert.setVisibility(View.VISIBLE);
      }
      }
    );
    sendVerifyCodeViewModel.getApiAuthFailureErrorLiveData().observe(this, volleyError -> {
    });
    sendVerifyCodeViewModel.getApiErrorLiveData().observe(this, volleyError -> {
      goToFailApiPage("ApiError");
    });
    sendVerifyCodeViewModel.getApiServerErrorLiveData().observe(this, volleyError ->
    {
      goToFailApiPage("ServerError");
    });
    sendVerifyCodeViewModel.getApiTimeOutErrorLiveData().observe(this, volleyError ->
      {
        goToFailApiPage("TimeOutError");
      }
    );
    sendVerifyCodeViewModel.getApiClientNetworkErrorLiveData().observe(this, volleyError -> {
      goToFailApiPage("ClientNetworkError");
    });

    sendVerifyCodeViewModel.getApiForbiden403ErrorLiveData().observe(this, volleyError -> {
    });
    sendVerifyCodeViewModel.getApiValidation422ErrorLiveData().observe(this, volleyError -> {
    });


  }


  private void goToFailApiPage(String failCause) {

    Intent intent = new Intent(this, FailApiActivity.class);
    intent.putExtra("failCause", failCause);
    startActivity(intent);
    finish();

  }


  private void showWating() {
    prgGetScoreOthers.setVisibility(View.VISIBLE);
    ButtonHelper.toggleAppCompatButtonStatus(btnGetScoreOthers, false);
  }

  private void stopWating() {
    prgGetScoreOthers.setVisibility(View.INVISIBLE);
    ButtonHelper.toggleAppCompatButtonStatus(btnGetScoreOthers, true);
  }




}
