package com.goodoil.aft.fragment;

import android.app.Activity;
import android.content.Intent;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.goodoil.aft.R;
import com.goodoil.aft.activity.ActivityWebNews;
import com.goodoil.aft.activity.CityListActivity;
import com.goodoil.aft.context.IntentCode;
import com.goodoil.aft.models.entry.CityItemEntry;
import com.goodoil.aft.models.entry.NewsEntry;
import com.goodoil.aft.view.MainHeaderView;
import com.goodoil.aft.view.news.LatestNewsListView;
import com.goodoil.aft.view.popup.PopShare;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.corelibrary.fragment.base.BaseFragment;
import com.corelibrary.utils.ButtonUtils;
import com.corelibrary.utils.ViewInject.ViewInject;
import com.corelibrary.view.decoration.SimpleListItemDecoration;

/**
 * Created by Administrator on 2017/9/28.
 */

public class MainFragment extends BaseFragment {

    @ViewInject("root")
    private View vRoot;
    @ViewInject("lv_news")
    private LatestNewsListView lvNews;
    @ViewInject(value = "titlebar_lefttext", setClickListener = true)
    private TextView tvLeft;
    @ViewInject(value = "titlebar_righticon", setClickListener = true)
    private ImageView ivRight;

    private MainHeaderView mainHeaderView;

    private PopShare popShare;

    private static final int SEL_CITY = 1001;

    @Override
    protected int getContentResId() {
        return R.layout.fragment_main;
    }

    @Override
    protected void onBindListener() {
        super.onBindListener();
        lvNews.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                NewsEntry entry = (NewsEntry) adapter.getData().get(position);
                Intent intent = new Intent(mContext, ActivityWebNews.class);
                intent.putExtra(IntentCode.INTENT_WEB_URL, entry.url);
                intent.putExtra(IntentCode.INTENT_NEWS_ITEM, entry);
                intent.putExtra(IntentCode.INTENT_WEB_TITLE, getString(R.string.news_detail));
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onApplyData() {
        super.onApplyData();
        mainHeaderView = new MainHeaderView(mContext);
        lvNews.addHeaderView(mainHeaderView);
        lvNews.addItemDecoration(new SimpleListItemDecoration(mContext, R.drawable.divider_latest_news));
        lvNews.refresh();

        popShare = new PopShare(mContext);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        if (ButtonUtils.isFastDoubleClick()) {
            return;
        }
        int id = v.getId();
        if (id == R.id.titlebar_lefttext) {
            Intent intent = new Intent(mContext, CityListActivity.class);
            startActivityForResult(intent, SEL_CITY);
        } else if (id == R.id.titlebar_righticon) {
            popShare.showAtLocation(vRoot, Gravity.BOTTOM, 0, 0);

        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SEL_CITY) {
            if (resultCode == Activity.RESULT_OK) {
                CityItemEntry entry = (CityItemEntry) data.getSerializableExtra(IntentCode.INTENT_CITY_ITEM);
                tvLeft.setText(entry.province_name);
            }
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (popShare != null && popShare.isShowing()) {
                popShare.dismiss();
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }
}
