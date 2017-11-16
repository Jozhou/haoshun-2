package com.corelibrary.application;

import android.content.Context;

public class AppContext {
    private static Context mAppContext;

    public static void init(Context context) {
        if (mAppContext == null) {
            mAppContext = context.getApplicationContext();
        } else {
            throw new IllegalStateException("set context duplicate");
        }
    }

    public static Context get() {
        if (mAppContext == null) {
            throw new IllegalStateException("forget init?");
        } else {
            return mAppContext;
        }
    }
}
