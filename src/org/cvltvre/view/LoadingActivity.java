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
<<<<<<< HEAD
	public static List<MuseumVO> museumVOs=new LinkedList<MuseumVO>();
=======
	public static Map<String,MuseumVO> museumVOs=Collections.synchronizedMap(new ConcurrentHashMap<String, MuseumVO>());
>>>>>>> 945320dd6af3acf2ad31193039c7d66e30b23f62
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
<<<<<<< HEAD
		Thread thread=new Thread(new MultiThreadRequest(handler));
=======
		Thread thread=new Thread(new MultiThreadRequest(handler,this));
>>>>>>> 945320dd6af3acf2ad31193039c7d66e30b23f62
		thread.start();
}
	
	
    // Define the Handler that receives messages from the thread and update the progress
    final Handler handler = new Handler() {
    	public void handleMessage(Message msg) {
    		LoadingActivity.customAdapter.notifyDataSetChanged();
    		if(!firstData){
    			firstData=!firstData;
<<<<<<< HEAD
    			LoadingActivity.museumVOs=museumVOs;
=======
>>>>>>> 945320dd6af3acf2ad31193039c7d66e30b23f62
    			Intent intent = new Intent(loadingActivity, MainTabLayout.class);
    			startActivity(intent);
    		}
        }
    };
}
