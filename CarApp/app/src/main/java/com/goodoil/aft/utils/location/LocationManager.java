package com.goodoil.aft.utils.location;

import android.os.Handler;

import com.goodoil.aft.R;
import com.corelibrary.utils.DialogUtils;
import com.corelibrary.utils.LogcatUtils;

import java.util.ArrayList;
import java.util.Collection;

public class LocationManager {

	private static final String TAG = "DCarLocation";

	private static LocationManager sInstance;

	public static double DEBUG_LAT;
	public static double DEBUG_LON;
	public static String CITYCODE;

	public static LocationManager getInstance() {
		if (sInstance == null) {
			sInstance = new LocationManager();
		}
		return sInstance;
	}

	private static final int MSGWHAT_LOCATION = 0;

	/**
	 * 是否正在运行
	 */
	private boolean mRunning;
	/**
	 * 定位频率
	 */
	private int mLocationInterval;
	/**
	 * 回调错误次数
	 */
	private int mCallbackErrorCount = 0;
	/**
	 * 当前监听数组
	 */
	private LocationArrayList mLocationArray;
	/**
	 * 定位模块
	 */
	private ILocationManager mLocationManager;
	/**
	 * 是否频繁打点模式
	 */
	private boolean mBusyMode;

	/**
	 * 获取定位频率（毫秒）
	 * @return
	 */
	public int getLocationInterval() {
		return mLocationInterval;
	}

	/**
	 * 获取gps卫星个数
	 * @return
	 */
	public int getGpsSatelliteNumber() {
		return mLocationManager.getGpsSatelliteNumber();
	}

	/**
	 * 获取gps信号强度
	 * @return
	 */
	public float getGpsSingnal() {
		return mLocationManager.getGpsSingnal();
	}

