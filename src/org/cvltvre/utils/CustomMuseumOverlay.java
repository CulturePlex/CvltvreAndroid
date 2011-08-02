package org.cvltvre.utils;

import java.util.ArrayList;
import java.util.List;

import android.graphics.drawable.Drawable;

import com.google.android.maps.ItemizedOverlay;
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
	
	
}
