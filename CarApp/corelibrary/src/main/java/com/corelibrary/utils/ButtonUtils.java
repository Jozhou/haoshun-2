package com.corelibrary.utils;

/**
 * Created by Administrator on 2017/5/22.
 */
public class ButtonUtils {

    /**
     * 防止按钮等连续两次点击
     */
    private static long	lastClickTime;

    public static boolean isFastDoubleClick()
    {
        long time = System.currentTimeMillis();
        long timeD = time - lastClickTime;
        if (0 < timeD && timeD < 500)
        {
            return true;
        }
        lastClickTime = time;
        return false;
    }
}
