package org.orient.flashsalesystem.utils;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Component;



@Component
public class MD5Util {
    public static String md5(String input) {
        return DigestUtils.md5Hex(input);
    }
    // 此处的盐 是 前端给的, 用于第一次前端加密
    private static final String salt = "1a2b3d4c";

    private static String inputPassToFormPass(String inputPass) {
        String str = salt.charAt(0) + salt.charAt(2) + inputPass + salt.charAt(5) + salt.charAt(4);
        return md5(str);
    }

    private static String formPassToDBPass(String formPass, String salt) {
        String str = salt.charAt(5) + salt.charAt(3) + formPass + salt.charAt(1) + salt.charAt(2);
        return md5(str);
    }

    public static String inputPassToDBPass(String inputPass, String salt) {
        String formPass = inputPassToFormPass(inputPass);
        return formPassToDBPass(formPass, salt);
    }
}
