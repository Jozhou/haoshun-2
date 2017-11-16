package com.goodoil.aft.activity;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.widget.FrameLayout;
import android.widget.RadioGroup;

import com.goodoil.aft.R;
import com.goodoil.aft.fragment.MainFragment;
import com.goodoil.aft.fragment.MineFragment;
import com.goodoil.aft.fragment.NewsFragment;
import com.goodoil.aft.fragment.StoreFragment;
import com.corelibrary.activity.base.BaseActivity;
import com.corelibrary.utils.DialogUtils;
import com.corelibrary.utils.ViewInject.DoubleBackUtils;
import com.corelibrary.utils.ViewInject.ViewInject;

/**
 * Created by Administrator on 2017/9/28.
 */

public class MainActivity extends BaseActivity {

    @ViewInject("rg_tab")
    private RadioGroup rgTabs;

    @ViewInject("fl_container")
    private FrameLayout flContainer;

    private Fragment currentFragment;
    private MainFragment mainFragment;
    private NewsFragment newsFragment;
    private StoreFragment storeFragment;
    private MineFragment mineFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onBindListener() {
        super.onBindListener();
        rgTabs.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                switchFragment(checkedId);
            }
        });
    }

    @Override
    protected void onApplyData() {
        super.onApplyData();
        mainFragment = new MainFragment();
        newsFragment = new NewsFragment();
        storeFragment = new StoreFragment();
        mineFragment = new MineFragment();
        rgTabs.check(R.id.rb_home);
    }

    private void switchFragment(int resId) {
        if (resId == R.id.rb_home) {
            switchFragment(currentFragment, mainFragment);
            currentFragment = mainFragment;
        } else if (resId == R.id.rb_news) {
            switchFragment(currentFragment, newsFragment);
            currentFragment = newsFragment;
        } else if (resId == R.id.rb_store) {
            switchFragment(currentFragment, storeFragment);
            currentFragment = storeFragment;
        } else if (resId == R.id.rb_mine) {
            switchFragment(currentFragment, mineFragment);
            currentFragment = mineFragment;
        }
    }

    /**
     * 切换Fragment
     *
     * @param fromFragment：需要隐藏的Fragment
     * @param toFragment：需要显示的Fragment
     */
    private void switchFragment(Fragment fromFragment, Fragment toFragment) {
        FragmentTransaction fragmentTransaction = manager.beginTransaction();
        if (fromFragment == null) {
            fragmentTransaction.add(R.id.fl_container, toFragment).commit();
        } else if (fromFragment != toFragment) {
            if (!toFragment.isAdded()) {
                fragmentTransaction.hide(fromFragment).add(R.id.fl_container, toFragment).commit();
            } else {
                fragmentTransaction.hide(fromFragment).show(toFragment).commit();
            }
        }
    }

//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        if (keyCode == KeyEvent.KEYCODE_BACK) {
//            if (DoubleBackUtils.isFastDoubleClick()) {
//                return super.onKeyDown(keyCode, event);
//            } else {
//                DialogUtils.showToastMessage(R.string.click_again_exit);
//                return true;
//            }
//        }
//        return super.onKeyDown(keyCode, event);
//    }

    @Override
    protected boolean onFragmentKeyDown(int keyCode, KeyEvent event) {
        if (super.onFragmentKeyDown(keyCode, event)) {
            return true;
        } else {
            if (DoubleBackUtils.isFastDoubleClick()) {
                return false;
            } else {
                DialogUtils.showToastMessage(R.string.click_again_exit);
                return true;
            }
        }
    }
}
