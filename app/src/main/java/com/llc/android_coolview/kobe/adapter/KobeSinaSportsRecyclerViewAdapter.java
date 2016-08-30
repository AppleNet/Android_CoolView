package com.llc.android_coolview.kobe.adapter;

import java.util.ArrayList;
import java.util.List;
import com.llc.android_coolview.R;
import com.llc.android_coolview.kobe.bean.sports.SinaSports;
import com.llc.android_coolview.kobe.view.ripplelayout.MaterialRippleLayout;
import com.llc.android_coolview.kobe.view.viewpager.MyViewPager;
import com.llc.android_coolview.mcgrady.controll.McgRadyImageLoader;
import com.llc.android_coolview.mcgrady.controll.McgRadyImageLoader.Type;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ImageView.ScaleType;

public class KobeSinaSportsRecyclerViewAdapter extends Adapter<RecyclerView.ViewHolder> {

	private SinaSports mSinaSports;
	private Context mContext;
	private McgRadyImageLoader imageLoader;
	
	private List<ImageView> mViews;
	private PagerAdapter mAdapter;
	
	private final int TYPE_0 = 0;
	private final int TYPE_1 = 1;
	
	
	public KobeSinaSportsRecyclerViewAdapter(SinaSports mSinaSports,Context mContext) {
		this.mSinaSports = mSinaSports;
		this.mContext = mContext;
		imageLoader=McgRadyImageLoader.getInstance(3, Type.LIFO);
	}

	@Override
	public int getItemCount() {
		return mSinaSports.getData().getList().size();
	}

	@SuppressWarnings("deprecation")
	@Override
	public void onBindViewHolder(ViewHolder holder, int position) {
		final SinaViewHolder1 holder1;
		SinaViewHolder2 holder2;
		int type=getItemViewType(position);
		mViews=new ArrayList<>();
		switch (type) {
		case TYPE_0:
			holder1=(SinaViewHolder1) holder;
			ImageView imageView=new ImageView(mContext);
			LinearLayout.LayoutParams lp=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
			imageView.setLayoutParams(lp);
			imageView.setScaleType(ScaleType.CENTER_CROP);
			imageLoader.loadImage(mSinaSports.getData().getList().get(position).getKpic(),imageView,true);
			mViews.add(imageView);
			mAdapter=new PagerAdapter() {
				
				@Override
				public boolean isViewFromObject(View arg0, Object arg1) {
					return arg0 == arg1;
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
			final String zero=mSinaSports.getData().getList().get(0).getTitle();
			final String one=mSinaSports.getData().getList().get(1).getTitle();
			final String two=mSinaSports.getData().getList().get(2).getTitle();
			final String three=mSinaSports.getData().getList().get(3).getTitle();
			final String four=mSinaSports.getData().getList().get(4).getTitle();
			holder1.titleContent.setText(zero);
			holder1.viewPager.setAdapter(mAdapter);
			holder1.viewPager.setOnPageChangeListener(new OnPageChangeListener() {
				
				@Override
				public void onPageSelected(int position) {
					switch (position) {
					case 0:
						holder1.titleContent.setText(zero);
						break;
					case 1:
						holder1.titleContent.setText(one);
						break;
					case 2:
						holder1.titleContent.setText(two);
						break;
					case 3:
						holder1.titleContent.setText(three);
						break;
					case 4:
						holder1.titleContent.setText(four);
						break;
					default:
						break;
					}
				}
				
				@Override
				public void onPageScrolled(int arg0, float arg1, int arg2) {
					
				}
				
				@Override
				public void onPageScrollStateChanged(int arg0) {
					
				}
			});
			break;
		case TYPE_1:
			holder2=(SinaViewHolder2) holder;
			int index=0;
			if(position>=1&&position<=4){
				imageView=new ImageView(mContext);
				lp=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
				imageView.setLayoutParams(lp);
				imageView.setScaleType(ScaleType.CENTER_CROP);
				imageLoader.loadImage(mSinaSports.getData().getList().get(position).getKpic(),imageView,true);
				mViews.add(imageView);
				mAdapter.notifyDataSetChanged();
			}
			if(position+4<mSinaSports.getData().getList().size()){
				index=position+4;
				if(mSinaSports.getData()!=null&&mSinaSports.getData().getList()!=null&&mSinaSports.getData().getList().size()>0){
					if(!mSinaSports.getData().getList().get(index).getPic().equals("")){
						imageLoader.loadImage(mSinaSports.getData().getList().get(index).getPic(), holder2.imageView, true);
					}else{
						imageLoader.loadImage(mSinaSports.getData().getList().get(index).getKpic(), holder2.imageView, true);
					}
					holder2.mTitle.setText(mSinaSports.getData().getList().get(index).getTitle());
					holder2.mIntro.setText(mSinaSports.getData().getList().get(index).getIntro());
					if(mSinaSports.getData().getList().get(index).getComment_count_info()!=null){
						holder2.replyCount.setText(mSinaSports.getData().getList().get(index).getComment_count_info().getTotal());
					}else{
						holder2.replyCount.setText(mSinaSports.getData().getList().get(index).getComment());
					}
				}
			}
			
			break;
		default:
			break;
		}
	}

	@Override
	public ViewHolder onCreateViewHolder(ViewGroup holder, int viewType) {
		switch (viewType) {
		case TYPE_0:
			View convertView0=LayoutInflater.from(mContext).inflate(R.layout.activity_kobe_fragment_sports_sina_item_header, null);
			return new SinaViewHolder1(MaterialRippleLayout.on(convertView0).rippleColor(Color.parseColor("#C2C2C2")).rippleAlpha(0.5f).rippleHover(true).create());
		case TYPE_1:
			View convertView1=LayoutInflater.from(mContext).inflate(R.layout.activity_kobe_fragment_sports_sina_item_content, null);
			return new SinaViewHolder2(MaterialRippleLayout.on(convertView1).rippleColor(Color.parseColor("#C2C2C2")).rippleAlpha(0.5f).rippleHover(true).create());
		default:
			break;
		}
		return null;
	}

	
	@Override
	public int getItemViewType(int position) {
		if(position==0){
			return TYPE_0;
		}else{
			return TYPE_1;
		}
	}
	
	public interface MyItemClickListener{
		void onItemClickListener(View view,int position);
	}
	
	private MyItemClickListener myItemClickListener;
	
	public void setOnItemClickListener(MyItemClickListener myItemClickListener){
		this.myItemClickListener=myItemClickListener;
	}
	
	class SinaViewHolder1 extends RecyclerView.ViewHolder{
		
		MyViewPager viewPager;
		TextView titleContent;
		
		public SinaViewHolder1(View itemView) {
			super(itemView);
			viewPager=(MyViewPager) itemView.findViewById(R.id.sports_title_viewpager);
			titleContent=(TextView) itemView.findViewById(R.id.sports_pic_text_title);
		}
	}
	
	class SinaViewHolder2 extends RecyclerView.ViewHolder{
		ImageView imageView;
		TextView mTitle;
		TextView mIntro;
		TextView replyCount;
		public SinaViewHolder2(View convertView) {
			super(convertView);
			imageView=(ImageView) convertView.findViewById(R.id.sports_content_pic);
			mTitle=(TextView) convertView.findViewById(R.id.sports_content_title);
			mIntro=(TextView) convertView.findViewById(R.id.sports_content);
			replyCount=(TextView) convertView.findViewById(R.id.sports_content_reploycount);
			convertView.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					myItemClickListener.onItemClickListener(v, getPosition());
				}
			});
		}
		
	}
}
