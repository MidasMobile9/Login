package com.test.login.network;

public class NetworkDefineOSH {
    // Host 주소
    public static final String HOST_URL = "http://35.187.156.145:3000";

    //요청 URL path
    // ex) public static String SERVER_URL_NAME;
    public static String SERVER_URL_UPDATE;
    public static String SERVER_URL_DELETE;
    static {
        // ex) SERVER_URL_NAME = HOST_URL + "[/aaa/bbb/param1]";
        SERVER_URL_UPDATE = HOST_URL + "/update/user/";
        SERVER_URL_DELETE = HOST_URL + "/delete/user/";
    }
}
