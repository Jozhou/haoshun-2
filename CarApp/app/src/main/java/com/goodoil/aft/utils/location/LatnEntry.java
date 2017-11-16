package com.goodoil.aft.utils.location;

import com.corelibrary.models.entry.BaseEntry;

import org.json.JSONException;
import org.json.JSONObject;

public class LatnEntry extends BaseEntry {

	private static final long serialVersionUID = -737356996258567096L;

	public int sourceID = 0;

	public double bd_lat = 0d;

	public double bd_lon = 0d;

	public double lat = 0d;

	public double lon = 0d;

	public float accuracy = -1f;

	public String adCode = "";

	public String address = "";

	public double altitude = 0d;

	public float bearing = 0f;

	public String city = "";

	public String cityCode = "";

	public String country = "";

	public String district = "";

	public String floor = "";

	public String poiId = "";

	public String poiName = "";

	public String provider = "";

	public String province = "";

	public String road = "";

	public float speed = 0f;

	public String street = "";

	public long time = 0L;

	public long localTime = 0L;

	public String orderId = "";

	public String toJsonString() {
		JSONObject j = new JSONObject();
		try {
			j.put("time", time);
			j.put("localTime", localTime);
			j.put("lat", lat);
			j.put("lon", lon);
			j.put("sourceID", sourceID);
			j.put("provider", provider);
			j.put("accuracy", accuracy);
			j.put("bearing", bearing);
			j.put("speed", speed);
			j.put("altitude", altitude);
			j.put("bdlat", bd_lat);
			j.put("bdlon", bd_lon);

		} catch (JSONException e) {
			e.printStackTrace();
		}
		return j.toString();
	}

	@Override
	public boolean equals(Object o) {
		if (o != null && o instanceof LatnEntry) {
			LatnEntry other = (LatnEntry) o;
			return other.lat == this.lat && other.lon == this.lon;
		}
		return super.equals(o);
	}

}
