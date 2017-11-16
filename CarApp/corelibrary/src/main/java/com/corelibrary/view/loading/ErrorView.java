package com.corelibrary.view.loading;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import com.corelibrary.R;
import com.corelibrary.utils.ButtonUtils;
import com.corelibrary.view.layoutview.MLinearLayout;

public class ErrorView extends MLinearLayout<Void> implements IErrorView {

	private ILoadingLayout mLoadingLayout;
	
	public ErrorView(Context context) {
		super(context);
	}
	
	public ErrorView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	
	@Override
	protected int getLayoutResId() {
		return R.layout.view_loading_failed;
	}
	
	@Override
	protected void onBindListener() {
		super.onBindListener();
		setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
	            if (ButtonUtils.isFastDoubleClick()) {
                     return;
                }
				if(mLoadingLayout != null) {
					mLoadingLayout.onApplyLoadingData();
				}
			}
		});
	}

	@Override
	protected void onApplyData() {
		
	}

	@Override
	public void setRefreshLayout(ILoadingLayout layout) {
		mLoadingLayout = layout;
	}

}
