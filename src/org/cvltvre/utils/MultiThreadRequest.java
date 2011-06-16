package org.cvltvre.utils;

import java.util.Collections;
import java.util.List;

import org.cvltvre.connector.RestConnector;
import org.cvltvre.view.LoadingActivity;
import org.cvltvre.vo.MuseumVO;
import org.json.JSONException;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;

public class MultiThreadRequest implements Runnable {

	Handler handler;
	private static boolean isBusy = true;
	private static Double startDistance=0.1;
	Context context;

	public MultiThreadRequest(Handler handler,Context context) {
		this.context=context;
		this.handler=handler;
	}

	public void run() {
		while(startDistance<LoadingActivity.maxDistance && LoadingActivity.museumVOs.size()<15){
			isBusy=true;
			Thread thread = new Thread() {
				@Override
				public void run() {
					super.run();
					try {
						String response = RestConnector.connect(startDistance);
						List<MuseumVO> museumVOs = JSONutils.getMuseumsFromResponseString(response,LoadingActivity.loadingActivity);
						if(museumVOs.size()>LoadingActivity.museumVOs.size()){
							Collections.sort(museumVOs); 
							LoadingActivity.museumVOs = museumVOs;
							handler.sendEmptyMessage(1);
						}
						MultiThreadRequest.isBusy = false;
					} catch (JSONException e) {
						AlertDialog.Builder builder = new AlertDialog.Builder(LoadingActivity.loadingActivity);
						builder.setMessage("Connection error");
						AlertDialog alertDialog=builder.create();
						alertDialog.show();
						e.printStackTrace();
					}
				}
			};
			thread.start();
			waitNextRadius();
			startDistance=startDistance+0.1;
		}
		
		getImages(LoadingActivity.museumVOs);
	}
	
	public void getImages(List<MuseumVO> museumVOs){
		for(MuseumVO museumVO:museumVOs){
			if(museumVO.getImage()!=null){
				Bitmap bitmap=RestConnector.getThumb(museumVO.getImage(), context);
				museumVO.setBitmap(bitmap);
				handler.sendEmptyMessage(1);
			}
		}
	}

	public void waitNextRadius() {
		while (isBusy) {
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	

}
