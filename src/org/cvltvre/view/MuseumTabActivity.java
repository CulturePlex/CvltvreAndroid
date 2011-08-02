package org.cvltvre.view;

import org.cvltvre.R;

import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.widget.TabHost;

public class MuseumTabActivity extends TabActivity{

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		Resources res = getResources(); // Resource object to get Drawables
		TabHost tabHost = getTabHost(); // The activity TabHost

		Intent intent = new Intent(this, MuseumMainActivity.class);
		tabHost.addTab(tabHost.newTabSpec("Museum")
				.setIndicator("Museum", res.getDrawable(R.drawable.ic_tab_main_list))
				.setContent(intent));

		Intent intent2 = new Intent(this, MuseumMapActivity.class);
		tabHost.addTab(tabHost
				.newTabSpec("Map")
				.setIndicator("Map", res.getDrawable(R.drawable.ic_tab_main_map))
				.setContent(intent2));
		tabHost.setCurrentTab(0);

		

	}
}
