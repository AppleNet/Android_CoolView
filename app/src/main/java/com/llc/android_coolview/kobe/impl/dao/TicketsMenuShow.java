package com.llc.android_coolview.kobe.impl.dao;

import com.llc.android_coolview.kobe.activity.KobeActivity;
import com.llc.android_coolview.kobe.impl.MenuOnClickListener;

public class TicketsMenuShow {

	private KobeActivity mKobeActivity;
	
	public TicketsMenuShow(KobeActivity mKobeActivity) {
		super();
		this.mKobeActivity = mKobeActivity;
	}

	public void setOnMenuClickListener(MenuOnClickListener menuOnClickListener){
		mKobeActivity.menuOnClickListener=menuOnClickListener;
	}
}
