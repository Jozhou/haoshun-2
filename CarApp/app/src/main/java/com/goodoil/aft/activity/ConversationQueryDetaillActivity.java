package com.goodoil.aft.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.goodoil.aft.R;
import com.goodoil.aft.context.IntentCode;
import com.goodoil.aft.models.adapter.FrameNumAdapter;
import com.goodoil.aft.models.entry.NameValueEntry;
import com.goodoil.aft.models.entry.VehicleQueryEntry;
import com.goodoil.aft.models.operater.GetConversationDetailOperater;
import com.corelibrary.activity.base.BaseActivity;
import com.corelibrary.models.http.BaseOperater;
import com.corelibrary.utils.ViewInject.ViewInject;
import com.corelibrary.view.TitleBar;

import java.util.List;

/**
 * Created by Administrator on 2017/9/29.
 */

public class ConversationQueryDetaillActivity extends BaseActivity {

    @ViewInject("titlebar")
    private TitleBar titleBar;
    @ViewInject(value = "tv_carcode", setClickListener = true)
    private TextView tvDetail;
    @ViewInject("lv_conversation")
    private ListView lvConversation;
    @ViewInject(value = "btn_conversation_center", setClickListener = true)
    private Button btnCenter;

    @ViewInject("sv_content")
    private ScrollView scrollView;

    private List<NameValueEntry> mData;
    FrameNumAdapter mAdater;
    private VehicleQueryEntry entry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversation_detail);
    }

    @Override
    protected void onQueryArguments(Intent intent) {
        super.onQueryArguments(intent);
        entry = (VehicleQueryEntry) intent.getSerializableExtra(IntentCode.INTENT_VEHICLE_QUERY_ITEM);
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
        scrollView.smoothScrollTo(0, 0);
        tvDetail.setText(entry.detail);
        getDetail();
    }

    private void getDetail() {
        final GetConversationDetailOperater operater = new GetConversationDetailOperater(this);
        operater.setParams(entry.carcode);
        operater.onReq(new BaseOperater.RspListener() {
            @Override
            public void onRsp(boolean success, Object obj) {
                if (success) {
                    mData = operater.getData();
                    mAdater = new FrameNumAdapter(ConversationQueryDetaillActivity.this, mData);
                    lvConversation.setAdapter(mAdater);
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        int id = v.getId();
        if (id == R.id.btn_conversation_center) {
        }
    }
}
