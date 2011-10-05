/**
 * 
 */
package com.cvltvre.view;

import java.util.Collections;
import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import com.cvltvre.R;
import com.cvltvre.adapter.CustomAdapter;
import com.cvltvre.utils.CustomLocationListener;
import com.cvltvre.utils.MultiThreadRequest;
import com.cvltvre.utils.SensorHandler;
import com.cvltvre.vo.MuseumVO;

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
	private static CustomLocationListener customLocationListener = null;
	public static SensorHandler sensorHandler = null;
	private static boolean paused = false;
	public static Set<MuseumVO> museumVOs = Collections.synchronizedSortedSet(new TreeSet<MuseumVO>(new Comparator<MuseumVO>() {
						public int compare(MuseumVO object1, MuseumVO object2) {
							Float distance1 = Float.parseFloat(object1.getDistance());
							Float distance2 = Float.parseFloat(object2.getDistance());
							return distance1.compareTo(distance2);
						}
					}));
	public static Double maxDistance = 1.0;

	private boolean firstData = false;
	public static boolean locationRetrieve = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		loadingActivity = this;
		setContentView(R.layout.loading);
		sensorHandler = new SensorHandler(handler, this.getApplicationContext());
		customAdapter = new CustomAdapter(this);
		customLocationListener = new CustomLocationListener(this.getApplicationContext());
		Thread threadForLocation = new Thread(customLocationListener);
		threadForLocation.start();
		if(!checkInternetConnection()){
			displayAlert();
		}else{
			Thread threadForMuseums = new Thread(new MultiThreadRequest(handler,this));
			threadForMuseums.start();	
		}
		
	}

	public void displayAlert(){
		new AlertDialog.Builder(this).setMessage("Please Check Your Internet Connection and Try Again").setTitle("Network Error").setCancelable(true).setNeutralButton(android.R.string.ok,	new DialogInterface.OnClickListener() { 
			public void onClick(DialogInterface dialog, int whichButton){
				finish();
			}}).show(); 
	}
	

	// Define the Handler that receives messages from the thread and update the
	// progress
	final Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			LoadingActivity.customAdapter.notifyDataSetChanged();
			if (msg.what == 1 && !firstData) {
				firstData = true;
				Intent intent = new Intent(loadingActivity,MainTabActivity.class);
				startActivity(intent);
			}
		}
	};

	protected void onPause() {
		super.onPause();
		paused = true;
		killListeners();
	};

	@Override
	protected void onResume() {
		super.onResume();
		if (paused) {
			startListeners();
			if (museumVOs != null && museumVOs.size() != 0) {
				Intent intent = new Intent(loadingActivity,MainTabActivity.class);
				startActivity(intent);
			}
		}
	}

	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		super.onRestart();
		Intent intent = new Intent(loadingActivity, MainTabActivity.class);
		startActivity(intent);
	}

	public static void startListeners() {
		CustomLocationListener.locationManager.requestLocationUpdates(CustomLocationListener.best, 2000, 0, customLocationListener);
		SensorHandler.mSensorManager.registerListener(sensorHandler,SensorHandler.mSensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION),SensorManager.SENSOR_DELAY_GAME);
	}

	public static void killListeners() {
		if (CustomLocationListener.locationManager != null && customLocationListener != null) {
			CustomLocationListener.locationManager.removeUpdates(customLocationListener);
		}
		if (SensorHandler.mSensorManager != null && sensorHandler != null) {
			SensorHandler.mSensorManager.unregisterListener(sensorHandler);
		}

	}

	public boolean checkInternetConnection() {
		ConnectivityManager conMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		// ARE WE CONNECTED TO THE NET
		if (conMgr.getActiveNetworkInfo() != null && conMgr.getActiveNetworkInfo().isAvailable() && conMgr.getActiveNetworkInfo().isConnected()) {
			return true;
		} else {
			return false;
		}
	}

}
