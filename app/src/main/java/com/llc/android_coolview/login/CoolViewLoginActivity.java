package com.llc.android_coolview.login;

import java.util.HashMap;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.tencent.qzone.QZone;
import cn.sharesdk.wechat.friends.Wechat;
import cn.smssdk.SMSSDK;

import com.llc.android_coolview.BaseActivity;
import com.llc.android_coolview.R;
import com.llc.android_coolview.menu.MainMenuActivity;

public class CoolViewLoginActivity extends BaseActivity implements
		OnClickListener, PlatformActionListener {

	private static final int MSG_SMSSDK_CALLBACK = 1;
	private static final int MSG_AUTH_CANCEL = 2;
	private static final int MSG_AUTH_ERROR= 3;
	private static final int MSG_AUTH_COMPLETE = 4;

	private TextView tvWeixin, tvWeibo, tvQq, tvOther,tvMsgRegister;

	//短信验证的对话框
	private Dialog msgLoginDlg;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		getWindow().requestFeature(Window.FEATURE_NO_TITLE);
		if(VERSION.SDK_INT>=VERSION_CODES.LOLLIPOP){
			getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
		}
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		initData();
		initView();
		initEvents();
	}

	private void initData() {
		ShareSDK.initSDK(this);

	}

	private void initView() {
		tvWeixin = (TextView) this.findViewById(R.id.tvWeixin);
		tvWeibo = (TextView) this.findViewById(R.id.tvWeibo);
		tvQq = (TextView) this.findViewById(R.id.tvQq);
		tvOther = (TextView) this.findViewById(R.id.tvOther);
		tvMsgRegister=(TextView) this.findViewById(R.id.tvMsgRegister);
	}

	private void initEvents() {
		tvWeixin.setOnClickListener(this);
		tvWeibo.setOnClickListener(this);
		tvQq.setOnClickListener(this);
		tvOther.setOnClickListener(this);
		tvMsgRegister.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.tvWeixin:
				//微信登录
				//测试时，需要打包签名；sample测试时，用项目里面的demokey.keystore
				//打包签名apk,然后才能产生微信的登录
				Platform wechat = ShareSDK.getPlatform(Wechat.NAME);
				authorize(wechat);
				break;
			case R.id.tvWeibo:
				//新浪微博
				Platform sina = ShareSDK.getPlatform(SinaWeibo.NAME);
				authorize(sina);
				break;
			case R.id.tvQq:
				//QQ空间
				Platform qzone = ShareSDK.getPlatform(QZone.NAME);
				authorize(qzone);
				break;
			case R.id.tvOther:
				//其他登录
				authorize(null);
				break;
			case R.id.tvMsgRegister:
				//短信登录
				popupMsgLogin();
				break;
		}
	}

	// 执行授权,获取用户信息
	private void authorize(Platform plat) {
		if (plat == null) {
			popupOthers();
			return;
		}
		plat.setPlatformActionListener(this);
		// 关闭SSO授权
		plat.SSOSetting(true);
		plat.showUser(null);
	}

	private void popupMsgLogin(){
		msgLoginDlg = new Dialog(CoolViewLoginActivity.this, R.style.WhiteDialog);
		View dlgView = View.inflate(CoolViewLoginActivity.this, R.layout.sharesdk_tpl_msg_login_dialog, null);
		final EditText etPhone = (EditText) dlgView.findViewById(R.id.et_phone);
		final EditText etVerifyCode = (EditText) dlgView.findViewById(R.id.et_verify_code);
		Button btnGetVerifyCode = (Button) dlgView.findViewById(R.id.btn_get_verify_code);
		Button btnSendVerifyCode = (Button) dlgView.findViewById(R.id.btn_send_verify_code);
		btnGetVerifyCode.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				String phone = etPhone.getText().toString();
				if(TextUtils.isEmpty(phone)){
					Toast.makeText(CoolViewLoginActivity.this, "请输入手机号码", Toast.LENGTH_SHORT).show();
				}else{
					SMSSDK.getVerificationCode("86", phone);
				}
			}
		});
		btnSendVerifyCode.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				String phone = etPhone.getText().toString();
				String verifyCode = etVerifyCode.getText().toString();
				if(TextUtils.isEmpty(verifyCode)){
					Toast.makeText(CoolViewLoginActivity.this, "请输入验证码", Toast.LENGTH_SHORT).show();
				}else{
					SMSSDK.submitVerificationCode("86", phone, verifyCode);
				}
			}
		});
		msgLoginDlg.requestWindowFeature(Window.FEATURE_NO_TITLE);
		msgLoginDlg.setContentView(dlgView);
		msgLoginDlg.show();
	}

	// 其他登录对话框
	private void popupOthers() {
		Dialog dlg = new Dialog(CoolViewLoginActivity.this, R.style.WhiteDialog);
		View dlgView = View.inflate(CoolViewLoginActivity.this,R.layout.sharesdk_tpl_other_plat_dialog, null);
		View tvFacebook = dlgView.findViewById(R.id.tvFacebook);
		tvFacebook.setTag(dlg);
		tvFacebook.setOnClickListener(this);
		View tvTwitter = dlgView.findViewById(R.id.tvTwitter);
		tvTwitter.setTag(dlg);
		tvTwitter.setOnClickListener(this);
		dlg.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dlg.setContentView(dlgView);
		dlg.show();
	}

	@Override
	public void onCancel(Platform platform, int action) {
		if (action == Platform.ACTION_USER_INFOR) {
			handler.sendEmptyMessage(MSG_AUTH_CANCEL);
		}
	}

	@Override
	public void onComplete(Platform platform, int action, HashMap<String, Object> res) {
		if (action == Platform.ACTION_USER_INFOR) {
			Message msg = new Message();
			msg.what = MSG_AUTH_COMPLETE;
			msg.obj = new Object[] {platform.getName(), res};
			handler.sendMessage(msg);
		}
	}

	@Override
	public void onError(Platform platform, int action, Throwable t) {
		if (action == Platform.ACTION_USER_INFOR) {
			handler.sendEmptyMessage(MSG_AUTH_ERROR);
		}
		t.printStackTrace();
	}

	Handler handler=new Handler(new Handler.Callback() {

		@SuppressWarnings("unchecked")
		@Override
		public boolean handleMessage(Message msg) {
			Intent intent=new Intent();
			switch(msg.what){
				case MSG_AUTH_CANCEL:
					Toast.makeText(CoolViewLoginActivity.this, "授权操作已取消！", Toast.LENGTH_LONG).show();
					break;
				case MSG_AUTH_ERROR:
					Toast.makeText(CoolViewLoginActivity.this, "授权失败！", Toast.LENGTH_LONG).show();
					break;
				case MSG_AUTH_COMPLETE:
					Toast.makeText(CoolViewLoginActivity.this, "授权成功！", Toast.LENGTH_LONG).show();
					boolean flag=true;
					// 跟后台服务器进行交互，判断这个第三方帐号是否是新用户，如果用户没有注册(是新用户)，此时需要获取用户信息
					if(flag){
						intent.setClass(CoolViewLoginActivity.this, MainMenuActivity.class);
						startActivity(intent);
						overridePendingTransition(R.anim.fade, R.anim.hold);
						CoolViewLoginActivity.this.finish();
					}else{
						intent.setClass(CoolViewLoginActivity.this, GetUserInfoActivity.class);
						startActivity(intent);
						overridePendingTransition(R.anim.fade, R.anim.hold);
						CoolViewLoginActivity.this.finish();
					}
					CoolViewLoginActivity.this.finish();
					break;
				case MSG_SMSSDK_CALLBACK:
					if (msg.arg2 == SMSSDK.RESULT_ERROR) {
						Toast.makeText(CoolViewLoginActivity.this, "操作失败", Toast.LENGTH_SHORT).show();
					} else {
						switch (msg.arg1) {
							case SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE: {
								if(msgLoginDlg != null && msgLoginDlg.isShowing()){
									msgLoginDlg.dismiss();
								}
								Toast.makeText(CoolViewLoginActivity.this, "提交验证码成功", Toast.LENGTH_SHORT).show();
								Message m = new Message();
								m.what = MSG_AUTH_COMPLETE;
								m.obj = new Object[] {"SMSSDK", (HashMap<String, Object>) msg.obj};
								handler.sendMessage(m);
							} break;
							case SMSSDK.EVENT_GET_VERIFICATION_CODE:{
								Toast.makeText(CoolViewLoginActivity.this, "验证码已经发送", Toast.LENGTH_SHORT).show();
							} break;
						}
					}
					break;
			}
			return false;
		}
	});
}
