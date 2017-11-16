package com.corelibrary.view.adapterview;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.SparseIntArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.ajguan.library.EasyRefreshLayout;
import com.ajguan.library.LoadModel;
import com.ajguan.library.view.SimpleRefreshHeaderView;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.chad.library.adapter.base.loadmore.LoadMoreView;
import com.corelibrary.R;
import com.corelibrary.models.entry.ArrayEntry;
import com.corelibrary.models.http.BaseOperater;
import com.corelibrary.models.http.IArrayOperater;
import com.corelibrary.utils.DialogUtils;
import com.corelibrary.utils.NetworkUtils;
import com.corelibrary.utils.ViewInject.ViewInject;
import com.corelibrary.view.layoutview.ILayoutView;
import com.corelibrary.view.loading.LoadingLayout;
import com.corelibrary.models.http.BaseOperater.RspListener;
import com.corelibrary.models.http.IArrayOperater.PageType;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by Administrator on 2017/7/3.
 */

public abstract class PullToRefreshMoreView<T extends MultiItemEntity> extends LoadingLayout {

    @ViewInject("swipe_layout")
    private EasyRefreshLayout mSwipLayout;
    @ViewInject("rv_pull_list")
    protected RecyclerView mRvList;
    @ViewInject("rl_list")
    private RelativeLayout rlList;

    protected IArrayOperater<T> mModel;
    protected ArrayEntry<T> mModelData;
    private BaseQuickAdapter.RequestLoadMoreListener loadMoreListener;
    private boolean mEnableRefresh = true;
    private boolean mEnableLoadMore = true;
    private SparseIntArray mLayouts;
    // 去重（或其他操作）接口
    private OnFilterListener mOnFilterListener;

    // 因为模板原因，此处用于头部刷新
    private OnRefreshHeadListener onRefreshHeadListener;

    private OnLoadDataListener onLoadDataListener;

    /**
     * 获取BaseListModel返回的数据实体
     * @return
     */
    public ArrayEntry<T> getModelData() {
        return mModelData;
    }

    private PullToRefreshMoreMultiAdapter mAdapter;

    protected RspListener mCallback = new RspListener() {
        @Override
        public void onRsp(boolean success, Object obj) {
            if (success) {
                mModelData = mModel.getDataEntry();
                if(mModelData != null && mModelData.getArray() != null) {
                    if(mModelData.getArray().size() < 1) {
                        onLoadBlank(getPageType());
                    } else {
                        onLoadSucc(getPageType());
                    }
                } else {
                    onLoadError(getPageType());
                }
            } else {
                onLoadError(getPageType());
            }
        }
    };

    public PullToRefreshMoreView(Context context) {
        super(context);
    }

