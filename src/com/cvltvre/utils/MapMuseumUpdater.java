package com.cvltvre.utils;

import java.math.BigDecimal;
import java.util.List;

import android.graphics.drawable.Drawable;

import com.cvltvre.R;
import com.cvltvre.view.LoadingActivity;
import com.cvltvre.view.MainMapActivity;
import com.cvltvre.vo.MuseumVO;
import com.google.android.maps.GeoPoint;
import com.google.android.maps.OverlayItem;

public class MapMuseumUpdater implements Runnable{

	private List<MuseumVO> museumVOs;
	public static Drawable museumDrawable=LoadingActivity.loadingActivity.getResources().getDrawable(R.drawable.museummap);
	
	
	public MapMuseumUpdater(List<MuseumVO> museumVOs) {
		super();
		setMuseumVOs(museumVOs);
	}

	public void run() {
		int latitude = (int) (CustomLocationListener.location.getLatitude() * 1000000);
		int longitude = (int) (CustomLocationListener.location.getLongitude() * 1000000);
		
		while(true){
			if(MainMapActivity.personOverlay==null||MainMapActivity.mapOverlays==null){
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}else{
				break;
			}
		}
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
		MainMapActivity.mapOverlays.add(customMuseumOverlay);
		//customMuseumOverlay.populateNow();
	}

	public void setMuseumVOs(List<MuseumVO> museumVOs) {
		this.museumVOs = museumVOs;
	}


	public List<MuseumVO> getMuseumVOs() {
		return museumVOs;
	}
}
