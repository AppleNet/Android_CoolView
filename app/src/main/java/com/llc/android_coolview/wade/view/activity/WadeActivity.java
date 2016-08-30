package com.llc.android_coolview.wade.view.activity;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ShareActionProvider;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.PopupWindow.OnDismissListener;
import com.llc.android_coolview.R;
import com.llc.android_coolview.animation.SwitchAnimationUtil;
import com.llc.android_coolview.wade.adapter.MyAdapter;
import com.llc.android_coolview.wade.adapter.holder.ViewHolder;
import com.llc.android_coolview.wade.adapter.impl.CommonAdapter;
import com.llc.android_coolview.wade.bean.ImageFolder;
import com.llc.android_coolview.wade.player.WadeVideoPlayerActivity;
import com.llc.android_coolview.wade.view.popupwindow.ListImageDirPopupWindow;
import com.llc.android_coolview.wade.view.popupwindow.ListImageDirPopupWindow.OnImageDirSelected;


public class WadeActivity extends AppCompatActivity implements OnImageDirSelected{

	private int mScreenHeight;
	private ProgressDialog mProgressDialog;
	private Set<String> mDirPaths=new HashSet<String>();
	private List<ImageFolder> mImageFloders=new ArrayList<ImageFolder>();
	private List<String> mImgs=new ArrayList<String>();
	private int totalCount;
	private File mImgDir;
	private MyAdapter myAdapter;
	private GridView gridView;
	private TextView mImageCount,mChooseDir;
	private ListImageDirPopupWindow mListImageDirPopupWindow;
	private RelativeLayout mBottomLy;
	private ArrayList<String> mSelectedImgList=new ArrayList<String>();

