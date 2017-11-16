package com.corelibrary.utils;

import android.os.Handler;
import android.os.Looper;

/**
 * Created by Administrator on 2017/9/28.
 */

public class MainHandler {

    private static final Handler mInstance = new Handler(Looper.getMainLooper());

    public static Handler get() {
        return mInstance;
    }

}
