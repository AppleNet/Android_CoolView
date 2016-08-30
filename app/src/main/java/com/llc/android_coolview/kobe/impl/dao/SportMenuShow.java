package com.llc.android_coolview.kobe.impl.dao;

import com.llc.android_coolview.kobe.activity.KobeActivity;
import com.llc.android_coolview.kobe.impl.SportMenuOnClickListener;

public class SportMenuShow {

	private KobeActivity mKobeActivity;
	
	public SportMenuShow(KobeActivity mKobeActivity) {
		super();
		this.mKobeActivity = mKobeActivity;
	}

	public void setSportMenuOnClickListener(SportMenuOnClickListener sportMenuOnClickListener){
		mKobeActivity.sportMenuOnClickListener=sportMenuOnClickListener;
	}
}