	private Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
				case MSGWHAT_LOCATION:
					if(notifyLocationChanged()) {
						mHandler.sendEmptyMessageDelayed(MSGWHAT_LOCATION, mLocationInterval);
					}
					break;
				default:
					break;
			}
		};
	};

	private LocationManager() {
		mLocationArray = new LocationArrayList();
	}

	/**
	 * 启动服务
	 */
	private void start() {
		mRunning = true;
		if (mLocationManager == null) {
			mLocationManager = getLocationManager();
		}
		mLocationManager.start();
		mHandler.sendEmptyMessage(MSGWHAT_LOCATION);
		LogcatUtils.e(TAG, "LocationManager start");
	}

	/**
	 * 重启定位
	 */
	private void restart() {
		if (mLocationManager == null) {
			mLocationManager = getLocationManager();
		}
		mLocationManager.restart();
		LogcatUtils.e(TAG, "LocationManager restart");
	}

	/**
	 * 停止服务
	 */
	private void stop() {
		if (mLocationManager == null) {
			mLocationManager = getLocationManager();
		}
		mLocationManager.stop();
		mHandler.removeCallbacksAndMessages(null);
		LogcatUtils.e(TAG, "LocationManager stop");
		mRunning = false;
	}

	/**
	 * 设置gps打点间隔
	 */
	private boolean refetchGpsInterval() {
		boolean changed = false;
		boolean onetime = false;
		for (int i = 0; i < mLocationArray.size(); i++) {
			if (mLocationArray.get(i) != null && mLocationArray.get(i).isOnceTime()) {
				onetime = true;
				break;
			}
		}
		int newInterval = 2 * 1000;
//		if (onetime || mBusyMode) {
//			newInterval = 10 * 1000;
//		} else {
//			newInterval = SettingEntry.getGPSIdleInterval();
//		}
		if(mLocationInterval != newInterval) {
			mLocationInterval = newInterval;
			changed = true;
		}
		return changed;
	}

	/**
	 * 注册监听
	 * @param l
	 */
	public synchronized void registLocationListener(LocationListener l) {
		if(mLocationArray == null) {
			mLocationArray = new LocationArrayList();
		}
		if(l == null) {
			return;
		}
		if (mLocationArray.contains(l)) {
			return;
		}
		mLocationManager = getLocationManager();
		l.initialize();
		if(l.isOnceTime()) {
			DLocation location = mLocationManager.getCurrentLocation();
			if(LocationUtils.isValidLocation(location)) {
				l.onLocationChanged(location);
				return;
			}
		}
		mLocationArray.add(l);
		if(!mRunning) {
			start();
		}
	}

	/**
	 * 反注册监听
	 * @param l
	 */
	public synchronized void unregistLocationListener(LocationListener l) {
		if(mLocationArray == null) {
			return;
		}
		mLocationArray.remove(l);
		l = null;
	}

	/**
	 * 通知改变gps打点频率
	 */
	public synchronized void notifyInterval() {
		if(refetchGpsInterval()) {
			restart();
		}
	}

	/**
	 * 通知改变gps打点频率
	 */
	public synchronized void notifyInterval(boolean busy) {
		mBusyMode = busy;
		notifyInterval();
	}

	/**
	 * 取得LocationManager
	 * @return
	 */
	private ILocationManager getLocationManager() {
//		if(SettingEntry.getGPSMode() == GpsMode.gps) {
//			return GpsLocationManager.getInstance();
//		} else if(SettingEntry.isBDMap()) {
//			return BDmapLocationManager.getInstance();
//		} else {
//			return AmapLocationManager.getInstance();
//		}

		return AmapLocationManager.getInstance();
	}

	/**
	 * 通知点位变化
	 */
	private synchronized boolean notifyLocationChanged() {
		if(mLocationArray != null && mLocationArray.size() > 0) {
			LocationListener l = null;
			DLocation location = mLocationManager.getCurrentLocation();
			if(!LocationUtils.isValidLocation(location)) {
				for(int i = mLocationArray.size() - 1; i >= 0; i--) {
					l = mLocationArray.get(i);
					if(l == null) {
						mLocationArray.remove(i);
						continue;
					}
					if(l.isOnceTime()) {
						if(l.isTimeOut()) {
							mLocationArray.remove(i);
							if(l.showErroToast()) {
								DialogUtils.showToastMessage(R.string.location_response_error);
							}
							l.onlocationFail();
						}
					} else {
						l.onlocationFail();
					}
				}
				mCallbackErrorCount++;
				if (mCallbackErrorCount >= 5) {
					mCallbackErrorCount = 0;
					restart();
				}
			} else {
				mCallbackErrorCount = 0;
				for(int i = mLocationArray.size() - 1; i >= 0; i--) {
					l = mLocationArray.get(i);
					if(l == null) {
						mLocationArray.remove(i);
						continue;
					}
					l.onLocationChanged(location);
					if(l.isOnceTime()) {
						mLocationArray.remove(i);
					}
				}
			}
		}
		if(mLocationArray != null && mLocationArray.size() > 0) {
			return true;
		} else {
			stop();
			return false;
		}
	}

	private class LocationArrayList extends ArrayList<LocationListener> {

		private static final long serialVersionUID = -4441953111107111953L;

		@Override
		public boolean add(LocationListener object) {
			boolean r = super.add(object);
			refetchGpsInterval();
			return r;
		}

		@Override
		public void add(int index, LocationListener object) {
			super.add(index, object);
			refetchGpsInterval();
		}

		@Override
		public boolean addAll(Collection<? extends LocationListener> collection) {
			boolean r =  super.addAll(collection);
			refetchGpsInterval();
			return r;
		}

		@Override
		public boolean addAll(int index, Collection<? extends LocationListener> collection) {
			boolean r =  super.addAll(index, collection);
			refetchGpsInterval();
			return r;
		}

		@Override
		public boolean remove(Object object) {
			boolean r =  super.remove(object);
			refetchGpsInterval();
			return r;
		}

		@Override
		public LocationListener remove(int index) {
			LocationListener r =  super.remove(index);
			refetchGpsInterval();
			return r;
		}

		@Override
		public boolean removeAll(Collection<?> collection) {
			boolean r =  super.removeAll(collection);
			refetchGpsInterval();
			return r;
		}

		@Override
		protected void removeRange(int fromIndex, int toIndex) {
			super.removeRange(fromIndex, toIndex);
			refetchGpsInterval();
		}
	}

}
