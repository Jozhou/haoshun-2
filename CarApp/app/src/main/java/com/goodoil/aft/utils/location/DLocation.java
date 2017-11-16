package com.goodoil.aft.utils.location;

import android.location.Location;
import android.os.Parcel;

public class DLocation extends Location{

    public static final Creator<DLocation> CREATOR =
            new Creator<DLocation>() {
                @Override
                public DLocation createFromParcel(Parcel in) {
                    String provider = in.readString();
                    DLocation l = new DLocation(provider);
                    return l;
                }

                @Override
                public DLocation[] newArray(int size) {
                    return new DLocation[size];
                }
            };

    public static final int BAIDU_LOCATION = 2;//百度地图打点
    public static final int AMAP_LOCATION = 1;//高德地图打点

    private int sourceID;
    private double mBaiDuLatitude;
    private double mBaiDuLongitude;

    public DLocation(String provider) {
        super(provider);
    }

    public int getSourceID() {
        return sourceID;
    }

    public void setSourceID(int sourceID) {
        this.sourceID = sourceID;
    }

    public double getBaiDuLatitude() {
        return mBaiDuLatitude;
    }

    public void setBaiDuLatitude(double mBaiDuLatitude) {
        this.mBaiDuLatitude = mBaiDuLatitude;
    }

    public double getBaiDuLongitude() {
        return mBaiDuLongitude;
    }

    public void setBaiDuLongitude(double mBaiDuLongitude) {
        this.mBaiDuLongitude = mBaiDuLongitude;
    }

    public static final int LOCATION_SUCCESS = 0;
    public static final int ERROR_CODE_INVALID_PARAMETER = 1;
    public static final int ERROR_CODE_FAILURE_WIFI_INFO = 2;
    public static final int ERROR_CODE_FAILURE_LOCATION_PARAMETER = 3;
    public static final int ERROR_CODE_FAILURE_CONNECTION = 4;
    public static final int ERROR_CODE_FAILURE_PARSER = 5;
    public static final int ERROR_CODE_FAILURE_LOCATION = 6;
    public static final int ERROR_CODE_FAILURE_AUTH = 7;
    public static final int ERROR_CODE_UNKNOWN = 8;
    public static final int ERROR_CODE_FAILURE_INIT = 9;
    public static final int ERROR_CODE_SERVICE_FAIL = 10;
    public static final int ERROR_CODE_FAILURE_CELL = 11;
    public static final int ERROR_CODE_FAILURE_LOCATION_PERMISSION = 12;
    public static final int LOCATION_TYPE_GPS = 1;
    public static final int LOCATION_TYPE_SAME_REQ = 2;
    /** @deprecated */
    public static final int LOCATION_TYPE_FAST = 3;
    public static final int LOCATION_TYPE_FIX_CACHE = 4;
    public static final int LOCATION_TYPE_WIFI = 5;
    public static final int LOCATION_TYPE_CELL = 6;
    public static final int LOCATION_TYPE_AMAP = 7;
    public static final int LOCATION_TYPE_OFFLINE = 8;
    private String a = "";
    private String b = "";
    private String c = "";
    private String d = "";
    private String e = "";
    private String f = "";
    private String g = "";
    private String h = "";
    private String i = "";
    private String j = "";
    private String k = "";
    private boolean l = true;
    private int m = 0;
    private String n = "success";
    private String o = "";
    private int p = 0;
    private double q = 0.0D;
    private double r = 0.0D;
    private int s = 0;
    private String t = "";


    public int getLocationType() {
        return this.p;
    }

    public void setLocationType(int var1) {
        this.p = var1;
    }

    public String getLocationDetail() {
        return this.o;
    }

    public void setLocationDetail(String var1) {
        this.o = var1;
    }

    public int getErrorCode() {
        return this.m;
    }

    public void setErrorCode(int var1) {
        if(this.m == 0) {
            switch(var1) {
                case 0:
                    this.n = "success";
                    break;
                case 1:
                    this.n = "重要参数为空";
                    break;
                case 2:
                    this.n = "WIFI信息不足";
                    break;
                case 3:
                    this.n = "请求参数获取出现异常";
                    break;
                case 4:
                    this.n = "网络连接异常";
                    break;
                case 5:
                    this.n = "解析XML出错";
                    break;
                case 6:
                    this.n = "定位结果错误";
                    break;
                case 7:
                    this.n = "KEY错误";
                    break;
                case 8:
                    this.n = "其他错误";
                    break;
                case 9:
                    this.n = "初始化异常";
                    break;
                case 10:
                    this.n = "定位服务启动失败";
                    break;
                case 11:
                    this.n = "错误的基站信息，请检查是否插入SIM卡";
                    break;
                case 12:
                    this.n = "缺少定位权限";
            }

            this.m = var1;
        }
    }

