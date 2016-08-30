package com.llc.android_coolview.kobe.activity;

import com.llc.android_coolview.R;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;
import android.widget.Toast;

public class KobeNewsDetailActivity extends Activity {

	private TextView titleTv;
	private WebView mWebView;
	private Intent intent;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_kobe_fragment_news_details);
		initViews();
		initDatas();
	}
	
	private void initViews(){
		titleTv=(TextView) this.findViewById(R.id.news_detail_title);
		mWebView=(WebView) this.findViewById(R.id.webView1);
	}
	
	private void initDatas(){
		intent=getIntent();
		String url_3w=null;
		if(intent!=null){
			String title=intent.getStringExtra("title");
			if(title!=null){
				titleTv.setText(title);
			}
			url_3w=intent.getStringExtra("url_3w");
		}
		mWebView.loadUrl(url_3w);
		mWebView.getSettings().setUseWideViewPort(true);
		mWebView.getSettings().setLoadWithOverviewMode(true); 
		mWebView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);  
	    mWebView.getSettings().setBuiltInZoomControls(true);  
	    mWebView.getSettings().setJavaScriptEnabled(true);
	    mWebView.setWebViewClient(new WebViewClient(){
	    	 @Override  
	            public boolean shouldOverrideUrlLoading(WebView view, String url) {  
	                view.loadUrl(url);
	                return true;
	            }  
	            @Override  
	            public void onPageStarted(WebView view, String url, Bitmap favicon) {  
	            }
	            @Override  
	            public void onPageFinished(WebView view, String url) {  
	            }
	            @Override  
	            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {  
	                Toast.makeText(KobeNewsDetailActivity.this, "Oh no! " + description, Toast.LENGTH_SHORT).show();  
	            }  
	     });
	    mWebView.setWebChromeClient(new WebChromeClient(){

			@Override
			public boolean onJsAlert(WebView view, String url, String message,
					JsResult result) {
				return super.onJsAlert(view, url, message, result);
			}
	    	
	    });
	    
	    
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && mWebView.canGoBack()) {
            mWebView.goBack();  
            return true;  
        }  
		return super.onKeyDown(keyCode, event);
	}
}
