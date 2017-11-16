//package com.corelibrary.view.adapterview.refresh;
//
//import android.content.Context;
//import android.util.AttributeSet;
//import android.util.Log;
//import android.view.Gravity;
//import android.view.animation.Animation;
//import android.view.animation.RotateAnimation;
//import android.widget.ImageView;
//import android.widget.LinearLayout;
//import android.widget.ProgressBar;
//import android.widget.TextView;
//
//import com.ajguan.library.IRefreshHeader;
//import com.ajguan.library.State;
//import com.qinhe.commonlibrary.R;
//
//import java.text.SimpleDateFormat;
//import java.util.Date;
//
//public class RefreshHeaderView extends LinearLayout implements IRefreshHeader {
//
//    private ImageView mArrowImageView;
//    private ProgressBar mProgressBar;
//    private TextView                 mHintTextView;
//    private TextView 				 mLastUpdatedTextView;
//    private Animation                mRotateUpAnim;
//    private Animation                mRotateDownAnim;
//
//    private final static int         ROTATE_ANIM_DURATION       = 180;
//
//    public RefreshHeaderView(Context context) {
//        this(context, null);
//    }
//
//    public RefreshHeaderView(Context context, AttributeSet attrs) {
//        super(context, attrs);
//
//        inflate(context, R.layout.layout_refresh_more_header, this);
//        setGravity(Gravity.BOTTOM);
//
//        mArrowImageView = (ImageView)findViewById(com.qinhe.corelibrary.R.id.iv_refresh_more_header_arrow);
//        mHintTextView = (TextView)findViewById(com.qinhe.corelibrary.R.id.tv_refresh_more_header_text_hint);
//        mProgressBar = (ProgressBar)findViewById(com.qinhe.corelibrary.R.id.pb_refresh_more_header_loading);
//        mLastUpdatedTextView = (TextView) findViewById(com.qinhe.corelibrary.R.id.tv_refresh_more_header_lastupdatetime);
//
//        mRotateUpAnim = new RotateAnimation(0.0f, -180.0f,Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,0.5f);
//        mRotateUpAnim.setDuration(ROTATE_ANIM_DURATION);
//        mRotateUpAnim.setFillAfter(true);
//        mRotateDownAnim = new RotateAnimation(-180.0f, 0.0f,Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,0.5f);
//        mRotateDownAnim.setDuration(ROTATE_ANIM_DURATION);
//        mRotateDownAnim.setFillAfter(true);
//    }
//
//    @Override
//    public void reset() {
//        mHintTextView.setText("下拉可刷新");
//        mArrowImageView.clearAnimation();
//        mArrowImageView.setVisibility(VISIBLE);
//        mProgressBar.setVisibility(INVISIBLE);
//    }
//
//    @Override
//    public void pull() {
//
//    }
//
//    @Override
//    public void refreshing() {
//        mArrowImageView.setVisibility(INVISIBLE);
//        mProgressBar.setVisibility(VISIBLE);
//        mHintTextView.setText("正在刷新...");
//        mArrowImageView.clearAnimation();
//    }
//
//    @Override
//    public void onPositionChange(float currentPos, float lastPos, float refreshPos, boolean isTouch, State state) {
//        // 往上拉
//        if (currentPos < refreshPos && lastPos >= refreshPos) {
//            Log.i("", ">>>>up");
//            if (isTouch && state == State.PULL) {
//                mArrowImageView.clearAnimation();
//                mArrowImageView.startAnimation(mRotateDownAnim);
//                mHintTextView.setText("下拉可刷新");
//            }
//            // 往下拉
//        } else if (currentPos > refreshPos && lastPos <= refreshPos) {
//            Log.i("", ">>>>down");
//            if (isTouch && state == State.PULL) {
//                mArrowImageView.clearAnimation();
//                mArrowImageView.startAnimation(mRotateUpAnim);
//                mHintTextView.setText("松开即刷新");
//            }
//        }
//    }
//
//    @Override
//    public void complete() {
//        mLastUpdatedTextView.setText(getContext().getResources().getString(com.qinhe.corelibrary.R.string.pull_to_refresh_update_date) + setDateStyle());
//    }
//
//    private String setDateStyle(){
//        SimpleDateFormat format = new SimpleDateFormat("yy-M-dd HH:mm:ss");
//        Date date = new Date();
//        return format.format(date);
//    }
//}
