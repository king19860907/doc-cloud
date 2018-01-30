package com.doc.cloud.user;

import org.apache.shiro.crypto.hash.SimpleHash;
import org.junit.Test;

/**
 * Created by majun on 31/01/2018.
 */
public class GeneratePasswordTest {

    @Test
    public void generatePassword(){
        System.out.println(generatePassword("admin","123456","123456"));
    }

    private String  generatePassword(String username,String password,String salt){
        String algorithmName = "md5";
        SimpleHash hash = new SimpleHash(algorithmName,password,username+salt,2);
        String encodePassword=hash.toHex();
        return encodePassword;
    }

}
