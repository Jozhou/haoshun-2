package com.goodoil.aft.view.popup;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.goodoil.aft.R;
import com.corelibrary.utils.DialogUtils;

import java.util.HashMap;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.wechat.friends.Wechat;
import cn.sharesdk.wechat.moments.WechatMoments;


/**
 * Created by Administrator on 2017/10/30.
 */

public class PopShare extends PopupWindow implements View.OnClickListener {

    private Context mContext;
    private View mContentView;
    private View vBackground;
    private View vContent;
    private TextView tvQQ;
    private TextView tvWx;
    private TextView tvFri;

    private View.OnClickListener mOneClickListener;
    private View.OnClickListener mTwoClickListener;
    private View.OnClickListener mCancelClickListener;

    public PopShare(Context context) {
        init(context);
    }

    private void init(Context context) {
        mContext = context;
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        mContentView = mInflater.inflate(R.layout.pop_share, null);

        vBackground = mContentView.findViewById(R.id.v_background);

        tvQQ = (TextView) mContentView.findViewById(R.id.tv_qq);
        tvWx = (TextView) mContentView.findViewById(R.id.tv_wx);
        tvFri = (TextView) mContentView.findViewById(R.id.tv_fri);
        vBackground = mContentView.findViewById(R.id.v_background);
        vContent = mContentView.findViewById(R.id.v_content);

        vBackground.setOnClickListener(this);
        tvQQ.setOnClickListener(this);
        tvWx.setOnClickListener(this);
        tvFri.setOnClickListener(this);

        setWindowLayoutMode(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        setOutsideTouchable(true);
        setContentView(mContentView);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.v_background) {
            dismiss();
        } else if (id == R.id.tv_qq) {
            dismiss();
            shareToQQ();
            if (mOneClickListener != null) {
                mOneClickListener.onClick(tvQQ);
            }
        } else if (id == R.id.tv_wx) {
            dismiss();
            shareToWX(Wechat.NAME);
            if (mTwoClickListener != null) {
                mTwoClickListener.onClick(tvWx);
            }
        } else if (id == R.id.tv_fri) {
            dismiss();
            shareToWX(WechatMoments.NAME);
            if (mCancelClickListener != null) {
                mCancelClickListener.onClick(tvFri);
            }
        }

    }

    public void setOneClickListener(View.OnClickListener mListener) {
        this.mOneClickListener = mListener;
    }

    public void setTwoClickListener(View.OnClickListener listener) {
        this.mTwoClickListener = listener;
    }

    public void setCancelClickListener(View.OnClickListener listener) {
        this.mCancelClickListener = listener;
    }

    private void shareToQQ() {
        Platform.ShareParams sp = new Platform.ShareParams();
        sp.setTitle("好顺");
        sp.setTitleUrl("http://www.baidu.com");// 标题的超链接
        sp.setText("一起来使用吧");
        sp.setImageUrl("http://47.92.150.165:7433/File/images/20165421020347.jpg");
//        sp.setSite("发布分享的网站名称");
//        sp.setSiteUrl("发布分享网站的地址");

        Platform qq = ShareSDK.getPlatform(QQ.NAME);

        // 设置分享事件回调（注：回调放在不能保证在主线程调用，不可以在里面直接处理UI操作）
        qq.setPlatformActionListener(new PlatformActionListener() {
            public void onError(Platform arg0, int arg1, Throwable arg2) {
                //失败的回调，arg:平台对象，arg1:表示当前的动作，arg2:异常信息
                DialogUtils.showToastMessage(R.string.share_fail);
            }

            public void onComplete(Platform arg0, int arg1, HashMap<String, Object> arg2) {
                //分享成功的回调
                DialogUtils.showToastMessage(R.string.share_succ);
            }

            public void onCancel(Platform arg0, int arg1) {
                //取消分享的回调
            }
        });
        // 执行图文分享
        qq.share(sp);

    }

    private void shareToWX(String platfrom) {
        Platform.ShareParams sp = new Platform.ShareParams();

        Bitmap logo = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.ic_launcher);
        sp.setShareType(Platform.SHARE_WEBPAGE);//分享网页一定要写
//        sp.setShareType(Platform.SHARE_IMAGE);//分享图片一定要写
        sp.setImageData(logo);
//        sp.setImagePath(""); // 三选一
//        sp.setImageUrl("http://47.92.150.165:7433/File/images/20165421020347.jpg");
        sp.setTitle("好顺");
        sp.setText("一起来使用吧");
        sp.setUrl("http://www.baidu.com");

        Platform weChat = ShareSDK.getPlatform(platfrom);

        // 设置分享事件回调（注：回调放在不能保证在主线程调用，不可以在里面直接处理UI操作）
        weChat.setPlatformActionListener(new PlatformActionListener() {
            public void onError(Platform arg0, int arg1, Throwable arg2) {
                //失败的回调，arg:平台对象，arg1:表示当前的动作，arg2:异常信息
                DialogUtils.showToastMessage(R.string.share_fail);
            }

            public void onComplete(Platform arg0, int arg1, HashMap<String, Object> arg2) {
                //分享成功的回调
                DialogUtils.showToastMessage(R.string.share_succ);
            }

            public void onCancel(Platform arg0, int arg1) {
                //取消分享的回调
            }
        });
        // 执行图文分享
        weChat.share(sp);

    }

    @Override
    public void showAtLocation(View parent, int gravity, int x, int y) {
        super.showAtLocation(parent, gravity, x, y);
        Animation animation = AnimationUtils.loadAnimation(mContext, R.anim.bottom_in);
        vContent.setAnimation(animation);

        Animation fadeAnimation = AnimationUtils.loadAnimation(mContext, R.anim.fade_in);
        vBackground.setAnimation(fadeAnimation);

        animation.start();
        fadeAnimation.start();
    }
}
