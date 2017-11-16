package com.goodoil.aft.utils.location;

import com.corelibrary.models.entry.BaseEntry;

public class Common extends BaseEntry {

	private static final long serialVersionUID = 8529163357377577977L;
	private static final String TAG = Common.class.getSimpleName();

	public static final int MSG_STATE_CHANGETO_TEMPWORKEND = 0;
	public static final int MSG_STATE_REQUEST_EMPTY_DISPATCH = 3; //主动空车调度
	
	private static final byte[] mLock = new byte[0];
	private static Common mInstance = null;

	public final static Common get() {
		synchronized (mLock) {
			if (mInstance == null) {
				mInstance = new Common();
			}
			return mInstance;
		}
	}
	
	private Common() {
		
	}
	
	private boolean isShowingPush = false;
    private boolean isStartExternalFromPoi = false;
    private boolean isStartExternalFromOrder = false;
    private boolean isStartExternalFromEmpty = false;
    private boolean isStartExternalFromRepair = false;

	/**
	 * 是否正在展示推送订单页面
	 * @param isShowingOrder
	 */
	public synchronized void setIsShowingPush(boolean isShowingOrder) {
		this.isShowingPush = isShowingOrder;
	}

    public synchronized boolean isShowingPush() {
        return this.isShowingPush;
    }
	

//    public synchronized void startExternalNav(int from) {
//        switch (from) {
//            case BaseNavManager.EXTERNAL_NAV_FOR_POI:
//                isStartExternalFromPoi = true;
//                break;
//            case BaseNavManager.EXTERNAL_NAV_FOR_ORDER:
//                isStartExternalFromOrder = true;
//                break;
//            case BaseNavManager.EXTERNAL_NAV_FOR_EMPTYDISPATCH:
//                isStartExternalFromEmpty = true;
//                break;
//            case BaseNavManager.EXTERNAL_NAV_FOR_REPAIRTASK:
//                isStartExternalFromRepair = true;
//                break;
//        }
//
//    }

    public synchronized void resetAllExternalNav(){
        isStartExternalFromPoi = false;
        isStartExternalFromOrder = false;
        isStartExternalFromEmpty = false;
        isStartExternalFromRepair = false;
    }

//    public synchronized void resetExternalNav(int from) {
//        switch (from) {
//            case BaseNavManager.EXTERNAL_NAV_FOR_POI:
//                isStartExternalFromPoi = false;
//                break;
//            case BaseNavManager.EXTERNAL_NAV_FOR_ORDER:
//                isStartExternalFromOrder = false;
//                break;
//            case BaseNavManager.EXTERNAL_NAV_FOR_EMPTYDISPATCH:
//                isStartExternalFromEmpty = false;
//                break;
//            case BaseNavManager.EXTERNAL_NAV_FOR_REPAIRTASK:
//                isStartExternalFromRepair = false;
//                break;
//        }
//    }

    public synchronized boolean isStartExternalNav() {
        return isStartExternalFromPoi || isStartExternalFromOrder || isStartExternalFromEmpty || isStartExternalFromRepair;
    }

	/**
	 * 是否含有百度地图定位
	 * @return
	 */
//	public boolean hasBaiDuLocation(){
//		return SettingEntry.isBDMap();
//	}

	/**
	 * 是否含有高德地图定位
	 * @return
	 */
//	public boolean hasAmapLocation(){
//		return !SettingEntry.isBDMap();
//	}

	/**
	 * 获取当前地图类型
	 * @return
	 */
	public int getMapType(){
//		if(hasBaiDuLocation()){
//			return DLocation.BAIDU_LOCATION;
//		}else{
//			return DLocation.AMAP_LOCATION;
//		}
		return DLocation.AMAP_LOCATION;
	}

//    public NaviRoute getCurrEnd(NaviRoute route) {
//        NaviRoute naviroute = route.clone();
//        if(hasAmapLocation()){
//            if(SettingEntry.getExternalNavType() == SettingEntry.EXTERNAL_NAVITYPE_BAIDU) {
//                com.baidu.mapapi.model.LatLng bdLatLng =
//                        CoordinateUtil.amap2Baidu(route.getLatitude(), route.getLongitude());
//                naviroute = new NaviRoute(bdLatLng.latitude, bdLatLng.longitude, route.getTitle());
//            }
//        } else {
//            if(SettingEntry.getExternalNavType() == SettingEntry.EXTERNAL_NAVITYPE_AMAP
//                    || SettingEntry.getExternalNavType() == SettingEntry.EXTERNAL_NAVITYPE_DEFAULT){
//                com.amap.api.maps.model.LatLng ampLatLng =
//                        CoordinateUtil.baidu2Amap(route.getLatitude(),route.getLongitude());
//                naviroute = new NaviRoute(ampLatLng.latitude, ampLatLng.longitude, route.getTitle());
//            }
//        }
//        return naviroute;
//    }

}
