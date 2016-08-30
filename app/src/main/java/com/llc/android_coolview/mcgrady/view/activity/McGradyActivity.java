package com.llc.android_coolview.mcgrady.view.activity;

import android.support.v4.app.Fragment;

import com.llc.android_coolview.R;
import com.llc.android_coolview.mcgrady.view.fragment.AbsSingleFragmentActivity;
import com.llc.android_coolview.mcgrady.view.fragment.McGragyImageListFragment;

public class McGradyActivity extends AbsSingleFragmentActivity{

	@Override
	protected Fragment createFragment() {
		return new McGragyImageListFragment();
	}

	@Override
	protected int getFragmentLayoutId() {
		return R.layout.activity_mcgrady_single_fragment;
	}

	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		super.onWindowFocusChanged(hasFocus);
		onResumeWindowFocusChanged.resumeWindowFoucsChanged();
	}

	private OnResumeWindowFocusChanged onResumeWindowFocusChanged;

	public  interface  OnResumeWindowFocusChanged{
		public void resumeWindowFoucsChanged();
	}

	public void setOnResumeWindowFocusChanged(OnResumeWindowFocusChanged onResumeWindowFocusChanged){
		this.onResumeWindowFocusChanged=onResumeWindowFocusChanged;
	}
}
