/*
 * Author : ErVaLt / techwavedev.com
 * Description : TabLayout Andorid App
 */
package org.cvltvre.view;

import java.util.ArrayList;
import java.util.List;

import org.cvltvre.R;
import org.cvltvre.R.layout;
import org.cvltvre.utils.CustomLocationListener;
import org.cvltvre.utils.CustomMuseumOverlay;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

public class MainMapActivity extends MapActivity {
	
	MapView mapView;
	private static Drawable drawable=LoadingActivity.loadingActivity.getResources().getDrawable(R.drawable.museum);
	public static List<Overlay> mapOverlays;
	
	public static CustomMuseumOverlay itemizedOverlay= new CustomMuseumOverlay(drawable);;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mainmap);
		mapView = (MapView) findViewById(R.id.mapview);
		mapView.setBuiltInZoomControls(true);
		mapOverlays=mapView.getOverlays();
		
		MapController controller=mapView.getController();
		int latitude = (int) (CustomLocationListener.location.getLatitude() * 1000000);
		int longitude = (int) (CustomLocationListener.location.getLongitude() * 1000000);
		controller.setCenter(new GeoPoint(latitude , longitude));
		controller.setZoom(10);
		Thread thread= new Thread(new Runnable() {
			
			public void run() {
				int latitude = (int) (CustomLocationListener.location.getLatitude() * 1000000);
				int longitude = (int) (CustomLocationListener.location.getLongitude() * 1000000);
				
				OverlayItem overlayItem=new OverlayItem(new GeoPoint(latitude, longitude), "ola", "adios");
				MainMapActivity.itemizedOverlay.addOverlay(overlayItem);
				MainMapActivity.mapOverlays.add(MainMapActivity.itemizedOverlay);
				
			}
		});
		thread.start();
	}

	@Override
	protected boolean isRouteDisplayed() {
		return false;
	}

}
