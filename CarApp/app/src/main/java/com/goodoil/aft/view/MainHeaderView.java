package com.goodoil.aft.view;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.goodoil.aft.R;
import com.goodoil.aft.activity.ActivityWeb;
import com.goodoil.aft.activity.ConversationQuerylActivity;
import com.goodoil.aft.activity.FrameNumQueryActivity;
import com.goodoil.aft.context.Config;
import com.goodoil.aft.context.IntentCode;
import com.goodoil.aft.models.adapter.AdAdapter;
import com.goodoil.aft.models.operater.GetAdOperater;
import com.corelibrary.models.http.BaseOperater;
import com.corelibrary.utils.DeviceUtils;
import com.corelibrary.utils.ViewInject.ViewInject;
import com.corelibrary.view.layoutview.MLinearLayout;
import com.corelibrary.view.viewflow.CircleFlowIndicator;
import com.corelibrary.view.viewflow.ViewFlow;

import java.util.List;

/**
 * Created by Administrator on 2017/9/28.
 */

public class MainHeaderView extends MLinearLayout {

    @ViewInject("rl_ad")
    private RelativeLayout rlAd;
    @ViewInject("viewflow")
    private ViewFlow viewflow;
    @ViewInject("viewflowindic")
    private CircleFlowIndicator viewflowindic;

    @ViewInject("ll_first_line")
    private View vFirstLine;
    @ViewInject("ll_second_line")
    private View vSecondLine;

    @ViewInject(value = "ll_conversation_know", setClickListener = true)
    private View llConversationKnow;
    @ViewInject(value = "ll_conversation_query", setClickListener = true)
    private View getLlConversationQuery;
    @ViewInject(value = "ll_oil_change", setClickListener = true)
    private View llOilChange;
    @ViewInject(value = "ll_frame_num_query", setClickListener = true)
    private View llFrameNumQuery;
    @ViewInject(value = "ll_pro_intro", setClickListener = true)
    private View llProIntro;

    public MainHeaderView(Context context) {
        super(context);
    }

    public MainHeaderView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MainHeaderView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_main_header;
    }

    @Override
    protected void initData() {
        super.initData();
        int height = DeviceUtils.getScreenWidth() / 3;
        ViewGroup.LayoutParams params = vFirstLine.getLayoutParams();
        params.height = height;
        vFirstLine.setLayoutParams(params);

        params = vSecondLine.getLayoutParams();
        params.height = height;
        vSecondLine.setLayoutParams(params);

        int adWidth = DeviceUtils.getScreenWidth();
        int adHeight = (int) (adWidth * AdAdapter.AD_RATIO);
        params = rlAd.getLayoutParams();
        params.width = adWidth;
        params.height = adHeight;
        rlAd.setLayoutParams(params);
        getAds();

    }

    @Override
    protected void onApplyData() {

    }

    private void getAds() {
        final GetAdOperater operater = new GetAdOperater(mContext);
        operater.onReq(new BaseOperater.RspListener() {
            @Override
            public void onRsp(boolean success, Object obj) {
                if (success) {
                    List<String> adList = operater.getAdList();
                    if (adList != null && !adList.isEmpty()) {
                        viewflow.setAdapter(new AdAdapter(mContext, adList));
                        viewflow.setFlowIndicator(viewflowindic);
                        viewflow.setSideBuffer(adList.size());
                        viewflow.setSelection(adList.size() * 1000); // 设置初始位置
                        viewflow.startAutoFlowTimer();
                    }
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        int id = v.getId();
        if (id == R.id.ll_conversation_know) {
            Intent intent = new Intent(mContext, ActivityWeb.class);
            intent.putExtra(IntentCode.INTENT_WEB_URL, Config.CONVERSATION_KNOW);
            intent.putExtra(IntentCode.INTENT_WEB_TITLE, getResources().getString(R.string.conservation_knowledge));
            mContext.startActivity(intent);
        } else if (id == R.id.ll_conversation_query) {
            Intent intent = new Intent(mContext, ConversationQuerylActivity.class);
            mContext.startActivity(intent);
        } else if (id == R.id.ll_oil_change) {
            Intent intent = new Intent(mContext, ConversationQuerylActivity.class);
            intent.putExtra(IntentCode.INTENT_QUERY_FROM, ConversationQuerylActivity.TYPE_OIL);
            mContext.startActivity(intent);
        } else if (id == R.id.ll_frame_num_query) {
            Intent intent = new Intent(mContext, FrameNumQueryActivity.class);
            mContext.startActivity(intent);
        } else if (id == R.id.ll_pro_intro) {
            Intent intent = new Intent(mContext, ActivityWeb.class);
            intent.putExtra(IntentCode.INTENT_WEB_URL, Config.PROJECT_INTRO);
            intent.putExtra(IntentCode.INTENT_WEB_TITLE, getResources().getString(R.string.pro_inro));
            mContext.startActivity(intent);
        }
    }
}
