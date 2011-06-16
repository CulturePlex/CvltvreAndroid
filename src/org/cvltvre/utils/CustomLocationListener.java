/**
 * 
 */
package org.cvltvre.utils;

import java.util.List;

import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;

/**
 * @author Pencerval
 *
 */
public class CustomLocationListener implements LocationListener{

	private static LocationManager locationManager;
	private static String best;
	public static Double longitude;
	public static Double latitude;
	

	public void init(Context context) {
		locationManager = (LocationManager) context.getSystemService(context.LOCATION_SERVICE);
		Criteria criteria = new Criteria();
		best = locationManager.getBestProvider(criteria, true);
		List<String> providers=locationManager.getProviders(true);
		Location location = locationManager.getLastKnownLocation(best);
		if(providers.contains(locationManager.GPS_PROVIDER)){
			best=locationManager.GPS_PROVIDER;
		}
		locationManager.requestLocationUpdates(best, 60000, 0, this);
		while(location==null){
			location = locationManager.getLastKnownLocation(best);
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		longitude=location.getLongitude();
		latitude=location.getLatitude();
	}

	public void onLocationChanged(Location location) {
		latitude=location.getLatitude();
		longitude=location.getLongitude();
		
	}

	public void onProviderDisabled(String provider) {
		if(provider.equals(LocationManager.GPS_PROVIDER)){
			best=locationManager.getBestProvider(new Criteria(), true);
			locationManager.removeUpdates(this);
			locationManager.requestLocationUpdates(best, 60000, 0, this);
		}
	}

	public void onProviderEnabled(String provider) {
		if(provider.equals(LocationManager.GPS_PROVIDER)){
			best=LocationManager.GPS_PROVIDER;
			locationManager.removeUpdates(this);
			locationManager.requestLocationUpdates(best, 60000, 0, this);
		}
	}

	public void onStatusChanged(String provider, int status, Bundle extras) {
	
	}

}
