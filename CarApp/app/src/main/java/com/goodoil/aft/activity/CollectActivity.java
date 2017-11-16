package com.goodoil.aft.activity;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.goodoil.aft.R;
import com.goodoil.aft.models.adapter.CollectPagerAdapter;
import com.corelibrary.activity.base.BaseActivity;
import com.corelibrary.utils.ViewInject.ViewInject;
import com.corelibrary.view.TitleBar;
import com.corelibrary.widget.PagerSlidingTabStrip;

/**
 * Created by Administrator on 2017/10/16.
 */

public class CollectActivity extends BaseActivity {


    @ViewInject(value = "titlebar")
    private TitleBar titlebar;
    @ViewInject(value = "pst_collect")
    private PagerSlidingTabStrip pstCollect;

    @ViewInject(value = "vp_collect")
    private ViewPager vpCollect;

    private CollectPagerAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collect);
    }

    @Override
    protected void onBindListener() {
        super.onBindListener();
        titlebar.setLeftOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void onApplyData() {
        super.onApplyData();
        mAdapter = new CollectPagerAdapter(getSupportFragmentManager());
        vpCollect.setAdapter(mAdapter);
        pstCollect.setViewPager(vpCollect);
    }
}
