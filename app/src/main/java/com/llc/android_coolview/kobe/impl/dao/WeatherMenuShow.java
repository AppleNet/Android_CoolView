package com.llc.android_coolview.kobe.impl.dao;

import com.llc.android_coolview.kobe.activity.KobeActivity;
import com.llc.android_coolview.kobe.impl.WeatherMenuOnClickListener;

public class WeatherMenuShow {

	private KobeActivity mKobeActivity;
	
	public WeatherMenuShow(KobeActivity mKobeActivity) {
		this.mKobeActivity = mKobeActivity;
	}

	public void setWeatherMenuOnClickListener(WeatherMenuOnClickListener weatherMenuOnClickListener){
		mKobeActivity.weatherMenuOnClickListener=weatherMenuOnClickListener;
	}
}
