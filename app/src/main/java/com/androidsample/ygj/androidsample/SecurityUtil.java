package com.androidsample.ygj.androidsample;

import java.nio.charset.Charset;
import java.security.MessageDigest;

/**
 * Created by YGJ on 2015/10/13 0013.
 */
public class SecurityUtil {

    /*
    * 使用MD5处理字符串
    * @Param str 要处理的字符串
    * */
    public static String MD5Encrypt(String str)
            throws Exception {
        if (str == null || str.length() == 0) return null;
        MessageDigest md = MessageDigest.getInstance("MD5");

        byte[] bytes = str.getBytes(Charset.defaultCharset());
        bytes = md.digest(bytes);
        StringBuilder stringBuilder = new StringBuilder();
        for(int i=0;i<bytes.length;i++){
            String hex = Integer.toHexString((bytes[i] & 0xFF));
            if(hex.length()==1){
                hex = "0" + hex;
            }
            stringBuilder.append(hex);
        }
        return stringBuilder.toString();
    }
}
