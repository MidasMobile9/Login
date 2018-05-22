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
import android.widget.Toast;

import com.test.login.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
    @OnClick({R.id.buttonLogin})
    public void onClickLoginButtonLogin(View view){
        String strEmail = editTextEmail.getText().toString();
        String strPassword = editTextPassword.getText().toString();
        if(!emailCheck(strEmail))
            return;
        if(!passwordCheck(strPassword))
            return;
        //여기서 서버로 확인 ㄱㄱ
        Intent intent = new Intent(mContext,MainActivity.class);
        startActivity(intent);
        finish();
    }
    /**
     * 회원가입 버튼 클릭 이벤트
     * */
    @OnClick({R.id.textViewJoin})
    public void onClickLoginButtonJoin(View view){
        Intent intent = new Intent(mContext,JoinActivity.class);
        startActivity(intent);
    }
    /**
     * 이메일 체크
     * */
    private boolean emailCheck(String email){
        if(email.length()==0){
            //이메일 길이가 0일 경우
            Toast.makeText(mContext, getString(R.string.email_length_zero), Toast.LENGTH_SHORT).show();
            editTextEmail.requestFocus();
            return false;
        }
        return true;
    }
    /**
     * 비밀번호 체크
     * */
    private boolean passwordCheck(String password){
        if(password.length()==0){
            //비밀번호 길이가 0일 경우
            Toast.makeText(mContext, getString(R.string.password_length_zero), Toast.LENGTH_SHORT).show();
            editTextPassword.requestFocus();
            return false;
        }
        return true;
    }
}
