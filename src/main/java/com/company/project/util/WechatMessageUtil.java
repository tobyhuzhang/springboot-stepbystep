package com.company.project.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author huyan@yxj.org.cn
 * @date 2022/2/28
 */
public class WechatMessageUtil {

    public static boolean checkSignNature(String[] paramArr,String signature) {

        StringBuilder content = new StringBuilder();
        for (int i = 0; i < paramArr.length; i++)
        {
            content.append(paramArr[i]);
        }

        String cipherText = null;
        try{
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            byte[] digest = md.digest(content.toString().getBytes());
            cipherText = byteToStr(digest);
        }catch (NoSuchAlgorithmException ex){
            ex.printStackTrace();
        }

        return cipherText != null ? cipherText.equals(signature.toUpperCase()) : false;
    }

    public static String byteToStr(byte[] digest) {
        String strDigest = "";
        for (int i = 0; i < digest.length; i++) {
            strDigest += byteToHexStr(digest[i]);
        }
        return strDigest;
    }

    public static String byteToHexStr(byte mByte) {
        char[] Digit = { '0', '1' , '2', '3', '4' , '5', '6', '7' , '8', '9', 'A' , 'B', 'C', 'D' , 'E', 'F'};
        char[] tempArr = new char[2];
        tempArr[0] = Digit[(mByte >>> 4) & 0X0F];
        tempArr[1] = Digit[mByte & 0X0F];

        String s = new String(tempArr);
        return s;
    }
}
