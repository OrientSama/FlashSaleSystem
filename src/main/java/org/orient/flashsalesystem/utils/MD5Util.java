package org.orient.flashsalesystem.utils;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Component;



@Component
public class MD5Util {
    public static String md5(String input) {
        return DigestUtils.md5Hex(input);
    }
    // 此处的盐 是 前端给的, 用于第一次前端加密
    private static final String salt = "1a2b3c4d";

    public static String inputPassToFormPass(String inputPass) {
        String str ="" + salt.charAt(0) + salt.charAt(2) + inputPass + salt.charAt(5) + salt.charAt(4);
        System.out.println(str);
        return md5(str);
    }

    private static String formPassToDBPass(String formPass, String back_salt) {
        String str ="" +  back_salt.charAt(5) + back_salt.charAt(3) + formPass + back_salt.charAt(1) + back_salt.charAt(2);
        return md5(str);
    }

    public static String inputPassToDBPass(String inputPass, String back_salt) {
        String formPass = inputPassToFormPass(inputPass);
        return formPassToDBPass(formPass, back_salt);
    }
}
