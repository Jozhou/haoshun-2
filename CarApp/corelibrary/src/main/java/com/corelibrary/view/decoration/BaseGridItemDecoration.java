package com.corelibrary.view.decoration;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;

/**
 * Created by Administrator on 2017/9/12.
 */

public class BaseGridItemDecoration extends RecyclerView.ItemDecoration {

    protected int getSpanCount(RecyclerView parent) {
        // 列数
        int spanCount = -1;
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            spanCount = ((GridLayoutManager) layoutManager).getSpanCount();
        }
        return spanCount;
    }

    protected boolean isFirstColum(int pos, int spanCount) {
        if ((pos + 1) % spanCount == 1) {
            return true;
        }
        return false;
    }

    protected boolean isLastColum(int pos, int spanCount) {
        if ((pos + 1) % spanCount == 0) {
            return true;
        }
        return false;
    }

    protected boolean isFirstRow(int pos, int spanCount) {
        if (pos < spanCount){
            return true;
        }
        return false;
    }

    protected boolean islastRow(int pos, int spanCount, int childCount) {
        int rows = childCount % spanCount == 0? childCount / spanCount : childCount / spanCount + 1;
        int curRow = (pos + 1) % spanCount == 0? (pos + 1) / spanCount : (pos + 1) / spanCount + 1;
        if (curRow == rows){
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
