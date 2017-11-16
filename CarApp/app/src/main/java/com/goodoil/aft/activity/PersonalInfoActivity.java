package com.goodoil.aft.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.goodoil.aft.R;
import com.goodoil.aft.common.data.Account;
import com.goodoil.aft.context.Config;
import com.goodoil.aft.context.IntentCode;
import com.goodoil.aft.models.entry.VehicleItemEntry;
import com.goodoil.aft.models.operater.UpdateHeadIconOperater;
import com.goodoil.aft.models.operater.UpdateSexOperater;
import com.goodoil.aft.view.popup.PopConfirm;
import com.corelibrary.activity.base.BaseActivity;
import com.corelibrary.models.http.BaseOperater;
import com.corelibrary.utils.DialogUtils;
import com.corelibrary.utils.ViewInject.ViewInject;
import com.corelibrary.view.TitleBar;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import it.sauronsoftware.base64.Base64;

/**
 * Created by Administrator on 2017/10/11.
 */

public class PersonalInfoActivity extends BaseActivity {

    @ViewInject("root")
    private View vRoot;
    @ViewInject("titlebar")
    private TitleBar titleBar;
    @ViewInject("iv_head")
    private ImageView ivHead;

    @ViewInject(value = "ll_head", setClickListener = true)
    private View vHead;
    @ViewInject(value = "ll_nick", setClickListener = true)
    private View vNick;
    @ViewInject(value = "ll_sex", setClickListener = true)
    private View vSex;
    @ViewInject(value = "ll_account", setClickListener = true)
    private View vAccount;
    @ViewInject(value = "ll_bind_tel", setClickListener = true)
    private View vBindTel;
    @ViewInject(value = "ll_modify_psw", setClickListener = true)
    private View vModifyPsw;
    @ViewInject(value = "ll_vehicle", setClickListener = true)
    private View vVehicle;

    @ViewInject("tv_nick")
    private TextView tvNick;
    @ViewInject("tv_sex")
    private TextView tvSex;
    @ViewInject("tv_account")
    private TextView tvAccount;
    @ViewInject("tv_bind_tel")
    private TextView tvBindTel;
    @ViewInject("tv_vehicle")
    private TextView tvVehicle;

    private static final int REQUEST_MODIFY_NICK = 1001;
    private static final int REQUEST_HEAD_ICON = 1002;

    private VehicleItemEntry brand;
    private VehicleItemEntry series;
    private VehicleItemEntry yearStyle;
    private VehicleItemEntry version;

