package com.goodoil.aft.application;

import android.app.Application;

import com.goodoil.aft.utils.imagepicker.GlideImageLoader;
import com.corelibrary.application.AppContext;
import com.lzy.imagepicker.ImagePicker;
import com.mob.MobSDK;

/**
 * Created by Administrator on 2017/9/28.
 */

public class CarApplication extends Application {

    private static CarApplication mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        AppContext.init(this);
        // 通过代码注册你的AppKey和AppSecret
        MobSDK.init(this, "21419b496759d", "64a01522511dd70afcf81f928949e400");
        ImagePicker.getInstance().setImageLoader(new GlideImageLoader());
    }

    public static CarApplication getInstance() {
        return mInstance;
    }
}
