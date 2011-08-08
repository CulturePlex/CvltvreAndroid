/**
 * 
 */
package org.cvltvre.view;

import java.util.Collections;
import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;

import org.cvltvre.R;
import org.cvltvre.adapter.CustomAdapter;
import org.cvltvre.utils.CustomLocationListener;
import org.cvltvre.utils.MultiThreadRequest;
import org.cvltvre.utils.SensorHandler;
import org.cvltvre.vo.MuseumVO;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

/**
 * @author Pencerval
 * 
 */
public class LoadingActivity extends Activity {
	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	public static LoadingActivity loadingActivity;
	public static CustomAdapter customAdapter;
	public static Set<MuseumVO> museumVOs=Collections.synchronizedSortedSet(new TreeSet<MuseumVO>(new Comparator<MuseumVO>() {

		public int compare(MuseumVO object1, MuseumVO object2) {
			Float distance1=Float.parseFloat(object1.getDistance());
			Float distance2=Float.parseFloat(object2.getDistance());
			return distance1.compareTo(distance2);
		}
	}));
	public static Double maxDistance=1.0;
	
	private boolean firstData=false;
	private SensorHandler sensorHandler;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.loading);
		sensorHandler=new SensorHandler(handler,this.getApplicationContext());
		CustomLocationListener customLocationListener=new CustomLocationListener();
		LocationManager locationManager=(LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
		locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, customLocationListener);
		customLocationListener.init(this);
		loadingActivity=this;
		customAdapter=new CustomAdapter(this);
		Thread thread=new Thread(new MultiThreadRequest(handler,this));
		thread.start();
}
	
	
    // Define the Handler that receives messages from the thread and update the progress
    final Handler handler = new Handler() {
    	public void handleMessage(Message msg) {
    		LoadingActivity.customAdapter.notifyDataSetChanged();
    		if(!firstData){
    			firstData=!firstData;
    			Intent intent = new Intent(loadingActivity, MainTabActivity.class);
    			startActivity(intent);
    		}
        }
    };
}
