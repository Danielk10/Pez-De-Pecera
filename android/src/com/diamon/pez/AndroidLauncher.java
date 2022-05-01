package com.diamon.pez;

import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.diamon.pez.PezDePecera;
import com.microsoft.appcenter.AppCenter;
import com.microsoft.appcenter.analytics.Analytics;
import com.microsoft.appcenter.crashes.Crashes;

public class AndroidLauncher extends AndroidApplication {
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AppCenter.start(getApplication(), "4942c9c2-c670-4a16-89ab-23bfc270ca4d",Analytics.class, Crashes.class);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		initialize(new PezDePecera(null), config);
	}
}
