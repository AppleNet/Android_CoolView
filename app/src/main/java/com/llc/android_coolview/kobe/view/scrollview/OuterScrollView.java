package com.llc.android_coolview.kobe.view.scrollview;

import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.widget.ScrollView;

public class OuterScrollView extends ScrollView {

	private static final int MAX_Y_OVERSCROLL_DISTANCE = 200;
    
    private Context mContext;
	private int mMaxYOverscrollDistance;
	
	public OuterScrollView(Context context){
		super(context);
		this.mContext=context;
		initBounceListView();
	}
	
	public OuterScrollView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.mContext=context;
		initBounceListView();
	}

	public OuterScrollView(Context context, AttributeSet attrs, int defStyle){
		super(context, attrs, defStyle);
		this.mContext = context;
		initBounceListView();
	}
	
	private void initBounceListView(){
		final DisplayMetrics metrics = mContext.getResources().getDisplayMetrics();
        final float density = metrics.density;
		mMaxYOverscrollDistance = (int) (density * MAX_Y_OVERSCROLL_DISTANCE);
	}
	
	@Override
	protected boolean overScrollBy(int deltaX, int deltaY, int scrollX,int scrollY, int scrollRangeX, int scrollRangeY, int maxOverScrollX,
			int maxOverScrollY, boolean isTouchEvent) {
		return super.overScrollBy(deltaX, deltaY, scrollX, scrollY, scrollRangeX,scrollRangeY, maxOverScrollX, mMaxYOverscrollDistance, isTouchEvent);
	}
}
