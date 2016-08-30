package com.llc.android_coolview.kobe.impl.dao;

import com.llc.android_coolview.kobe.activity.KobeActivity;
import com.llc.android_coolview.kobe.impl.NewsMenuOnClickListener;

public class NewsMenuShow {

	private KobeActivity mKobeActivity;
	
	public NewsMenuShow(KobeActivity mKobeActivity) {
		this.mKobeActivity = mKobeActivity;
	}

	public void setNewsMenuOnClickListener(NewsMenuOnClickListener newsMenuOnClickListener){
		mKobeActivity.newsMenuOnClickListener=newsMenuOnClickListener;
	}
}
