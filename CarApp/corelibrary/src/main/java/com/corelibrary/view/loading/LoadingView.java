package com.corelibrary.view.loading;

import android.content.Context;
import android.util.AttributeSet;

import com.corelibrary.R;
import com.corelibrary.view.layoutview.MRelativeLayout;

public class LoadingView extends MRelativeLayout<Void> {

	public LoadingView(Context context) {
		super(context);
	}
	
	public LoadingView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	
	@Override
	protected int getLayoutResId() {
		return R.layout.view_loading_loading;
	}

	@Override
	protected void onApplyData() {
		
	}

}
