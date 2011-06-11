/**
 * 
 */
package org.cvltvre.view;

import java.util.LinkedList;
import java.util.List;

import org.cvltvre.R;
import org.cvltvre.connector.RestConnector;
import org.cvltvre.utils.CustomLocationListener;
import org.cvltvre.utils.JSONutils;
import org.cvltvre.vo.MuseumVO;
import org.json.JSONException;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;

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
	public LoadingActivity loadingActivity;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.loading);
		CustomLocationListener.init(this);
		loadingActivity=this;
		Thread thread=new Thread(){
			/* (non-Javadoc)
			 * @see java.lang.Thread#run()
			 */
			@Override
			public void run() {
				String response=RestConnector.connect();
				List<MuseumVO> museumVOs=new LinkedList<MuseumVO>();
				try {
					museumVOs = JSONutils.getMuseumsFromResponseString(response,loadingActivity);
				} catch (JSONException e) {
					AlertDialog.Builder builder = new AlertDialog.Builder(loadingActivity);
					builder.setMessage("Connection error");
					AlertDialog alertDialog=builder.create();
					alertDialog.show();
					e.printStackTrace();
				}
				MainListActivity.museumVOs=museumVOs;
				Intent intent = new Intent(loadingActivity, MainTabLayout.class);
				startActivity(intent);
			}
		};
		thread.start();
	}
}
