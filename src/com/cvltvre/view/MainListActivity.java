/**
 * 
 */
package com.cvltvre.view;

import java.util.LinkedList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.cvltvre.R;
import com.cvltvre.adapter.CustomAdapter;
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
		final EditText editText=(EditText) findViewById(R.id.editSearch);
		
		//editText.setNextFocusDownId(R.id.buttonSearch);
		//editText.setNextFocusLeftId(R.id.buttonSearch);
		//editText.setNextFocusUpId(R.id.buttonSearch);
		//editText.setNextFocusRightId(R.id.buttonSearch);
		//editText.setFocusable(false);
		editText.setOnKeyListener(new OnKeyListener() {
		    public boolean onKey(View v, int keyCode, KeyEvent event) {
		        // If the event is a key-down event on the "enter" button
		        if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
		            (keyCode == KeyEvent.KEYCODE_ENTER)) {
		          // Perform action on key press
		        	if(editText.getText() !=null && editText.getText().toString()!="" && editText.getText().toString()!=" "){
						List<MuseumVO> museumVOs=new LinkedList<MuseumVO>();
						for(MuseumVO museumVO:LoadingActivity.museumVOs){
							if(museumVO.getTitle().toLowerCase().contains(editText.getText().toString().toLowerCase())){
								museumVOs.add(museumVO);
							}
						}
						CustomAdapter.filteredMuseums=museumVOs;
						CustomAdapter.filtered=true;
					}else{
						CustomAdapter.filtered=true;
					}
		        	LoadingActivity.customAdapter.notifyDataSetChanged();
					InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
					imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
					return true;
		        }
		        return false;
		    }
		});
		//editText.setOnFocusChangeListener(new On)
		
		
		Button search=(Button) findViewById(R.id.buttonSearch);
		search.setText("");
		search.setBackgroundResource(R.drawable.search);
		search.setHeight(editText.getHeight());
		search.setWidth(editText.getHeight());
		search.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(editText.getText() !=null && editText.getText().toString()!="" && editText.getText().toString()!=" "){
					List<MuseumVO> museumVOs=new LinkedList<MuseumVO>();
					for(MuseumVO museumVO:LoadingActivity.museumVOs){
						if(museumVO.getTitle().toLowerCase().contains(editText.getText().toString().toLowerCase())){
							museumVOs.add(museumVO);
						}
					}
					CustomAdapter.filteredMuseums=museumVOs;
					CustomAdapter.filtered=true;
					
				}else{
					CustomAdapter.filtered=false;
				}
				LoadingActivity.customAdapter.notifyDataSetChanged();
				InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
				imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
				
			}
		});
		
		
		Button clear=(Button) findViewById(R.id.buttonClear);
		clear.setBackgroundResource(R.drawable.clear);
		clear.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				editText.setText("");
				CustomAdapter.filtered=false;
				LoadingActivity.customAdapter.notifyDataSetChanged();
				InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
				imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
			}
		});
		
		
		

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