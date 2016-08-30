package com.llc.android_coolview.kobe.activity;

import java.util.ArrayList;
import java.util.List;

import com.llc.android_coolview.R;
import com.llc.android_coolview.kobe.bean.news.pic.Picture;
import com.llc.android_coolview.mcgrady.controll.McgRadyImageLoader;
import com.llc.android_coolview.mcgrady.controll.McgRadyImageLoader.Type;
import com.llc.android_coolview.util.HttpClientHelper;
import com.llc.android_coolview.util.JSONUtil;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.TextView;

public class KobeNewsPicDetailActivity extends Activity implements OnClickListener{

	private ViewPager mViewPager;
	private ImageView back;
	private ImageView more;
	private TextView  replyCount;
	private TextView contentTitle;
	private TextView picCount;
	private TextView currentPicCount;
	private TextView content;
	
	private PagerAdapter mPagerAdapter;
	private McgRadyImageLoader imageLoader;
	private Intent intent;
	private int count;
	private String contentDesc;
	private Picture picture;
	private List<ImageView> mViews=new ArrayList<>();
	
	
	private Handler handler=new Handler(new Handler.Callback() {
		
		@Override
		public boolean handleMessage(Message msg) {
			if(msg.what==1){
				String result=(String) msg.obj;
				picture=JSONUtil.fromJson(result, Picture.class);
				if(picture!=null){
					count=picture.getPhotos().size();
					picCount.setText("/"+count);
					contentDesc=picture.getDesc();
					content.setText(contentDesc);
					addImageViews(picture);
					mViewPager.setAdapter(mPagerAdapter);
					mPagerAdapter.notifyDataSetChanged();
				}
			}
			return false;
		}
	});
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_kobe_fragment_news_pic_detail);
		initViews();
		initDatas();
		setOnClickListener();
	}
	
	private void initViews(){
		mViewPager=(ViewPager) this.findViewById(R.id.pic_detail_viewpager);
		back=(ImageView) this.findViewById(R.id.pic_detail_back);
		more=(ImageView) this.findViewById(R.id.pic_detail_more);
		replyCount=(TextView) this.findViewById(R.id.pic_detail_replycount);
		contentTitle=(TextView) this.findViewById(R.id.pic_detail_content_title);
		picCount=(TextView) this.findViewById(R.id.pic_detail_count);
		currentPicCount=(TextView) this.findViewById(R.id.pic_detail_current_count);
		content=(TextView) this.findViewById(R.id.content);
	}
	
	@SuppressWarnings("deprecation")
	private void setOnClickListener(){
		back.setOnClickListener(this);
		more.setOnClickListener(this);
		mPagerAdapter=new PagerAdapter() {
			
			@Override
			public boolean isViewFromObject(View arg0, Object arg1) {
				return arg0==arg1;
			}
			
			@Override
			public int getCount() {
				return mViews.size();
			}
			
			@Override
			public void destroyItem(View container, int position,Object object) {
				((ViewPager) container).removeView(mViews.get(position));
			}
			
			@Override
			public Object instantiateItem(View container, int position) {
				 View view = mViews.get(position);  
				 ((ViewPager) container).addView(view);  
	             return view;  
			}
		};
		
		mViewPager.setOnPageChangeListener(new OnPageChangeListener() {
			
			@Override
			public void onPageSelected(int position) {
				currentPicCount.setText(String.valueOf(position+1));
				content.setText(picture.getPhotos().get(position).getNote());
			}
			
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				
			}
			
			@Override
			public void onPageScrollStateChanged(int arg0) {
				
			}
		});
	}
	
	private void initDatas(){
		intent=getIntent();
		imageLoader=McgRadyImageLoader.getInstance(3, Type.LIFO);
		if(intent!=null){
			String title=intent.getStringExtra("title");
			if(title!=null){
				contentTitle.setText(getResources().getString(R.string.book)+title);
			}
			String replycount=intent.getStringExtra("replycount");
			if(replycount!=null){
				replyCount.setText(replycount);
			}
			final String skipID=intent.getStringExtra("skipID");
			if(skipID!=null){
				new Thread(){

					@Override
					public void run() {
						super.run();
						HttpClientHelper helper=new HttpClientHelper();
						helper.setRequestMethod("GET");
						String result=helper.readHtml("http://c.m.163.com/photo/api/set/0096/"+skipID+".json");
						Message msg=new Message();
						msg.what=1;
						msg.obj=result;
						handler.sendMessage(msg);
					}
					
				}.start();
			}
		}
	}

	private void addImageViews(Picture picture){
		for(int i=0;i<count;i++){
			ImageView imageView=new ImageView(this);
			LinearLayout.LayoutParams rp=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 300);
			imageView.setLayoutParams(rp);
			imageView.setScaleType(ScaleType.CENTER);
			imageLoader.loadImage(picture.getPhotos().get(i).getImgurl(), imageView, true);
			mViews.add(imageView);
		}
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.pic_detail_back:
			finish();
			break;
		case R.id.pic_detail_more:
			break;
		default:
			break;
		}
	}
	
}
