package org.cvltvre.connector;

import java.io.InputStream;
import java.math.BigDecimal;
import java.math.MathContext;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.cvltvre.utils.CustomLocationListener;
import org.cvltvre.utils.IOUtils;
import org.cvltvre.view.LoadingActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Looper;
import android.util.Log;

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
	
	public static String connect(BigDecimal top,BigDecimal right,BigDecimal botom,BigDecimal left) {
		HttpGet httpGet=new HttpGet("http://www.cvltvre.com/pg/api/rest/json/?method=museum.get_geo_by_coordinates&top="+top.round(new MathContext(5))+"&bottom="+botom.round(new MathContext(5))+"&left="+left.round(new MathContext(5))+"&right="+right.round(new MathContext(5)));
		HttpClient client=new DefaultHttpClient();
		try {
			HttpResponse httpResponse=client.execute(httpGet);
			HttpEntity entity=httpResponse.getEntity();
			InputStream stream=entity.getContent();
			String response=IOUtils.toString(stream);
			//Log.d(RestConnector.class.getName(), "Request:\n"+"http://www.cvltvre.com/pg/api/rest/json/?method=museum.get_geo_by_coordinates&top="+top.round(new MathContext(3)).floatValue()+"&bottom="+botom+"&left="+left.round(new MathContext(3)).floatValue()+"&right="+right.round(new MathContext(3)).floatValue()+"\nResponse:\n"+response);
			Log.d(RestConnector.class.getName(), "Request:\n"+"http://www.cvltvre.com/pg/api/rest/json/?method=museum.get_geo_by_coordinates&top="+top.round(new MathContext(5))+"&bottom="+botom.round(new MathContext(5))+"&left="+left.round(new MathContext(5))+"&right="+right.round(new MathContext(5))+"\nResponse:\n"+response);
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
			thumb=BitmapFactory.decodeResource(context.getResources(), org.cvltvre.R.drawable.museum);
		}
		return thumb;
	}
*/
}
