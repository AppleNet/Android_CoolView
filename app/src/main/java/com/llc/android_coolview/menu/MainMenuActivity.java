package com.llc.android_coolview.menu;

import com.llc.android_coolview.BaseActivity;
import com.llc.android_coolview.R;
import com.llc.android_coolview.kobe.activity.KobeActivity;
import com.llc.android_coolview.mcgrady.view.activity.McGradyActivity;
import com.llc.android_coolview.menu.customview.MyImageView;
import com.llc.android_coolview.paul.view.activity.PaulActivity;
import com.llc.android_coolview.wade.view.activity.WadeActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;

public class MainMenuActivity extends BaseActivity implements OnClickListener {

	private MyImageView mcgrady, paul, wade, kobe;
	private long currentBackPressedTime = 0;
	private static final int BACK_PRESSED_INTERVAL = 2000;
	private TextView exit;
	private SharedPreferences sp;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		getWindow().requestFeature(Window.FEATURE_NO_TITLE);
		if(VERSION.SDK_INT>=VERSION_CODES.LOLLIPOP){
			getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
		}
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initView();
		initEvents();
	}

	private void initView() {
		mcgrady = (MyImageView) this.findViewById(R.id.nba_mcgrady);
		paul = (MyImageView) this.findViewById(R.id.nba_paul);
		wade = (MyImageView) this.findViewById(R.id.nba_wade);
		kobe = (MyImageView) this.findViewById(R.id.nba_kobe);
		exit=(TextView) this.findViewById(R.id.exitLogin);
	}

	private void initEvents() {
		mcgrady.setOnClickListener(this);
		paul.setOnClickListener(this);
		wade.setOnClickListener(this);
		kobe.setOnClickListener(this);
		exit.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		Intent intent = null;
		switch (v.getId()) {
			case R.id.nba_mcgrady:
				intent = new Intent(MainMenuActivity.this, McGradyActivity.class);
				startActivity(intent);
				break;
			case R.id.nba_paul:
				intent = new Intent(MainMenuActivity.this, PaulActivity.class);
				startActivity(intent);
				break;
			case R.id.nba_wade:
				intent = new Intent(MainMenuActivity.this, WadeActivity.class);
				startActivity(intent);
				break;
			case R.id.nba_kobe:
				intent=new Intent(MainMenuActivity.this,KobeActivity.class);
				startActivity(intent);
				break;
			case R.id.exitLogin:
				sp=getSharedPreferences("isFirstLogin", Context.MODE_PRIVATE);
				Editor edit=sp.edit();
				edit.putBoolean("isLogin", false);
				edit.commit();
				this.finish();
				break;
			default:
				break;
		}
	}

	@Override
	public void onBackPressed() {
		// 判断时间间隔
		if (System.currentTimeMillis() - currentBackPressedTime > BACK_PRESSED_INTERVAL) {
			currentBackPressedTime = System.currentTimeMillis();
			Toast.makeText(this, "再按一次返回键退出程序", Toast.LENGTH_LONG).show();
		} else {
			// 退出
			finish();
		}

	}
}
