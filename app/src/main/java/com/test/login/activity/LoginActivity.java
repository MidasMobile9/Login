package com.test.login.activity;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.test.login.R;
import com.test.login.application.LoginApplication;
import com.test.login.data.User;
import com.test.login.model.LoginModel;
import com.test.login.util.Encryption;
import com.test.login.util.ProgressBarShow;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity {

    private String mTAG = "LoginActivity";
    private Context mContext = LoginActivity.this;

    @BindView(R.id.linearLayoutLoginActivity)
    LinearLayout linearLayoutLoginActivity;
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
        String strEmail = editTextEmail.getText().toString().trim();
        String strPassword = editTextPassword.getText().toString().trim();
        if(!emailCheck(strEmail))
            return;
        if(!passwordCheck(strPassword))
            return;

        // 로그인 어싱크 태스크
        strPassword = Encryption.getMD5(strPassword);
        new UserLoginTask().execute(strEmail, strPassword);
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
            Snackbar.make(linearLayoutLoginActivity, getString(R.string.email_length_zero), Snackbar.LENGTH_SHORT).show();
            editTextEmail.requestFocus();
            return false;
        }

        String emailRegExp = "[\\w\\~\\-\\.]+@[\\w\\~\\-]+(\\.[\\w\\~\\-]+)+";
        Matcher matcher = Pattern.compile(emailRegExp).matcher(email);
        if(!matcher.matches()){
            //이메일 형식이 아닐 경우
            Snackbar.make(linearLayoutLoginActivity, getString(R.string.email_combination), Snackbar.LENGTH_SHORT).show();
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
            Snackbar.make(linearLayoutLoginActivity, getString(R.string.password_length_zero), Snackbar.LENGTH_SHORT).show();
            editTextPassword.requestFocus();
            return false;
        }
        return true;
    }

    // AsyncTask ====================================================================================
    public class UserLoginTask extends AsyncTask<String, Void, Map<String, Object>> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            ProgressBarShow.getProgressBarShowSingleton(LoginActivity.this).show(linearLayoutLoginActivity);
        }

        @Override
        protected Map<String, Object> doInBackground(String... params) {
            String strEmail = params[0];
            String strPassword = params[1];

            Map<String, Object> map = LoginModel.loginUser(strEmail, strPassword);

            return map;
        }

        @Override
        protected void onPostExecute(Map<String, Object> map) {
            super.onPostExecute(map);
            ProgressBarShow.getProgressBarShowSingleton(LoginActivity.this).remove(linearLayoutLoginActivity);

            if ( map == null ) {
                // 통신실패
                String message = "인터넷 연결이 원활하지 않습니다. 잠시후 다시 시도해주세요.";
                Snackbar.make(linearLayoutLoginActivity, message, Snackbar.LENGTH_SHORT).show();
            } else {
                // 통신성공
                boolean result = false;
                String message = null;
                String cookie = null;
                User user = null;

                if ( map.containsKey("result") ) {
                    result = (boolean)map.get("result");
                }

                if ( map.containsKey("message") ) {
                    message = (String)map.get("message");
                }

                if ( map.containsKey("cookie") ) {
                    cookie = (String)map.get("cookie");
                }

                if ( map.containsKey("data") ) {
                    user = (User)map.get("data");
                }

                if ( result ) {
                    // 로그인 성공
                    LoginApplication.setCookie(cookie);
                    LoginApplication.setUser(user.getNo(), user.getEmail(), user.getNickname(), user.getProfileimg());

                    Intent intent = new Intent(mContext,MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    // 로그인 실패
                    Snackbar.make(linearLayoutLoginActivity, message, Snackbar.LENGTH_SHORT).show();
                }
            }
        }
    }
    // ==============================================================================================
}
