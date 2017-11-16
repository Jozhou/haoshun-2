package com.goodoil.aft.utils.location;

public interface ILocationManager {

	DLocation getCurrentLocation();
	void start();
	void restart();
	void stop();
	int getGpsSatelliteNumber();
	float getGpsSingnal();
	
}
