package com.test.login.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.test.login.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity {

    private String mTAG = "LoginActivity";
    private Context mContext = LoginActivity.this;

    @BindView(R.id.editTextEmail)
    EditText editTextEmail;
    @BindView(R.id.editTextPassword)
    EditText editTextPassword;
    @BindView(R.id.checkBoxAutoLogin)
    CheckBox checkBoxAutoLogin;
    @BindView(R.id.buttonLogin)
    Button buttonLogin;
    @BindView(R.id.textViewJoin)
    TextView textViewJoin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
    }

    /**
     * 로그인 버튼 클릭 이벤트
     * */
    @OnClick({R.id.textViewJoin})
    public void onClickLoginButtonLogin(View view){
        String email = editTextEmail.getText().toString().trim();
        String pw = editTextPassword.getText().toString().trim();

        Intent intent = new Intent(mContext,MainActivity.class);
        startActivity(intent);
    }
    /**
     * 회원가입 버튼 클릭 이벤트
     * */
    @OnClick({R.id.textViewJoin})
    public void onClickLoginButtonJoin(View view){
        Intent intent = new Intent(mContext,JoinActivity.class);
        startActivity(intent);
    }
}
