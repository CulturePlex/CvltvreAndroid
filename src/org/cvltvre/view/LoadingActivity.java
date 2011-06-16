/**
 * 
 */
package org.cvltvre.view;

import java.util.LinkedList;
import java.util.List;

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
	public static List<MuseumVO> museumVOs=new LinkedList<MuseumVO>();
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
    			LoadingActivity.museumVOs=museumVOs;
    			Intent intent = new Intent(loadingActivity, MainTabLayout.class);
    			startActivity(intent);
    		}
        }
    };
}
