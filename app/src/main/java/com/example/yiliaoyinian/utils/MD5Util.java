package com.example.yiliaoyinian.utils;



import android.util.Log;

import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author lumi
 */
public class MD5Util {

    /**
     * md5 - 32位
     * @param sourceStr
     * @return
     * @throws Exception
     */
    public static String MD5(String sourceStr){
        String result = "";
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(sourceStr.getBytes(Charset.defaultCharset()));
            byte[] b = md.digest();
            int i;
            StringBuffer buf = new StringBuffer();
            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if (i < 0) {
                    i += 256;
                }
                if (i < 16) {
                    buf.append("0");
                }
                buf.append(Integer.toHexString(i));
            }
            result = buf.toString();
        } catch (NoSuchAlgorithmException e) {
            Log.d("md5 error, e:{}", e.getMessage()+"");
        }
        return result;
    }


}
