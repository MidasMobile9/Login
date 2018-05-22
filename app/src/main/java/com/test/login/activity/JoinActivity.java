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
import android.widget.LinearLayout;
import android.widget.TextView;

import com.test.login.R;
import com.test.login.util.Permission;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;

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
    @BindView(R.id.circleImageViewProfileImage)
    CircleImageView circleImageViewProfileImage;
    @BindView(R.id.editTextNickname)
    EditText editTextNickname;
    @BindView(R.id.textViewSaveProfile)
    TextView textViewSaveProfile;

    private String email;
    private String password;
    private Permission mPermission;
    private String[] permissions= {Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);
        ButterKnife.bind(this);
        mPermission = new Permission(this, permissions);
        mPermission.setSnackbar(linearLayoutJoinActivity,getString(R.string.permission_snackbar_text),getString(R.string.permission_snackbar_text_rational));
    }

    /**
     * 가입하기 버튼 클릭 이벤트
     * */
    @OnClick({R.id.textViewDoJoin})
    public void onClickJoinButtonJoin(View view){
        email = editTextInputEmail.getText().toString().trim();
        password = editTextInputPasswordFirst.getText().toString().trim();

        textViewDoJoin.setVisibility(View.GONE);
        linearLayoutEmailPassword.setVisibility(View.GONE);

        textViewSaveProfile.setVisibility(View.VISIBLE);
        linearLayoutProfileInfo.setVisibility(View.VISIBLE);
    }
    /**
     * 프로필 사진 클릭 이벤트
     * */
    @OnClick({R.id.circleImageViewProfileImage})
    public void onClickJoinCircleImageViewProfileImage(View view){
        if(!mPermission.checkPermissions())
            return;
        doTakeAlbumAction();
    }

    private final int PICK_FROM_CAMERA = 0;
    private final int PICK_FROM_ALBUM = 1;
    private final int CROP_FROM_CAMERA = 2;
    private boolean isImageSelected=false;
    private Uri mImageCaptureUri;
    /**
     * 앨범에서 이미지 가져오기
     */
    private void doTakeAlbumAction() {
        // 앨범 호출
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType(android.provider.MediaStore.Images.Media.CONTENT_TYPE);
        startActivityForResult(intent, PICK_FROM_ALBUM);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_OK) {
            return;
        }
        switch (requestCode) {
            case CROP_FROM_CAMERA: {
                // 크롭이 된 이후의 이미지를 넘겨 받습니다.
                // 이미지뷰에 이미지를 보여준다거나 부가적인 작업 이후에
                // 임시 파일을 삭제합니다.
                final Bundle extras = data.getExtras();
                if (extras != null) {
                    Bitmap photo = extras.getParcelable("data");
                    cropImage(photo, Environment.getExternalStorageDirectory().getAbsolutePath() + "/sample.png");
                    isImageSelected=true;
                }
                // 임시 파일 삭제
                File f = new File(mImageCaptureUri.getPath());
                if (f.exists()) {
                    f.delete();
                }
                break;
            }

            case PICK_FROM_ALBUM: {
                mImageCaptureUri = data.getData();
            }
            case PICK_FROM_CAMERA: {
                // 이미지를 가져온 이후의 리사이즈할 이미지 크기를 결정합니다.
                // 이후에 이미지 크롭 어플리케이션을 호출하게 됩니다.
                Intent intent = new Intent("com.android.camera.action.CROP");
                intent.setDataAndType(mImageCaptureUri, "image/*");
                intent.putExtra("aspectX", 1);      //정사각형
                intent.putExtra("aspectY", 1);
                //set crop properties
                //indicate output X and Y
                intent.putExtra("outputX", 300);
                intent.putExtra("outputY", 300);
                //retrieve data on return
                intent.putExtra("scale", true);
                intent.putExtra("return-data", true);
                startActivityForResult(intent, CROP_FROM_CAMERA);
                break;
            }
        }
    }

    private void cropImage(Bitmap bitmap,String filePath){
        String tempPath = Environment.getExternalStorageDirectory().getAbsolutePath();
        File image = new File(tempPath);
        if(!image.exists()){      //폴더 없으면 새로 만들기
            image.mkdir();
        }
        File copyFile = new File(filePath);
        BufferedOutputStream outputStream = null;
        try{
            copyFile.createNewFile();
            outputStream = new BufferedOutputStream(new FileOutputStream(copyFile));
            bitmap.compress(Bitmap.CompressFormat.PNG,100,outputStream);
            sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,
                    Uri.fromFile(copyFile)));
            outputStream.flush();
            outputStream.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        File f = new File(mImageCaptureUri.getPath());
        if (f.exists()) {
            f.delete();
        }
    }
}
