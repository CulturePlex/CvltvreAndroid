package org.cvltvre.utils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.cvltvre.R;
import org.cvltvre.view.LoadingActivity;
import org.cvltvre.view.MainMapActivity;
import org.cvltvre.view.MuseumMainActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.view.MotionEvent;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.MapView;
import com.google.android.maps.OverlayItem;

public class CustomMuseumOverlay extends ItemizedOverlay<OverlayItem> {

	private List<OverlayItem> mOverlays = new ArrayList<OverlayItem>();
	private ImageView icon=(ImageView) LoadingActivity.loadingActivity.findViewById(R.drawable.museummap);
	private int lastaped;
	Context context;
	
	public CustomMuseumOverlay(Drawable defaultMarker) {
		super(boundCenterBottom(defaultMarker));
		context=LoadingActivity.loadingActivity.getApplicationContext();
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
	
	@Override
	public boolean onTouchEvent(MotionEvent event, MapView mapView) {
		// Track which MapLocation was hit…if any
		OverlayItem hitMapLocation = null;

		RectF hitTestRecr = new RectF();
		int[] screenCoords = new int[2];
		Iterator<OverlayItem> iterator = mOverlays.iterator();
		while(iterator.hasNext()) {

			OverlayItem testLocation = iterator.next();

			// Use this information to create a ‘hit” testing Rectangle to represent the size
			// of our location’s icon at the correct location on the screen. 
			// As we want the base of our balloon icon to be at the exact location of
			// our map location, we set our Rectangle’s location so the bottom-middle of
			// our icon is at the screen coordinates of our map location (shown above).
			Drawable bubbleIcon=MainMapActivity.museumDrawable;
			
			hitTestRecr.set(-bubbleIcon.getIntrinsicWidth()/2,-bubbleIcon.getIntrinsicHeight(),bubbleIcon.getIntrinsicWidth()/2,0);
	
			// Next, offset the Rectangle to location of our location’s icon on the screen.
			hitTestRecr.offset(screenCoords[0],screenCoords[1]);
	
			// Finally test for match between ‘hit’ Rectangle and location clicked by the user.
			// If a hit occurred, then we stop processing and return the result;
			if (hitTestRecr.contains(event.getX(),event.getY())) {
	
				Intent intent = new Intent(MainMapActivity.mapView.getContext(), MuseumMainActivity.class);
				intent.putExtra("id", testLocation.getTitle());
				LoadingActivity.loadingActivity.startActivity(intent);
				break;
	
			}
	
		}

		return hitMapLocation!=null;
	}
	
	@Override
	public boolean onTap(GeoPoint p, MapView mapView) {
	 // TODO Auto-generated method stub
	  
	 if(super.onTap(p, mapView)){
	  return true;
	 }
	 return false;
	}
	 
	@Override
	protected boolean onTap(int index) {
	 // TODO Auto-generated method stub
	 //return super.onTap(index);
	  
	Intent intent = new Intent(MainMapActivity.mapView.getContext(), MuseumMainActivity.class);
	intent.putExtra("id", mOverlays.get(index).getTitle());
	LoadingActivity.loadingActivity.startActivity(intent);
	  
	 return true;
	}
	
	
}
