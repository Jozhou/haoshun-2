package com.goodoil.aft.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.goodoil.aft.R;
import com.goodoil.aft.common.data.Account;
import com.goodoil.aft.models.operater.UpdateNickOperater;
import com.corelibrary.activity.base.BaseActivity;
import com.corelibrary.models.http.BaseOperater;
import com.corelibrary.utils.DialogUtils;
import com.corelibrary.utils.ViewInject.ViewInject;
import com.corelibrary.view.TitleBar;

/**
 * Created by Administrator on 2017/10/12.
 */

public class ModifyNickActivity extends BaseActivity {

    @ViewInject("titlebar")
    private TitleBar titleBar;
    @ViewInject("et_nickname")
    private EditText etNick;
    @ViewInject(value = "iv_clear", setClickListener = true)
    private ImageView ivClear;
    @ViewInject(value = "btn_save", setClickListener = true)
    private Button btnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_nick);
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
    protected void onApplyData() {
        super.onApplyData();
        etNick.setText(Account.get().nickname);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        int id = v.getId();
        if (id == R.id.iv_clear) {
            etNick.setText("");
        } else if (id == R.id.btn_save) {
            save();
        }
    }

    private void save() {
        final String nick = etNick.getText().toString().trim();
        if (TextUtils.isEmpty(nick)) {
            DialogUtils.showToastMessage(R.string.hint_input_nickname);
        }
        if (TextUtils.equals(nick, Account.get().nickname)) {
            finish();
        }
        final UpdateNickOperater operater = new UpdateNickOperater(this);
        operater.setParams(nick);
        operater.onReq(new BaseOperater.RspListener() {
            @Override
            public void onRsp(boolean success, Object obj) {
                if (success) {
                    DialogUtils.showToastMessage(operater.getMsg());
                    Account.get().setNickname(nick);
                    setResult(RESULT_OK);
                    finish();
                }
            }
        });

    }
}
