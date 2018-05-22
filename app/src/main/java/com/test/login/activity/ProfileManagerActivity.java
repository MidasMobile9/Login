package com.test.login.activity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.test.login.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ProfileManagerActivity extends AppCompatActivity {
    public static final int REQUEST_TAKE_PROFILE_FROM_ALBUM = 302;


    @BindView(R.id.editTextProfileManagerPasswordOriginal)
    EditText editTextProfileManagerPasswordOriginal;
    @BindView(R.id.editTextProfileManagerNewPasswordFirst)
    EditText editTextProfileManagerNewPasswordFirst;
    @BindView(R.id.editTextProfileManagerNewPasswordSecond)
    EditText editTextProfileManagerNewPasswordSecond;

    @BindView(R.id.contentsLinearLayout)
    LinearLayout contentsLinearLayout;

    @BindView(R.id.changeProfileImage)
    ImageView changeProfileImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_manager);

        ButterKnife.bind(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case REQUEST_TAKE_PROFILE_FROM_ALBUM:
                if(resultCode == Activity.RESULT_OK){
                    Uri profileImageUri = data.getData();
                }
                break;
        }
    }

    @OnClick(R.id.textViewProfileManagerComplete)
    public void onCompleteClick(){
        if (!checkOriginalPasswordValidate()){
            Snackbar.make(contentsLinearLayout, "현재 비밀번호가 틀렸습니다. 확인해주세요.", Snackbar.LENGTH_LONG).show();
            return;
        }
        if (!checkNewPasswordValidate()){
            Snackbar.make(contentsLinearLayout, "비밀번호와 비밀번호 확인이 서로 일치하지 않습니다. 확인해주세요.", Snackbar.LENGTH_LONG).show();
            return;
        }

        /**
         * 서버로 닉네임과 비밀번호, 프로필 사진 전송
         */

        Intent resultIntent = getIntent();
        setResult(RESULT_OK, resultIntent);
        finish();
    }

    private boolean checkOriginalPasswordValidate(){
        //
        String originalPassword = editTextProfileManagerPasswordOriginal.getText().toString();

        /**
         * 입력된 '현재 비밀번호' 와 서버로부터 받아온 비밀번호가 같은지 검사
         */

        return true;
    }

    private boolean checkNewPasswordValidate(){
        //새비밀번호 와 새비밀번호확인 이 같은지 검사
        String newPasswordFirst = editTextProfileManagerNewPasswordFirst.getText().toString();
        String newPasswordSecond = editTextProfileManagerNewPasswordSecond.getText().toString();

        if(newPasswordFirst.equals(newPasswordSecond)){
            return true;
        }

        return false;
    }

    @OnClick(R.id.textViewProfilePrivateInfoPolicy)
    public void onPrivateInfoPolicyClick(){
        String privateInfoPolicyURL = "https://blog.naver.com/tyrano_1/221281685956";

        Intent webpageIntent = new Intent(Intent.ACTION_VIEW);
        webpageIntent.setData(Uri.parse(privateInfoPolicyURL));
        startActivity(webpageIntent);
    }

    @OnClick(R.id.imageViewProfileManagerBack)
    public void onProfileManagerBackClick(){
        Intent resultIntent = getIntent();
        setResult(RESULT_CANCELED, resultIntent);
        finish();
    }

    @OnClick(R.id.changeProfileImage)
    public void onChangeProfileImageClick(){
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
        startActivityForResult(intent, REQUEST_TAKE_PROFILE_FROM_ALBUM);
    }
}
