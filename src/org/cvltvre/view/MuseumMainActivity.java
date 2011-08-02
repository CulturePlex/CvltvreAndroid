package org.cvltvre.view;

import org.cvltvre.R;
import org.cvltvre.vo.MuseumVO;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.text.method.ScrollingMovementMethod;
import android.widget.ImageView;
import android.widget.TextView;

public class MuseumMainActivity extends Activity{

	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.museuminfo);
		MuseumVO museumVO=LoadingActivity.museumVOs.get(this.getIntent().getExtras().get("id"));
		TextView name=(TextView) findViewById(R.id.museumname);
		name.setText(museumVO.getTitle());
		if(museumVO.getBitmap()!=null){
			ImageView imageView=(ImageView) findViewById(R.id.museumimage);
			imageView.setImageBitmap(museumVO.getBitmap());
		}
		
		TextView distance=(TextView) findViewById(R.id.museumdistance);
		distance.setText(museumVO.getDistance());
		TextView phone=(TextView) findViewById(R.id.museumphone);
		phone.setText(museumVO.getPhone());
		TextView website=(TextView) findViewById(R.id.museumwebsite);
		website.setText(museumVO.getWebsite());
		TextView description=(TextView) findViewById(R.id.museumdescription);
		description.setText(Html.fromHtml(museumVO.getDescription()));
		description.setMovementMethod(new ScrollingMovementMethod());
		
	}

}
