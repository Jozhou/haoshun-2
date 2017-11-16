package com.goodoil.aft.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.goodoil.aft.R;
import com.goodoil.aft.common.data.Account;
import com.goodoil.aft.models.operater.LoginOperater;
import com.corelibrary.activity.base.BaseActivity;
import com.corelibrary.manager.ActivityManager;
import com.corelibrary.models.http.BaseOperater;
import com.corelibrary.utils.DialogUtils;
import com.corelibrary.utils.ViewInject.ViewInject;
import com.corelibrary.view.TitleBar;

/**
 * Created by Administrator on 2017/9/28.
 */

public class LoginActivity extends BaseActivity {

    @ViewInject("titlebar")
    private TitleBar titleBar;
    @ViewInject("et_account")
    private EditText etAccount;
    @ViewInject("et_psw")
    private EditText etPsw;
    @ViewInject(value = "btn_login", setClickListener = true)
    private Button btnLogin;
    @ViewInject(value = "tv_register", setClickListener = true)
    private TextView tvRegister;
    @ViewInject(value = "tv_forget_psw", setClickListener = true)
    private TextView tvForgetPsw;
    @ViewInject(value = "iv_wx", setClickListener = true)
    private ImageView ivWx;
    @ViewInject(value = "iv_qq", setClickListener = true)
    private ImageView ivQQ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    @Override
    protected void onApplyData() {
        super.onApplyData();
        ActivityManager.get().popupAllActivityExclusiveCurrent();
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        int id = v.getId();
        if (id == R.id.btn_login) {
            doLogin();
        } else if (id == R.id.tv_register) {
            Intent intent = new Intent(this, RegisterActivity.class);
            startActivity(intent);
        } else if (id == R.id.tv_forget_psw) {
            Intent intent = new Intent(this, ModifyPswActivity.class);
            startActivity(intent);
        } else if (id == R.id.btn_login) {

        } else if (id == R.id.iv_wx) {

        } else if (id == R.id.iv_qq) {

        }
    }

    private void doLogin() {
        String tel = etAccount.getText().toString().trim();
        String pwd = etPsw.getText().toString().trim();
        if (TextUtils.isEmpty(tel) || TextUtils.isEmpty(pwd)) {
            DialogUtils.showToastMessage(R.string.tip_input_name_psw);
            return;
        }

        LoginOperater operater = new LoginOperater(this);
        operater.setParams("1", tel, pwd);
        operater.onReq(new BaseOperater.RspListener() {
            @Override
            public void onRsp(boolean success, Object obj) {
                if (success) {
                    if (Account.get().isLogin()) {
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        DialogUtils.showToastMessage(R.string.login_fail);
                    }
                }
            }
        });
    }
}
