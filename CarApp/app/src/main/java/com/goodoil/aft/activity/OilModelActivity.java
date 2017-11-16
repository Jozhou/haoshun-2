package com.goodoil.aft.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.bumptech.glide.Glide;
import com.goodoil.aft.R;
import com.goodoil.aft.context.IntentCode;
import com.corelibrary.activity.base.BaseActivity;
import com.corelibrary.utils.ViewInject.ViewInject;
import com.corelibrary.view.TitleBar;

import uk.co.senab.photoview.PhotoView;

/**
 * Created by Administrator on 2017/9/28.
 */

public class OilModelActivity extends BaseActivity {

    private static final String TAG = OilModelActivity.class.getSimpleName();

    @ViewInject("titlebar")
    private TitleBar titleBar;
    @ViewInject("photoview")
    private PhotoView photoView;

    private String path;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oil_image);
    }

    @Override
    protected void onQueryArguments(Intent intent) {
        super.onQueryArguments(intent);
        path = intent.getStringExtra(IntentCode.INTENT_OIL_MODEL);
    }

    @Override
    protected void onBindListener() {
        super.onBindListener();
        titleBar.setLeftOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void onApplyData() {
        super.onApplyData();
        Glide.with(this).load(path).dontAnimate().into(photoView);
    }

}
