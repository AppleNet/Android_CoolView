package com.llc.android_coolview.kobe.view.textview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.TextView;

public class DrawableCenterTextView extends TextView {

	public DrawableCenterTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public DrawableCenterTextView(Context context, AttributeSet attrs,int defStyle) {
		super(context, attrs, defStyle);
	}
	
	public DrawableCenterTextView(Context context){
		super(context);
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		
		Drawable[] drawables=getCompoundDrawables();
		if(drawables!=null){
			Drawable drawableLeft=drawables[0];
			if(drawableLeft!=null){
				float textWidth=getPaint().measureText(getText().toString());
				int drawablePadding=getCompoundDrawablePadding();
				int drawableWidth=drawableLeft.getIntrinsicWidth();
				float bodyWidth=textWidth+drawablePadding+drawableWidth;
				canvas.translate((getWidth()-bodyWidth)/2, getHeight());
			}
		}
		super.onDraw(canvas);
	}
}
