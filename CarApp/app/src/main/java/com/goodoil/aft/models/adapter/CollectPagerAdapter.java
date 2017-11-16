package com.goodoil.aft.models.adapter;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import com.goodoil.aft.fragment.MyNewsFragment;
import com.goodoil.aft.fragment.MyStoreFragment;
import com.corelibrary.fragment.base.BaseFragment;

import java.util.ArrayList;

/**
 * 秀场pagerAdapter
 * 
 * @author tuxiang
 *
 */
public class CollectPagerAdapter extends FragmentPagerAdapter {
	public static final String TAG = CollectPagerAdapter.class.getSimpleName();

	private ArrayList<BaseFragment> fragments;
	private ArrayList<String> titles;

	public CollectPagerAdapter(FragmentManager fm) {
		super(fm);
		fragments = new ArrayList<>();
		MyNewsFragment myNewsFragment = new MyNewsFragment();
		MyStoreFragment myStoreFragment = new MyStoreFragment();
		fragments.add(myNewsFragment);
		fragments.add(myStoreFragment);
		titles = new ArrayList<>();
		titles.add("资讯");
		titles.add("修理厂");
	}

	@Override
	public BaseFragment getItem(int position) {
		return fragments.get(position);
	}

	@Override
	public Object instantiateItem(ViewGroup container, final int position) {
		Object obj = super.instantiateItem(container, position);
		return obj;
	}

	@Override
	public int getCount() {
		if (fragments == null) {
			return 0;
		}
		return fragments.size();
	}

	@Override
	public CharSequence getPageTitle(int position) {
		return titles.get(position);
	}
}
