package com.goodoil.aft.activity;

import android.content.Intent;
import android.os.Bundle;

import com.goodoil.aft.R;
import com.goodoil.aft.common.data.Account;
import com.corelibrary.activity.base.BaseActivity;
import com.corelibrary.utils.MainHandler;

/**
 * Created by Administrator on 2017/9/28.
 */

public class SplashActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
    }

    @Override
    protected void onApplyData() {
        super.onApplyData();
        MainHandler.get().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (Account.get().isLogin()) {
                    Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        }, 1000);
    }
}
