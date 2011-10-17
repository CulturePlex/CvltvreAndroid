package com.cvltvre.utils;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import android.graphics.drawable.Drawable;
import android.os.Handler;

import com.cvltvre.R;
import com.cvltvre.adapter.CustomAdapter;
import com.cvltvre.view.LoadingActivity;
import com.cvltvre.view.MainMapActivity;
import com.cvltvre.vo.MuseumVO;
import com.google.android.maps.GeoPoint;
import com.google.android.maps.OverlayItem;

public class MapMuseumUpdater implements Runnable{

	private List<MuseumVO> museumVOs;
	public static Drawable museumDrawable=LoadingActivity.loadingActivity.getResources().getDrawable(R.drawable.museummap);
	private Handler handler;
	
	public MapMuseumUpdater(Handler staticHandler) {
		super();
		if(CustomAdapter.filtered){
			setMuseumVOs(CustomAdapter.filteredMuseums);	
		}else{
			setMuseumVOs(LoadingActivity.museumVOs);
		}
		
		this.setHandler(staticHandler);
	}

	public void run() {
		int latitude = (int) (CustomLocationListener.location.getLatitude() * 1000000);
		int longitude = (int) (CustomLocationListener.location.getLongitude() * 1000000);
		
		if(MainMapActivity.mapOverlays!=null){
			CustomMuseumOverlay customMuseumOverlay=new CustomMuseumOverlay(museumDrawable);
			//MainMapActivity.mapOverlays.add(MainMapActivity.museumOverlay);
			OverlayItem overlayItem;
			for(MuseumVO museumVO:getMuseumVOs()){
				String[] coords=museumVO.getMap_options().split(",");
				latitude = (int) (new BigDecimal(coords[1]).doubleValue() * 1000000);
				longitude = (int) (new BigDecimal(coords[0]).doubleValue() * 1000000);
				overlayItem=new OverlayItem(new GeoPoint(latitude, longitude), museumVO.getId(), museumVO.getId());
				customMuseumOverlay.addOverlay(overlayItem);
			}
			MainMapActivity.mapOverlays.clear();
			MainMapActivity.mapOverlays.add(customMuseumOverlay);
			int latitude2 = (int) (CustomLocationListener.location.getLatitude() * 1000000);
			int longitude2 = (int) (CustomLocationListener.location.getLongitude() * 1000000);
			OverlayItem overlayItemPerson=new OverlayItem(new GeoPoint(latitude2, longitude2), "user", "user");
			MainMapActivity.personOverlay.addOverlay(overlayItemPerson);
			MainMapActivity.mapOverlays.add(MainMapActivity.personOverlay);
		    
			MainMapActivity.mapView.invalidate();
			
			//customMuseumOverlay.populateNow();
		}
	}

	public void setMuseumVOs(List<MuseumVO> museumVOs) {
		this.museumVOs = museumVOs;
	}
	
	public void setMuseumVOs(Set<MuseumVO> museumVOs) {
		List<MuseumVO> museumVOs2=new LinkedList<MuseumVO>();
	    for (MuseumVO museumVO:museumVOs) {
	        museumVOs2.add(museumVO);
	      }
		this.museumVOs = museumVOs2;
	}


	public List<MuseumVO> getMuseumVOs() {
		return museumVOs;
	}

	public void setHandler(Handler handler) {
		this.handler = handler;
	}

	public Handler getHandler() {
		return handler;
	}
}
