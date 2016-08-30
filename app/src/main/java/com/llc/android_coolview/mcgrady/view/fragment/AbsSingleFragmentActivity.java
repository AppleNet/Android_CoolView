package com.llc.android_coolview.mcgrady.view.fragment;

import com.llc.android_coolview.R;
import android.os.Bundle;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.Window;
import android.view.WindowManager;

public abstract class AbsSingleFragmentActivity extends FragmentActivity {

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		getWindow().requestFeature(Window.FEATURE_NO_TITLE);
		getWindow().setWindowAnimations(R.anim.win_show);
		if(VERSION.SDK_INT>=VERSION_CODES.LOLLIPOP){
			getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
		}
		setContentView(getFragmentLayoutId());
		FragmentManager fm=getSupportFragmentManager();
		Fragment fragment=fm.findFragmentById(R.id.id_fragmentContainer);
		if(fragment==null){
			fragment=createFragment();
			fm.beginTransaction().add(R.id.id_fragmentContainer, fragment).commit();
		}
	}
	
	protected abstract Fragment createFragment();
	protected abstract int getFragmentLayoutId();
}
