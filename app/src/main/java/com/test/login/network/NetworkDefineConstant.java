package com.test.login.network;

public class NetworkDefineConstant {
    // Host 주소
    public static final String HOST_URL = "http://35.187.156.145:3000";

    //요청 URL path
    // ex) public static String SERVER_URL_NAME;
    public static String join;
    public static String checkNickname;
    public static String checkEmail;
    static {
        join = HOST_URL + "/join";
        checkNickname = HOST_URL + "/check/nickname/";
        checkEmail = HOST_URL + "/check/email/";
        // ex) SERVER_URL_NAME = HOST_URL + "[/aaa/bbb/param1]";
    }
}
