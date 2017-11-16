package com.corelibrary.view.decoration;


import android.content.Context;
import android.graphics.Canvas;
import android.support.v7.widget.RecyclerView;
import android.view.View;


/**
 * Grid分割线，同GridItemDecoration，区别在于可设置分割线颜色及形状
 */
public class GridItemDecorationWithColor extends GridItemDecoration {

    public GridItemDecorationWithColor(Context context, int res) {
        super(context, res);
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        drawHorizontal(c, parent);
        drawVertical(c, parent);
    }

    public void drawHorizontal(Canvas c, RecyclerView parent) {
        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View child = parent.getChildAt(i);
            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child
                    .getLayoutParams();
            final int left = child.getLeft() - params.leftMargin - mWidth;
            final int right = child.getRight() + params.rightMargin
                    + mWidth;
            final int top = child.getTop() - params.topMargin - mHeight;
            final int bottom = top + mHeight;
            mDivider.setBounds(left, top, right, bottom);
            mDivider.draw(c);
        }
    }

    public void drawVertical(Canvas c, RecyclerView parent) {
        final int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View child = parent.getChildAt(i);
            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child
                    .getLayoutParams();
            int top = child.getTop() - params.topMargin - mHeight;
            int bottom = child.getBottom() + params.bottomMargin;
            int left = child.getLeft() - params.leftMargin - mWidth;
            int right = left + mWidth;
            mDivider.setBounds(left, top, right, bottom);
            mDivider.draw(c);

            left = child.getRight() + params.rightMargin;
            right = left + mWidth;
            mDivider.setBounds(left, top, right, bottom);
            mDivider.draw(c);
        }
    }

}