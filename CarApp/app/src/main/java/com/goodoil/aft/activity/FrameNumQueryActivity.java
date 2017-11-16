package com.goodoil.aft.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.goodoil.aft.R;
import com.goodoil.aft.context.IntentCode;
import com.goodoil.aft.models.entry.NameValueEntry;
import com.goodoil.aft.models.operater.GetFrameNumOperater;
import com.corelibrary.activity.base.BaseActivity;
import com.corelibrary.models.http.BaseOperater;
import com.corelibrary.utils.DialogUtils;
import com.corelibrary.utils.ViewInject.ViewInject;
import com.corelibrary.view.TitleBar;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/9/29.
 */

public class FrameNumQueryActivity extends BaseActivity {


    @ViewInject("titlebar")
    private TitleBar titleBar;
    @ViewInject("et_frame_num")
    private EditText etFrameNum;
    @ViewInject(value = "btn_query", setClickListener = true)
    private Button btnQuery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frame_query);
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
    public void onClick(View v) {
        super.onClick(v);
        int id = v.getId();
        if (id == R.id.btn_query) {
            onQueryFrameNum();
        }
    }

    private void onQueryFrameNum() {
        String vim = etFrameNum.getText().toString();
        if (TextUtils.isEmpty(vim) || vim.length() != 17) {
            DialogUtils.showToastMessage(R.string.hint_input_frame_num);
            return;
        }
        final GetFrameNumOperater operater = new GetFrameNumOperater(this);
        operater.setParams(vim);
        operater.onReq(new BaseOperater.RspListener() {
            @Override
            public void onRsp(boolean success, Object obj) {
                if (success) {
                    List<NameValueEntry> mdata = operater.getData();
                    Intent intent = new Intent(FrameNumQueryActivity.this, FrameDetailActivity.class);
                    intent.putStringArrayListExtra(IntentCode.INTENT_FRAME_NUM_LIST, (ArrayList)mdata);
                    startActivity(intent);
                }
            }
        });

    }
}
