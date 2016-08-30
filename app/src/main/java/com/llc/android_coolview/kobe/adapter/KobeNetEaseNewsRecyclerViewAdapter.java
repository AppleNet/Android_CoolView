package com.llc.android_coolview.kobe.adapter;

import java.util.ArrayList;
import java.util.List;

import com.llc.android_coolview.R;
import com.llc.android_coolview.kobe.bean.news.FirstNew;
import com.llc.android_coolview.kobe.bean.news.News;
import com.llc.android_coolview.kobe.bean.news.ThirdNew;
import com.llc.android_coolview.kobe.view.ripplelayout.MaterialRippleLayout;
import com.llc.android_coolview.kobe.view.viewpager.MyViewPager;
import com.llc.android_coolview.kobe.view.viewpager.MyViewPager.OnSingleTouchListener;
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
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ImageView.ScaleType;

public class KobeNetEaseNewsRecyclerViewAdapter extends Adapter<RecyclerView.ViewHolder> {

	private List<News> news;
	private Context mContext;
	private PagerAdapter mAdapter;
	private List<ImageView> mViews;
	private McgRadyImageLoader imageLoader;
	
	private final int TYPE_0 = 0;
	private final int TYPE_1 = 1;
	private final int TYPE_2 = 2;
	private final int TYPE_3 = 3;
	
	
	public KobeNetEaseNewsRecyclerViewAdapter(List<News> news,Context mContext) {
		this.news = news;
		this.mContext = mContext;
		imageLoader=McgRadyImageLoader.getInstance(3, Type.LIFO);
	}

	@Override
	public int getItemCount() {
		return news.size();
	}

