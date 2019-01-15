//package com.util;
package org.apereo.cas;
import java.io.IOException;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class DESCoder {
    private final static String KEY = "12345678"; // 字节数必须是8的倍数  
    public static String decrypt(String input) throws Exception   
    {  
        byte[] result = base64Decode(input);  
        return new String(desDecrypt(result));  
    }  
    private static byte[] desDecrypt(byte[] encryptText) throws Exception   
    {  
        SecureRandom sr = new SecureRandom();  
        DESKeySpec dks =  new DESKeySpec(KEY.getBytes());  
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");  
        SecretKey key = keyFactory.generateSecret(dks);  
        Cipher cipher = Cipher.getInstance("DES");  
        cipher.init(Cipher.DECRYPT_MODE, key, sr);  
        byte encryptedData[] = encryptText;  
        byte decryptedData[] = cipher.doFinal(encryptedData);  
        return decryptedData;  
    }      
      
    private static byte[] base64Decode(String s) throws IOException   
    {  
        if (s == null)  
        {  
           return null;  
        }  
        BASE64Decoder decoder = new BASE64Decoder();  
        byte[] b = decoder.decodeBuffer(s);  
        return b;  
    }  
    public static  void main(String args[]) {  
        try {  
            System.out.println(DESCoder.decrypt("+4ziokuJ1beLjLhUZrPnaw=="));  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
    }   
}
