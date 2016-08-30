package com.llc.android_coolview.guide;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Parcelable;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.TextView;

import com.llc.android_coolview.BaseActivity;
import com.llc.android_coolview.R;
import com.llc.android_coolview.login.CoolViewLoginActivity;
import com.llc.android_coolview.menu.MainMenuActivity;

public class GuideActivity extends BaseActivity implements OnPageChangeListener{

	private ViewPager viewPager;
	private ViewFlowImageAdapter adapter;
	private TextView skip;
	private int[] images=new int[]{
			R.drawable.guide_1,
			R.drawable.guide_2,
			R.drawable.guide_3,
			R.drawable.guide_4
		};
	private ArrayList<ImageView> imageList;
	private ArrayList<View> dotList;
	private int oldPosition = 0;
	private SharedPreferences sp;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		getWindow().requestFeature(Window.FEATURE_NO_TITLE);
		if(VERSION.SDK_INT>=VERSION_CODES.LOLLIPOP){
			getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
		}
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_guide);
		initView();
		initData();
	}
	
	private void initView(){
		viewPager=(ViewPager) this.findViewById(R.id.viewpager);
		skip=(TextView) findViewById(R.id.userguide_btn_skip);
	}
	
	@SuppressWarnings("deprecation")
	private void initData(){
		imageList=new ArrayList<ImageView>();
		dotList=new ArrayList<View>();
		dotList.add(findViewById(R.id.v_dot0));
		dotList.add(findViewById(R.id.v_dot1));
		dotList.add(findViewById(R.id.v_dot2));
		dotList.add(findViewById(R.id.v_dot3));
		for(int i=0;i<images.length;i++){
			ImageView imageView=new ImageView(this);
			imageView.setImageResource(images[i]);
			imageView.setScaleType(ScaleType.CENTER_CROP);
			imageList.add(imageView);
		}
		adapter=new ViewFlowImageAdapter();
		viewPager.setAdapter(adapter);
		viewPager.setOnPageChangeListener(this);
		
	}
	
	
	public void onSkip(View v){
		exitGuide();
	}
	
	private void exitGuide(){
		sp=getSharedPreferences("isFirstLogin", Context.MODE_PRIVATE);
		boolean isLogin=sp.getBoolean("isLogin", false);
		if(!isLogin){
			Intent intent=new Intent();
			intent.setClass(GuideActivity.this, CoolViewLoginActivity.class);
			startActivityForResult(intent,0);
			overridePendingTransition(R.anim.fade, R.anim.hold);
			Editor edit=sp.edit();
			edit.putBoolean("isLogin", true);
			edit.commit();
			this.finish();
		}else{
			Intent intent=new Intent(GuideActivity.this,MainMenuActivity.class);
			startActivity(intent);
			this.finish();
		}
		
	}
	
	
	private class ViewFlowImageAdapter extends PagerAdapter{

		@Override
		public int getCount() {
			return images.length;
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0==arg1;
		}
		
		@Override
		public Object instantiateItem(View arg0, int arg1) {
			((ViewPager) arg0).addView(imageList.get(arg1));
			return imageList.get(arg1);
		}

		@Override
		public void destroyItem(View arg0, int arg1, Object arg2) {
			((ViewPager) arg0).removeView((View) arg2);
		}

		@Override
		public void restoreState(Parcelable arg0, ClassLoader arg1) {

		}

		@Override
		public Parcelable saveState() {
			return null;
		}

		@Override
		public void startUpdate(View arg0) {

		}

		@Override
		public void finishUpdate(View arg0) {

		}
	}

	@Override
	public void onPageScrollStateChanged(int arg0) {
		
	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
		
	}

	@Override
	public void onPageSelected(int position) {
		dotList.get(oldPosition).setBackgroundResource(R.drawable.dot_normal);
		dotList.get(position).setBackgroundResource(R.drawable.dot_focused);
		oldPosition = position;
		if(position>=3){
			//exitGuide();
			skip.setVisibility(View.VISIBLE);
		}else{
			skip.setVisibility(View.GONE);
		}
	}
}