    public PullToRefreshMoreView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PullToRefreshMoreView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.view_pulltorefresh_more;
    }

    @Override
    protected void onBindListener() {
        super.onBindListener();
        mSwipLayout.addEasyEvent(new EasyRefreshLayout.EasyEvent() {
            @Override
            public void onLoadMore() {

            }

            @Override
            public void onRefreshing() {
                getFirstPage(false, false, true);
            }
        });

        loadMoreListener = new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                getNextPage();
            }
        };
    }

    @Override
    protected void onApplyData() {
        super.onApplyData();
        mLayouts = getItemViewTypeAndResId();
        mSwipLayout.setLoadMoreModel(LoadModel.NONE);
        mSwipLayout.setEnablePullToRefresh(mEnableRefresh);
        mSwipLayout.setRefreshHeadView(getRefreshHeaderView());
        mSwipLayout.setHideLoadViewAnimatorDuration(100);
        mAdapter = new PullToRefreshMoreMultiAdapter(null);
        mAdapter.setHeaderAndEmpty(true);
        mRvList.setLayoutManager(new LinearLayoutManager(mContext));
        mRvList.setAdapter(mAdapter);
        mAdapter.setOnLoadMoreListener(loadMoreListener, mRvList);
        mAdapter.setEnableLoadMore(mEnableLoadMore);
        if (getLoadMoreView() != null) {
            mAdapter.setLoadMoreView(getLoadMoreView());
        }
    }

    class PullToRefreshMoreMultiAdapter extends BaseMultiItemQuickAdapter<T, BaseViewHolder> {

        public PullToRefreshMoreMultiAdapter(List<T> data) {
            super(data);
            int size = mLayouts.size();
            for (int i = 0; i < size; i ++) {
                addItemType(mLayouts.keyAt(i), mLayouts.valueAt(i));
            }
        }

        @Override
        protected void convert(BaseViewHolder helper, T item) {
            PullToRefreshMoreView.this.convert(helper, item);
        }

        @Override
        protected View getItemView(@LayoutRes int layoutResId, ViewGroup parent) {
            int index = mLayouts.indexOfValue(layoutResId);
            if (index == -1) {
                return super.getItemView(layoutResId, parent);
            } else {
                return getLayoutItemView(layoutResId);
            }
        }

        public void setLayoutManager(RecyclerView.LayoutManager manager) {
            if (manager instanceof GridLayoutManager) {
                // 此处或许需要优化，设定不给SpanSizeLookup
                final GridLayoutManager gridManager = ((GridLayoutManager) manager);
                gridManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                    @Override
                    public int getSpanSize(int position) {
                        int type = getItemViewType(position);
                        if (type == HEADER_VIEW && isHeaderViewAsFlow()) {
                            return 1;
                        }
                        if (type == FOOTER_VIEW && isFooterViewAsFlow()) {
                            return 1;
                        }
                        return isFixedViewType(type) ? gridManager.getSpanCount() : 1;
                    }


                });
            }
        }

    }

    public void refresh() {
        getFirstPage(true);
    }

    /**
     * 刷新时，若需要显示刷新header，则调用此方法
     */
    public void autoRefresh() {
        mSwipLayout.autoRefresh(0);
    }

    /**
     * 请求第一页数据
     */
    public void getFirstPage() {
        getFirstPage(true);
    }

    /**
     * 请求第一页数据
     * @param loadcache 是否加载缓存
     */
    public void getFirstPage(boolean loadcache) {
        getFirstPage(loadcache, false, false);
    }

    public void getFirstPage(boolean loadcache, boolean isShowLoading) {
        getFirstPage(loadcache, isShowLoading, false);
    }

    /**
     * 请求第一页数据
     * @param loadcache 是否加载缓存
     */
    public void getFirstPage(boolean loadcache, boolean isShowLoading, boolean isRefreshHead) {
        if(mModel == null) {
            mModel = createMode();
        }
        if(mModel == null) {
            throw new RuntimeException("mModel is null");
        }
        boolean hasData = getAdapterCount() > 1;
        boolean hasCache = mModel.getFirstPage(loadcache && !hasData, mCallback);
        onBeginLoad(true, isShowLoading && !hasCache, isRefreshHead);
    }

    /**
     * 开始加载数据
     * @param isFirstPage 是否请求的第一页数据
     */
    protected void onBeginLoad(boolean isFirstPage, boolean isShowLoading, boolean isRefreshHead) {
        if(isFirstPage) {
            if (mEnableRefresh) {
                mAdapter.setEnableLoadMore(false);
            }
            if (isShowLoading) {
                gotoLoading();
            }
            if (isRefreshHead && onRefreshHeadListener != null) {
                onRefreshHeadListener.onRefreshHead();
            }
        } else {
            if (mEnableLoadMore) {
                mSwipLayout.setEnablePullToRefresh(false);            }
        }
    }

    /**
     * 取得Model
     * @return
     */
    public IArrayOperater<T> getModel() {
        return mModel;
    }

    /**
     * 生成Model
     * @return
     */
    protected abstract IArrayOperater<T> createMode();

    public void getNextPage() {
        if (isLastPage()) {
            return;
        }
        onBeginLoad(false, false, false);
        if(mModel == null) {
            mModel = createMode();
        }
        if(mModel == null) {
            throw new RuntimeException("mModel is null");
        }
        mModel.getNextPage(mCallback);
    }

    protected void setParams(LinkedHashMap params) {

    }

    /**
     * 设置数据源
     * @param data
     */
    public void setDataSource(ArrayList<T> data) {
        mAdapter.setNewData(data);
    }

    /**
     * 取得数据源
     * @return
     */
    public List<T> getDataSource() {
        if(mAdapter != null)
            return mAdapter.getData();
        else
            return null;
    }

    /**
     *数据请求完成
     * @param pagetype
     */
    protected void onLoadSucc(PageType pagetype) {
        ArrayList<T> data = mModelData.getArray();
        if(pagetype == PageType.CachePage || pagetype == PageType.FirstPage) {
            if (mOnFilterListener != null) {
                mAdapter.setNewData(mOnFilterListener.onFilterFirst(data));
            } else {
                mAdapter.setNewData(data);
            }
        } else {
            if (mOnFilterListener != null) {
                mAdapter.addData(mOnFilterListener.onFilter(mAdapter.getData(), data));
            } else {
                mAdapter.addData(data);
            }
        }
        boolean isLastPage  = mModelData.isLastPage();
        if(pagetype != PageType.CachePage) {
            onEndLoad(isLastPage, pagetype, true);
        }
        gotoSuccessful();
        if (onLoadDataListener != null) {
            onLoadDataListener.onLoadSucc(pagetype);
        }
    }

    /**
     * 数据请求完成（空数据）
     * @param pagetype
     */
    protected void onLoadBlank(IArrayOperater.PageType pagetype) {
        if(pagetype != PageType.CachePage) {
            onEndLoad(true, pagetype, true);
        }
        if(pagetype == PageType.FirstPage) {
            setDataSource(new ArrayList<T>());
        }
        if(pagetype == PageType.FirstPage && getAdapterCount() < 1) {
            gotoBlank();
        }
        if (onLoadDataListener != null) {
            onLoadDataListener.onLoadBlank(pagetype);
        }
    }

    /**
     * 数据请求出错
     * @param pagetype
     */
    protected void onLoadError(PageType pagetype) {
        if(pagetype != PageType.CachePage){
            boolean isLastPage  = false;
            try {
                isLastPage = mModelData.isLastPage();
            } catch (Exception e) {
                isLastPage = false;
            }
            onEndLoad(isLastPage, pagetype, false);
        }

        if(pagetype == PageType.FirstPage && getAdapterCount() < 1) {
            gotoError();
        } else {
            if(NetworkUtils.isNetWorkConnected()) {
                int resid = getLoadErrResId();
                if(resid > 0) {
                    DialogUtils.showToastMessage(resid);
                }
            }
        }
        if (onLoadDataListener != null) {
            onLoadDataListener.onLoadError(pagetype);
        }
    }

    /**
     * 结束加载数据
     * @param isLastPage 是否是最后一页数据
     */
    protected void onEndLoad(boolean isLastPage, PageType pageType, boolean success) {
        if (mEnableRefresh) {
            mSwipLayout.setEnablePullToRefresh(true);
            mSwipLayout.refreshComplete();
        }
        if (pageType == PageType.NextPage) {
            if (success) {
                if(isLastPage) {
                    mAdapter.loadMoreEnd();
                } else {
                    mAdapter.loadMoreComplete();
                }
            } else {
                mAdapter.loadMoreFail();
            }
        } else if (pageType == PageType.FirstPage) {
            if (success) {
                if(isLastPage) {
                    mAdapter.loadMoreEnd();
                }
            }
        }
    }

    @Override
    public void onApplyLoadingData() {
        super.onApplyLoadingData();
        getFirstPage(false, true, true);
    }

    protected int getAdapterCount() {
        return mAdapter.getData() == null? 0 : mAdapter.getData().size();
    }

    /**
     * 请求第一页数据返回后是否滑动至顶部或底部
     * @return
     */
    protected boolean scrollToEdgeOnGetFirstPage() {
        return true;
    }

    /**
     * 滑动到顶部
     */
    public void scrollToTop() {
        // TODO
    }

    protected int getLoadErrResId() {
        return 0;
    }

    /**
     * 设置是否可以下拉刷新
     * @return
     */
    public void enableRefresh(boolean enable) {
        mEnableRefresh = enable;
        mSwipLayout.setEnablePullToRefresh(mEnableRefresh);
    }

    /**
     * 设置是否可以加载更多
     * @return
     */
    public void enableLoadMore(boolean enable) {
        mEnableLoadMore = enable;
        mAdapter.setOnLoadMoreListener(enable? loadMoreListener : null, mRvList);
        mAdapter.setEnableLoadMore(mEnableLoadMore);
    }

    protected abstract SparseIntArray getItemViewTypeAndResId();

    /**
     * 根据resId获取对应的itemView
     * @param resId
     * @return
     */
    protected abstract View getLayoutItemView(int resId);

    protected void convert(BaseViewHolder holder, T item) {
        ((ILayoutView)holder.itemView).setDataSource(item);
    }

    /**
     * 设置刷新的headerview
     * @return
     */
    protected View getRefreshHeaderView() {
        return new SimpleRefreshHeaderView(mContext);
    }

    /**
     * 设置加载更多的footerview
     * @return
     */
    protected LoadMoreView getLoadMoreView() {
        return null;
    }

    /** 有关Recyclerview的方法 start **/
    public void addItemDecoration(RecyclerView.ItemDecoration decor, int index) {
        mRvList.addItemDecoration(decor, index);
    }

    public void addItemDecoration(RecyclerView.ItemDecoration decor) {
        mRvList.addItemDecoration(decor);
    }

    public void setLayoutManager(RecyclerView.LayoutManager layout) {
        mRvList.setLayoutManager(layout);
        mAdapter.setLayoutManager(layout);
    }

    public void addHeaderView(View v) {
        if (mAdapter != null) {
            mAdapter.addHeaderView(v);
        }
    }

    public void notifyDataSetChanged() {
        if (mAdapter != null) {
            mAdapter.notifyDataSetChanged();
        }
    }

    public void notifyItemChanged(int pos) {
        if (mAdapter != null) {
            mAdapter.notifyItemChanged(pos);
        }
    }

    public void removeHeaderView(View v) {
        if (mAdapter != null) {
            mAdapter.removeHeaderView(v);
        }
    }

    public void addHeaderView(View v, int index) {
        if (mAdapter != null) {
            mAdapter.addHeaderView(v, index);
        }
    }

    public void setItemAnimator(RecyclerView.ItemAnimator itemAnimator) {
        mRvList.setItemAnimator(itemAnimator);
    }

    public BaseQuickAdapter getAdapter() {
        return this.mAdapter;
    }

    public void smoothScrollToPosition(int position) {
        mRvList.smoothScrollToPosition(position);
    }
    /** 有关Recyclerview的方法 end **/


    /** 有关Adapter的方法 start **/

    public void setEmptyView(View v) {
        mAdapter.setEmptyView(v);
    }

    public void isUseEmpty(boolean isUseEmpty) {
        mAdapter.isUseEmpty(isUseEmpty);
    }

    public void setOnItemChildClickListener(BaseQuickAdapter.OnItemChildClickListener listener) {
        mAdapter.setOnItemChildClickListener(listener);
    }

    public void setOnItemClickListener(BaseQuickAdapter.OnItemClickListener listener) {
        mAdapter.setOnItemClickListener(listener);
    }

    /** 有关Adapter的方法 end **/

    public void setOnFilterListener(OnFilterListener onFilterListener) {
        this.mOnFilterListener = onFilterListener;
    }

    /**
     * 该接口用于对获取到的数据进行去重等操作
     * @param <T>
     */
    public static abstract class OnFilterListener<T extends MultiItemEntity> {
        /**
         *
         * @param originData originData为源数据，不可对其进行增删操作
         * @param addData 需要添加的数据
         * @return
         */
        public abstract List<T> onFilter(List<T> originData, List<T> addData);

        /**
         * 首次加载时对数据进行处理
         * @param addData
         * @return
         */
        public List<T> onFilterFirst(List<T> addData) {
            return addData;
        }
    }

    public void setOnRefreshHeadListener(OnRefreshHeadListener listener) {
        this.onRefreshHeadListener = listener;
    }

    public interface OnRefreshHeadListener {
        void onRefreshHead();
    }

    public void forbidRefresh() {
        mSwipLayout.setVisibility(View.GONE);
        mSwipLayout.removeAllViews();
        rlList.addView(mRvList, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
    }

    public RecyclerView getRecyclerView() {
        return mRvList;
    }

    private boolean isLastPage() {
        boolean isLastPage = false;
        if (mModelData != null) {
            isLastPage  = mModelData.isLastPage();
        }
        return isLastPage;
    }

    public void setOnLoadDataListener(OnLoadDataListener listener) {
        this.onLoadDataListener = listener;
    }

    public interface OnLoadDataListener {
        void onLoadSucc(PageType pageType);
        void onLoadError(PageType pageType);
        void onLoadBlank(PageType pageType);
    }

}
