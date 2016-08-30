package com.llc.android_coolview.kobe.view.dialog;

import com.llc.android_coolview.R;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.TextView;

public class MyAlertDialog extends AlertDialog {

	private MyProgressDialog myProgressDialog;
	private TextView update_text;
	private int mMax;
	private int mProgressVal;
	private boolean mHasStarted;
	private int width;

	public MyAlertDialog(Context context) {
		super(context);
	}

	public MyAlertDialog(Context context, int theme) {
		super(context, theme);
	}

	public MyAlertDialog(Context context, boolean cancelable,
						 OnCancelListener cancelListener) {
		super(context, cancelable, cancelListener);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_kobe_dialog_layout_update);
		initData();
	}

	private void initData() {
		myProgressDialog = (MyProgressDialog) this
				.findViewById(R.id.myprogress);
		update_text = (TextView) this.findViewById(R.id.update_text);
		width = 500 / 2;
		myProgressDialog.getLayoutParams().height = width;
		myProgressDialog.getLayoutParams().width = width;
		myProgressDialog.setTextSize(90);
		update_text.setText("正在获取中...");
		update_text.setTextColor(
				getContext().getResources().getColor(R.color.white));
		update_text.setTextSize(40);
	}

	public void setProgress(int value) {
		if (mHasStarted) {
			myProgressDialog.setProgress(value);
		} else {
			mProgressVal = value;
		}
	}

	public int getProgress() {
		if (myProgressDialog != null) {
			return myProgressDialog.getProgress();
		}
		return mProgressVal;
	}

	public void setMax(int max) {
		if (myProgressDialog != null) {
			myProgressDialog.setMax(max);
		} else {
			mMax = max;
		}
	}

	public int getMax() {
		if (myProgressDialog != null) {
			return myProgressDialog.getMax();
		}
		return mMax;
	}

	@Override
	public void onStart() {
		super.onStart();
		mHasStarted = true;
	}

	@Override
	protected void onStop() {
		super.onStop();
		mHasStarted = false;
	}
}
