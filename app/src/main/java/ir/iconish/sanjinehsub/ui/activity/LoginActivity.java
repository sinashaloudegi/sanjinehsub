package ir.iconish.sanjinehsub.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.widget.ImageView;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ir.iconish.sanjinehsub.R;

public class LoginActivity extends AppCompatActivity{

    @BindView(R.id.btnCheckPhone)
    AppCompatButton btnCheckPhone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);


    }

    @OnClick(R.id.btnCheckPhone)
    public void btnCheckPhoneAction() {

        startActivity(new Intent(this,LoginVerificatonActivity.class));
        finish();
}

}