	// 分享
	private ShareActionProvider mShareActionProvider;
	private Toolbar mToolbar;
	private DrawerLayout mDrawerLayout;
	private ActionBarDrawerToggle mDrawerToggle;
	private ListView mListView;
	private SwitchAnimationUtil switchAnimationUtil;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_wade);
		DisplayMetrics outMetrics=new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(outMetrics);
		mScreenHeight=outMetrics.heightPixels;
		initView();
		getImages();
		initEvent();
	}

	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		super.onWindowFocusChanged(hasFocus);
		if(switchAnimationUtil==null){
			switchAnimationUtil=new SwitchAnimationUtil();
			switchAnimationUtil.startAnimation(gridView, SwitchAnimationUtil.AnimationType.ROTATE);
		}
	}

	private void initView(){
		gridView=(GridView) this.findViewById(R.id.id_gridView);
		mImageCount=(TextView) this.findViewById(R.id.id_total_count);
		mBottomLy=(RelativeLayout) findViewById(R.id.id_bottom_ly);
		mChooseDir=(TextView) this.findViewById(R.id.id_choose_dir);
		mToolbar=(Toolbar) this.findViewById(R.id.layout_toolbar);
		mToolbar.setBackgroundColor(getResources().getColor(R.color.toolbar_background));
		mToolbar.setTitle("韦德");
		mToolbar.setTitleTextColor(getResources().getColor(R.color.action_bar_title_color));
		mToolbar.collapseActionView();
		setSupportActionBar(mToolbar);
		Window window = getWindow();
		// 很明显，这是新API才有的。设置状态栏的颜色
		window.setStatusBarColor(getResources().getColor(R.color.toolbar_title_background));

		if (getSupportActionBar() != null){
			getSupportActionBar().setHomeAsUpIndicator(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
			getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		}
		mDrawerLayout=(DrawerLayout) this.findViewById(R.id.drawer_layout);
		mListView=(ListView) this.findViewById(R.id.left_drawer_listview);
		mListView.setAdapter(new CommonAdapter<ImageFolder>(WadeActivity.this,mImageFloders,R.layout.activity_wade_list_dir_item) {

			@Override
			public void convert(ViewHolder holder, ImageFolder item) {
				holder.setText(R.id.id_dir_item_name, item.getName());
				holder.setImageByUrl(R.id.id_dir_item_image,item.getFirstImagePath());
				holder.setText(R.id.id_dir_item_count, item.getCount() + "张");
			}
		});
		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
									int position, long id) {
				mImgDir=new File(mImageFloders.get(position).getDir());
				mImgs=Arrays.asList(mImgDir.list(new FilenameFilter() {

					@Override
					public boolean accept(File dir, String filename) {
						if (filename.endsWith(".jpg") || filename.endsWith(".png")|| filename.endsWith(".jpeg"))
							return true;
						return false;
					}
				}));
				myAdapter=new MyAdapter(getApplicationContext(), mImgs, R.layout.activity_wade_grid_item,mImgDir.getAbsolutePath(),handler);
				gridView.setAdapter(myAdapter);
				mImageCount.setText(totalCount+"张");
				mChooseDir.setText(mImageFloders.get(position).getName());
				mToolbar.setTitle(mImageFloders.get(position).getName());
				mListImageDirPopupWindow.dismiss();
				mDrawerLayout.closeDrawer(Gravity.LEFT);
			}
		});
		mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, 0, 0){
			@Override
			public void onDrawerOpened(View drawerView) {
				super.onDrawerOpened(drawerView);
				invalidateOptionsMenu();
				mToolbar.setTitle("所有图片");
			}

			@Override
			public void onDrawerClosed(View drawerView) {
				super.onDrawerClosed(drawerView);
				invalidateOptionsMenu();
			}
		};
		mDrawerToggle.syncState();
		mDrawerToggle.setDrawerIndicatorEnabled(true);
		mDrawerLayout.setDrawerListener(mDrawerToggle);
		mDrawerLayout.setScrimColor(getResources().getColor(R.color.drawer_scrim_color));
	}

	private Handler handler=new Handler(new Handler.Callback() {

		@SuppressWarnings("unchecked")
		@Override
		public boolean handleMessage(Message msg) {

			if(msg.what==1){
				Bundle bundle=msg.getData();
				String imgDir=bundle.getString("ImgDir");
				mImgs=(List<String>) msg.obj;
				myAdapter=new MyAdapter(getApplicationContext(), mImgs, R.layout.activity_wade_grid_item,imgDir,handler);
				gridView.setAdapter(myAdapter);
				myAdapter.notifyDataSetChanged();
				totalCount=bundle.getInt("ImgCount");
				mImageCount.setText(totalCount+"张");
				String dirName=bundle.getString("ImgDirName");
				mChooseDir.setText("/"+dirName);
			}else{
				mProgressDialog.dismiss();
				//为View绑定数据
				data2View();
				//初始化展示文件夹的popupWindw
				initListDirPopupWindow();
			}
			return false;
		}
	});

	/**
	 * 利用ContentProvider扫描手机中的图片，此方法在运行在子线程中 完成图片的扫描，最终获得jpg最多的那个文件夹
	 */
	private void getImages(){

		mProgressDialog=ProgressDialog.show(WadeActivity.this, null, "正在加载...");
		new Thread(new Runnable() {

			@Override
			public void run() {
				String firstImage=null;
				Uri mImageUri=MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
				ContentResolver resolver=WadeActivity.this.getContentResolver();
				Cursor cursor=resolver.query(mImageUri, null,
						MediaStore.Images.Media.MIME_TYPE + " = ? or "
								+ MediaStore.Images.Media.MIME_TYPE + " = ? or "
								+ MediaStore.Images.Media.MIME_TYPE + " = ? ",
						new String[] { "image/jpeg", "image/png", "image/jpg" },
						MediaStore.Images.Media.DATE_MODIFIED);
				assert cursor != null;
				while(cursor.moveToNext()){
					// 获取所有拓展名是jpeg,jpg,png的图片
					String path=cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
					if(firstImage==null){
						firstImage=path;
					}
					//返回此抽象路径名的父目录的抽象路径名，或如果路径名没有指定父则为null 即每张图片所在的文件夹的名称路径
					File parentFile=new File(path).getParentFile();
					if(parentFile==null){
						continue;
					}
					// 获取文件夹的所在路径
					String dirPath=parentFile.getAbsolutePath();

					ImageFolder imageFloder;
					if(mDirPaths.contains(dirPath)){
						continue;
					}else{
						mDirPaths.add(dirPath);
						imageFloder=new ImageFolder();
						imageFloder.setDir(dirPath);
						imageFloder.setFirstImagePath(path);
					}
					int picSize=parentFile.list(new FilenameFilter() {

						@Override
						public boolean accept(File dir, String filename) {
							return filename.endsWith("jpg") || filename.endsWith("png") || filename.endsWith("jpeg");
						}
					}).length;
					totalCount+=picSize;
					imageFloder.setCount(picSize);
					mImageFloders.add(imageFloder);
					int mPicsSize=mImageFloders.size();
					if (picSize > mPicsSize){
						mPicsSize = picSize;
						if(parentFile.getAbsolutePath().equals("/storage/sdcard0/DCIM/Camera")){
							mImgDir = parentFile;
						}
					}
				}
				cursor.close();
				mDirPaths=null;
				handler.sendEmptyMessage(0x110);
			}
		}).start();;
	}

	private void data2View(){
		if(mImgDir==null){
			Toast.makeText(getApplicationContext(), "擦，一张图片没扫描到",Toast.LENGTH_SHORT).show();
			return;
		}
		/*
		 * 如果不指定parentFile.getAbsolutePath().equals("/storage/sdcard0/DCIM/Camera")这段代码的话，
		 * 最后展示的将是最后一个扫描的文件夹下的图片
		 * */
		mImgs=Arrays.asList(mImgDir.list());
		myAdapter=new MyAdapter(getApplicationContext(), mImgs, R.layout.activity_wade_grid_item,mImgDir.getAbsolutePath(),handler);
		gridView.setAdapter(myAdapter);
		mImageCount.setText(totalCount+"张");
	}

	private void initListDirPopupWindow(){
		mListImageDirPopupWindow=new ListImageDirPopupWindow(
				LayoutParams.MATCH_PARENT,
				(int) (mScreenHeight * 0.7),
				mImageFloders,
				LayoutInflater.from(getApplicationContext()).inflate(R.layout.activity_wade_list_dir, null));
		mListImageDirPopupWindow.setOnDismissListener(new OnDismissListener() {

			@Override
			public void onDismiss() {
				// 设置背景颜色变暗
				WindowManager.LayoutParams lp = getWindow().getAttributes();
				lp.alpha = 1.0f;
				getWindow().setAttributes(lp);
			}
		});
		// 设置选择文件夹的回调
		mListImageDirPopupWindow.setOnImageDirSelected(this);
	}

	private void initEvent(){
		mBottomLy.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				mListImageDirPopupWindow.setAnimationStyle(R.style.anim_popup_dir);
				mListImageDirPopupWindow.showAsDropDown(mBottomLy, 0, 0);
				// 设置背景颜色变暗
				WindowManager.LayoutParams lp = getWindow().getAttributes();
				lp.alpha = .3f;
				getWindow().setAttributes(lp);
			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		/* ShareActionProvider配置 */
		mShareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(menu.findItem(R.id.action_share));
		Intent intent = new Intent(Intent.ACTION_SEND);
		intent.setType("text/*");
		mShareActionProvider.setShareIntent(intent);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		Intent intent=null;
		switch (id) {
			case R.id.action_settings:
				showSelectImg();
				break;
			case R.id.action_scan:
				// 扫一扫
				intent=new Intent(WadeActivity.this,WadeQRActivity.class);
				startActivity(intent);
				break;
			case R.id.action_group_chat:
				// 调用录制视频

				break;
			case R.id.action_add_friend:
				// 在线看视频
				intent=new Intent(WadeActivity.this, WadeVideoPlayerActivity.class);
				startActivity(intent);
				break;
			default:
				break;
		}
		return super.onOptionsItemSelected(item);
	}

	private void showSelectImg(){
		Intent intent=new Intent();
		mSelectedImgList=myAdapter.getSelectImg();
		if(mSelectedImgList.size()<=0){
			Toast.makeText(WadeActivity.this, "请选择您要展示的图片...", Toast.LENGTH_LONG).show();
			return;
		}else{
			intent.setClass(WadeActivity.this, WadeMultipleImageActivity.class);
			intent.putStringArrayListExtra("localImgUrl", mSelectedImgList);
			startActivity(intent);
		}
	}

	@Override
	public void selected(ImageFolder folder) {
		mImgDir=new File(folder.getDir());
		mImgs=Arrays.asList(mImgDir.list(new FilenameFilter() {

			@Override
			public boolean accept(File dir, String filename) {
				if (filename.endsWith(".jpg") || filename.endsWith(".png")
						|| filename.endsWith(".jpeg"))
					return true;
				return false;
			}
		}));
		myAdapter=new MyAdapter(getApplicationContext(), mImgs, R.layout.activity_wade_grid_item,mImgDir.getAbsolutePath(),handler);
		gridView.setAdapter(myAdapter);
		mImageCount.setText(totalCount+"张");
		mChooseDir.setText(folder.getName());
		mListImageDirPopupWindow.dismiss();
	}
}
