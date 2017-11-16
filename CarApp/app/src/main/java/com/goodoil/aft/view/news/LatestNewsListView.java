package com.goodoil.aft.view.news;

import android.content.Context;
import android.util.AttributeSet;
import android.util.SparseIntArray;
import android.view.View;

import com.goodoil.aft.R;
import com.goodoil.aft.models.entry.NewsEntry;
import com.goodoil.aft.models.operater.GetNewsOperater;
import com.corelibrary.models.http.IArrayOperater;
import com.corelibrary.view.adapterview.PullToRefreshMoreView;

/**
 * Created by Administrator on 2017/10/9.
 */

public class LatestNewsListView extends PullToRefreshMoreView<NewsEntry> {

    private GetNewsOperater operater;

    public LatestNewsListView(Context context) {
        super(context);
    }

    public LatestNewsListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public LatestNewsListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected IArrayOperater<NewsEntry> createMode() {
        if (operater == null) {
            operater = new GetNewsOperater(mContext);
            operater.setShowLoading(false);
        }
        return operater;
    }

    @Override
    protected SparseIntArray getItemViewTypeAndResId() {
        SparseIntArray array = new SparseIntArray();
        array.put(0, R.layout.item_latest_news);
        return array;
    }

    @Override
    protected View getLayoutItemView(int resId) {
        return new LatestNewsItemView(mContext);
    }

    @Override
    public void gotoBlank() {
        super.gotoSuccessful();
    }

    @Override
    public void gotoError() {
        super.gotoSuccessful();
    }

    @Override
    public void gotoLoading() {
        super.gotoSuccessful();
    }
}
