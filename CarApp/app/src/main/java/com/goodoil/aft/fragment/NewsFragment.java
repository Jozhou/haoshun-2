package com.goodoil.aft.fragment;

import android.content.Intent;
import android.view.View;

import com.goodoil.aft.R;
import com.goodoil.aft.activity.ActivityWebNews;
import com.goodoil.aft.context.IntentCode;
import com.goodoil.aft.models.entry.NewsEntry;
import com.goodoil.aft.view.news.NewsListView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.corelibrary.fragment.base.BaseFragment;
import com.corelibrary.utils.ViewInject.ViewInject;

/**
 * Created by Administrator on 2017/9/28.
 */

public class NewsFragment extends BaseFragment {

    @ViewInject("rv_news")
    private NewsListView rvNews;

    @Override
    protected int getContentResId() {
        return R.layout.fragment_news;
    }

    @Override
    protected void onBindListener() {
        super.onBindListener();
        rvNews.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
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
        rvNews.getFirstPage(false, true);
    }
}
