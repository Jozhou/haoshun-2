package com.goodoil.aft.utils.location;

import android.app.PendingIntent;
import android.app.PendingIntent.CanceledException;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.net.Uri;

import com.amap.api.location.AMapLocation;
import com.amap.api.maps.model.LatLng;

/**
 * 导航定位工具类
 * @author Administrator
 *
 */
public class LocationUtils {

	public static final String PROVIDER_GPS = "gps";
	public static final String PROVIDER_LBS = "lbs";
	public static final String ISPHONE_GPS = "isphonegps";

	/**
	 * 米转千米
	 * @param meter
	 * @return
	 */
	public static String convertMeterToKm(int meter) {
		if (meter != -1) {
			return String.format("  %.2f公里", ((float) meter / 1000));
		}
		return "";
	}

//	/**
//	 * 获取规划路径
//	 * @param aMapNaviGuides
//	 * @return
//	 */
//	public static String getGuideString(List<AMapNaviGuide> aMapNaviGuides) {
//
//		int center = aMapNaviGuides.size() / 2;
//		int distance = 10000;
//		String firstAddress = "";
//		String centerAddress = "";
//		String lastAddress = "";
//
//		int index = 0;
//
//		List<String> addresses = new ArrayList<String>();
//		for (Iterator<AMapNaviGuide> iterator = aMapNaviGuides.iterator(); iterator.hasNext();) {
//			AMapNaviGuide aMapNaviGuide = (AMapNaviGuide) iterator.next();
//			if (!TextUtils.isEmpty(aMapNaviGuide.getName())) {
//				addresses.add(aMapNaviGuide.getName());
//				if (index == 0) {
//					firstAddress = aMapNaviGuide.getName();
//				}
//
//				if (Math.abs(center - index) < distance) {
//					distance = Math.abs(center - index);
//					centerAddress = aMapNaviGuide.getName();
//				}
//				lastAddress = aMapNaviGuide.getName();
//				index++;
//
//			}
//		}
//
//		StringBuffer buffer = new StringBuffer();
//		if (!StringUtil.isEmpty(firstAddress)) {
//			buffer.append(firstAddress);
//			buffer.append("->" + centerAddress);
//			buffer.append("->" + lastAddress);
//		}
//		return buffer.toString();
//	}

	/**
	 * 监测gps开关
	 * @param context
	 * @return
	 */
	public static boolean checkGPSStatus(Context context) {
		try {
			LocationManager locationManager = (LocationManager) context
					.getSystemService(Context.LOCATION_SERVICE);
			// 通过GPS卫星定位，定位级别可以精确到街（通过24颗卫星定位，在室外和空旷的地方定位准确、速度快）
			boolean gps = locationManager
					.isProviderEnabled(LocationManager.GPS_PROVIDER);
			// 通过WLAN或移动网络(3G/2G)确定的位置（也称作AGPS，辅助GPS定位。主要用于在室内或遮盖物（建筑群或茂密的深林等）密集的地方定位）
			//		boolean network = locationManager
			//				.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
			if (gps) {
				return true;
			}
			return false;
		} catch (RuntimeException e) {
			return false;
		}
	}

//	/**
//	 * 监测虚拟定位开关
//	 * @param context
//	 * @return
//	 */
//	public static boolean checkTestGPSStatus(Context context) {
//		try {
//			ApiEnvironment env = ZCConfig.getApiEnv();
//			//if (env == ApiEnvironment.pre || env == ApiEnvironment.product) {
//				return Settings.Secure.getInt(context.getContentResolver(),
//						Settings.Secure.ALLOW_MOCK_LOCATION, 0) != 0;
//			//} else {
//			//	return false;
//			//}
//		} catch (Exception e) {
//			return false;
//		}
//	}

