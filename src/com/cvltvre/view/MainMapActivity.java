/**
 * 
 */
package com.cvltvre.view;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import android.graphics.drawable.Drawable;
import android.os.Bundle;

import com.cvltvre.R;
import com.cvltvre.utils.CustomLocationListener;
import com.cvltvre.utils.CustomMuseumOverlay;
import com.cvltvre.utils.MapMuseumUpdater;
import com.cvltvre.vo.MuseumVO;
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
		
		MapController controller=mapView.getController();
		int latitude = (int) (CustomLocationListener.location.getLatitude() * 1000000);
		int longitude = (int) (CustomLocationListener.location.getLongitude() * 1000000);
		controller.setCenter(new GeoPoint(latitude , longitude));
		controller.setZoom(10);
		if(mapOverlays==null){
			mapOverlays=mapView.getOverlays();
		}else{
			mapOverlays=mapView.getOverlays();	
			OverlayItem museumOverlayItem;
			CustomMuseumOverlay customMuseumOverlay=new CustomMuseumOverlay(MapMuseumUpdater.museumDrawable);
			for(MuseumVO museumVO:LoadingActivity.museumVOs){
				String[] coords=museumVO.getMap_options().split(",");
				latitude = (int) (new BigDecimal(coords[1]).doubleValue() * 1000000);
				longitude = (int) (new BigDecimal(coords[0]).doubleValue() * 1000000);
				museumOverlayItem=new OverlayItem(new GeoPoint(latitude, longitude), museumVO.getId(), museumVO.getId());
				customMuseumOverlay.addOverlay(museumOverlayItem);
			}
			mapOverlays.add(customMuseumOverlay);
		}
		OverlayItem overlayItem=new OverlayItem(new GeoPoint(latitude, longitude), "user", "user");
		MainMapActivity.personOverlay.addOverlay(overlayItem);
		MainMapActivity.mapOverlays.add(MainMapActivity.personOverlay);
		
		/*
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
		 */
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
		mapView.refreshDrawableState();
		
	}
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		LoadingActivity.killListeners();
	}

}
