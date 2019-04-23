package ir.iconish.sanjinehsub.ui.activity;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.AppCompatButton;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;


import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ir.iconish.sanjinehsub.R;
import ir.iconish.sanjinehsub.util.ButtonHelper;


public class FailApiActivity extends AppCompatActivity {

String failCause;





  @BindView(R.id.fail_icon)
  ImageView imageViewFail;




  @BindView(R.id.text_no_connection)
  TextView textMessage;



  @BindView(R.id.btnRety)
  AppCompatButton btnRetry;



  @BindView(R.id.prg)
  ProgressBar progressBar;






  public String getFailCause() {
    return failCause;
  }

  public void setFailCause(String failCause) {
    this.failCause = failCause;
  }


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_fail_api);
    AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);

    ButterKnife.bind(this);

    failCause=getIntent().getStringExtra("failCause");

showCorrectData();

  }



  private void showCorrectData(){
    if(failCause.equals("ServerError")){


      imageViewFail.setImageDrawable(getResources().getDrawable(R.drawable.fail_api_icon));
      textMessage.setText(getText(R.string.no_server_connection));
    }
    else if(failCause.equals("TimeOutError")){
      imageViewFail.setImageDrawable(getResources().getDrawable(R.drawable.fail_api_icon));
      textMessage.setText(getText(R.string.no_server_connection));
    }
    else if(failCause.equals("ClientNetworkError")){
imageViewFail.setImageDrawable(getResources().getDrawable(R.drawable.no_connection_error_icon));
      textMessage.setText(getText(R.string.no_internet));
    }

 else if(failCause.equals("ApiError")){
imageViewFail.setImageDrawable(getResources().getDrawable(R.drawable.fail_api_icon));
      textMessage.setText(getText(R.string.no_server_connection));
    }


  }
  @OnClick(R.id.btnRety)
  public void showAllConfirm(View view) {
    progressBar.setVisibility(View.VISIBLE);
    ButtonHelper.toggleButtonStatus(btnRetry,false);
checkInternet();

  }

private void checkInternet(){
isInternetAvailable();

}


private void doInternetAvialbaleAction(){



 runOnUiThread(()->{
    progressBar.setVisibility(View.INVISIBLE);
    ButtonHelper.toggleButtonStatus(btnRetry,true);  });

finish();




  }




private void doFailInternetAvialbaleAction(){

 runOnUiThread(()->{

    progressBar.setVisibility(View.INVISIBLE);
    ButtonHelper.toggleButtonStatus(btnRetry,true);  });

}




  public void isInternetAvailable() {



    new Thread(new Runnable() {
      @Override
      public void run() {



        String host = "www.google.com";
        int port = 80;
        Socket socket = new Socket();

        try {
          socket.connect(new InetSocketAddress(host, port), 10000);
          socket.close();



          doInternetAvialbaleAction();
        } catch (Exception e) {
          try {
            socket.close();
          } catch (IOException es) {

          }
          doFailInternetAvialbaleAction();

        }

      }
    }).start();


  }


  @Override
  public void onBackPressed() {
    //DialogHelper.sureExit(this);
  }
}
