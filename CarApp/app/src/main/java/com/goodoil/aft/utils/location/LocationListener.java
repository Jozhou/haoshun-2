package com.goodoil.aft.utils.location;

/**
 * location变化通知
 * @author Administrator
 *
 */
public abstract class LocationListener {

	private long mStartTimestamp;

	public LocationListener() {
		mStartTimestamp = System.currentTimeMillis();
	}

	public abstract void onLocationChanged(DLocation location);

	public abstract void onlocationFail();

	/**
	 * 定位失败是否显示提示
	 * @return
	 */
	public boolean showErroToast() {
		return true;
	}

	/**
	 * 是否只请求一次
	 * @return
	 */
	public boolean isOnceTime() {
		return true;
	}

	/**
	 * 超时时间，单位毫秒
	 * @return
	 */
	protected int getTimeOut() {
		return 6000;
	}

	/**
	 * 是否已超时
	 * @return
	 */
	public boolean isTimeOut() {
		if(isOnceTime()) {
			if(getTimeOut() <= 0) {
				return true;
			} else {
				return System.currentTimeMillis() - mStartTimestamp >= getTimeOut();
			}
		} else {
			return false;
		}
	}

	/**
	 * 初始化时间
	 */
	public void initialize() {
		mStartTimestamp = System.currentTimeMillis();
	}

}
