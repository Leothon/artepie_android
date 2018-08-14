package com.leothon.cogito.Utils;

import java.security.MessageDigest;

/**
 * created by leothon on 2018.7.24
 * 使用md5对明文密码进行加密
 */
public class MD5Utils {

    public final static String encrypt(String password){

        //使用md5之后，转为十六进制
        char hexDigits[] = {'0','1','2','3','4','5','6','7','8','9','a','b','c','d','e','f'};

        try {
            byte[] btInput = password.getBytes();
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.update(btInput);
            byte[] md = messageDigest.digest();

            int j = md.length;
            char str[] = new char[j*2];
            int k = 0;
            for (int i = 0;i < j;i++){
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        }catch (Exception e){
            return null;
        }
    }
}