	/**
	 * 启用gps
	 * @param context
	 */
	public static void enableGPS(Context context) {
		Intent GPSIntent = new Intent();
		GPSIntent.setClassName("com.android.settings",
				"com.android.settings.widget.SettingsAppWidgetProvider");
		GPSIntent.addCategory("android.intent.category.ALTERNATIVE");
		GPSIntent.setData(Uri.parse("custom:3"));
		try {
			PendingIntent.getBroadcast(context, 0, GPSIntent, 0).send();
		} catch (CanceledException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获取gps点位的provider
	 * @param provider
	 * @return
	 */
	public static String getProvider(String provider) {
		String[] sourceStrArray = provider.split("-");
		if(sourceStrArray.length == 0) {
			return provider;
		}
		if(sourceStrArray.length >= 0) {
			return  sourceStrArray[sourceStrArray.length - 1];
		}
		return "";
	}

	/**
	 * 转变gps location至amap location
	 * @param l
	 * @return
	 */
//	public static DLocation gpsLocation2DLocation(Location l) {
//		try {
//			if(l == null) {
//				return null;
//			}
//
//			// 执行转换操作
//			LatLng desLatLng = CoordinateUtil.transform(l.getLatitude(),l.getLongitude());
//
//			DLocation location = new DLocation(l.getProvider());
//			location.setLatitude(desLatLng.latitude);
//			location.setLongitude(desLatLng.longitude);
//			location.setAccuracy(l.getAccuracy());
//			location.setBearing(l.getBearing());
//			location.setAltitude(l.getAltitude());
//			location.setSpeed(l.getSpeed());
//			location.setTime(l.getTime());
//
//			com.baidu.mapapi.model.LatLng bdLatLng = CoordinateUtil.transformToBaidu(l.getLatitude(), l.getLongitude());
//			location.setSourceID(Common.get().getMapType());
//			location.setBaiDuLatitude(bdLatLng.latitude);
//			location.setBaiDuLongitude(bdLatLng.longitude);
//
//			Bundle bundle = new Bundle();
//			bundle.putBoolean(ISPHONE_GPS, true);
//			location.setExtras(bundle);
//			return location;
//		} catch (Exception e) {
//			e.printStackTrace();
//			return null;
//		}
//	}
//
	public static DLocation aMapLocation2DLocation(AMapLocation l) {
		DLocation dLocation = new DLocation(l.getProvider());

//		com.baidu.mapapi.model.LatLng bdLatLng = CoordinateUtil.amap2Baidu(l.getLatitude(), l.getLongitude());
		dLocation.setSourceID(DLocation.AMAP_LOCATION);
//		dLocation.setBaiDuLatitude(bdLatLng.latitude);
//		dLocation.setBaiDuLongitude(bdLatLng.longitude);

		dLocation.setSpeed(l.getSpeed());
		dLocation.setAltitude(l.getAltitude());
		dLocation.setBearing(l.getBearing());
		dLocation.setAccuracy(l.getAccuracy());

		dLocation.setLocationDetail(l.getLocationDetail());
		dLocation.setLocationType(l.getLocationType());

		dLocation.setLatitude(l.getLatitude());
		dLocation.setLongitude(l.getLongitude());
		dLocation.setAddress(l.getAddress());
		dLocation.setCity(l.getCity());
		dLocation.setCityCode(l.getCityCode());
		dLocation.setProvince(l.getProvince());
		dLocation.setDistrict(l.getDistrict());
		dLocation.setCountry(l.getCountry());
		dLocation.setErrorInfo(l.getErrorInfo());
		dLocation.setErrorCode(l.getErrorCode());
		dLocation.setStreet(l.getStreet());
		dLocation.setTime(l.getTime());
		dLocation.setSatellites(l.getSatellites());
		dLocation.setAdCode(l.getAdCode());
		dLocation.setAoiName(l.getAoiName());
		dLocation.setNumber(l.getStreetNum());
		dLocation.setPoiName(l.getPoiName());
		return dLocation;
	}

	/**
	 * 转变bd location至amap location
	 * @param l
	 * @return
	 */
//	public static DLocation bdLocation2DLocation(BDLocation l) {
//		try {
//			if(l == null) {
//				return null;
//			}
//			LatLng desLatLng = CoordinateUtil.baidu2Amap(l.getLatitude(),l.getLongitude());
//			DLocation location = null;
//			if (l.getLocType() == BDLocation.TypeGpsLocation) {
//				location = new DLocation(PROVIDER_GPS);
//			}else{
//				location = new DLocation(PROVIDER_LBS);
//			}
//			location.setSourceID(DLocation.BAIDU_LOCATION);
//			location.setBaiDuLatitude(l.getLatitude());
//			location.setBaiDuLongitude(l.getLongitude());
//			location.setSpeed(l.getSpeed());
//			location.setAltitude(l.getAltitude());
//			location.setBearing(l.getDirection());
//			location.setAccuracy(l.getRadius());
//
//			location.setLatitude(desLatLng.latitude);
//			location.setLongitude(desLatLng.longitude);
//			location.setAddress(l.getAddrStr());
//			location.setCity(l.getCity());
//			location.setCityCode(l.getCityCode());
//			location.setProvince(l.getProvince());
//			location.setDistrict(l.getDistrict());
//			location.setCountry(l.getCountry());
//			location.setErrorInfo(l.getLocTypeDescription());
//			location.setErrorCode(l.getLocType());
//			location.setStreet(l.getStreet());
//			location.setTime(DateUtils.formatDate(l.getTime()));
//			location.setSatellites(l.getSatelliteNumber());
//			location.setLocationDetail(l.getLocTypeDescription());
//			location.setLocationType(l.getLocType());
//
//			return location;
//		} catch (Exception e) {
//			e.printStackTrace();
//			return null;
//		}
//	}

	/**
	 * 转变gps location至amap location
	 * @param l
	 * @return
	 */
//	public static AMapLocation naviLocation2AMapLocation(AMapNaviLocation l) {
//		try {
//			if(l == null) {
//				return null;
//			}
//			AMapLocation location = new AMapLocation(PROVIDER_GPS);
//			location.setLatitude(l.getCoord().getLatitude());
//			location.setLongitude(l.getCoord().getLongitude());
//			location.setAccuracy(l.getAccuracy());
//			location.setAltitude(l.getAltitude());
//			location.setBearing(l.getBearing());
//			location.setSpeed(l.getSpeed());
//			location.setTime(l.getTime());
//			return location;
//		} catch (Exception e) {
//			e.printStackTrace();
//			return null;
//		}
//	}

	/**
	 * 检查location是否为有效定位
	 * @param l
	 * @return
	 */
	public static boolean isValidLocation(DLocation l) {
		try {
			if (l == null) {
				return false;
			}
			return isValidLatAndLon(l.getCurrentLatitude(), l.getCurrentLongitude());
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * 检查location是否为有效定位
	 * @param l
	 * @return
	 */
	public static boolean isValidLatLng(LatLng l) {
		try {
			if (l == null) {
				return false;
			}
			return isValidLatAndLon(l.latitude, l.longitude);
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * 检查location是否为有效定位
	 * @param l
	 * @return
	 */
//	public static boolean isValidLatnEntry(LatnEntry l) {
//		try {
//			if (l == null) {
//				return false;
//			}
//			if(Common.get().hasBaiDuLocation()) {
//				return isValidLatAndLon(l.bd_lat, l.bd_lon);
//			}else{
//				return isValidLatAndLon(l.lat, l.lon);
//			}
//		} catch (Exception e) {
//			return false;
//		}
//	}

	/**
	 * 检查location是否为有效定位
	 * @return
	 */
	public static boolean isValidLatAndLon(double lat, double lon) {
		if (lon < 73.4d || lon > 135.2d) {
			return false;
		}
		if (lat < 3.5d || lat > 53.3d) {
			return false;
		}
		return true;
	}

}
