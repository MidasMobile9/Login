package com.test.login.application;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.bumptech.glide.Glide;
import com.test.login.data.User;

public class LoginApplication extends Application {
    private static Context mContext;
    private static String mCookie = "";

    public static User user = null;

    @Override
    public void onCreate() {
        super.onCreate();

        mContext = this;
    }

    public static Context getLoginApplicationContext() {

        if ( mContext == null ) {
            Log.e("CONTEXT_ERROR", "Login application Context is null");

            return null;
        }

        return mContext;
    }

    public static void setCookie(String cookie) {
        if ( cookie != null ) {
            mCookie = cookie;
        }
    }

    public static String getCookie() {
        return mCookie;
    }

    public static void clearCookie() {
        mCookie = "";
    }

    public static void setUser(int no, String email, String nickname, String profileimg) {
        if ( user == null ) {
            user = new User();
        }

        user.setNo(no);
        user.setEmail(email);
        user.setNickname(nickname);
        user.setProfileimg(profileimg);
    }

    /** Glide 사용시 OOM 처리 */
    @Override public void onLowMemory() {
        super.onLowMemory();
        Glide.get(this).clearMemory();
    }

    @Override public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        Glide.get(this).trimMemory(level);
    }
}
