package com.cvltvre.connector;

import java.io.InputStream;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.util.Log;

import com.cvltvre.utils.CustomLocationListener;
import com.cvltvre.utils.IOUtils;

public class RestConnector {
	
	
	public static String connect(double distance) {
		Double latitude=CustomLocationListener.location.getLatitude();
		Double longitude=CustomLocationListener.location.getLongitude();
		
		HttpGet httpGet=new HttpGet("http://www.cvltvre.com/pg/api/rest/json/?method=museum.get_geo_by_coordinates&top="+(latitude+distance)+"&bottom="+(latitude-distance)+"&left="+(longitude-distance)+"&right="+(longitude+distance));
		HttpClient client=new DefaultHttpClient();
		try {
			HttpResponse httpResponse=client.execute(httpGet);
			HttpEntity entity=httpResponse.getEntity();
			InputStream stream=entity.getContent();
			return IOUtils.toString(stream);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	public static String connect(int top,int right,int botom,int left) {
		HttpGet httpGet=new HttpGet("http://www.cvltvre.com/pg/api/rest/json/?method=museum.get_geo_by_coordinates&top="+top+"&bottom="+botom+"&left="+left+"&right="+right);
		HttpClient client=new DefaultHttpClient();
		try {
			HttpResponse httpResponse=client.execute(httpGet);
			HttpEntity entity=httpResponse.getEntity();
			InputStream stream=entity.getContent();
			String response=IOUtils.toString(stream);
			//Log.d(RestConnector.class.getName(), "Request:\n"+"http://www.cvltvre.com/pg/api/rest/json/?method=museum.get_geo_by_coordinates&top="+top.round(new MathContext(3)).floatValue()+"&bottom="+botom+"&left="+left.round(new MathContext(3)).floatValue()+"&right="+right.round(new MathContext(3)).floatValue()+"\nResponse:\n"+response);
			Log.d(RestConnector.class.getName(), "Request:\n"+"http://www.cvltvre.com/pg/api/rest/json/?method=museum.get_geo_by_coordinates&top="+top+"&bottom="+botom+"&left="+left+"&right="+right+"\nResponse:\n"+response);
			return response;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * @param string
	 * @return
	 */
/*	public static Bitmap getThumb(String url,Context context) {
		Bitmap thumb=null;
		try{
			HttpGet httpGet=new HttpGet("http://www.cvltvre.com/"+url);
			HttpClient client=new DefaultHttpClient();
			HttpResponse httpResponse=client.execute(httpGet);
			HttpEntity entity=httpResponse.getEntity();
			InputStream stream=entity.getContent();
			thumb=BitmapFactory.decodeStream(stream);
		}catch (Exception e) {
			e.printStackTrace();
		}
		if(thumb==null){
			thumb=BitmapFactory.decodeResource(context.getResources(), com.cvltvre.R.drawable.museum);
		}
		return thumb;
	}
*/
	

}
