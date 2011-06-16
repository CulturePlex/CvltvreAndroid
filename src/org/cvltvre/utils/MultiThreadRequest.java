package org.cvltvre.utils;

import java.util.List;

import org.cvltvre.connector.RestConnector;
import org.cvltvre.view.LoadingActivity;
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
