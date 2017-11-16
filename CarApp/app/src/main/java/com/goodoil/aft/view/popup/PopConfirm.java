package com.goodoil.aft.view.popup;

import android.app.Activity;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.goodoil.aft.R;

public class PopConfirm extends PopupWindow implements OnClickListener {
	private Context mContext;
	private View mContentView;
	private View vEmpty;
	private View vContent;
	private TextView tvOne;
	private TextView tvTwo;
	private TextView tvCancel;

	private OnClickListener mOneClickListener;
	private OnClickListener mTwoClickListener;
	private OnClickListener mCancelClickListener;

	private Animation fadeInAnimation;
	private Animation fadeOutAnimation;
	private Animation bottomInAnimation;
	private Animation bottomOutAnimation;

	public PopConfirm(Context context) {
		init(context);
	}

	private void init(Context context) {
		mContext = context;
		LayoutInflater mInflater = LayoutInflater.from(mContext);
		mContentView = mInflater.inflate(R.layout.pop_confirm, null);

		vEmpty = mContentView.findViewById(R.id.v_empty);

		tvOne = (TextView) mContentView.findViewById(R.id.tv_one);
		tvTwo = (TextView) mContentView.findViewById(R.id.tv_two);
		tvCancel = (TextView) mContentView.findViewById(R.id.tv_cancel);
		vContent = mContentView.findViewById(R.id.content);
		vEmpty.setOnClickListener(this);
		tvOne.setOnClickListener(this);
		tvTwo.setOnClickListener(this);
		tvCancel.setOnClickListener(this);

		initAnimation(context);

		setWindowLayoutMode(LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT);
		setOutsideTouchable(true);
		setContentView(mContentView);
	}

	private void initAnimation(Context context) {
		fadeInAnimation = AnimationUtils.loadAnimation(context, R.anim.fade_in);
		fadeOutAnimation = AnimationUtils.loadAnimation(context, R.anim.fade_out);
		bottomInAnimation = AnimationUtils.loadAnimation(context, R.anim.bottom_in);
		bottomOutAnimation = AnimationUtils.loadAnimation(context, R.anim.bottom_out);
		bottomOutAnimation.setAnimationListener(new Animation.AnimationListener() {
			@Override
			public void onAnimationStart(Animation animation) {
			}

			@Override
			public void onAnimationEnd(Animation animation) {
				dismiss();
			}

			@Override
			public void onAnimationRepeat(Animation animation) {
			}
		});
	}

	@Override
	public void onClick(View v) {
		int id = v.getId();
		if (id == R.id.v_empty) {
			dismissWindow();
		} else if (id == R.id.tv_one) {
			dismissWindow();
            if (mOneClickListener != null) {
                mOneClickListener.onClick(tvOne);
            }
		} else if (id == R.id.tv_two) {
			dismissWindow();
            if (mTwoClickListener != null) {
                mTwoClickListener.onClick(tvTwo);
            }
		} else if (id == R.id.tv_cancel) {
			dismissWindow();
            if (mCancelClickListener != null) {
                mCancelClickListener.onClick(tvCancel);
            }
		}

	}

	public void setText(CharSequence strOne, CharSequence strTwo) {
		tvOne.setText(strOne);
		tvTwo.setText(strTwo);
	}

	public void setOneClickListener(OnClickListener mListener) {
		this.mOneClickListener = mListener;
	}

	public void setTwoClickListener(View.OnClickListener listener) {
		this.mTwoClickListener = listener;
	}

	public void setCancelClickListener(OnClickListener listener) {
		this.mCancelClickListener = listener;
	}

	@Override
	public void showAtLocation(View parent, int gravity, int x, int y) {
		super.showAtLocation(parent, gravity, x, y);
		startAnimation();
	}

	private void startAnimation() {
		vContent.startAnimation(bottomInAnimation);
		vEmpty.startAnimation(fadeInAnimation);
	}

	private void exitAnimation() {
		vContent.startAnimation(bottomOutAnimation);
		vEmpty.startAnimation(fadeOutAnimation);
	}

	public void show() {
		showAtLocation(((Activity)mContext).getWindow().getDecorView(), 0, 0, Gravity.NO_GRAVITY);
	}

	public void dismissWindow() {
		exitAnimation();
	}

}
