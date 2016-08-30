package com.llc.android_coolview.util;


import android.app.ProgressDialog;
import android.content.Context;

public class ProgressDialogUtils {

	private ProgressDialog mProgressDialog;
	
	public ProgressDialogUtils(Context mContext) {
		if(mProgressDialog!=null&&mProgressDialog.isShowing()){
			return;
		}
		mProgressDialog=new ProgressDialog(mContext);
		mProgressDialog.setCanceledOnTouchOutside(false);
	}
	
	public void showProgressDialog(String msg){
		mProgressDialog.setMessage(msg);
		mProgressDialog.show();
	}
	
	public void dismissProgressDialog(){
		if (mProgressDialog != null) {
			mProgressDialog.dismiss();
		}
	}
}
