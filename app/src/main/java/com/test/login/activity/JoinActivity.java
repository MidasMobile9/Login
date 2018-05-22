package com.test.login.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.ProxyInfo;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.test.login.R;
import com.test.login.util.ImageUtil;
import com.test.login.util.Permission;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;

public class JoinActivity extends AppCompatActivity {

    private String mTAG = "JoinActivity";
    private Context mContext = JoinActivity.this;

    @BindView(R.id.linearLayoutJoinActivity)
    LinearLayout linearLayoutJoinActivity;

    /**회원가입 뷰*/
    @BindView(R.id.linearLayoutEmailPassword)
    LinearLayout linearLayoutEmailPassword;
    @BindView(R.id.editTextInputEmail)
    EditText editTextInputEmail;
    @BindView(R.id.textViewCheckEmailDuplication)
    TextView textViewCheckEmailDuplication;
    @BindView(R.id.editTextInputPasswordFirst)
    EditText editTextInputPasswordFirst;
    @BindView(R.id.editTextInputPasswordSecond)
    EditText editTextInputPasswordSecond;
    @BindView(R.id.textViewDoJoin)
    TextView textViewDoJoin;

    /**프로필 뷰*/
    @BindView(R.id.linearLayoutProfileInfo)
    LinearLayout linearLayoutProfileInfo;
    @BindView(R.id.frameLayoutProfileImage)
    FrameLayout frameLayoutProfileImage;
    @BindView(R.id.circleImageViewProfileImage)
    CircleImageView circleImageViewProfileImage;
    @BindView(R.id.editTextNickname)
    EditText editTextNickname;
    @BindView(R.id.textViewSaveProfile)
    TextView textViewSaveProfile;

    private String strEmail;
    private String strPassWord;
    private String strNickName;

    private Permission mPermission;
    private String[] permissions= {Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA};

    private final int PICK_FROM_ALBUM = 1;
    private Uri mImageCaptureUri = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);
        ButterKnife.bind(this);
        mPermission = new Permission(this, permissions);
        mPermission.setSnackbar(linearLayoutJoinActivity,getString(R.string.permission_snackbar_text),getString(R.string.permission_snackbar_text_rational));
    }


    /**
     * 가입하기-프로필저장 버튼 클릭 이벤트
     * */
    @OnClick({R.id.textViewDoJoin,R.id.textViewSaveProfile})
    public void onClickJoinButtonJoin(View view){
        switch (view.getId()){
            //가입하기
            case R.id.textViewDoJoin:
                strEmail = editTextInputEmail.getText().toString();
                strPassWord = editTextInputPasswordFirst.getText().toString();
                if(!emailCheck(strEmail)){
                    return;
                }
                if(!passwordCheck(strPassWord)){
                    return;
                }
                textViewDoJoin.setVisibility(View.GONE);
                linearLayoutEmailPassword.setVisibility(View.GONE);

                textViewSaveProfile.setVisibility(View.VISIBLE);
                linearLayoutProfileInfo.setVisibility(View.VISIBLE);
                break;
            //프로필저장
            case R.id.textViewSaveProfile:
                strNickName = editTextNickname.getText().toString().trim();
                if(!nicknameCheck(strNickName)){
                    return;
                }
                //여기서 서버로 전송 ㄱㄱ
                finish();
                break;
        }
    }

    /**
     * 이메일 체크
     * */
    private boolean emailCheck(String email){
        if(email.length()==0){
            //이메일 길이가 0일 경우
            Toast.makeText(mContext, getString(R.string.email_length_zero), Toast.LENGTH_SHORT).show();
            editTextInputEmail.requestFocus();
            return false;
        }
        String emailRegExp = "[\\w\\~\\-\\.]+@[\\w\\~\\-]+(\\.[\\w\\~\\-]+)+";
        Matcher matcher = Pattern.compile(emailRegExp).matcher(email);
        if(!matcher.matches()){
            //이메일 형식이 아닐 경우
            Toast.makeText(mContext, getString(R.string.email_combination), Toast.LENGTH_SHORT).show();
            editTextInputEmail.requestFocus();
            return false;
        }
        //서버 중복확인 필요
        return true;
    }
    /**
     * 비밀번호 체크
     * */
    private boolean passwordCheck(String password){
        if(password.length()==0){
            //비밀번호 길이가 0일 경우
            Toast.makeText(mContext, getString(R.string.password_length_zero), Toast.LENGTH_SHORT).show();
            editTextInputPasswordFirst.requestFocus();
            return false;
        }
        String pwPattern = "^(?=.*\\d)(?=.*[~`!@#$%\\^&*()-])(?=.*[a-z]).{9,12}$";
        Matcher matcher = Pattern.compile(pwPattern).matcher(password);
        pwPattern = "(.)\\1\\1\\1";
        Matcher matcher2 = Pattern.compile(pwPattern).matcher(password);
        if(!matcher.matches()){
            //영문 숫자 특수문자 구분
            Toast.makeText(mContext, getString(R.string.password_combination), Toast.LENGTH_SHORT).show();
            return false;
        }
        if(matcher2.find()){
            //같은 문자 4자리 이상
            Toast.makeText(mContext, getString(R.string.password_repeat), Toast.LENGTH_SHORT).show();
            return false;
        }
        if(password.contains(strEmail)){
            //아이디 포함 여부
            Toast.makeText(mContext, getString(R.string.password_contain_id), Toast.LENGTH_SHORT).show();
            return false;
        }
        if(password.contains(" ")){
            //공백문자 사용 불가
            Toast.makeText(mContext, getString(R.string.password_blank), Toast.LENGTH_SHORT).show();
            return false;
        }
        if(!password.equals(editTextInputPasswordSecond.getText().toString())){
            //비밀번호 확인
            Toast.makeText(mContext, getString(R.string.password_not_matched), Toast.LENGTH_SHORT).show();
            editTextInputPasswordSecond.requestFocus();
            return false;
        }
        return true;
    }
    /**
     * 닉네임 체크
     * */
    private boolean nicknameCheck(String nickname){
        if(nickname.length()==0){
            //닉네임 길이가 0일 경우
            Toast.makeText(mContext, getString(R.string.nickname_length_zero), Toast.LENGTH_SHORT).show();
            editTextNickname.requestFocus();
            return false;
        }
        //서버 중복확인 필요
        return true;
    }
    /**
     * 프로필 사진 클릭 이벤트
     * */
    @OnClick({R.id.frameLayoutProfileImage})
    public void onClickJoinCircleImageViewProfileImage(View view){
        if(!mPermission.checkPermissions()){
            Toast.makeText(mContext, getString(R.string.permission_snackbar_text), Toast.LENGTH_SHORT).show();
            return;
        }
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType(android.provider.MediaStore.Images.Media.CONTENT_TYPE);
        startActivityForResult(intent, PICK_FROM_ALBUM);
    }

    /**
     * 앨범에서 이미지를 가져온 후 이미지 세팅.
     * */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_OK) {
            return;
        }
        switch (requestCode) {
            case PICK_FROM_ALBUM: {
                mImageCaptureUri = data.getData();
                Glide.with(mContext)
                        .load(mImageCaptureUri)
                        .into(circleImageViewProfileImage);
                break;
            }
        }
    }

}
