package com.corelibrary.context;

import android.content.pm.ApplicationInfo;

import com.corelibrary.application.AppContext;

/**
 * Created by Administrator on 2017/5/20.
 */
public class Config {

    private static final String KEY_IS_DEBUG = "IS_DEBUG";
    /**
     * 开启debug
     */
    public static boolean IS_DEBUG;

    static {
        try {
            ApplicationInfo info = AppContext.get().getApplicationInfo();
            IS_DEBUG =  (info.flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0;
        } catch (Exception e) {
            IS_DEBUG = false;
        }
    }

    public static String getBaseUrl() {
        return "http://39.106.23.54";
//        return "http://47.92.150.165:7433";
    }

}
