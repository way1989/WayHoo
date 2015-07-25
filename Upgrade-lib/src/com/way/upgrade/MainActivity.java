package com.way.upgrade;

import android.app.Activity;
import android.os.Bundle;

import com.way.upgrade.core.UpgradeManager;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		final UpgradeManager upgradeMangeer = UpgradeManager.newInstance(this);

		upgradeMangeer.askForNewVersion();
	}
}
