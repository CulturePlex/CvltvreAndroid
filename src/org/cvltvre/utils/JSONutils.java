/**
 * 
 */
package org.cvltvre.utils;

import java.util.LinkedList;
import java.util.List;

import org.cvltvre.vo.MuseumVO;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.graphics.Bitmap;

/**
 * @author Pencerval
 *
 */
public class JSONutils {

	public static List<MuseumVO> getMuseumsFromResponseString(String response,Context context) throws JSONException{
		List<MuseumVO> museumsList=new LinkedList<MuseumVO>();
		JSONObject jsonObject=new JSONObject(response);
		JSONArray jsonArray=jsonObject.getJSONArray("api");
		JSONObject jsonObject2=(JSONObject)jsonArray.get(0);
		JSONArray museums=jsonObject2.getJSONArray("result");
		for(int cont=0;cont<museums.length();cont++){
			JSONObject museum=(JSONObject)museums.get(cont);	
			museumsList.add(new MuseumVO(museum,context));
		}
		return museumsList;
	}


}
