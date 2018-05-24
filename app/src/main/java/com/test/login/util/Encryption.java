package com.test.login.util;

import android.util.Base64;

import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class Encryption {
    public static String encAES(String str) {
        String enStr = "";

        try {
            Key keySpec = getAESKey();
            Cipher c = Cipher.getInstance("AES/CBC/PKCS5Padding");
            c.init(Cipher.ENCRYPT_MODE, keySpec);
            byte[] encrypted = c.doFinal(str.getBytes("UTF-8"));
            enStr = new String(Base64.encode(encrypted, Base64.NO_WRAP));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return enStr;
    }

    private static Key getAESKey() {
        Key keySpec = null;

        try {
            String key = "MIDASMOBILE9";
            byte[] keyBytes = new byte[16];
            byte[] b = key.getBytes("UTF-8");

            int len = b.length;
            if (len > keyBytes.length) {
                len = keyBytes.length;
            }

            System.arraycopy(b, 0, keyBytes, 0, len);
            keySpec = new SecretKeySpec(keyBytes, "AES");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return keySpec;
    }
}
