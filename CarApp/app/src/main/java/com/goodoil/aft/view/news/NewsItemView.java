package com.goodoil.aft.view.news;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.goodoil.aft.R;
import com.goodoil.aft.models.entry.NewsEntry;
import com.corelibrary.utils.ViewInject.ViewInject;
import com.corelibrary.view.layoutview.MRelativeLayout;

/**
 * Created by Administrator on 2017/10/13.
 */
public class NewsItemView extends MRelativeLayout<NewsEntry> {

    @ViewInject("iv_news")
    private ImageView ivNews;
    @ViewInject("tv_title")
    private TextView tvTitle;
    @ViewInject("tv_time")
    private TextView tvTime;
    @ViewInject("tv_praise")
    private TextView tvPraise;

    public NewsItemView(Context context) {
        super(context);
    }

    public NewsItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public NewsItemView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.item_news;
    }

    @Override
    protected void onApplyData() {
        Glide.with(mContext).load(mDataItem.imgurl).into(ivNews);
        tvTitle.setText(mDataItem.title);
        tvTime.setText(mDataItem.create_date);
        tvPraise.setText(mDataItem.clickamount + "");
    }
}
