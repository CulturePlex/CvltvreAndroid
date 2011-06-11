/**
 * 
 */
package org.cvltvre.utils;

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
	

	public static void init(Context context) {
		locationManager = (LocationManager) context.getSystemService(context.LOCATION_SERVICE);
		Criteria criteria = new Criteria();
		best = locationManager.getBestProvider(criteria, true);
		Location location = locationManager.getLastKnownLocation(best);
		longitude=location.getLongitude();
		latitude=location.getLatitude();
	}

	/*@Override
	protected void onResume() {
		super.onResume();
		// Start updates (doc recommends delay >= 60000 ms)
		mgr.requestLocationUpdates(best, 15000, 1, this);
	}

	@Override
	protected void onPause() {
		super.onPause();
		// Stop updates to save power while app paused
		mgr.removeUpdates(this);
	}
*/
	public void onLocationChanged(Location location) {
		latitude=location.getLatitude();
		longitude=location.getLongitude();
		
	}

	public void onProviderDisabled(String provider) {

	}

	public void onProviderEnabled(String provider) {
	
	}

	public void onStatusChanged(String provider, int status, Bundle extras) {
	
	}

}
