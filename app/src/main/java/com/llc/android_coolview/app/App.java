package com.llc.android_coolview.app;

import android.app.Application;

import com.thinkland.sdk.android.JuheSDKInitializer;

import org.acra.ACRA;
import org.acra.ReportingInteractionMode;
import org.acra.annotation.ReportsCrashes;
import org.acra.sender.HttpSender;

@ReportsCrashes(
		httpMethod = HttpSender.Method.POST,
		formUri = "",
		formUriBasicAuthLogin = "tester",
		formUriBasicAuthPassword = "12345",
		reportType = HttpSender.Type.JSON,
		mode = ReportingInteractionMode.DIALOG
)
public class App extends Application {

	@Override
	public void onCreate() {
		super.onCreate();
		JuheSDKInitializer.initialize(getApplicationContext());
		ACRA.init(this);
	}

	@Override
	public void onTerminate() {
		super.onTerminate();
	}
}
