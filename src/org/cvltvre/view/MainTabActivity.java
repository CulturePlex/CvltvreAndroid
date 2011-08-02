/*
 * Author : ErVaLt / techwavedev.com
 * Description : TabLayout Andorid App
 */
package org.cvltvre.view;

import org.cvltvre.R;
import org.cvltvre.R.drawable;
import org.cvltvre.R.layout;

import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.TabHost;

public class MainTabActivity extends TabActivity {

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		Resources res = getResources(); // Resource object to get Drawables
		TabHost tabHost = getTabHost(); // The activity TabHost

		Intent intent = new Intent(this, MainListActivity.class);
		tabHost.addTab(tabHost.newTabSpec("Museums")
				.setIndicator("Museums", res.getDrawable(R.drawable.ic_tab_main_list))
				.setContent(intent));

		Intent intent2 = new Intent(this, MainMapActivity.class);
		tabHost.addTab(tabHost
				.newTabSpec("Map")
				.setIndicator("Map", res.getDrawable(R.drawable.ic_tab_main_map))
				.setContent(intent2));
		tabHost.setCurrentTab(0);

		

	}
}