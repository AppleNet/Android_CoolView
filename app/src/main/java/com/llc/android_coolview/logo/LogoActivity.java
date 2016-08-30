package com.llc.android_coolview.logo;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.llc.android_coolview.BaseActivity;
import com.llc.android_coolview.R;
import com.llc.android_coolview.guide.GuideActivity;
import com.llc.android_coolview.login.CoolViewLoginActivity;
import com.llc.android_coolview.menu.MainMenuActivity;

public class LogoActivity extends BaseActivity {

	private TextView logoContent;
	private SharedPreferences sp;
	private Intent intent;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		getWindow().requestFeature(Window.FEATURE_NO_TITLE);
		if(VERSION.SDK_INT>=VERSION_CODES.LOLLIPOP){
			getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
		}
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_logo);
		initView();
		initData();
	}

	private void initView(){
		logoContent=(TextView) this.findViewById(R.id.logo_content);
		logoContent.setText(getResources().getString(R.string.logo_content));
	}



	private void initData(){

		new Handler().postDelayed(new Runnable() {

			@Override
			public void run() {
				isFirstLogin(isFirst());
			}
		}, 2000);

	}

	private boolean isFirst(){
		sp=getSharedPreferences("isFirst", Context.MODE_PRIVATE);
		boolean isFirst=sp.getBoolean("isFirstLogin", true);
		Editor editor=sp.edit();
		editor.putBoolean("isFirstLogin", false);
		editor.commit();
		return isFirst;
	}

	private void isFirstLogin(boolean flag){
		intent = new Intent();
		if(flag){
			intent.setClass(LogoActivity.this, GuideActivity.class);
			startActivityForResult(intent, 0);
			overridePendingTransition(R.anim.fade, R.anim.hold);
			this.finish();
		}else{
			sp=getSharedPreferences("isFirstLogin", Context.MODE_PRIVATE);
			// 授权登录
			boolean isLogin=sp.getBoolean("isLogin", false);
			if(!isLogin){
				// 初次进来，没有登录，让用户去登录
				intent.setClass(LogoActivity.this, CoolViewLoginActivity.class);
				startActivityForResult(intent,0);
				overridePendingTransition(R.anim.fade, R.anim.hold);
				Editor edit=sp.edit();
				edit.putBoolean("isLogin", true);
				edit.commit();
				this.finish();
			}else{
				intent.setClass(LogoActivity.this, MainMenuActivity.class);
				startActivityForResult(intent, 0);
				overridePendingTransition(R.anim.fade, R.anim.hold);
			}
		}
		this.finish();
	}
}
