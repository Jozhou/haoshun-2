package com.goodoil.aft.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.ScrollView;

import com.bumptech.glide.Glide;
import com.goodoil.aft.R;
import com.goodoil.aft.context.IntentCode;
import com.goodoil.aft.models.adapter.FrameNumAdapter;
import com.goodoil.aft.models.entry.NameValueEntry;
import com.corelibrary.activity.base.BaseActivity;
import com.corelibrary.utils.DeviceUtils;
import com.corelibrary.utils.ViewInject.ViewInject;
import com.corelibrary.view.TitleBar;

import java.util.List;

/**
 * Created by Administrator on 2017/9/29.
 */

public class FrameDetailActivity extends BaseActivity {

    @ViewInject("titlebar")
    private TitleBar titleBar;
    @ViewInject("rg_tab")
    private RadioGroup rgTabs;
    @ViewInject("lv_config")
    private ListView lvConfig;
    @ViewInject("lv_oem")
    private ListView lvOem;
    @ViewInject("lv_parts")
    private ListView lvParts;
    @ViewInject("iv_pic")
    private ImageView ivPic;

    @ViewInject("fl_container")
    private FrameLayout flContainer;

    @ViewInject("sv_content")
    private ScrollView scrollView;

    private List<NameValueEntry> mData;
    FrameNumAdapter mAdater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frame_detail);
    }

    @Override
    protected void onQueryArguments(Intent intent) {
        super.onQueryArguments(intent);
        mData = (List<NameValueEntry>) intent.getSerializableExtra(IntentCode.INTENT_FRAME_NUM_LIST);
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

        rgTabs.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.rb_config) {
                    lvConfig.setVisibility(View.VISIBLE);
                    lvOem.setVisibility(View.GONE);
                    lvParts.setVisibility(View.GONE);
                } else if (checkedId == R.id.rb_oem) {
                    lvConfig.setVisibility(View.GONE);
                    lvOem.setVisibility(View.VISIBLE);
                    lvParts.setVisibility(View.GONE);
                } else if (checkedId == R.id.rb_conversation) {
                    lvConfig.setVisibility(View.GONE);
                    lvOem.setVisibility(View.GONE);
                    lvParts.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    @Override
    protected void onApplyData() {
        super.onApplyData();
        mAdater = new FrameNumAdapter(this, mData);
        lvConfig.setAdapter(mAdater);

        rgTabs.check(R.id.rb_all);
        scrollView.smoothScrollTo(0, 0);

        int width = DeviceUtils.getScreenWidth();
        Glide.with(this).load(R.drawable.bg_frame_detail)
                .override(width, width)
                .into(ivPic);
    }
}
