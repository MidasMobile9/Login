package com.test.login.activity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.test.login.R;
import com.test.login.application.LoginApplication;
import com.test.login.model.ProfileManagerModel;
import com.test.login.util.Encryption;
import com.test.login.util.ImageUtil;
import com.test.login.util.PasswordUtil;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ProfileManagerActivity extends AppCompatActivity {
    public static final int REQUEST_TAKE_PROFILE_FROM_ALBUM = 302;
    public static final String PROFILE_URL_HEADER = "http://35.187.156.145:3000/profileimg/";

    private File resultImageFile;
    private boolean isChangeProfileImage = false;


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
    @BindView(R.id.circleImageViewProfileManagerProfileImage)
    ImageView circleImageViewProfileManagerProfileImage;
    @BindView(R.id.editTextProfileManagerNickname)
    EditText editTextProfileManagerNickname;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_manager);

        ButterKnife.bind(this);

        setProfileInit();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_TAKE_PROFILE_FROM_ALBUM:
                if (resultCode == Activity.RESULT_OK) {
                    Uri profileImageUri = data.getData();
                    Bitmap resizeBitmap = ImageUtil.scaleImageDownToBitmap(this, profileImageUri);
                    resultImageFile = ImageUtil.scaleImageDownToFile(this, profileImageUri);
                    isChangeProfileImage = true;
                    circleImageViewProfileManagerProfileImage.setImageBitmap(resizeBitmap);
                }
                break;
        }
    }

    private void setProfileInit() {
        //서버로부터 프로필 이미지와 닉네임을 받아서 표시하는 함수
        editTextProfileManagerNickname.setText(LoginApplication.user.getNickname());

        Glide.with(getApplicationContext()) // Activity 또는 Fragment의 context
                .load(PROFILE_URL_HEADER + LoginApplication.user.getProfileimg()) // drawable에 저장된 이미지
                .into(circleImageViewProfileManagerProfileImage); // 이미지를 보여줄 view
    }

    @OnClick(R.id.textViewProfileManagerComplete)
    public void onCompleteClick() {
        //완료 버튼 클릭

        String originalPassword = editTextProfileManagerPasswordOriginal.getText().toString();

        if (!checkNewPasswordValidate()) {
            Snackbar.make(contentsLinearLayout, "비밀번호와 비밀번호 확인이 서로 일치하지 않습니다. 확인해주세요.", Snackbar.LENGTH_LONG).show();
            return;
        }

        boolean isValidNewPasword = PasswordUtil.passwordCheck(
                getApplicationContext(),
                editTextProfileManagerPasswordOriginal.getText().toString(),
                editTextProfileManagerNewPasswordFirst.getText().toString(),
                LoginApplication.user.getEmail()
        );

        boolean isInputNewPassword;
        if (editTextProfileManagerNewPasswordFirst.getText().toString() != ""){
            isInputNewPassword = true;
        } else {
            isInputNewPassword = false;
        }


        if (isInputNewPassword) {
            if (isValidNewPasword) {
                //새로운 비밀번호를 입력했고 타당한 비밀번호 일때
                updateUserInfo();
                if (isChangeProfileImage) {
                    //그리고 프로필 이미지까지 바꿨을 때
                    updateProfileImage();
                }
            }
        } else {
            if (isChangeProfileImage) {
                //비밀번호는 입력하지 않았지만 프로필 이미지를 바꿨을 때
                updateProfileImage();
            }
        }


        Intent resultIntent = getIntent();
        setResult(RESULT_OK, resultIntent);
        finish();
    }

    private boolean checkNewPasswordValidate() {
        //새비밀번호 와 새비밀번호확인 이 같은지 검사
        String newPasswordFirst = editTextProfileManagerNewPasswordFirst.getText().toString();
        String newPasswordSecond = editTextProfileManagerNewPasswordSecond.getText().toString();

        if (newPasswordFirst.equals(newPasswordSecond)) {
            return true;
        }

        return false;
    }

    @OnClick(R.id.textViewProfilePrivateInfoPolicy)
    public void onPrivateInfoPolicyClick() {
        String privateInfoPolicyURL = "https://blog.naver.com/tyrano_1/221281685956";

        Intent webpageIntent = new Intent(Intent.ACTION_VIEW);
        webpageIntent.setData(Uri.parse(privateInfoPolicyURL));
        startActivity(webpageIntent);
    }

    @OnClick(R.id.imageViewProfileManagerBack)
    public void onProfileManagerBackClick() {
        Intent resultIntent = getIntent();
        setResult(RESULT_CANCELED, resultIntent);
        finish();
    }

    @OnClick(R.id.circleImageViewProfileManagerProfileImage)
    public void onChangeProfileImageClick() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
        startActivityForResult(intent, REQUEST_TAKE_PROFILE_FROM_ALBUM);
    }

    @OnClick(R.id.textViewProfileManagerDelete)
    public void onProfileDeleteClick() {
        final Intent profileDeleteIntent = new Intent(this, LoginActivity.class);
        profileDeleteIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        profileDeleteIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final EditText passwordEditText = new EditText(this);
        passwordEditText.setHint(getString(R.string.password_current));

        builder.setView(passwordEditText)
                .setMessage(getString(R.string.profile_delete_alert_dialog))
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String inputPassword = passwordEditText.getText().toString();
                        boolean isDeleted = deleteUser(inputPassword);

                        if (isDeleted) {
                            //삭제 성공
                            startActivity(profileDeleteIntent);
                            finish();
                        } else {
                            //삭제 실패
                            Toast.makeText(getApplicationContext(), "비밀번호를 확인해주세요.", Toast.LENGTH_LONG).show();
                        }
                    }
                })
                .setNegativeButton("CANCLE", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .show();

    }


    private void updateUserInfo() {
        ProfileManagerModel.updateUserInfo(
                getApplicationContext(),
                LoginApplication.user.getEmail(),
                Encryption.getEncryptedAES(editTextProfileManagerPasswordOriginal.getText().toString()),
                LoginApplication.user.getNickname(),
                Encryption.getEncryptedAES(editTextProfileManagerNewPasswordFirst.getText().toString())
                );

    }

    private void updateProfileImage() {
        ProfileManagerModel.uploadProfileImage(
                getApplicationContext(),
                resultImageFile,
                LoginApplication.user.getEmail(),
                LoginApplication.user.getPassword(),
                LoginApplication.user.getNickname()
        );
    }

    private boolean deleteUser(String password){
        return ProfileManagerModel.deleteUser(
                getApplicationContext(),
                LoginApplication.user.getEmail(),
                password
        );
    }
}
