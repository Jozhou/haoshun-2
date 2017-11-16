package com.goodoil.aft.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.goodoil.aft.R;
import com.goodoil.aft.context.IntentCode;
import com.goodoil.aft.models.adapter.VehicleQueryItemAdapter;
import com.goodoil.aft.models.entry.VehicleItemEntry;
import com.goodoil.aft.models.entry.VehicleQueryEntry;
import com.goodoil.aft.models.operater.QueryOilOperater;
import com.goodoil.aft.view.vehicle.ConversationQueryWrapper;
import com.goodoil.aft.view.vehicle.OilQueryWrapper;
import com.corelibrary.activity.base.BaseActivity;
import com.corelibrary.models.http.BaseOperater;
import com.corelibrary.utils.DialogUtils;
import com.corelibrary.utils.ViewInject.ViewInject;
import com.corelibrary.view.TitleBar;

import java.util.List;

/**
 * Created by Administrator on 2017/9/29.
 */

public class ConversationQuerylActivity extends BaseActivity {

    @ViewInject("titlebar")
    private TitleBar titleBar;
    @ViewInject(value = "tv_clear", setClickListener = true)
    private TextView tvClear;
    @ViewInject("lv_conversation")
    private ListView lvConversation;
    @ViewInject(value = "btn_query", setClickListener = true)
    private Button btnQuery;

    @ViewInject("sv_content")
    private ScrollView scrollView;

    private List<VehicleQueryEntry> mData;
    VehicleQueryItemAdapter mAdater;
    public static final int TYPE_CONVERSATION = 1;
    public static final int TYPE_OIL = 2;

    private int from = TYPE_CONVERSATION;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversation_query);
    }

    @Override
    protected void onQueryArguments(Intent intent) {
        super.onQueryArguments(intent);
        from = intent.getIntExtra(IntentCode.INTENT_QUERY_FROM, TYPE_CONVERSATION);
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

        titleBar.setRightOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ConversationQuerylActivity.this, SelBrandActivity.class);
                intent.putExtra(IntentCode.INTENT_SEL_VEHICLE_FROM, SelVehicleActivity.FROM_Conversation);
                intent.putExtra(IntentCode.INTENT_TYPE, SelVehicleActivity.SEL_BRAND);
                startActivityForResult(intent, SelVehicleActivity.SEL_BRAND);
            }
        });
    }

    @Override
    protected void onApplyData() {
        super.onApplyData();
        if (from == TYPE_CONVERSATION) {
            titleBar.setTitle(R.string.conservation_query);
            mData = ConversationQueryWrapper.get().getData();
        } else {
            titleBar.setTitle(R.string.oil_change_book);
            mData = OilQueryWrapper.get().getData();
        }

        mAdater = new VehicleQueryItemAdapter(this, mData);
        lvConversation.setAdapter(mAdater);

        if (mData == null || mData.isEmpty()) {
            tvClear.setVisibility(View.GONE);
        } else {
            tvClear.setVisibility(View.VISIBLE);
        }
        scrollView.smoothScrollTo(0, 0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SelVehicleActivity.SEL_BRAND) {
            if (resultCode == RESULT_OK) {
                VehicleItemEntry brand = (VehicleItemEntry) data.getSerializableExtra(IntentCode.INTENT_BRAND);
                VehicleItemEntry series = (VehicleItemEntry) data.getSerializableExtra(IntentCode.INTENT_SERIES);
                VehicleItemEntry yearStyle = (VehicleItemEntry) data.getSerializableExtra(IntentCode.INTENT_YEAR_STYLE);
                VehicleItemEntry version = (VehicleItemEntry) data.getSerializableExtra(IntentCode.INTENT_VERSION);
                VehicleQueryEntry entry = new VehicleQueryEntry();
                entry.detail = getResources().getString(R.string.show_vehicle2, brand.name, series.name, yearStyle.name, version.name);
                entry.carcode = version.carcode;
                if (from == TYPE_CONVERSATION) {
                    ConversationQueryWrapper.get().add(entry);
                } else {
                    OilQueryWrapper.get().add(entry);
                }

                mAdater.notifyDataSetChanged();
                tvClear.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        int id = v.getId();
        if (id == R.id.tv_clear) {
            if (from == TYPE_CONVERSATION) {
                ConversationQueryWrapper.get().clear();
            } else {
                OilQueryWrapper.get().clear();
            }
            mAdater.setSelPos(0);
            mAdater.notifyDataSetChanged();
            tvClear.setVisibility(View.GONE);
        } else if (id == R.id.btn_query) {
            if (mData == null || mData.isEmpty()) {
                DialogUtils.showToastMessage(R.string.toast_sel_vehicle);
                return;
            }
            if (from == TYPE_CONVERSATION) {
                Intent intent = new Intent(this, ConversationQueryDetaillActivity.class);
                intent.putExtra(IntentCode.INTENT_VEHICLE_QUERY_ITEM, mAdater.getSelItem());
                startActivity(intent);
            } else {
                queryOil();
            }
        }
    }

    private void queryOil() {
        final QueryOilOperater operater = new QueryOilOperater(this);
        operater.setParams(mAdater.getSelItem().carcode);
        operater.onReq(new BaseOperater.RspListener() {
            @Override
            public void onRsp(boolean success, Object obj) {
                if (success) {
                    String url = operater.getUrl();
                    if (!TextUtils.isEmpty(url)) {
                        Intent intent = new Intent(ConversationQuerylActivity.this, ActivityWeb.class);
                        intent.putExtra(IntentCode.INTENT_WEB_TITLE, getString(R.string.oil_change_book));
                        intent.putExtra(IntentCode.INTENT_WEB_URL, url);
                        startActivity(intent);
                    }
                }
            }
        });

    }
}
