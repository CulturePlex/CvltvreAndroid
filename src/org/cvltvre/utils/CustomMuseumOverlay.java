package org.cvltvre.utils;

import java.util.ArrayList;
import java.util.List;

import org.cvltvre.view.LoadingActivity;
import org.cvltvre.view.MainListActivity;
import org.cvltvre.view.MuseumMainActivity;
import org.cvltvre.vo.MuseumVO;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.MapView;
import com.google.android.maps.OverlayItem;

public class CustomMuseumOverlay extends ItemizedOverlay<OverlayItem> {

	private List<OverlayItem> mOverlays = new ArrayList<OverlayItem>();
	
	public CustomMuseumOverlay(Drawable defaultMarker) {
		super(boundCenterBottom(defaultMarker));
	}

	@Override
	protected OverlayItem createItem(int i) {
		return mOverlays.get(i);
	}

	@Override
	public int size() {
		return mOverlays.size();
	}

	public void addOverlay(OverlayItem overlay) {
		boolean exist=false;
		for(OverlayItem item:mOverlays){
			if(item.getTitle().equals(overlay.getTitle())){
				exist=true;
				break;
			}
		}
		if(!exist){
			mOverlays.add(overlay);
		    populate();
		
		}
	}
	
	public boolean onTap (final GeoPoint p, final MapView mapView){
	    boolean tapped = super.onTap(p, mapView);
	    if (tapped){       
	    	
	    }                           
	    return true;
	}
	
	
	@Override
	protected boolean onTap(int index) {
	    return true;
	}
}
