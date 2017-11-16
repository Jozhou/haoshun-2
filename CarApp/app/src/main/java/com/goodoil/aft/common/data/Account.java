package com.goodoil.aft.common.data;

import android.content.Context;
import android.text.TextUtils;

import com.goodoil.aft.R;
import com.goodoil.aft.application.CarApplication;
import com.goodoil.aft.models.entry.VehicleItemEntry;
import com.goodoil.aft.utils.DataUtils;
import com.corelibrary.activity.base.BaseActivity;
import com.corelibrary.cache.data.CacheManager;
import com.corelibrary.models.entry.BaseEntry;

public class Account extends BaseEntry {

	private static final String TAG = Account.class.getSimpleName();

	private static final byte[] mLock = new byte[0];
	private static Account mInstance = null;

	public final static Account get() {
		synchronized (mLock) {
			if (mInstance == null) {
				mInstance = new Account();
			}
			return mInstance;
		}
	}

	public int user_id;
	public String imageurl = "";
	public String nickname = "";
	public int sex; // 1/2 男／女
	public String brand_id = "";
	public String brand_name = "";
	public String series_id = "";
	public String series_name = "";
	public String year_style = "";
	public String version = "";
	public String pwd = "";
    public String tel = "";
	public String carcode = "";
	public int usertype; // 0是普通的，1是修理厂
	public String tel2 = ""; // 维修厂的手机号

	public CharSequence getSexStr() {
		if (sex == 1) {
			return CarApplication.getInstance().getText(R.string.male);
		} else if (sex == 2) {
			return CarApplication.getInstance().getText(R.string.female);
		}
		return CarApplication.getInstance().getText(R.string.other);
	}

	private Account() {
		DataUtils.getAccount(this);
	}

	/**
	 * 重新加载数据
	 */
	public void reload() {
		DataUtils.getAccount(this);
	}

	/**
	 * 是否已登录
	 * 
	 * @return
	 */
	public boolean isLogin() {
		return isLogin(false);
	}

	/**
	 * 是否已登录
	 * 
	 * @param loadFromPref
	 *            是否从文件pref加载最新状态
	 * @return
	 */
	public boolean isLogin(boolean loadFromPref) {
		if (loadFromPref) {
			DataUtils.getAccount(this);
		}
		return user_id != 0
				&& !TextUtils.isEmpty(pwd);
	}

	/**
	 * 登录
	 */
	public void login(int user_id, String imageurl,
			String nickname, int sex, String brand_id,
			String brand_name, String series_id,
			String series_name, String year_style,
			String version, String pwd, String tel,
					  String carcode, int usertype,
					  String tel2) {
		this.user_id = user_id;
		this.imageurl = imageurl;
		this.nickname = nickname;
		this.sex = sex;
		this.brand_id = brand_id;
		this.brand_name = brand_name;
		this.series_id = series_id;
		this.series_name = series_name;
		this.year_style = year_style;
		this.version = version;
		this.pwd = pwd;
        this.tel = tel;
		this.carcode = carcode;
		this.usertype = usertype;
		this.tel2 = tel2;

		save();
	}

	public void setImageurl(String imageurl) {
		this.imageurl = imageurl;
		DataUtils.setImageurl(imageurl);
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
		DataUtils.setNickname(nickname);
	}

	public void setSex(int sex) {
		this.sex = sex;
		DataUtils.setSex(sex);
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
		DataUtils.setPwd(pwd);
	}

	public void setVehicle(VehicleItemEntry brand, VehicleItemEntry series, VehicleItemEntry yearStyle, VehicleItemEntry version) {
		this.brand_id = brand.id;
		this.brand_name = brand.name;
		this.series_id = series.id;
		this.series_name = series.name;
		this.year_style = yearStyle.name;
		this.version = version.name;
		this.carcode = version.carcode;
		DataUtils.setVehicle(brand_id, brand_name, series_id, series_name, year_style, this.version, carcode);
	}

	public void logout(final Context context) {
		logout(context, false);
	}

	/**
	 * 退出登录
	 * 
	 * @param context
	 * @param requestOperater
	 *            是否需要请求operater
	 */
	public void logout(final Context context, boolean requestOperater) {
		if (requestOperater) {
			if (context != null && context instanceof BaseActivity) {
				((BaseActivity) context).showLoading(true);
			}
		} else {
			_logout();
		}
	}

	/**
	 * 退出登录
	 */
	private void _logout() {
		clear();
		// 跳转至登录页
//		Intent intent = new Intent(DriverApp.getContext(), ReLoginActivity.class);
//		PendingIntent restartIntent = PendingIntent.getActivity(
//				DriverApp.getContext(), 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
//		AlarmManager manager = (AlarmManager) DriverApp.getContext().getSystemService(Context.ALARM_SERVICE);
//		manager.set(AlarmManager.RTC, System.currentTimeMillis() + 200, restartIntent);
//		com.szzc.ucar.manager.ActivityManager.get().popupAllActivity();
//		System.exit(0);

	}

	/**
	 * 清除登录信息
	 */
	public void clear() {
		this.user_id = 0;
		this.imageurl = "";
		this.nickname = "";
		this.sex = 0;
		this.brand_id = "";
		this.brand_name = "";
		this.series_id = "";
		this.series_name = "";
		this.year_style = "";
		this.version = "";
		this.pwd = "";
        this.tel = "";
		this.carcode = "";
		this.usertype = 0;
		this.tel2 = "";
		// 清除账号信息
		DataUtils.removeAccount();
		// 清除缓存
		CacheManager.get().clearCache();
	}

	public void save() {
		DataUtils.putAccount(this);
	}

}
