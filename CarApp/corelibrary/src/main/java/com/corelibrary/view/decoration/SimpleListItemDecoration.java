package com.corelibrary.view.decoration;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;

public class SimpleListItemDecoration extends RecyclerView.ItemDecoration {

    private static final int[] ATTRS = new int[]{ android.R.attr.listDivider };

    private Drawable mDivider;

    private final Rect mBounds = new Rect();

    public SimpleListItemDecoration(Context context) {
        final TypedArray a = context.obtainStyledAttributes(ATTRS);
        mDivider = a.getDrawable(0);
        a.recycle();
    }

    public SimpleListItemDecoration(Context context, int resId) {
        mDivider = context.getResources().getDrawable(resId);
    }

    /**
     * Sets the {@link Drawable} for this divider.
     *
     * @param drawable Drawable that should be used as a divider.
     */
    public void setDrawable(@NonNull Drawable drawable) {
        if (drawable == null) {
            throw new IllegalArgumentException("Drawable cannot be null.");
        }
        mDivider = drawable;
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        if (parent.getLayoutManager() == null) {
            return;
        }
        drawHorizontal(c, parent);
    }

    private void drawHorizontal(Canvas canvas, RecyclerView parent) {
        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View child = parent.getChildAt(i);
            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child
                    .getLayoutParams();
            parent.getLayoutManager().getDecoratedBoundsWithMargins(child, mBounds);
            final int right = parent.getRight();
            final int left = parent.getLeft();
            final int top = child.getTop() - params.topMargin - mDivider.getIntrinsicHeight();
            final int bottom = top + mDivider.getIntrinsicHeight();
            mDivider.setBounds(left, top, right, bottom);
            mDivider.draw(canvas);
        }
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent,
                               RecyclerView.State state) {
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
        } else if (isFirstRow(actualPos)) {
            outRect.set(0,0,0,0);
        } else {
            outRect.set(0, mDivider.getIntrinsicHeight(), 0, 0);
        }
    }

    protected boolean isFirstRow(int pos) {
        if (pos == 0){
            return true;
        }
        return false;
    }

    protected boolean isFixedViewType(int type) {
        return type == BaseQuickAdapter.EMPTY_VIEW ||
                type == BaseQuickAdapter.FOOTER_VIEW ||
                type == BaseQuickAdapter.LOADING_VIEW ||
                type == BaseQuickAdapter.HEADER_VIEW;
    }
}
