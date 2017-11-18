package com.goodoil.aft.models.entry;

import android.text.TextUtils;

/**
 * Created by Administrator on 2017/11/17.
 */

public class ShareEntry {

    private static ShareEntry shareEntry;

    public String title = "";
    public String content = "";
    public String pic = "";
    public String url = "";

    private ShareEntry() {

    }


    public static ShareEntry getInstance() {
        if (shareEntry == null) {
            synchronized (ShareEntry.class) {
                if (shareEntry == null) {
                    shareEntry = new ShareEntry();
                }
            }
        }
        return shareEntry;
    }

    public boolean isInit() {
        return !TextUtils.isEmpty(pic) && !TextUtils.isEmpty(url);
    }
}
