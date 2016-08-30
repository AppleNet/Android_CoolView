package com.llc.android_coolview.kobe.view.viewpager;

import android.content.Context;
import android.graphics.PointF;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class MyViewPager extends ViewPager {

	private PointF downPoint = new PointF();
	private OnSingleTouchListener onSingleTouchListener;

	public MyViewPager(Context context) {
		super(context);
	}

	public MyViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
	}



	@Override
	public boolean onTouchEvent(MotionEvent event) {
		switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:
				// 记录按下时候的坐标
				downPoint.x = event.getX();
				downPoint.y = event.getY();
				if (this.getChildCount() > 1) {
					//有内容，多于1个时
					//通知其父控件，现在进行的是本控件的操作，不允许拦截
					getParent().requestDisallowInterceptTouchEvent(true);
				}
				break;
			case MotionEvent.ACTION_MOVE:
				if (this.getChildCount() > 1) {
					//有内容，多于1个时
					//通知其父控件，现在进行的是本控件的操作，不允许拦截
					getParent().requestDisallowInterceptTouchEvent(true);
				}
				break;
			case MotionEvent.ACTION_UP:
				// 在up时判断是否按下和松手的坐标为一个点
				if (PointF.length(event.getX() - downPoint.x, event.getY() - downPoint.y) < (float) 5.0) {
					onSingleTouchListener.onSingleTouch();
					return true;
				}
				break;
			default:
				break;
		}
		return super.onTouchEvent(event);
	}

	public interface OnSingleTouchListener{
		public void onSingleTouch();
	}

	public void setOnSingleTouchListener(OnSingleTouchListener onSingleTouchListener){
		this.onSingleTouchListener=onSingleTouchListener;
	}
}