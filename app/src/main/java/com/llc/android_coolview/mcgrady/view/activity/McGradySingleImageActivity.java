package com.llc.android_coolview.mcgrady.view.activity;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Environment;
import android.os.IBinder;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;

import com.llc.android_coolview.BaseActivity;
import com.llc.android_coolview.R;
import com.llc.android_coolview.mcgrady.adapter.GalleryAdapter;
import com.llc.android_coolview.mcgrady.controll.McgRadyImageLoader;
import com.llc.android_coolview.mcgrady.controll.McgRadyImageLoader.Type;
import com.llc.android_coolview.mcgrady.model.Images;
import com.llc.android_coolview.mcgrady.service.DownloadService;
import com.llc.android_coolview.mcgrady.service.DownloadService.MyBinder;
import com.llc.android_coolview.mcgrady.slidingmenu.SlidingMenu;

@SuppressWarnings("deprecation")
public class McGradySingleImageActivity extends BaseActivity implements
		OnClickListener {

	private Gallery gallery;
	private ImageView singleImg;
	private Intent intent;
	private String[] mImgUrls = Images.imageThumbUrls;
	private McgRadyImageLoader imageLoader;
	private GalleryAdapter galleryAdapter;
	private SlidingMenu slidingMenu;

	private RelativeLayout downLoad, share;
	private View view;
	private MyBinder binder;

	private int imagePosition;
	private String imgName;
	private String shareImgPath;

	private ServiceConnection conn = new ServiceConnection() {

		@Override
		public void onServiceDisconnected(ComponentName name) {

		}

		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			binder = (MyBinder) service;
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		getWindow().requestFeature(Window.FEATURE_NO_TITLE);
		if(VERSION.SDK_INT>=VERSION_CODES.LOLLIPOP){
			getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
		}
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mcgrady_singleimage);
		initView();
		initMenu();
		initData();
		initEvents();
		ShareSDK.initSDK(this);
	}

	private void initView() {
		gallery = (Gallery) this.findViewById(R.id.gallery);
		singleImg = (ImageView) this.findViewById(R.id.mcgrady_ssingle_img);

		view = getLayoutInflater().inflate(
				R.layout.activity_mcgrady_slidingmenu_left, null);
		downLoad = (RelativeLayout) view.findViewById(R.id.download_layout);
		share = (RelativeLayout) view.findViewById(R.id.share_layout);
	}

	private void initMenu() {
		slidingMenu = new SlidingMenu(this);
		slidingMenu.setMode(SlidingMenu.LEFT);
		// 设置滑动的屏幕范围，该设置为全屏区域都可以滑动
		//slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
		// 设置阴影图片的宽度
		slidingMenu.setShadowWidthRes(R.dimen.shadow_width);
		// 设置阴影图片
		slidingMenu.setShadowDrawable(R.drawable.shadow);
		// 设置SlidingMenu菜单的宽度
		slidingMenu.setBehindOffset(400);
		// SlidingMenu滑动时的渐变程度
		slidingMenu.setFadeDegree(0.5f);
		// 使SlidingMenu附加在Activity上
		slidingMenu.attachToActivity(this, SlidingMenu.SLIDING_WINDOW);
		slidingMenu.setMenu(view);

		slidingMenu
				.setSecondaryMenu(R.layout.activity_mcgrady_slidingmenu_right);
		slidingMenu.setSecondaryShadowDrawable(R.drawable.shadowright);
	}

	private void initData() {
		imageLoader = McgRadyImageLoader.getInstance(3, Type.LIFO);
		intent = getIntent();
		int url = intent.getIntExtra("url", 0);
		imageLoader.loadImage(mImgUrls[url], singleImg, true);
		galleryAdapter = new GalleryAdapter(McGradySingleImageActivity.this, 0,
				mImgUrls);
		gallery.setAdapter(galleryAdapter);
		gallery.setSelection(url);
		if(binder!=null){
			imgName=binder.getImgName();
			if(imgName!=null){
				shareImgPath=Environment.getExternalStorageDirectory().getAbsolutePath()+"/ImageCache/"+imgName+".jpg";
			}else{
				shareImgPath=Environment.getExternalStorageDirectory().getAbsolutePath()+"/DCIM/Screenshots/1.png";
			}
		}else{
			shareImgPath=Environment.getExternalStorageDirectory().getAbsolutePath()+"/DCIM/Screenshots/1.png";
		}
	}

	private void initEvents() {
		gallery.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
									   int position, long id) {
				imagePosition = position;
				imageLoader.loadImage(mImgUrls[position], singleImg, true);
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {

			}
		});
		downLoad.setOnClickListener(this);
		share.setOnClickListener(this);
		singleImg.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.download_layout:
				downLoadImg();
				break;
			case R.id.share_layout:
				// 一键分享。还可以自定义分享(自己指定分享的UI)。通过不同平台的接口进行分享的接入
				slidingMenu.toggle();
				showShare();
				break;
			case R.id.mcgrady_ssingle_img:
				slidingMenu.showMenu();
				break;
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (binder != null) {
			unbindService(conn);
		}
	}

	private void downLoadImg(){
		Toast.makeText(McGradySingleImageActivity.this, "正在下载，您可以继续浏览...",Toast.LENGTH_LONG).show();
		Intent intent = new Intent(McGradySingleImageActivity.this,DownloadService.class);
		intent.putExtra("url", mImgUrls[imagePosition]);
		bindService(intent, conn, BIND_AUTO_CREATE);
		slidingMenu.toggle();
	}

	private void showShare() {
		OnekeyShare oks = new OnekeyShare();
		// 关闭sso授权
		oks.disableSSOWhenAuthorize();
		// 分享时Notification的图标和文字 2.5.9以后的版本不调用此方法
		// oks.setNotification(R.drawable.ic_launcher,getString(R.string.app_name));
		// title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
		oks.setTitle(getString(R.string.share));
		// titleUrl是标题的网络链接，仅在人人网和QQ空间使用
		oks.setTitleUrl("http://sharesdk.cn");
		// text是分享文本，所有平台都需要这个字段
		oks.setText(getString(R.string.share_text));
		// imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
		oks.setImagePath(shareImgPath);// 确保SDcard下面存在此张图片
		// url仅在微信（包括好友和朋友圈）中使用
		if(mImgUrls==null||mImgUrls.length==0){
			oks.setUrl(getString(R.string.share_url));
		}else{
			oks.setUrl(mImgUrls[imagePosition]);
		}
		// comment是我对这条分享的评论，仅在人人网和QQ空间使用
		oks.setComment(getString(R.string.share_comment));
		// site是分享此内容的网站名称，仅在QQ空间使用
		oks.setSite(getString(R.string.app_name));
		// siteUrl是分享此内容的网站地址，仅在QQ空间使用
		oks.setSiteUrl("http://sharesdk.cn");
		// 启动分享GUI
		oks.show(this);
	}
}
