package com.goodoil.aft.view.news;

import android.content.Context;
import android.util.AttributeSet;
import android.util.SparseIntArray;
import android.view.View;

import com.goodoil.aft.R;
import com.goodoil.aft.models.entry.NewsEntry;
import com.goodoil.aft.models.operater.GetMyNewsOperater;
import com.corelibrary.models.http.IArrayOperater;
import com.corelibrary.view.adapterview.PullToRefreshMoreView;

/**
 * Created by Administrator on 2017/10/9.
 */

public class MyNewsListView extends PullToRefreshMoreView<NewsEntry> {

    private GetMyNewsOperater operater;

    public MyNewsListView(Context context) {
        super(context);
    }

    public MyNewsListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyNewsListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected IArrayOperater<NewsEntry> createMode() {
        if (operater == null) {
            operater = new GetMyNewsOperater(mContext);
            operater.setShowLoading(false);
        }
        return operater;
    }

    @Override
    protected SparseIntArray getItemViewTypeAndResId() {
        SparseIntArray array = new SparseIntArray();
        array.put(0, R.layout.item_news);
        return array;
    }

    @Override
    protected View getLayoutItemView(int resId) {
        return new NewsItemView(mContext);
    }

}
