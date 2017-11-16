package com.corelibrary.view.decoration;


import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;


/**
 * Grid分割线，分割线背景为Recyclerview的背景色
 * 该类做了简化处理，适用于特殊情况：当分割线较细的情况（若是分割区域较大，则view的宽高可能不一致，应该使用GridItemDecoration）
 */
public class SimpleGridItemDecoration extends BaseGridItemDecoration {

    protected Context mContext;
    protected int mHeight;
    protected int mWidth;
    protected Drawable mDivider;

    public SimpleGridItemDecoration(Context context, int width, int height) {
        this.mContext = context;
        this.mHeight = height;
        this.mWidth = width / 2;
        mDivider = context.getResources().getDrawable(android.R.color.transparent);
    }

    public SimpleGridItemDecoration(Context context, int res) {
        this.mContext = context;
        this.mDivider = context.getResources().getDrawable(res);
        this.mHeight = mDivider.getIntrinsicHeight() <= 0 ? 2 : mDivider.getIntrinsicHeight();
        this.mWidth = mDivider.getIntrinsicWidth() <= 0 ? 1 : mDivider.getIntrinsicWidth() / 2;

    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        int spanCount = getSpanCount(parent);
        int itemPosition = parent.getChildAdapterPosition(view);

        boolean isSpec = false;
        int actualPos = itemPosition;
        if (parent.getAdapter() instanceof  BaseQuickAdapter) {
            BaseQuickAdapter adapter = (BaseQuickAdapter) parent.getAdapter();
            int type = adapter.getItemViewType(itemPosition);
            isSpec = isFixedViewType(type);
            actualPos = itemPosition - adapter.getHeaderLayoutCount();
        }
        if (isSpec) {
            outRect.set(0,0,0,0);
        } else if (isFirstRow(actualPos, spanCount)) {
            if (isFirstColum(actualPos, spanCount)) {
                outRect.set(0, 0, mWidth, 0);
            } else if (isLastColum(actualPos, spanCount)) {
                outRect.set(mWidth, 0, 0, 0);
            } else {
                outRect.set(mWidth, 0, mWidth, 0);
            }
        } else {
            if (isFirstColum(actualPos, spanCount)) {
                outRect.set(0, mHeight, mWidth, 0);
            } else if (isLastColum(actualPos, spanCount)) {
                outRect.set(mWidth, mHeight, 0, 0);
            } else {
                outRect.set(mWidth, mHeight, mWidth, 0);
            }
        }
    }

}