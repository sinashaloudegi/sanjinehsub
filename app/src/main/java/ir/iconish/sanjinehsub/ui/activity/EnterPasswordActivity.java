package ir.iconish.sanjinehsub.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ir.iconish.sanjinehsub.R;

public class EnterPasswordActivity extends AppCompatActivity{

    @BindView(R.id.btnEnter)
    AppCompatButton btnEnter;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_password);
        ButterKnife.bind(this);


    }

    @OnClick(R.id.btnEnter)
    public void navMenuAction() {

        startActivity(new Intent(this,MainActivity.class));
        finish();
}



    @OnClick(R.id.txtForgetPassword)
    public void txtForgetPasswordAction() {

        startActivity(new Intent(this,ForgetPasswordActivity.class));
        finish();
}






}
