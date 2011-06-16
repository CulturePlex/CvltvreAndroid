/*
 * Author : ErVaLt / techwavedev.com
 * Description : TabLayout Andorid App
 */
package org.cvltvre.view;

import java.util.List;

import org.cvltvre.R;
import org.cvltvre.adapter.CustomAdapter;
import org.cvltvre.utils.CustomLocationListener;
import org.cvltvre.vo.MuseumVO;

import android.app.Activity;
import android.os.Bundle;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;

public class MainListActivity extends Activity{

	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ListView listMuseumView=new ListView(this);
		//listMuseumView.setAdapter(new ArrayAdapter(this, R.layout.listmuseums, new String[]{"Hola","Adios"}));
		listMuseumView.setAdapter(LoadingActivity.customAdapter);
//		listMuseumView.setOnItemClickListener(new OnItemClickListener() {
//			@Override
//			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
//					long arg3) {
//				String response=RestConnector.connect();
//				AlertDialog.Builder builder = new AlertDialog.Builder(arg1.getContext());
//				builder.setMessage(response);
//				AlertDialog alertDialog=builder.create();
//				//alertDialog.show();
//				try {
//					JSONObject jsonObject=new JSONObject(response);
//				} catch (JSONException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//			}
//		});
		setContentView(R.layout.mainlist);
		addContentView(listMuseumView, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));

	}

}