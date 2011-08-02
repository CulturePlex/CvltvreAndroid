package org.cvltvre.utils;

<<<<<<< HEAD
=======
import java.math.BigDecimal;
import java.util.Collections;
>>>>>>> 945320dd6af3acf2ad31193039c7d66e30b23f62
import java.util.List;

import org.cvltvre.connector.RestConnector;
import org.cvltvre.view.LoadingActivity;
<<<<<<< HEAD
import org.cvltvre.vo.MuseumVO;
import org.json.JSONException;

import android.app.AlertDialog;
import android.os.Handler;

public class MultiThreadRequest implements Runnable {

	Handler handler;
	private static boolean isBusy = true;
	private static Double startDistance=0.1;

	public MultiThreadRequest(Handler handler) {
		this.handler=handler;
	}

	public void run() {
=======
import org.cvltvre.view.MainMapActivity;
import org.cvltvre.vo.MuseumVO;
import org.json.JSONException;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.OverlayItem;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.location.LocationManager;
import android.os.Handler;
import android.os.Looper;

public class MultiThreadRequest implements Runnable {

	private Handler handler;
	private static boolean isBusy = false;
	private static int busy = 0;
	//private static Double startDistance=0.1;
	private static BigDecimal dotOne=new BigDecimal(0.3);

	Context context;

	public MultiThreadRequest(Handler handler,Context context) {
		this.context=context;
		this.handler=handler;
	}

	public void run(){
		try {
			doMatrix(dotOne, new BigDecimal(CustomLocationListener.location.getLatitude()), new BigDecimal(CustomLocationListener.location.getLongitude()), new BigDecimal(CustomLocationListener.location.getLatitude()), new BigDecimal(CustomLocationListener.location.getLongitude()));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/*public void run() {
>>>>>>> 945320dd6af3acf2ad31193039c7d66e30b23f62
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
<<<<<<< HEAD
=======
							Collections.sort(museumVOs); 
>>>>>>> 945320dd6af3acf2ad31193039c7d66e30b23f62
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
		
<<<<<<< HEAD

	}
=======
		getImages(LoadingActivity.museumVOs);
	}
	*/
	/*
	public void getImages(List<MuseumVO> museumVOs){
		for(MuseumVO museumVO:museumVOs){
			if(museumVO.getImage()!=null){
				Bitmap bitmap=RestConnector.getThumb(museumVO.getImage(), context);
				museumVO.setBitmap(bitmap);
				handler.sendEmptyMessage(1);
			}
		}
	}
	*/
>>>>>>> 945320dd6af3acf2ad31193039c7d66e30b23f62

	public void waitNextRadius() {
		while (isBusy) {
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
<<<<<<< HEAD
=======
	
	
	public void doMatrix(BigDecimal size,BigDecimal top,BigDecimal right,BigDecimal bottom,BigDecimal left) throws JSONException{
		if(LoadingActivity.museumVOs.size()<20){
			if(size==dotOne){
				BigDecimal diferenceSize=new BigDecimal(0.15);
				makeThreadRequest(top.add(diferenceSize),right.add(diferenceSize),bottom.subtract(diferenceSize),left.subtract(diferenceSize));
				doMatrix(size.add(diferenceSize), top.add(diferenceSize), right.add(diferenceSize),bottom.subtract(diferenceSize),left.subtract(diferenceSize));
			}else{
				BigDecimal diferenceSize=size.subtract(new BigDecimal(0.15));
				makeThreadRequest(top.add(diferenceSize),right.add(diferenceSize),bottom.add(diferenceSize),left.add(diferenceSize));
				makeThreadRequest(top.subtract(diferenceSize),right.subtract(diferenceSize),bottom.subtract(diferenceSize),left.subtract(diferenceSize));
				makeThreadRequest(top.add(diferenceSize),right.subtract(diferenceSize),bottom.add(diferenceSize),left.subtract(diferenceSize));
				makeThreadRequest(top.subtract(diferenceSize),right.add(diferenceSize),bottom.subtract(diferenceSize),left.add(diferenceSize));
				for(BigDecimal x=(right.subtract(diferenceSize));x.floatValue()<(left.add(diferenceSize).floatValue());x=x.add(dotOne)){
					//top right ->
					makeThreadRequest(top.add(diferenceSize),x.add(dotOne),bottom.add(diferenceSize),x);
					//bottom right ->
					makeThreadRequest(top.subtract(diferenceSize),x.add(dotOne),bottom.subtract(diferenceSize),x);
				}
				for(BigDecimal x=(top.subtract(diferenceSize));x.floatValue()<(bottom.add(diferenceSize).floatValue());x=x.add(dotOne)){
					//left top ^
					makeThreadRequest(x.add(dotOne),right.subtract(diferenceSize),x,left.subtract(diferenceSize));
					//right top ^
					makeThreadRequest(x.add(dotOne),right.add(diferenceSize),x,left.add(diferenceSize));
				}
				doMatrix(size.add(dotOne), top, right,bottom,left);
			}
		}else{
			return;
		}
	}
	
	private void makeThreadRequest(final BigDecimal top,final BigDecimal right,final BigDecimal bottom,final BigDecimal left){
		if(isBusy){
			waitNextRadius();
		}else{
			isBusy=true;
			Thread thread=new Thread(){
				@Override
				public void run() {
					super.run();
					try {
						String response=RestConnector.connect(top,right,bottom,left);
						if(response==null){
							throw new JSONException("Connection problem");
						}
						List<MuseumVO> museumVOs = JSONutils.getMuseumsFromResponseString(response,LoadingActivity.loadingActivity);
						if(museumVOs.size()>0){
							for(MuseumVO museumVO:museumVOs){
								if(!LoadingActivity.museumVOs.containsKey(museumVO.getId())){
									LoadingActivity.museumVOs.put(museumVO.getId(), museumVO);
								}
							}
							//Collections.sort(LoadingActivity.museumVOs);
							handler.sendEmptyMessage(1);
							//getImages(museumVOs);
						}
						isBusy=false;
					} catch (JSONException e) {
						AlertDialog.Builder builder = new AlertDialog.Builder(LoadingActivity.loadingActivity);
						builder.setMessage("Connection problem");
						Looper.prepare();
						AlertDialog alertDialog=builder.create();
						alertDialog.show();
						e.printStackTrace();
					}
				}
			};
			thread.start();
		}
		
	}
>>>>>>> 945320dd6af3acf2ad31193039c7d66e30b23f62
}
