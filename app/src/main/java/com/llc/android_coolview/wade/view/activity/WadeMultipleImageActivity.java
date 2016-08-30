package com.llc.android_coolview.wade.view.activity;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ViewFlipper;

import com.llc.android_coolview.BaseActivity;
import com.llc.android_coolview.R;
import com.llc.android_coolview.wade.control.WadeImageLoader;
import com.llc.android_coolview.wade.control.WadeImageLoader.Type;

public class WadeMultipleImageActivity extends BaseActivity implements OnGestureListener, OnTouchListener, OnClickListener{

	private GestureDetector detector;
	private ViewFlipper myViewFlipper;
	private ImageView flipperPre,flipperNext;
	private Intent intent;
	private WadeImageLoader imageLoader;
	private ImageView image;
	private ArrayList<String> mImgUrls;
	private View view;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		getWindow().requestFeature(Window.FEATURE_NO_TITLE);
		if(VERSION.SDK_INT>=VERSION_CODES.LOLLIPOP){
			getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
		}
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mcgrady_image_flipper_shade);
		initViews();
		initEvents();
	}

	@SuppressWarnings("deprecation")
	private void initViews(){
		detector=new GestureDetector(this);
		myViewFlipper=(ViewFlipper) this.findViewById(R.id.viewflipper);
		flipperPre=(ImageView) this.findViewById(R.id.flipper_pre);
		flipperNext=(ImageView) this.findViewById(R.id.flipper_next);
		imageLoader=WadeImageLoader.getInstance(3, Type.LIFO);
	}

	private void initEvents(){
		intent=getIntent();
		mImgUrls=intent.getStringArrayListExtra("localImgUrl");
		myViewFlipper.setOnTouchListener(this);
		myViewFlipper.setLongClickable(true);// 设置长按事件
		//myViewFlipper.setAutoStart(true);// 设置是否自动播放，默认不自动播放
		flipperPre.setOnClickListener(this);
		flipperNext.setOnClickListener(this);
		addFlipperView();
	}


	private void addFlipperView(){
		if(mImgUrls.size()>=0){
			for(int i=0;i<mImgUrls.size();i++){
				view = LayoutInflater.from(WadeMultipleImageActivity.this).inflate(R.layout.activity_mcgrady_flipper_image_view, null);
				image=(ImageView) view.findViewById(R.id.id_flipper_img);
				imageLoader.loadImage(mImgUrls.get(i), image);
				myViewFlipper.addView(view);
			}
		}

	}

	@Override
	public boolean onDown(MotionEvent e) {
		return false;
	}

	@Override
	public void onShowPress(MotionEvent e) {

	}

	@Override
	public boolean onSingleTapUp(MotionEvent e) {
		return false;
	}

	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
							float distanceY) {
		return false;
	}

	@Override
	public void onLongPress(MotionEvent e) {

	}

	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
						   float velocityY) {
//		// 返回当前正在显示的子视图的索引  
		if (e1.getX() - e2.getX() >= 100) {
			showNextView();
		} else if (e2.getX() - e1.getX() >= 100) {
			showPreviousView();
		}
		return false;
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		return detector.onTouchEvent(event);
	}

	@Override
	public void onClick(View v) {
		switch(v.getId()){
			case R.id.flipper_next:
				showNextView();
				break;
			case R.id.flipper_pre:
				showPreviousView();
				break;
		}
	}

	private void showPreviousView() {
		myViewFlipper.setInAnimation(AnimationUtils.loadAnimation(this,
				R.anim.right_in));
		myViewFlipper.setOutAnimation(AnimationUtils.loadAnimation(this,
				R.anim.right_out));
		myViewFlipper.showPrevious();
	}

	private void showNextView() {
		myViewFlipper.setInAnimation(AnimationUtils.loadAnimation(this,
				R.anim.left_in));
		myViewFlipper.setOutAnimation(AnimationUtils.loadAnimation(this,
				R.anim.left_out));
		myViewFlipper.showNext();

	}
}
