package com.llc.android_coolview.paul.view.activity;

import java.util.ArrayList;
import java.util.List;

import com.llc.android_coolview.BaseActivity;
import com.llc.android_coolview.R;
import com.llc.android_coolview.paul.bean.ImageNames;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.view.Window;
import android.view.WindowManager;

public class PaulActivity extends BaseActivity {

	public static List<Bitmap> images=new ArrayList<Bitmap>();
	public static List<String> teamName=new ArrayList<String>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		getWindow().requestFeature(Window.FEATURE_NO_TITLE);
		if(VERSION.SDK_INT>=VERSION_CODES.LOLLIPOP){
			getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
		}
		super.onCreate(savedInstanceState);
		initData();
		setContentView(R.layout.activity_paul);
		initView();
		initEvents();
	}
	
	private void initView(){
	}
	
	private void initEvents(){
	}

	private void initData(){
		images=ImageNames.getBitmap(PaulActivity.this);
		teamName=ImageNames.getTeamName();
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		recyleBitmap();
	}

	private void recyleBitmap(){
		for(Bitmap bitmap : images){
			bitmap.recycle();
			images=null;
		}
		teamName=null;
	}
}
