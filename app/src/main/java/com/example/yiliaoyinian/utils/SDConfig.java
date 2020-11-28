package com.example.yiliaoyinian.utils;

import com.example.yiliaoyinian.MyApplication;

public class SDConfig {

    /**
     * 是否打开调试页面
     */
    public static final boolean isNeedJumpToTestPage = false;

    /**
     * 文件保存位置
     */


    public static String getDemoFolder(){
        return MyApplication.SDPATH1;
    }

    public static String getRecordsFolder(){
        return MyApplication.SDPATH1 ;
    }

    public static String getCapturesFolder(){
        return MyApplication.SDPATH3 ;
    }

    public static String getStreamsFolder(){
        return MyApplication.SDPATH2;
    }
}
