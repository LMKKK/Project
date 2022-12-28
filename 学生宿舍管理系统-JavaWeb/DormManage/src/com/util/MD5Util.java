package com.util;


import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class MD5Util {
    /*
     * 实现将密码MD5加密
     * */
    public static String encoderPwdByMD5(String str) {
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return new BigInteger(1, md5.digest(str.getBytes(StandardCharsets.UTF_8))).toString(16);
    }

    public static void main(String[] args) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        System.out.println(MD5Util.encoderPwdByMD5("111"));
    }
}
