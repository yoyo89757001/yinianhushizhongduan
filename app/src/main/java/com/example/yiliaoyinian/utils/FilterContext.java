package com.example.yiliaoyinian.utils;


import android.util.Log;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.Map;

/**
 * @author: hongsheng.wei
 * @Date: 2018/11/13
 * @Description:
 */

public class FilterContext {

    /**
     * 生成签名
     *
     * @param parames
     * @return
     */
    public static String createSign(Map<String, String> parames, String appKey, Boolean encode) {
        //sort
        Object[] keys = parames.keySet().toArray();
        Arrays.sort(keys);
        StringBuilder stringBuffer = new StringBuilder();
        boolean first = true;
        for (Object key : keys) {
            if (first) {
                first = false;
            } else {
                stringBuffer.append("&");
            }
            stringBuffer.append(key).append("=");
            String paramValue = parames.get(key);
            String valueString = "";
            if (null != paramValue) {
                valueString = String.valueOf(paramValue);
            }
            if (encode) {
                try {
                    stringBuffer.append(URLEncoder.encode(valueString, "UTF-8"));
                } catch (UnsupportedEncodingException e) {
                    Log.d("URLEncoder encode error", e.getMessage()+"");
                }
            } else {
                stringBuffer.append(valueString);
            }
        }
        //append appKey
        String signStr = stringBuffer.append("&").toString().toLowerCase() + appKey;
        try {
         //   Log.d("FilterContext", "signStr: "+signStr);
            return MD5Util.MD5(signStr);
        } catch (Exception e) {
            Log.d("URLEncoder encode error", e.getMessage()+"");
        }
        return null;
    }

}
