package com.goodoil.aft.view.popup;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.PopupWindow;

import com.bumptech.glide.Glide;
import com.goodoil.aft.R;
import com.corelibrary.utils.DeviceUtils;

public class PopShowPic extends PopupWindow implements OnClickListener {
	private Context mContext;
	private View mContentView;
	private ImageView ivPic;
	private View vContent;

	public PopShowPic(Context context) {
		init(context);
	}

	private void init(Context context) {
		mContext = context;
		LayoutInflater mInflater = LayoutInflater.from(mContext);
		mContentView = mInflater.inflate(R.layout.pop_oil_image, null);
		vContent = mContentView.findViewById(R.id.v_content);
		ivPic = (ImageView) mContentView.findViewById(R.id.iv_pic);

		vContent.setOnClickListener(this);
		ivPic.setClickable(false);

		setWindowLayoutMode(LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT);
		setOutsideTouchable(true);
		setAnimationStyle(R.style.pop_show_pic);
		setContentView(mContentView);

		LayoutParams params = ivPic.getLayoutParams();
		params.width = DeviceUtils.getScreenWidth() * 3/4;
		params.height = DeviceUtils.getScreenHeight()*3/4;
		ivPic.setLayoutParams(params);
	}

	public void setData(String url) {
		Glide.with(mContext).load(url).dontAnimate()
				.placeholder(R.drawable.placeholder_square)
				.error(R.drawable.placeholder_square)
				.into(ivPic);

		showAtLocation(((FragmentActivity)mContext).getWindow().getDecorView(), Gravity.CENTER, 0, 0);
	}

	@Override
	public void onClick(View v) {
		int id = v.getId();
		if (id == R.id.v_content) {
			dismiss();
		}
	}

}
