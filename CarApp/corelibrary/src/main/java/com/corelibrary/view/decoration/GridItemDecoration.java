package com.corelibrary.view.decoration;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;


/**
 * Grid分割线，SimpleGridItemDecoration的增强版，考虑了各个item的间隙
 */
public class GridItemDecoration extends BaseGridItemDecoration {

    protected Context mContext;
    protected int mHeight;
    protected int mWidth;
    protected Drawable mDivider;

    private int edgeColumnWidth;
    private int singleColumnWidth;
    private int doubleColumnWidth;

    public GridItemDecoration(Context context, int width, int height) {
        this.mContext = context;
        this.mHeight = height;
        this.mWidth = width;
        mDivider = context.getResources().getDrawable(android.R.color.transparent);
    }

    public GridItemDecoration(Context context, int res) {
        this.mContext = context;
        this.mDivider = context.getResources().getDrawable(res);
        this.mHeight = mDivider.getIntrinsicHeight() <= 0 ? 2 : mDivider.getIntrinsicHeight();
        this.mWidth = mDivider.getIntrinsicWidth() <= 0 ? 2 : mDivider.getIntrinsicWidth();

    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        int spanCount = getSpanCount(parent);
        int itemPosition = parent.getChildAdapterPosition(view);
        int totalWidth = parent.getWidth();
        edgeColumnWidth = (int) (totalWidth * 1f / spanCount - (totalWidth - (spanCount - 1f) * mWidth) / spanCount);
        doubleColumnWidth = mWidth - edgeColumnWidth;
        singleColumnWidth = edgeColumnWidth - doubleColumnWidth;

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
                outRect.set(0, 0, edgeColumnWidth, 0);
            } else if (isLastColum(actualPos, spanCount)) {
                outRect.set(edgeColumnWidth, 0, 0, 0);
            } else {
                if (actualPos % 2 == 0) {
                    outRect.set(singleColumnWidth, 0, doubleColumnWidth, 0);
                } else {
                    outRect.set(doubleColumnWidth, 0, singleColumnWidth, 0);
                }
            }
        } else {
            if (isFirstColum(actualPos, spanCount)) {
                outRect.set(0, mHeight, edgeColumnWidth, 0);
            } else if (isLastColum(actualPos, spanCount)) {
                outRect.set(edgeColumnWidth, mHeight, 0, 0);
            } else {
                if (actualPos % 2 == 0) {
                    outRect.set(singleColumnWidth, mHeight, doubleColumnWidth, 0);
                } else {
                    outRect.set(doubleColumnWidth, mHeight, singleColumnWidth, 0);
                }
            }
        }
    }

}