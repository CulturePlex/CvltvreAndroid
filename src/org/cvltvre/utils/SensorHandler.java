package org.cvltvre.utils;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Handler;
import android.util.Config;
import android.util.Log;

public class SensorHandler implements SensorEventListener {
	private SensorManager mSensorManager;
	private Sensor mSensor;
	private static int cont=0;
	
	public static float[] mValues;
	public static boolean updated=false;
	private static Handler handler=null;

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
		cont++;
		if(cont>=10){
			handler.sendEmptyMessage(0);
			cont=0;
		}
	}
}