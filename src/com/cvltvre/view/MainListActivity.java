/**
 * 
 */
package com.cvltvre.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.cvltvre.R;
import com.cvltvre.vo.MuseumVO;

public class MainListActivity extends Activity{

	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mainlist);
		ListView listMuseumView=(ListView) findViewById(R.id.museumListView);
		listMuseumView.setAdapter(LoadingActivity.customAdapter);
		listMuseumView.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) {
				Intent intent = new Intent(MainListActivity.this, MuseumMainActivity.class);
				intent.putExtra("id", (String)((MuseumVO)LoadingActivity.museumVOs.toArray()[position]).getId());
				startActivity(intent);
				
			}
		});
		
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
		//addContentView(listMuseumView, new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
		

	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		LoadingActivity.startListeners();
	}
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		LoadingActivity.killListeners();
	}

}