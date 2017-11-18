package com.goodoil.aft.fragment;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.corelibrary.models.http.BaseOperater;
import com.goodoil.aft.R;
import com.goodoil.aft.activity.CollectActivity;
import com.goodoil.aft.activity.PersonalInfoActivity;
import com.goodoil.aft.activity.SettingsActivity;
import com.goodoil.aft.common.data.Account;
import com.corelibrary.fragment.base.BaseFragment;
import com.corelibrary.utils.ViewInject.ViewInject;
import com.corelibrary.view.CircleImageView;
import com.goodoil.aft.models.operater.GetQrcodeOperater;

/**
 * Created by Administrator on 2017/9/28.
 */

public class MineFragment extends BaseFragment {

    @ViewInject(value = "ll_head", setClickListener = true)
    private View vHead;
    @ViewInject("tv_name")
    private TextView tvName;
    @ViewInject("tv_tel")
    private TextView tvTel;
    @ViewInject("iv_head")
    private CircleImageView ivHead;

    @ViewInject(value = "ll_conversation_query", setClickListener = true)
    private View vConversation;
    @ViewInject(value = "ll_my_coll", setClickListener = true)
    private View vColl;
    @ViewInject(value = "ll_settings", setClickListener = true)
    private View vSettings;

    @ViewInject(value = "iv_qrcode")
    private ImageView ivQrcode;

    @Override
    protected int getContentResId() {
        return R.layout.fragment_mine;
    }

    @Override
    protected void onApplyData() {
        super.onApplyData();

        getQucode();
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        int id = v.getId();
        if (id == R.id.ll_head) {
            Intent intent = new Intent(mContext, PersonalInfoActivity.class);
            mContext.startActivity(intent);
        } else if (id == R.id.ll_settings) {
            Intent intent = new Intent(mContext, SettingsActivity.class);
            startActivity(intent);
        } else if (id == R.id.ll_my_coll) {
            Intent intent = new Intent(mContext, CollectActivity.class);
            startActivity(intent);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        refreshHead();
    }

    private void refreshHead() {
        tvName.setText(Account.get().nickname);
        tvTel.setText(Account.get().tel);
        Glide.with(mContext).load(Account.get().imageurl)
                .placeholder(R.drawable.head_icon)
                .error(R.drawable.head_icon)
                .dontAnimate()
                .into(ivHead);
    }

    private void getQucode() {
        final GetQrcodeOperater operater = new GetQrcodeOperater(mContext);
        operater.setShowLoading(false);
        operater.onReq(new BaseOperater.RspListener() {
            @Override
            public void onRsp(boolean success, Object obj) {
                if (success) {
                    if (!TextUtils.isEmpty(operater.getPic())) {
                        Glide.with(mContext).load(operater.getPic())
                                .dontAnimate()
                                .into(ivQrcode);
                    }
                }

            }
        });
    }
}
