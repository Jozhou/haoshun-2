package com.corelibrary.utils.ViewInject;

/**
 * Created by Administrator on 2017/5/22.
 */
public class DoubleBackUtils {

    /**
     * 防止按钮等连续两次点击
     */
    private static long	lastClickTime;

    public static boolean isFastDoubleClick()
    {
        long time = System.currentTimeMillis();
        long timeD = time - lastClickTime;
        if (0 < timeD && timeD < 1000)
        {
            return true;
        }
        lastClickTime = time;
        return false;
    }
}
