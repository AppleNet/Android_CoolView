package com.llc.android_coolview.view;

import com.llc.android_coolview.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class ToastUtils extends Toast {

	
	public ToastUtils(Context context,String message) {
		super(context);
		View layout=LayoutInflater.from(context).inflate(R.layout.activity_toast, null);
		TextView textView=(TextView) layout.findViewById(R.id.message);
		textView.setText(message);
		this.setDuration(Toast.LENGTH_SHORT);
		this.setView(layout);
	}

	
}
