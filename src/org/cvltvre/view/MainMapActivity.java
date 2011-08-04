/*
 * Author : ErVaLt / techwavedev.com
 * Description : TabLayout Andorid App
 */
package org.cvltvre.view;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.cvltvre.R;
import org.cvltvre.R.layout;
import org.cvltvre.utils.CustomLocationListener;
import org.cvltvre.utils.CustomMuseumOverlay;
import org.cvltvre.vo.MuseumVO;

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
	private static Drawable museumDrawable=LoadingActivity.loadingActivity.getResources().getDrawable(R.drawable.museummap);
	private static Drawable personDrawable=LoadingActivity.loadingActivity.getResources().getDrawable(R.drawable.person);
	public static List<Overlay> mapOverlays;
	
	public static CustomMuseumOverlay museumOverlay= new CustomMuseumOverlay(museumDrawable);
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
		Thread thread= new Thread(new Runnable() {
			
			public void run() {
				int latitude = (int) (CustomLocationListener.location.getLatitude() * 1000000);
				int longitude = (int) (CustomLocationListener.location.getLongitude() * 1000000);
				
				OverlayItem overlayItem=new OverlayItem(new GeoPoint(latitude, longitude), "user", "user");
				MainMapActivity.personOverlay.addOverlay(overlayItem);
				MainMapActivity.mapOverlays.add(MainMapActivity.personOverlay);
				MainMapActivity.mapOverlays.add(MainMapActivity.museumOverlay);
				while(true){
					if(LoadingActivity.museumVOs.size()>0){
						for(MuseumVO museumVO:LoadingActivity.museumVOs){
							String[] coords=museumVO.getMap_options().split(",");
							latitude = (int) (new BigDecimal(coords[1]).doubleValue() * 1000000);
							longitude = (int) (new BigDecimal(coords[0]).doubleValue() * 1000000);
							overlayItem=new OverlayItem(new GeoPoint(latitude, longitude), museumVO.getTitle(), museumVO.getTitle());
							MainMapActivity.museumOverlay.addOverlay(overlayItem);
						}
					}
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				
				
				
			}
		});
		thread.start();
	}

	@Override
	protected boolean isRouteDisplayed() {
		return false;
	}

}