    public String getErrorInfo() {
        return this.n;
    }

    public void setErrorInfo(String var1) {
        this.n = var1;
    }

    public String getCountry() {
        return this.h;
    }

    public void setCountry(String var1) {
        this.h = var1;
    }

    /** @deprecated */
    public String getRoad() {
        return this.i;
    }

    /** @deprecated */
    public void setRoad(String var1) {
        this.i = var1;
    }

    public String getAddress() {
        return this.f;
    }

    public void setAddress(String var1) {
        this.f = var1;
    }

    public String getProvince() {
        return this.a;
    }

    public void setProvince(String var1) {
        this.a = var1;
    }

    public String getCity() {
        return this.b;
    }

    public void setCity(String var1) {
        this.b = var1;
    }

    public String getDistrict() {
        return this.c;
    }

    public void setDistrict(String var1) {
        this.c = var1;
    }

    public String getCityCode() {
        return this.d;
    }

    public void setCityCode(String var1) {
        this.d = var1;
    }

    public String getAdCode() {
        return this.e;
    }

    public void setAdCode(String var1) {
        this.e = var1;
    }

    public String getPoiName() {
        return this.g;
    }

    public void setPoiName(String var1) {
        this.g = var1;
    }

    public double getLatitude() {
        return this.q;
    }

    public void setLatitude(double var1) {
        this.q = var1;
    }

    public double getLongitude() {
        return this.r;
    }

    public void setLongitude(double var1) {
        this.r = var1;
    }

    public int getSatellites() {
        return this.s;
    }

    public void setSatellites(int var1) {
        this.s = var1;
    }

    public String getStreet() {
        return this.j;
    }

    public void setStreet(String var1) {
        this.j = var1;
    }

    public String getStreetNum() {
        return this.k;
    }

    public void setNumber(String var1) {
        this.k = var1;
    }

    public void setOffset(boolean var1) {
        this.l = var1;
    }

    public boolean isOffset() {
        return this.l;
    }

    public String getAoiName() {
        return this.t;
    }

    public void setAoiName(String var1) {
        this.t = var1;
    }

    public String toString() {
        StringBuffer var1 = new StringBuffer();
        var1.append("time=" + this.getTime());
        var1.append("latitude=" + this.q);
        var1.append("longitude=" + this.r);
        var1.append("province=" + this.a + "#");
        var1.append("city=" + this.b + "#");
        var1.append("district=" + this.c + "#");
        var1.append("cityCode=" + this.d + "#");
        var1.append("adCode=" + this.e + "#");
        var1.append("address=" + this.f + "#");
        var1.append("country=" + this.h + "#");
        var1.append("road=" + this.i + "#");
        var1.append("poiName=" + this.g + "#");
        var1.append("street=" + this.j + "#");
        var1.append("streetNum=" + this.k + "#");
        var1.append("aoiName=" + this.t + "#");
        var1.append("errorCode=" + this.m + "#");
        var1.append("errorInfo=" + this.n + "#");
        var1.append("locationDetail=" + this.o + "#");
        var1.append("locationType=" + this.p);
        return var1.toString();
    }

    public float getAccuracy() {
        return super.getAccuracy();
    }

    public float getBearing() {
        return super.getBearing();
    }

    public double getAltitude() {
        return super.getAltitude();
    }

    public float getSpeed() {
        return super.getSpeed();
    }

    public String getProvider() {
        return super.getProvider();
    }


    public double getCurrentLatitude() {
        if(Common.get().getMapType() == BAIDU_LOCATION) {
            return this.getBaiDuLatitude();
        }else{
            return this.getLatitude();
        }
    }

    public double getCurrentLongitude() {
        if(Common.get().getMapType() == BAIDU_LOCATION) {
            return this.getBaiDuLongitude();
        }else{
            return this.getLongitude();
        }
    }
}