    private PopConfirm popSelPic;
    private PopConfirm popSelSex;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_info);
    }

    @Override
    protected void onBindListener() {
        super.onBindListener();
        titleBar.setLeftOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        int id = v.getId();
        if (id == R.id.ll_head) {
            selPic();
        } else if (id == R.id.ll_head) {

        } else if (id == R.id.ll_nick) {
            Intent intent = new Intent(this, ModifyNickActivity.class);
            startActivityForResult(intent, REQUEST_MODIFY_NICK);
        } else if (id == R.id.ll_sex) {
            selSex();
        } else if (id == R.id.ll_bind_tel) {

        } else if (id == R.id.ll_modify_psw) {
            Intent intent = new Intent(this, ModifyPswActivity.class);
            startActivity(intent);
        } else if (id == R.id.ll_vehicle) {
            onSelVehicle();
        }
    }

    private void onSelVehicle() {
        Intent intent = new Intent(this, SelBrandActivity.class);
        intent.putExtra(IntentCode.INTENT_SEL_VEHICLE_FROM, SelVehicleActivity.FROM_PERSONAL);
        intent.putExtra(IntentCode.INTENT_TYPE, SelVehicleActivity.SEL_BRAND);
        startActivityForResult(intent, SelVehicleActivity.SEL_BRAND);
    }

    @Override
    protected void onApplyData() {
        super.onApplyData();
        Glide.with(this).load(Account.get().imageurl)
                .placeholder(R.drawable.head_icon)
                .error(R.drawable.head_icon)
                .into(ivHead);
        tvNick.setText(Account.get().nickname);
        tvSex.setText(Account.get().getSexStr());
        tvAccount.setText(Account.get().tel);
        tvBindTel.setText(Account.get().tel);
        tvVehicle.setText(getResources().getString(R.string.show_vehicle,
                Account.get().brand_name,
                Account.get().series_name,
                Account.get().year_style,
                Account.get().version));
    }

    private void selPic() {
        if (popSelPic == null) {
            initSelPicPop();
        }
        popSelPic.showAtLocation(vRoot, Gravity.NO_GRAVITY, 0, 0);
    }

    private void selSex() {
        if (popSelSex == null) {
            initSelSex();
        }
        popSelSex.showAtLocation(vRoot, Gravity.NO_GRAVITY, 0, 0);
    }

    private void initSelPicPop() {
        popSelPic = new PopConfirm(this);
        popSelPic.setOneClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImagePicker imagePicker = ImagePicker.getInstance();
                imagePicker.setCrop(true);
                imagePicker.setMultiMode(false);
                imagePicker.setShowCamera(false);
                imagePicker.setOutPutX(Config.HEAD_WIDTH);
                imagePicker.setOutPutY(Config.HEAD_WIDTH);
                imagePicker.setFocusWidth(Config.HEAD_FOCUS_WIDTH);
                imagePicker.setFocusHeight(Config.HEAD_FOCUS_WIDTH);

                Intent intent = new Intent(PersonalInfoActivity.this, ImageGridActivity.class);
                intent.putExtra(ImageGridActivity.EXTRAS_TAKE_PICKERS, true);
                startActivityForResult(intent, REQUEST_HEAD_ICON);
            }
        });

        popSelPic.setTwoClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImagePicker imagePicker = ImagePicker.getInstance();
                imagePicker.setCrop(true);
                imagePicker.setMultiMode(false);
                imagePicker.setShowCamera(false);
                imagePicker.setOutPutX(Config.HEAD_WIDTH);
                imagePicker.setOutPutY(Config.HEAD_WIDTH);
                imagePicker.setFocusWidth(Config.HEAD_FOCUS_WIDTH);
                imagePicker.setFocusHeight(Config.HEAD_FOCUS_WIDTH);

                Intent intent = new Intent(PersonalInfoActivity.this, ImageGridActivity.class);
                startActivityForResult(intent, REQUEST_HEAD_ICON);
            }
        });
    }

    private void initSelSex() {
        popSelSex = new PopConfirm(this);
        popSelSex.setText(getText(R.string.male), getText(R.string.female));
        popSelSex.setOneClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateSex(1);
            }
        });

        popSelSex.setTwoClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateSex(2);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_HEAD_ICON) {
            if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
                ArrayList<ImageItem> imageItems = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                if (imageItems != null && !imageItems.isEmpty()) {
                    ImageItem imageItem = imageItems.get(0);
                    updateHadIcon(bitmapToBase64(BitmapFactory.decodeFile(imageItem.path)));
                }
            }
        } else if (requestCode == REQUEST_MODIFY_NICK) {
            if (resultCode == RESULT_OK) {
                tvNick.setText(Account.get().nickname);
            }
        } else if (requestCode == SelVehicleActivity.SEL_BRAND) {
            if (resultCode == RESULT_OK) {
                brand = (VehicleItemEntry) data.getSerializableExtra(IntentCode.INTENT_BRAND);
                series = (VehicleItemEntry) data.getSerializableExtra(IntentCode.INTENT_SERIES);
                yearStyle = (VehicleItemEntry) data.getSerializableExtra(IntentCode.INTENT_YEAR_STYLE);
                version = (VehicleItemEntry) data.getSerializableExtra(IntentCode.INTENT_VERSION);
                tvVehicle.setText(getResources().getString(R.string.show_vehicle, brand.name, series.name, yearStyle.name, version.name));
                Account.get().setVehicle(brand, series, yearStyle, version);
            }
        }
    }

    private void updateSex(final int sex) {
        final UpdateSexOperater operater = new UpdateSexOperater(this);
        operater.setParams(sex);
        operater.onReq(new BaseOperater.RspListener() {
            @Override
            public void onRsp(boolean success, Object obj) {
                if (success) {
                    DialogUtils.showToastMessage(operater.getMsg());
                    Account.get().setSex(sex);
                    tvSex.setText(Account.get().getSexStr());
                }
            }
        });
    }

    private void updateHadIcon(String headimage) {
        final UpdateHeadIconOperater operater = new UpdateHeadIconOperater(this);
        operater.setParams(headimage);
        operater.onReq(new BaseOperater.RspListener() {
            @Override
            public void onRsp(boolean success, Object obj) {
                if (success && !TextUtils.isEmpty(operater.getImageurl())) {
                    Account.get().setImageurl(operater.getImageurl());
                    Glide.with(PersonalInfoActivity.this).load(operater.getImageurl()).into(ivHead);
                    DialogUtils.showToastMessage(operater.getMsg());
                }
            }
        });
    }

    public String bitmapToBase64(Bitmap bitmap) {
        // 将Bitmap转换成字符串
        String string = null;
        ByteArrayOutputStream bStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bStream);
        byte[] bytes = bStream.toByteArray();
        try {
            string = new String(Base64.encode(bytes), "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return string;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (popSelPic != null && popSelPic.isShowing()) {
            popSelPic.dismissWindow();
            return true;
        }
        if (popSelSex != null && popSelSex.isShowing()) {
            popSelSex.dismissWindow();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
