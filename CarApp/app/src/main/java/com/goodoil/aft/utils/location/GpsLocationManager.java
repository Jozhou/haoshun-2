//package com.carapp.utils.location;
//
//import android.content.Context;
//import android.location.Criteria;
//import android.location.GpsSatellite;
//import android.location.GpsStatus;
//import android.location.Location;
//import android.os.Bundle;
//import android.os.Handler;
//import android.text.TextUtils;
//
//import java.util.Iterator;
//
//class GpsLocationManager implements ILocationManager {
//
//	private static final String TAG = "DCarLocation";
//
//	private static GpsLocationManager sInstance;
//	public static GpsLocationManager getInstance() {
//		if (sInstance == null) {
//			sInstance = new GpsLocationManager();
//		}
//		return sInstance;
//	}
//
//	private static final int MSGWHAT_RESTART = 1;
//	private static final int MSGDELAY_RESTART = 2000;
//
//	/**
//	 * 定位距离
//	 */
//	private static final int LOCATION_DISTANCE = 1;
//	/**
//	 * 定位错误最大次数
//	 */
//	private static final int LOCATION_MAXERROR = 2;
//	/**
//	 * 定位信息有效的最大时间间隔
//	 */
//	private static final int INTERVAL_VALID_MAX = 10000;
//
//	/**
//	 * 当前位置
//	 */
//	private DLocation mCurrentLocation;
//	/**
//	 * 上次定位时间
//	 */
//	private long mCurrentLocationTimeMillis = 0;
//	/**
//	 * 错误定位次数
//	 */
//	private int mLocationErrorCount = 0;
//	/**
//	 * gps卫星个数
//	 */
//	private int mGpsSatelliteNumber = 0;
//	/**
//	 * gps信号强度
//	 */
//	private float mGpsSingnal = 0;
//	/**
//	 * LocationManager
//	 */
//	private android.location.LocationManager mLocationManager;
//
//    GpsStatus gpsStatus = null;
//
//	/**
//	 * LocationListener
//	 */
//	private android.location.LocationListener mLocationListener = new android.location.LocationListener() {
//		@Override
//		public void onStatusChanged(String provider, int status, Bundle extras) {
//
//		}
//
//		@Override
//		public void onProviderEnabled(String provider) {
//
//		}
//
//		@Override
//		public void onProviderDisabled(String provider) {
//
//		}
//
//		@Override
//		public void onLocationChanged(Location location) {
//			if (location != null) {
//				LogcatUtils.i(TAG, "get GpsLocation:" + location.toString());
//			}
//			setCurrentLocation(location);
//			if(!LocationUtils.isValidLocation(mCurrentLocation)) {
//				mLocationErrorCount++;
//				if(mLocationErrorCount >= LOCATION_MAXERROR) {
//					mLocationErrorCount = 0;
//					mHandler.removeCallbacksAndMessages(null);
//					mHandler.sendEmptyMessageDelayed(MSGWHAT_RESTART, MSGDELAY_RESTART);
//				}
//			}
//		}
//	};
//
//	/**
//	 *  状态监听
//	 */
//	GpsStatus.Listener mGpsStatusListener = new GpsStatus.Listener() {
//		public void onGpsStatusChanged(int event) {
//			switch (event) {
//				// first location
//				case GpsStatus.GPS_EVENT_FIRST_FIX:
//					break;
//				// 卫星状态改变
//				case GpsStatus.GPS_EVENT_SATELLITE_STATUS:
//					if (null == mLocationManager) {
//						return;
//					}
//                    try {
//                        gpsStatus = mLocationManager.getGpsStatus(gpsStatus);
//                        int maxSatellites = gpsStatus.getMaxSatellites();
//                        Iterator<GpsSatellite> iters = gpsStatus.getSatellites().iterator();
//                        int gpsnumber = 0;
//                        float gpssingal = 0;
//                        while (iters.hasNext() && gpsnumber <= maxSatellites) {
//                            GpsSatellite s = iters.next();
//                            gpsnumber++;
//                            float s_signal = s.getSnr();
//                            if (s_signal > gpssingal) {
//                                gpssingal = s_signal;
//                            }
//                        }
//                        mGpsSatelliteNumber = gpsnumber;
//                        mGpsSingnal = gpssingal;
//                    } catch(Exception e) {
//                        LogcatUtils.e(TAG,"onGpsStatusChanged exception:"+ e.toString());
//                    }
//					break;
//				// location start
//				case GpsStatus.GPS_EVENT_STARTED:
//					LogcatUtils.i(TAG, "定位启动");
//					break;
//				// location end
//				case GpsStatus.GPS_EVENT_STOPPED:
//					LogcatUtils.i(TAG, "定位结束");
//					break;
//			}
//		};
//	};
//
//	private Handler mHandler = new Handler() {
//		public void handleMessage(android.os.Message msg) {
//			switch (msg.what) {
//				case MSGWHAT_RESTART:
//					restart();
//					break;
//				default:
//					break;
//			}
//		};
//	};
//
//	/**
//	 * 获取gps卫星个数
//	 * @return
//	 */
//	@Override
//	public int getGpsSatelliteNumber() {
//		return mGpsSatelliteNumber;
//	}
//
//	/**
//	 * 获取gps信号强度
//	 * @return
//	 */
//	@Override
//	public float getGpsSingnal() {
//		return mGpsSingnal;
//	}
//
//	private GpsLocationManager() {
//		mLocationManager = (android.location.LocationManager)
//				DriverApp.getInstance().getSystemService(Context.LOCATION_SERVICE);
//		// 为获取地理位置信息时设置查询条件
//		String bestProvider = mLocationManager.getBestProvider(getCriteria(), true);
//		// 如果不设置查询要求，getLastKnownLocation方法传人的参数为LocationManager.GPS_PROVIDER
//		if(!TextUtils.isEmpty(bestProvider)) {
//			mCurrentLocation = LocationUtils.gpsLocation2DLocation(
//					mLocationManager.getLastKnownLocation(bestProvider));
//		}
//	}
//
//	/**
//	 * 返回查询条件
//	 *
//	 * @return
//	 */
//	private Criteria getCriteria() {
//		Criteria criteria = new Criteria();
//		// 设置定位精确度 Criteria.ACCURACY_COARSE比较粗略，Criteria.ACCURACY_FINE则比较精细
//		criteria.setAccuracy(Criteria.ACCURACY_FINE);
//		// 设置是否要求速度
//		criteria.setSpeedRequired(true);
//		// 设置是否允许运营商收费
//		criteria.setCostAllowed(true);
//		// 设置是否需要方位信息
//		criteria.setBearingRequired(true);
//		// 设置是否需要海拔信息
//		criteria.setAltitudeRequired(true);
//		// 设置对电源的需求
//		criteria.setPowerRequirement(Criteria.POWER_LOW);
//		return criteria;
//	}
//
//	/**
//	 * 启动服务
//	 */
//	@Override
//	public synchronized void start() {
//		try {
//			if(SettingEntry.getGpsUploadMode() == 1) {
//				mLocationManager.addGpsStatusListener(mGpsStatusListener);
//			}
//			mLocationManager.requestLocationUpdates(
//					android.location.LocationManager.GPS_PROVIDER,
//					LocationManager.getInstance().getLocationInterval(),
//					LOCATION_DISTANCE, mLocationListener);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		if(Common.get().hasBaiDuLocation()){
//			BDmapLocationManager.getInstance().start();
//		}else {
//			AmapLocationManager.getInstance().start();
//		}
//		LogcatUtils.e(TAG, "GpsLocationManager start");
//	}
//
//	/**
//	 * 重启定位
//	 */
//	@Override
//	public synchronized void restart() {
//		try {
//			if(SettingEntry.getGpsUploadMode() == 1) {
//				mLocationManager.removeUpdates(mLocationListener);
//			}
//			mLocationManager.requestLocationUpdates(
//					android.location.LocationManager.GPS_PROVIDER,
//					LocationManager.getInstance().getLocationInterval(),
//					LOCATION_DISTANCE, mLocationListener);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		if(Common.get().hasBaiDuLocation()){
//			BDmapLocationManager.getInstance().restart();
//		}else {
//			AmapLocationManager.getInstance().restart();
//		}
//		LogcatUtils.e(TAG, "GpsLocationManager restart");
//	}
//
//	/**
//	 * 停止服务
//	 */
//	@Override
//	public synchronized void stop() {
//		try {
//			if(mLocationManager != null) {
//				mLocationManager.removeGpsStatusListener(mGpsStatusListener);
//				if(mLocationListener != null) {
//					mLocationManager.removeUpdates(mLocationListener);
//				}
//			}
//		} catch (Exception e) {
//		}
//
//		if(Common.get().hasBaiDuLocation()){
//			BDmapLocationManager.getInstance().stop();
//		}else {
//			AmapLocationManager.getInstance().stop();
//		}
//		LogcatUtils.e(TAG, "GpsLocationManager stop");
//	}
//
//	/**
//	 * 设置当前点位
//	 * @param l
//	 */
//	private synchronized void setCurrentLocation(Location location) {
//		if(ZCConfig.IS_DEBUG) {
//			if(LocationManager.DEBUG_LAT != 0 && LocationManager.DEBUG_LON != 0) {
//				if(location != null) {
//					location.setLatitude(LocationManager.DEBUG_LAT);
//					location.setLongitude(LocationManager.DEBUG_LON);
//				}
//			}
//		}
//		// update current
//		DLocation l = LocationUtils.gpsLocation2DLocation(location);
//		mCurrentLocation = l;
//		mCurrentLocationTimeMillis = System.currentTimeMillis();
//	}
//
//	/**
//	 * 获取当前点位
//	 * @return
//	 */
//	@Override
//	public synchronized DLocation getCurrentLocation() {
//		if(System.currentTimeMillis() - mCurrentLocationTimeMillis > INTERVAL_VALID_MAX) {
//			mCurrentLocationTimeMillis = 0;
//			mCurrentLocation = null;
//		}
//		if(LocationUtils.isValidLocation(mCurrentLocation)) {
//			return mCurrentLocation;
//		} else {
//			if(Common.get().hasBaiDuLocation()){
//				return BDmapLocationManager.getInstance().getCurrentLocation();
//			}else {
//				return AmapLocationManager.getInstance().getCurrentLocation();
//			}
//
//		}
//	}
//
//}
