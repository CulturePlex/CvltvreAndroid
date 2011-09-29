/**
 * 
 */
package com.cvltvre.view;

import java.util.List;

import android.graphics.drawable.Drawable;
import android.os.Bundle;

import com.cvltvre.R;
import com.cvltvre.utils.CustomLocationListener;
import com.cvltvre.utils.CustomMuseumOverlay;
import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;

public class MainMapActivity extends MapActivity {
	
	public static MapView mapView;
	private static Drawable personDrawable=LoadingActivity.loadingActivity.getResources().getDrawable(R.drawable.person);
	public static List<Overlay> mapOverlays;
	
	//public static CustomMuseumOverlay museumOverlay= new CustomMuseumOverlay(museumDrawable);
	public static CustomMuseumOverlay personOverlay= new CustomMuseumOverlay(personDrawable);

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
		OverlayItem overlayItem=new OverlayItem(new GeoPoint(latitude, longitude), "user", "user");
		MainMapActivity.personOverlay.addOverlay(overlayItem);
		MainMapActivity.mapOverlays.add(MainMapActivity.personOverlay);
		
	}

	@Override
	protected boolean isRouteDisplayed() {
		return false;
	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		LoadingActivity.startListeners();
	}
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		LoadingActivity.killListeners();
	}

}
