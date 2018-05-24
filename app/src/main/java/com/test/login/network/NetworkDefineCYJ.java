package com.test.login.network;

public class NetworkDefineCYJ {
    // Host 주소
    public static final String HOST_URL = "http://35.187.156.145:3000";

    //요청 URL path
    public static String SERVER_URL_LOGIN_USER;

    static {
        SERVER_URL_LOGIN_USER = HOST_URL + "/login";
    }
}
