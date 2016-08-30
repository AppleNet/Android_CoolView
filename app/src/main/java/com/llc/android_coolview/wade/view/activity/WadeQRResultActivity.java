package com.llc.android_coolview.wade.view.activity;

import com.llc.android_coolview.R;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class WadeQRResultActivity extends AppCompatActivity {

	private ImageView barcodeImageView;
	private TextView formatTextView;
	private TextView timeTextView;
	private TextView metaTextView;
	private Bitmap barcodeBitmap;
	private String barcodeFormat;
	private String decodeDate;
	private CharSequence metadataText;
	private String resultString;
	private Bundle bundle;

	private WebView mWebView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_wade_qr_result);
		initView();
		mGetIntentData();
		setView();
	}

	private void setView() {
		barcodeImageView.setImageBitmap(barcodeBitmap);
		formatTextView.setText(barcodeFormat);
		timeTextView.setText(decodeDate);
		metaTextView.setText(metadataText);

		mWebView.loadUrl(resultString);
		//设置加载进来的页面自适应手机屏幕 
		mWebView.getSettings().setUseWideViewPort(true);
		mWebView.getSettings().setLoadWithOverviewMode(true);
		mWebView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
		mWebView.getSettings().setBuiltInZoomControls(true);
		mWebView.getSettings().setJavaScriptEnabled(true);
	     /*
	      * WebView默认用系统自带浏览器处理页面跳转。
	      * 为了让页面跳转在当前WebView中进行，重写WebViewClient。 
	      * 但是按BACK键时，不会返回跳转前的页面，而是退出本Activity。重写onKeyDown()方法来解决此问题。 
	      * */
		mWebView.setWebViewClient(new WebViewClient(){
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				view.loadUrl(url);//使用当前WebView处理跳转
				return true;//true表示此事件在此处被处理，不需要再广播
			}
			@Override
			public void onPageStarted(WebView view, String url, Bitmap favicon) {
				//有页面跳转时被回调
			}
			@Override
			public void onPageFinished(WebView view, String url) {
				//页面跳转结束后被回调
			}
			@Override
			public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
				Toast.makeText(WadeQRResultActivity.this, "Oh no! " + description, Toast.LENGTH_SHORT).show();
			}
		});
	    /*
	     * 当WebView内容影响UI时 调用WebView的webChromeClient
	     * 
	     * */
		mWebView.setWebChromeClient(new WebChromeClient(){

			@Override
			public boolean onJsAlert(WebView view, String url, String message,
									 JsResult result) {
				return super.onJsAlert(view, url, message, result);
			}

		});
	}

	private void mGetIntentData() {
		bundle = new Bundle();
		bundle = this.getIntent().getExtras();
		barcodeBitmap = bundle.getParcelable("bitmap");
		barcodeFormat = bundle.getString("barcodeFormat");
		decodeDate = bundle.getString("decodeDate");
		metadataText = bundle.getCharSequence("metadataText");
		resultString = bundle.getString("resultString");
	}

	private void initView() {
		barcodeImageView = (ImageView) findViewById(R.id.barcode_image_view);
		formatTextView = (TextView) findViewById(R.id.format_text_view);
		timeTextView = (TextView) findViewById(R.id.time_text_view);
		metaTextView = (TextView) findViewById(R.id.meta_text_view);
		mWebView=(WebView) this.findViewById(R.id.result_uri);
	}

	public void backCapture(View view) {
		runBack();
	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			//runBack();
			//处理WebView跳转返回  
			if ((keyCode == KeyEvent.KEYCODE_BACK) && mWebView.canGoBack()) {
				mWebView.goBack();
				return true;
			}
		}
		return false;
	}

	public void runBack() {
		Intent intent = new Intent(WadeQRResultActivity.this, WadeQRActivity.class);
		startActivity(intent);
		WadeQRResultActivity.this.finish();
	}
}
