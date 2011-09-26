/**
 * 
 */
package org.cvltvre.utils;

import java.util.List;

import org.cvltvre.view.LoadingActivity;

import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Looper;

/**
 * @author Pencerval
 *
 */
public class CustomLocationListener implements LocationListener, Runnable{

	public static LocationManager locationManager;
	public static String best;
	public static Location location;
	private static Context context;
	
	public CustomLocationListener(Context context) {
		super();
		CustomLocationListener.context=context;
	}
	

	public void onLocationChanged(Location location) {
		CustomLocationListener.location=location;
		LoadingActivity.locationRetrieve=true;
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



	public void run() {
		Looper.prepare();
		locationManager=(LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
		

		Criteria criteria = new Criteria();
		best = locationManager.getBestProvider(criteria, true);
		List<String> providers=locationManager.getProviders(true);
		
		if(best==null && providers != null && providers.size()>0){
			best=providers.get(0);
		}
		Location location = locationManager.getLastKnownLocation(best);
		if(providers.contains(LocationManager.GPS_PROVIDER)){
			best=LocationManager.GPS_PROVIDER;
		}
		if(location==null){
			location = locationManager.getLastKnownLocation(best);
		}
		locationManager.requestLocationUpdates(best, 2000, 0, this);
		CustomLocationListener.location=location;
		while(CustomLocationListener.location==null){
			location = locationManager.getLastKnownLocation(best);
			CustomLocationListener.location=location;
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		LoadingActivity.locationRetrieve=true;
	}

}
