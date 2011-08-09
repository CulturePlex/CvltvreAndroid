package org.cvltvre.utils;

import java.math.BigDecimal;
import java.util.List;

import org.cvltvre.connector.RestConnector;
import org.cvltvre.view.LoadingActivity;
import org.cvltvre.vo.MuseumVO;
import org.json.JSONException;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;

public class MultiThreadRequest implements Runnable {

	private Handler handler;
	private static boolean isBusy = false;
	//private static Double startDistance=0.1;
	private static BigDecimal dotOne=new BigDecimal(0.3);

	Context context;

	public MultiThreadRequest(Handler handler,Context context) {
		this.context=context.getApplicationContext();
		this.handler=handler;
	}

	public void run(){
		try {
			while(!LoadingActivity.locationRetrieve){
				Thread.sleep(100);
			}
			doMatrix(dotOne, new BigDecimal(CustomLocationListener.location.getLatitude()), new BigDecimal(CustomLocationListener.location.getLongitude()), new BigDecimal(CustomLocationListener.location.getLatitude()), new BigDecimal(CustomLocationListener.location.getLongitude()));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
								boolean exist=false;
								for(MuseumVO museumVO2:LoadingActivity.museumVOs){
									if(museumVO.getId().equals(museumVO2.getId())){
										exist=true;
										break;
									}
								}
								if(!exist){
									LoadingActivity.museumVOs.add(museumVO);
									handler.sendEmptyMessage(1);
								}
							}
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
	
	
	
}
