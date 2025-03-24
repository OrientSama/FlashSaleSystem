package org.orient.flashsalesystem;

import org.junit.jupiter.api.Test;
import org.orient.flashsalesystem.utils.MD5Util;

public class TestMD5
{
    @Test
    public void testMd5(){
        String password = "123456";
        // d3b1294a61a07da9b49b6e22b2cbd7f9
        System.out.println(MD5Util.inputPassToFormPass(password));
        // ce21b747de5af71ab5c2e20ff0a60eea
        System.out.println(MD5Util.md5("12123456c3"));  //d3b1294a61a07da9b49b6e22b2cbd7f9

        System.out.println(MD5Util.inputPassToDBPass(password, "a12s3df1"));

    }
}
