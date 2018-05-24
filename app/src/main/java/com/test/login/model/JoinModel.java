package com.test.login.model;

import android.util.Log;
import com.google.gson.Gson;
import com.test.login.network.NetworkDefineConstant;
import com.test.login.network.OkHttpAPICall;
import com.test.login.network.OkHttpInitSingletonManager;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.UnsupportedEncodingException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import okhttp3.OkHttpClient;
import okhttp3.Response;


/***
 *가입시 필요한 백그라운드 메소드
 */
public class JoinModel {
    // JoinActivity의 Model static 함수 정의

    /**
     * 가입하는 백그라운드 메소드
     * @return 가입하는 결과
     */
    public static boolean getJoinResult() {
        String TAG = "JoinModel";
        String functionName = "getJoinResult()";
        OkHttpClient client = OkHttpInitSingletonManager.getOkHttpClient();
        Response response = null;
        String message = null;
        boolean result = false;
        try {
            response = OkHttpAPICall.GET(client, NetworkDefineConstant.join);

            if ( response == null ) {
                Log.e(TAG, "Response of "+functionName+" is null.");
                return result;
            } else {
                JSONObject jsonFromServer = new JSONObject(response.body().string());

                if ( jsonFromServer.has("result") ) {
                    result = jsonFromServer.getBoolean("result");
                }

                if ( jsonFromServer.has("message") ) {
                    message = jsonFromServer.getString("message");
                    Log.e(TAG, message);
                }
            }
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if ( response != null ) {
                response.close();
            }
        }
        return result;
    }


    /**
     * 이메일 체크하는 백그라운드 메소드
     * @return 이메일체크 결과
     */
    public static boolean getEmailCheckResult() {
        String TAG = "JoinModel";
        String functionName = "getEmailCheckResult()";
        OkHttpClient client = OkHttpInitSingletonManager.getOkHttpClient();
        Response response = null;
        String message = null;
        boolean result = false;
        try {
            response = OkHttpAPICall.GET(client, NetworkDefineConstant.checkEmail);

            if ( response == null ) {
                Log.e(TAG, "Response of "+functionName+" is null.");
                return result;
            } else {
                JSONObject jsonFromServer = new JSONObject(response.body().string());

                if ( jsonFromServer.has("result") ) {
                    result = jsonFromServer.getBoolean("result");
                }

                if ( jsonFromServer.has("message") ) {
                    message = jsonFromServer.getString("message");
                    Log.e(TAG, message);
                }
            }
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if ( response != null ) {
                response.close();
            }
        }
        return result;
    }


    /**
     * 닉네임 체크하는 백그라운드 메소드
     * @return 닉네임체크 결과
     */
    public static boolean getNicknameCheckResult() {
        String TAG = "JoinModel";
        String functionName = "getNicknameCheckResult()";
        OkHttpClient client = OkHttpInitSingletonManager.getOkHttpClient();
        Response response = null;
        String message = null;
        boolean result = false;
        try {
            response = OkHttpAPICall.GET(client, NetworkDefineConstant.checkNickname);

            if ( response == null ) {
                Log.e(TAG, "Response of "+functionName+" is null.");
                return result;
            } else {
                JSONObject jsonFromServer = new JSONObject(response.body().string());

                if ( jsonFromServer.has("result") ) {
                    result = jsonFromServer.getBoolean("result");
                }

                if ( jsonFromServer.has("message") ) {
                    message = jsonFromServer.getString("message");
                    Log.e(TAG, message);
                }
            }
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if ( response != null ) {
                response.close();
            }
        }
        return result;
    }

}
