package com.goodoil.aft.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.bumptech.glide.Glide;
import com.corelibrary.activity.base.BaseActivity;
import com.corelibrary.models.http.BaseOperater;
import com.corelibrary.utils.ViewInject.ViewInject;
import com.corelibrary.view.TitleBar;
import com.goodoil.aft.R;
import com.goodoil.aft.context.IntentCode;
import com.goodoil.aft.models.operater.GetStudyOperater;

import uk.co.senab.photoview.PhotoView;

/**
 * Created by Administrator on 2017/11/20.
 */

public class OnlineStudyActivity extends BaseActivity {

    private static final String TAG = OilModelActivity.class.getSimpleName();

    @ViewInject("titlebar")
    private TitleBar titleBar;
    @ViewInject("photoview")
    private PhotoView photoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_online_study);
    }

    @Override
    protected void onQueryArguments(Intent intent) {
        super.onQueryArguments(intent);
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
        getOnLineStudy();
    }

    private void getOnLineStudy() {
        final GetStudyOperater operater = new GetStudyOperater(this);
        operater.onReq(new BaseOperater.RspListener() {
            @Override
            public void onRsp(boolean success, Object obj) {
                if (success) {
                    if (!TextUtils.isEmpty(operater.getImgurl())) {
                        Glide.with(OnlineStudyActivity.this).load(operater.getImgurl()).dontAnimate().into(photoView);
                    }
                }

            }
        });
    }
}
