package com.llc.android_coolview.paul.view.fragment;

import com.llc.android_coolview.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

public class NBAMenuFragment extends Fragment implements OnClickListener {

	private View view;
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view=inflater.inflate(R.layout.activity_paul_fragment, container,false);
		initView();
		initEvents();
		return view;
	}
	
	private void initView(){
		
	}
	
	private void initEvents(){
		
	}

	@Override
	public void onClick(View v) {
		
	}
}