	@SuppressWarnings("deprecation")
	@Override
	public void onBindViewHolder(ViewHolder holder, int position) {
		
		final ViewHolder0 holder0;
		ViewHolder1 holder1 = null;
		ViewHolder2 holder2 = null;
		ViewHolder3 holder3 = null; 
		int type=getItemViewType(position);
		switch (type) {
		case TYPE_0:
			holder0=(ViewHolder0) holder;
			mViews=new ArrayList<>();
			for(int i=0;i<((FirstNew)news.get(position)).getAds().size();i++){
				ImageView imageView=new ImageView(mContext);
				LinearLayout.LayoutParams lp=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
				imageView.setLayoutParams(lp);
				imageView.setScaleType(ScaleType.CENTER_CROP);
				imageLoader.loadImage(((FirstNew)news.get(position)).getAds().get(i).getImgsrc(),imageView,true);
				mViews.add(imageView);
			}
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
			final String zero=((FirstNew) news.get(position)).getAds().get(0).getTitle();
			final String one=((FirstNew) news.get(position)).getAds().get(1).getTitle();
			final String two=((FirstNew) news.get(position)).getAds().get(2).getTitle();
			final String three=((FirstNew) news.get(position)).getAds().get(3).getTitle();
			final String four=((FirstNew) news.get(position)).getAds().get(4).getTitle();
			holder0.titleContent.setText(zero);
			holder0.viewPager.setAdapter(mAdapter);
			holder0.viewPager.setOnPageChangeListener(new OnPageChangeListener() {
				@Override
				public void onPageSelected(int position) {
					switch (position) {
					case 0:
						holder0.titleContent.setText(zero);
						break;
					case 1:
						holder0.titleContent.setText(one);
						break;
					case 2:
						holder0.titleContent.setText(two);
						break;
					case 3:
						holder0.titleContent.setText(three);
						break;
					case 4:
						holder0.titleContent.setText(four);
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
			holder0.viewPager.setOnSingleTouchListener(new OnSingleTouchListener() {
				
				@Override
				public void onSingleTouch() {
					
				}
			});
			break;
		case TYPE_1:
			holder1=(ViewHolder1) holder;
			if(news.get(position).getImgsrc()!=null&&holder1.imageView!=null){
				imageLoader.loadImage(news.get(position).getImgsrc(),holder1.imageView,true);
				holder1.title.setText(news.get(position).getTitle());
				holder1.content.setText(news.get(position).getDigest());
				holder1.replyCount.setText(news.get(position).getReplyCount()+mContext.getResources().getString(R.string.gentie));
			}
			break;
		case TYPE_2:
			holder2=(ViewHolder2) holder;
			String title=((ThirdNew)news.get(position)).getTitle();
			if(title!=null){
				holder2.centerContent.setText(title);
			}
			String replyCount=((ThirdNew)news.get(position)).getReplyCount();
			
			if(replyCount!=null){
				holder2.centerReplyCount.setText(replyCount+mContext.getResources().getString(R.string.gentie));
			}
			String path=((ThirdNew)news.get(position)).getImgsrc();
			if(path!=null){
				imageLoader.loadImage(path,holder2.left,true);
			}
			if(((ThirdNew) news.get(position)).getImgextra()!=null&&((ThirdNew) news.get(position)).getImgextra().size()>0){
				imageLoader.loadImage(((ThirdNew) news.get(position)).getImgextra().get(0).getImgsrc(),holder2.center,true);
				imageLoader.loadImage(((ThirdNew) news.get(position)).getImgextra().get(1).getImgsrc(),holder2.right,true);
			}
			break;
		case TYPE_3:
			holder3=(ViewHolder3) holder;
			if(news.get(position).getImgsrc()!=null&&holder3.imageView!=null){
				imageLoader.loadImage(news.get(position).getImgsrc(),holder3.imageView,true);
				holder3.title.setText(news.get(position).getTitle());
				holder3.content.setText(news.get(position).getDigest());
				holder3.replyCount.setText(news.get(position).getReplyCount()+mContext.getResources().getString(R.string.gentie));
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
			View convertView0=LayoutInflater.from(holder.getContext()).inflate(R.layout.activity_kobe_fragment_news_netease_item_header, null);
			return new ViewHolder0(MaterialRippleLayout.on(convertView0).rippleColor(Color.parseColor("#C2C2C2")).rippleAlpha(0.5f).rippleHover(true).create());
		case TYPE_1:
			View convertView1=LayoutInflater.from(holder.getContext()).inflate(R.layout.activity_kobe_fragment_news_netease_item_content, null);
			return new ViewHolder1(MaterialRippleLayout.on(convertView1).rippleColor(Color.parseColor("#C2C2C2")).rippleAlpha(0.5f).rippleHover(true).create());
		case TYPE_2:
			View convertView2=LayoutInflater.from(holder.getContext()).inflate(R.layout.activity_kobe_fragment_news_netease_item_center, null);
			return new ViewHolder2(MaterialRippleLayout.on(convertView2).rippleColor(Color.parseColor("#C2C2C2")).rippleAlpha(0.5f).rippleHover(true).create());
		case TYPE_3:
			View convertView3=LayoutInflater.from(holder.getContext()).inflate(R.layout.activity_kobe_fragment_news_netease_item_content, null);
			return new ViewHolder3(MaterialRippleLayout.on(convertView3).rippleColor(Color.parseColor("#C2C2C2")).rippleAlpha(0.5f).rippleHover(true).create());
		default:
			return null;
		}	
	}

	@Override
	public int getItemViewType(int position) {
		if(position==0){
			return TYPE_0;
		}else{
			if(news.get(position).getFlag().equals("second")){
				return TYPE_1;
			}else if(news.get(position).getFlag().equals("third")){
				return TYPE_2;
			}else if(news.get(position).getFlag().equals("fourth")){
				return TYPE_3;
			}
		}
		return TYPE_1;
	}
	
	class ViewHolder0 extends RecyclerView.ViewHolder{
		
		MyViewPager viewPager;
		TextView titleContent;
		
		public ViewHolder0(View itemView) {
			super(itemView);
			viewPager=(MyViewPager) itemView.findViewById(R.id.title_viewpager);
			titleContent=(TextView) itemView.findViewById(R.id.news_pic_text_title);
		}
		
	}
	
	public interface MyItemClickListener{
		void onItemClickListener(View view,int position);
	}
	
	private MyItemClickListener myItemClickListener;
	
	public void setOnItemClickListener(MyItemClickListener myItemClickListener){
		this.myItemClickListener=myItemClickListener;
	}
	
	public interface MyItemLongClickListener{
		void onItemLongClickListener(View view,int position);
	}
	
	private MyItemLongClickListener myItemLongClickListener;
	public void setOnItenLongClickListener(MyItemLongClickListener myItemLongClickListener){
		this.myItemLongClickListener=myItemLongClickListener;
	}
	
	class ViewHolder1 extends RecyclerView.ViewHolder implements OnClickListener{
		
		ImageView imageView;
		TextView title;
		TextView content;
		TextView replyCount;
		public ViewHolder1(View itemView) {
			super(itemView);
			imageView=(ImageView) itemView.findViewById(R.id.news_content_pic);
			title=(TextView) itemView.findViewById(R.id.news_content_title);
			content=(TextView) itemView.findViewById(R.id.news_content);
			replyCount=(TextView) itemView.findViewById(R.id.news_content_reploycount);
			itemView.setOnClickListener(this);
		}

		@Override
		public void onClick(View v) {
			myItemClickListener.onItemClickListener(v, getPosition());
		}
		
	}
	class ViewHolder2 extends RecyclerView.ViewHolder implements OnClickListener{
		ImageView left,center,right;
		TextView centerContent,centerReplyCount;
		public ViewHolder2(View convertView) {
			super(convertView);
			centerContent=(TextView) convertView.findViewById(R.id.center_contents);
			left=(ImageView) convertView.findViewById(R.id.left);
			center=(ImageView) convertView.findViewById(R.id.center);
			right=(ImageView) convertView.findViewById(R.id.right);
			centerReplyCount=(TextView) convertView.findViewById(R.id.center_replucount);
			convertView.setOnClickListener(this);
		}
		@Override
		public void onClick(View v) {
			myItemClickListener.onItemClickListener(v, getPosition());
		}
		
	}
	class ViewHolder3 extends RecyclerView.ViewHolder implements OnClickListener, OnLongClickListener{
		ImageView imageView;
		TextView title;
		TextView content;
		TextView replyCount;
		
		public ViewHolder3(View convertView) {
			super(convertView);
			imageView=(ImageView) convertView.findViewById(R.id.news_content_pic);
			title=(TextView) convertView.findViewById(R.id.news_content_title);
			content=(TextView) convertView.findViewById(R.id.news_content);
			replyCount=(TextView) convertView.findViewById(R.id.news_content_reploycount);
			convertView.setOnClickListener(this);
			convertView.setOnLongClickListener(this);
		}

		@Override
		public void onClick(View v) {
			myItemClickListener.onItemClickListener(v, getPosition());
		}

		@Override
		public boolean onLongClick(View v) {
			myItemLongClickListener.onItemLongClickListener(v, getPosition());
			return false;
		}
		
	}
}
