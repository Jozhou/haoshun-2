//package com.carapp.utils.location;
//
//import android.os.Handler;
//import android.text.TextUtils;
//
//import com.baidu.location.BDLocation;
//import com.baidu.location.BDLocationListener;
//import com.baidu.location.LocationClient;
//import com.baidu.location.LocationClientOption;
//import com.szzc.ucar.application.DriverApp;
//import com.szzc.ucar.context.ZCConfig;
//import com.szzc.ucar.utils.LocationUtils;
//import com.szzc.ucar.utils.LogcatUtils;
//
//class BDmapLocationManager implements ILocationManager {
//
//    private static final String TAG = "DCarLocation";
//
//    private static BDmapLocationManager sInstance;
//    public synchronized static BDmapLocationManager getInstance() {
//        if (sInstance == null) {
//            sInstance = new BDmapLocationManager();
//        }
//        return sInstance;
//    }
//
//    private static final int MSGWHAT_RESTART = 1;
//    private static final int MSGDELAY_RESTART = 2000;
//
//    /**
//     * 定位错误最大次数
//     */
//    private static final int LOCATION_MAXERROR = 2;
//    /**
//     * 定位信息有效的最大时间间隔
//     */
//    private static final int INTERVAL_VALID_MAX = 10000;
//
//    /**
//     * 当前位置
//     */
//    private DLocation mCurrentLocation;
//    /**
//     * 上次定位时间
//     */
//    private long mCurrentLocationTimeMillis = 0;
//    /**
//     * 错误定位次数
//     */
//    private int mLocationErrorCount = 0;
//    /**
//     * BDMapLocationClient
//     */
//    private LocationClient mLocationClient = null;
//    /**
//     * BDMapLocationClientOption
//     */
//    private LocationClientOption mLocationOption = null;
//
//    /**
//     * BDMapLocationListener
//     */
//    private BDLocationListener mLocationListener = new BDLocationListener() {
//        @Override
//        public void onReceiveLocation(BDLocation location) {
//            if (location == null) {
//                return;
//            }
//            if (location.getLocType() != BDLocation.TypeGpsLocation &&
//                    location.getLocType() != BDLocation.TypeNetWorkLocation &&
//                    location.getLocType() != BDLocation.TypeOffLineLocation &&
//                       location.getLocType() != BDLocation.TypeCacheLocation) {
//                LogcatUtils.i(TAG, "DBlocation Error, ErrCode:"
//                        + location.getLocType() + ", errInfo:"
//                        + location.getLocTypeDescription());
//                return;
//            }
//            DLocation loc = LocationUtils.bdLocation2DLocation(location);
//            if (loc != null) {
//                LogcatUtils.i(TAG, "get BDMapLocation:" + loc.toString());
//            }
//            setCurrentLocation(loc);
//            if(!LocationUtils.isValidLocation(loc)) {
//                mLocationErrorCount++;
//                if(mLocationErrorCount >= LOCATION_MAXERROR) {
//                    mLocationErrorCount = 0;
//                    mHandler.removeCallbacksAndMessages(null);
//                    mHandler.sendEmptyMessageDelayed(MSGWHAT_RESTART, MSGDELAY_RESTART);
//                }
//            }
//        }
//
//    };
//
//    private Handler mHandler = new Handler() {
//        public void handleMessage(android.os.Message msg) {
//            switch (msg.what) {
//                case MSGWHAT_RESTART:
//                    restart();
//                    break;
//                default:
//                    break;
//            }
//        };
//    };
//
//    /**
//     * 获取gps卫星个数
//     * @return
//     */
//    @Override
//    public int getGpsSatelliteNumber() {
//        return 0;
//    }
//
//    /**
//     * 获取gps信号强度
//     * @return
//     */
//    @Override
//    public float getGpsSingnal() {
//        return 0f;
//    }
//
//    /**
//     * 启动服务
//     */
//    @Override
//    public synchronized void start() {
//        initializeLocationClient();
//        if(mLocationClient != null && !mLocationClient.isStarted()){
//            mLocationClient.start();
//        }
//        LogcatUtils.e(TAG, "BDmapLocationManager start");
//    }
//
//    /**
//     * 初始化定位client
//     */
//    private void initializeLocationClient() {
//        //初始化定位
//        mLocationClient = new LocationClient(DriverApp.getContext().getApplicationContext());
//        //给定位客户端对象设置定位参数
//        mLocationClient.setLocOption(getLocationClientOption());
//        //设置定位回调监听
//        mLocationClient.registerLocationListener(mLocationListener);
//    }
//
//    /**
//     * 初始化定位option
//     * @return
//     */
//    private LocationClientOption getLocationClientOption() {
//        if (mLocationOption == null) {
//            //初始化定位参数
//            mLocationOption = new LocationClientOption();
//            //设置定位模式为高精度模式，Battery_Saving为低功耗模式，Device_Sensors是仅设备模式
//            mLocationOption.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
//            mLocationOption.setOpenGps(true); // 打开gps
//            mLocationOption.setCoorType("bd09ll");//可选，默认gcj02，设置返回的定位结果坐标系，如果配合百度地图使用，建议设置为bd09ll;
//            mLocationOption.setIsNeedAddress(true);//可选，设置是否需要地址信息，默认不需要
//            mLocationOption.setIsNeedLocationDescribe(true);//可选，设置是否需要地址描述
//            mLocationOption.setNeedDeviceDirect(true);//可选，设置是否需要设备方向结果
//            mLocationOption.setLocationNotify(false);//可选，默认false，设置是否当gps有效时按照1S1次频率输出GPS结果
//            mLocationOption.setIgnoreKillProcess(true);//可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
//            mLocationOption.setIsNeedLocationDescribe(true);//可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
//            mLocationOption.setIsNeedLocationPoiList(true);//可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
//            mLocationOption.SetIgnoreCacheException(true);//可选，默认false，设置是否收集CRASH信息，默认收集
//            mLocationOption.setIsNeedAltitude(true);//可选，默认false，设置定位时是否需要海拔信息，默认不需要，除基础定位版本都可用
//            mLocationOption.setTimeOut(INTERVAL_VALID_MAX);
//        }
//        //可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
//        mLocationOption.setScanSpan(LocationManager.getInstance().getLocationInterval());
//        return mLocationOption;
//    }
//
//    /**
//     * 重启定位
//     */
//    @Override
//    public synchronized void restart() {
//        if (mLocationClient != null && mLocationClient.isStarted()) {
//            mLocationClient.stop();
//        }
//        initializeLocationClient();
//        mLocationClient.start();
//        LogcatUtils.e(TAG, "BDmapLocationManager restart");
//    }
//
//    /**
//     * 停止服务
//     */
//    @Override
//    public synchronized void stop() {
//        if(mLocationClient != null && mLocationClient.isStarted()){
//            mLocationClient.unRegisterLocationListener(mLocationListener);
//            mLocationClient.stop();
//        }
//        LogcatUtils.e(TAG, "BDmapLocationManager stop");
//    }
//
//    /**
//     * 设置当前点位
//     * @param location
//     */
//    private synchronized void setCurrentLocation(DLocation location) {
//        if(ZCConfig.IS_DEBUG) {
//            if(location != null) {
//                if(LocationManager.DEBUG_LAT != 0 && LocationManager.DEBUG_LON != 0) {
//                    location.setBaiDuLatitude(LocationManager.DEBUG_LAT);
//                    location.setBaiDuLongitude(LocationManager.DEBUG_LON);
//                }
//                if (!TextUtils.isEmpty(LocationManager.CITYCODE)) {
//                    location.setCityCode(LocationManager.CITYCODE);
//                }
//            }
//        }
//        // update current
//        mCurrentLocation = location;
//        mCurrentLocationTimeMillis = System.currentTimeMillis();
//    }
//
//    /**
//     * 获取当前点位
//     * @return
//     */
//    @Override
//    public synchronized DLocation getCurrentLocation() {
//        if(System.currentTimeMillis() - mCurrentLocationTimeMillis > INTERVAL_VALID_MAX) {
//            mCurrentLocationTimeMillis = 0;
//            mCurrentLocation = null;
//        }
//        return mCurrentLocation;
//    }
//}
