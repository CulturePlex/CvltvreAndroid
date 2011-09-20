package org.cvltvre.utils;

import org.cvltvre.R;
import org.cvltvre.view.LoadingActivity;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Handler;
import android.util.Config;
import android.util.Log;

public class SensorHandler implements SensorEventListener {
	public static SensorManager mSensorManager;
	private Sensor mSensor;
	private static int cont=0;
	private float lastValue=400;
	
	public static float[] mValues;
	public static boolean updated=false;
	private Handler handler=null;
	private static Resources resources=LoadingActivity.loadingActivity.getApplicationContext().getResources();

	public SensorHandler(Handler handler, Context context) {
		mSensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
		mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION);
		mSensorManager.registerListener(this, mSensor,
                SensorManager.SENSOR_DELAY_GAME);
		this.handler=handler;
	}

	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// TODO Auto-generated method stub

	}

	public void onSensorChanged(SensorEvent event) {
		if (Config.DEBUG)
			Log.d(this.getClass().getName(), "sensorChanged ("
					+ event.values[0] + ", " + event.values[1] + ", "
					+ event.values[2] + ")");
		mValues = event.values;
		updated=true;
		//cont++;
		if(lastValue==400){
			lastValue=mValues[0];
			handler.sendEmptyMessage(0);
		}else if(lastValue>mValues[0] && lastValue-mValues[0]>5){
			lastValue=mValues[0];
			handler.sendEmptyMessage(0);
		}else if(lastValue<mValues[0] && mValues[0]-lastValue>5){
			lastValue=mValues[0];
			handler.sendEmptyMessage(0);
		}
			//if(cont>=10){
		//	handler.sendEmptyMessage(0);
		//	cont=0;
		//}
	}
	
	
	public static Bitmap getCompassImage(double latitude, double longitude){
		if(updated){
			int degress=(int) (SensorHandler.mValues[0]);
			Log.d("degrees", ""+SensorHandler.mValues[0]);
			
			
			degress=(int) getLocationDirection((int) SensorHandler.mValues[0], CustomLocationListener.location.getLongitude(), CustomLocationListener.location.getLatitude(), longitude, latitude);
			
			
			if(degress>180){
				degress=degress-180;
			}else{
				degress=degress+180;
			}
			degress=(degress*64)/360;
			degress=64-degress;
			if(degress>48){
				degress=degress+16-64;
			}else{
				degress=degress+16;
			}
			int id;
			//System.out.println("imagen "+degress+" latitude:"+latitude);
			if(degress<10){
				id=resources.getIdentifier("org.cvltvre:drawable/arrow000"+degress, null, null);
			}else{
				id=resources.getIdentifier("org.cvltvre:drawable/arrow00"+degress, null, null);
			}
			return BitmapFactory.decodeResource(resources,id);

		}else{
			return BitmapFactory.decodeResource(resources, R.drawable.arrow0001);
		}
	}
	
	public static int getLocationDirection(int north,double userX, double userY,double locationX,double locationY){
		//double m=Math.toDegrees(Math.tan(north));
		double degreesPoint=0;
		if(locationX-userX==0){
			degreesPoint=360;
		}else{
			degreesPoint= ((locationY-userY)/(locationX-userX));	
		}
		
		
		
		degreesPoint=(int) Math.toDegrees((1/Math.tan(degreesPoint)));
		
		while(degreesPoint>360){
			degreesPoint=degreesPoint-360;
		}
		while(degreesPoint<0){
			degreesPoint=degreesPoint+360;
		}
		
		//degreesPoint=(degreesPoint*north)/90;
		//90-degrees
		//north-x
		degreesPoint=north+degreesPoint-90;
		if(degreesPoint<0){
			degreesPoint=degreesPoint+360;
		}else if(degreesPoint>360){
			degreesPoint=degreesPoint-360;
		}
		/**/
		
		return (int) degreesPoint;
	}
	
}