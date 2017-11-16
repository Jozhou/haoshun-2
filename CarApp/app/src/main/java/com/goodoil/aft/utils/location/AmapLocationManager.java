package com.goodoil.aft.utils.location;

import android.os.Handler;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.corelibrary.application.AppContext;
import com.corelibrary.utils.LogcatUtils;

class AmapLocationManager implements ILocationManager {

	private static final String TAG = "DCarLocation";

	private static AmapLocationManager sInstance;
	public static AmapLocationManager getInstance() {
		if (sInstance == null) {
			sInstance = new AmapLocationManager();
		}
		return sInstance;
	}

	private static final int MSGWHAT_RESTART = 1;
	private static final int MSGDELAY_RESTART = 2000;

	/**
	 * 定位错误最大次数
	 */
	private static final int LOCATION_MAXERROR = 10;
	/**
	 * 定位信息有效的最大时间间隔
	 */
	private static final int INTERVAL_VALID_MAX = 100000;

	/**
	 * 当前位置
	 */
	private DLocation mCurrentLocation;
	/**
	 * 上次定位时间
	 */
	private long mCurrentLocationTimeMillis = 0;
	/**
	 * 错误定位次数
	 */
	private int mLocationErrorCount = 0;
	/**
	 * AMapLocationClient
	 */
	private AMapLocationClient mLocationClient = null;
	/**
	 * AMapLocationClientOption
	 */
	private AMapLocationClientOption mLocationOption = null;

	/**
	 * AMapLocationListener
	 */
	private AMapLocationListener mLocationListener = new AMapLocationListener() {
		@Override
		public void onLocationChanged(AMapLocation location) {
			if (location != null) {
				LogcatUtils.i(TAG, "get AMapLocation:" + location.toString());
			}
			if (location.getErrorCode() != AMapLocation.LOCATION_SUCCESS) {
				LogcatUtils.i(TAG, "location Error, ErrCode:"
						+ location.getErrorCode() + ", errInfo:"
						+ location.getErrorInfo());
				return;
			}
			DLocation dLocation = LocationUtils.aMapLocation2DLocation(location);
			setCurrentLocation(dLocation);
			if(!LocationUtils.isValidLocation(dLocation)) {
				mLocationErrorCount++;
				if(mLocationErrorCount >= LOCATION_MAXERROR) {
					mLocationErrorCount = 0;
					mHandler.removeCallbacksAndMessages(null);
					mHandler.sendEmptyMessageDelayed(MSGWHAT_RESTART, MSGDELAY_RESTART);
				}
			}
		}
	};

	private Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
				case MSGWHAT_RESTART:
					restart();
					break;
				default:
					break;
			}
		};
	};

	/**
	 * 获取gps卫星个数
	 * @return
	 */
	@Override
	public int getGpsSatelliteNumber() {
		return 0;
	}

	/**
	 * 获取gps信号强度
	 * @return
	 */
	@Override
	public float getGpsSingnal() {
		return 0f;
	}

	private AmapLocationManager() {

	}

	/**
	 * 初始化定位client
	 */
	private void initializeLocationClient() {
		//初始化定位
		mLocationClient = new AMapLocationClient(AppContext.get());
		//给定位客户端对象设置定位参数
		mLocationClient.setLocationOption(getLocationClientOption());
		//设置定位回调监听
		mLocationClient.setLocationListener(mLocationListener);
	}

	/**
	 * 初始化定位option
	 * @return
	 */
	private AMapLocationClientOption getLocationClientOption() {
		if (mLocationOption == null) {
			//初始化定位参数
			mLocationOption = new AMapLocationClientOption();
			//设置定位模式为高精度模式，Battery_Saving为低功耗模式，Device_Sensors是仅设备模式
			mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
			//设置是否返回地址信息（默认返回地址信息）
			mLocationOption.setNeedAddress(true);
			//设置是否只定位一次,默认为false
			mLocationOption.setOnceLocation(false);
			//设置是否强制刷新WIFI，默认为强制刷新
			mLocationOption.setWifiActiveScan(true);
			//设置是否允许模拟位置,默认为false，不允许模拟位置
			mLocationOption.setMockEnable(false);
			//设置是否主动刷新WIFI 
			mLocationOption.setWifiActiveScan(true);
			//设置联网超时时间
			mLocationOption.setHttpTimeOut(INTERVAL_VALID_MAX);
			//设置定位间隔,单位毫秒,默认为2000ms
		}
		mLocationOption.setInterval(LocationManager.getInstance().getLocationInterval());
		return mLocationOption;
	}

	/**
	 * 启动服务
	 */
	@Override
	public synchronized void start() {
		initializeLocationClient();
		mLocationClient.startLocation();
		LogcatUtils.e(TAG, "AmapLocationManager start");
	}

	/**
	 * 重启定位
	 */
	@Override
	public synchronized void restart() {
		if (mLocationClient != null) {
			mLocationClient.stopLocation();
			mLocationClient.onDestroy();
		}
		initializeLocationClient();
		mLocationClient.startLocation();
		LogcatUtils.e(TAG, "AmapLocationManager restart");
	}

	/**
	 * 停止服务
	 */
	@Override
	public synchronized void stop() {
		if (mLocationClient != null) {
			mLocationClient.stopLocation();
			mLocationClient.onDestroy();
		}
		LogcatUtils.e(TAG, "AmapLocationManager stop");
	}

	/**
	 * 设置当前点位
	 */
	private synchronized void setCurrentLocation(DLocation location) {
//		if(ZCConfig.IS_DEBUG) {
//			if(location != null) {
//				if(LocationManager.DEBUG_LAT != 0 && LocationManager.DEBUG_LON != 0) {
//					location.setLatitude(LocationManager.DEBUG_LAT);
//					location.setLongitude(LocationManager.DEBUG_LON);
//				}
//				if (!TextUtils.isEmpty(LocationManager.CITYCODE)) {
//					location.setCityCode(LocationManager.CITYCODE);
//				}
//			}
//		}
		// update current
		mCurrentLocation = location;
		mCurrentLocationTimeMillis = System.currentTimeMillis();
	}

	/**
	 * 获取当前点位
	 * @return
	 */
	@Override
	public synchronized DLocation getCurrentLocation() {
		if(System.currentTimeMillis() - mCurrentLocationTimeMillis > INTERVAL_VALID_MAX) {
			mCurrentLocationTimeMillis = 0;
			mCurrentLocation = null;
		}
		return mCurrentLocation;
	}

}
