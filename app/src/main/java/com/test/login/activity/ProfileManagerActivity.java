package com.test.login.activity;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.test.login.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ProfileManagerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_manager);

        ButterKnife.bind(this);
    }

    @OnClick(R.id.textViewProfileManagerComplete)
    public void onCompleteClick(){
        /**
         * 서버로 닉네임과 비밀번호, 프로필 사진 전송
         */

        Intent resultIntent = getIntent();
        setResult(RESULT_OK, resultIntent);
        finish();
    }

    @OnClick(R.id.textViewProfilePrivateInfoPolicy)
    public void onPrivateInfoPolicyClick(){
        String privateInfoPolicyURL = "http://blog.naver.com/tyrano_1/221164727191";

        Intent webpageIntent = new Intent(Intent.ACTION_VIEW);
        webpageIntent.setData(Uri.parse(privateInfoPolicyURL));
        startActivity(webpageIntent);
    }
}
