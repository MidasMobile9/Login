package com.test.login.fragment;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.test.login.R;
import com.test.login.activity.LoginActivity;
import com.test.login.activity.MainActivity;
import com.test.login.activity.ProfileManagerActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileFragment extends Fragment {
    public static final int REQUEST_CODE_PROFILE_MANAGER_ACTIVITY = 301;

    public static String USER_NAME;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private Context mContext = null;
    private MainActivity mActivity = null;

    Unbinder unbinder = null;

    @BindView(R.id.circleImageViewProfileFragmentProfileImage) CircleImageView circleImageViewProfileFragmentProfileImage;
    @BindView(R.id.textViewProfileFragmentProfileNickname) TextView textViewProfileFragmentProfileNickname;
    @BindView(R.id.textViewProfileFragmentProfileEmail) TextView textViewProfileFragmentProfileEmail;
    @BindView(R.id.textViewProfileFragmentModifyProfile) TextView textViewProfileFragmentModifyProfile;
    @BindView(R.id.textViewProfileFragmentLogout) TextView textViewProfileFragmentLogout;

    public ProfileFragment() {
        // Required empty public constructor
    }

    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        this.mContext = context;
        this.mActivity = (MainActivity) getActivity();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_profile, container, false);
        // 버터나이프
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onDetach() {
        super.onDetach();

        // 버터나이프 해제
        unbinder.unbind();
        // mContext 해제
        this.mContext = null;
        // mActivity 해제
        this.mActivity = null;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_CODE_PROFILE_MANAGER_ACTIVITY:
                if (resultCode == Activity.RESULT_OK) {
                    /**
                     * 프로필을 변경하였으므로, 데이터를 서버로부터 새로 받아와야함
                     */
                } else if (resultCode == Activity.RESULT_CANCELED) {
                    /**
                     * 프로필을 변경 하지 않았음
                     */
                }
                break;
        }
    }

    @OnClick(R.id.textViewProfileFragmentModifyProfile)
    public void onModifyProfileCllick() {
        Intent intent = new Intent(mContext, ProfileManagerActivity.class);
        startActivityForResult(intent, REQUEST_CODE_PROFILE_MANAGER_ACTIVITY);
    }

    @OnClick(R.id.textViewProfileFragmentLogout)
    public void onProfileLogoutClick(){
        final Intent profileDeleteIntent = new Intent(mContext, LoginActivity.class);
        startActivity(profileDeleteIntent);
        mActivity.finish();
    }
}
