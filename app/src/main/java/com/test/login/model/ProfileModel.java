package com.test.login.model;

import android.util.Log;

import com.test.login.network.NetworkDefineConstant;
import com.test.login.network.OkHttpAPICall;
import com.test.login.network.OkHttpInitSingletonManager;

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.UnknownHostException;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ProfileModel {
    private static final String TAG = "ProfileModel";

    // ProfileFragment의 Model static 함수 정의
    public static boolean logoutUser() {
        OkHttpClient client = OkHttpInitSingletonManager.getOkHttpClient();
        Response response = null;

        boolean isLogouted = false;
        String message = null;

        RequestBody requestBody = new FormBody.Builder().build();

        try {
            response = OkHttpAPICall.POST(client, NetworkDefineConstant.SERVER_URL_LOGOUT_USER, requestBody);

            if (response == null) {
                Log.e(TAG, "Response of deleteUser() is null.");

                return false;
            } else {
                JSONObject jsonFromServer = new JSONObject(response.body().string());

                if (jsonFromServer.has("result")) {
                    isLogouted = jsonFromServer.getBoolean("result");
                }

                if (jsonFromServer.has("message")) {
                    message = jsonFromServer.getString("message");
                }
            }

        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (response != null) {
                response.close();
            }
        }

        return isLogouted;
    }
}
