/**
 * 
 */
package org.cvltvre.view;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.cvltvre.R;
import org.cvltvre.adapter.CustomAdapter;
import org.cvltvre.utils.CustomLocationListener;
import org.cvltvre.utils.MultiThreadRequest;
import org.cvltvre.vo.MuseumVO;

import android.app.Activity;
import android.content.Intent;
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
	public static Map<String,MuseumVO> museumVOs=Collections.synchronizedMap(new ConcurrentHashMap<String, MuseumVO>());
	public static Double maxDistance=1.0;
	
	private boolean firstData=false;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.loading);
		CustomLocationListener customLocationListener=new CustomLocationListener();
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
