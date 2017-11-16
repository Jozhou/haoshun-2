package com.corelibrary.view.layoutview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

import com.corelibrary.utils.ViewInject.ViewInjectUtils;

public abstract class MRelativeLayout<T> extends RelativeLayout implements ILayoutView<T>, View.OnClickListener {

	protected T mDataItem;
	protected Context mContext;
	protected int mPosition = 0;
	protected int mTotal = 0;

	public MRelativeLayout(Context context) {
		super(context);
		initializeLayout(context);
	}

	public MRelativeLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		initializeLayout(context);
	}

	public MRelativeLayout(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initializeLayout(context);
	}

	protected void initializeLayout(Context context) {
		mContext = context;
		seContentView();
		onInjectView();
		onFindView();
		onBindListener();
		initData();
	}

	/**
	 * 设置ContentView
	 */
	protected void seContentView() {
		int resId = getLayoutResId();
		if(resId != 0) {
			LayoutInflater.from(mContext).inflate(resId, this, true);
		}
	}

	/**
	 * 设置view在listview的位置
	 * @param position
	 * @param total
	 */
	public void setPosition(int position, int total) {
		mPosition = position;
		mTotal = total;
	}

	/**
	 * 是否为listview的第一条
	 * @return
	 */
	public boolean isFirst() {
		return mPosition == 0;
	}

	/**
	 * 是否为listview的最后一条
	 * @return
	 */
	public boolean isLast() {
		return mPosition == mTotal - 1;
	}

	/**
	 * 设置数据源进行渲染
	 * @param t
	 */
	public void setDataSource(T t) {
		mDataItem = t;
		onApplyData();
	}

	/**
	 * 刷新数据
	 */
	public void notifyDataSetChanged() {
		onApplyData();
	}

	/**
	 * 取得数据源
	 * @return
	 */
	public T getDataSource() {
		return mDataItem;
	}

	/**
	 * 获取布局文件resid
	 * @return
	 */
	protected int getLayoutResId() {
		return 0;
	}

	/**
	 * 初始化控件、获取内部控件（注释类遍历）
	 */
	protected void onInjectView() {
		ViewInjectUtils.onInjectView(this);
	}

	/**
	 * 初始化控件、获取内部控件
	 */
	protected void onFindView() {

	}

	/**
	 * 设置事件
	 */
	protected void onBindListener() {

	}

	/**
	 * 渲染控件数据
	 */
	protected abstract void onApplyData();

	/**
	 * 数据初始化
	 */
	protected void initData() {

	}

	@Override
	public void onClick(View v) {

	}
}