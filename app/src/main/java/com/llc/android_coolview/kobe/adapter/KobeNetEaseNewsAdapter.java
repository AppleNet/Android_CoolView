package com.llc.android_coolview.kobe.adapter;

import java.util.ArrayList;
import java.util.List;
import com.llc.android_coolview.R;
import com.llc.android_coolview.kobe.bean.news.FirstNew;
import com.llc.android_coolview.kobe.bean.news.News;
import com.llc.android_coolview.kobe.bean.news.ThirdNew;
import com.llc.android_coolview.kobe.view.viewpager.MyViewPager;
import com.llc.android_coolview.mcgrady.controll.McgRadyImageLoader;
import com.llc.android_coolview.mcgrady.controll.McgRadyImageLoader.Type;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;

public class KobeNetEaseNewsAdapter extends BaseAdapter {

	private List<News> news;
	private Context mContext;
	private PagerAdapter mAdapter;
	private List<ImageView> mViews;
	private McgRadyImageLoader imageLoader;
	
	private final int TYPE_0 = 0;
	private final int TYPE_1 = 1;
	private final int TYPE_2 = 2;
	private final int TYPE_3 = 3;
	
	public KobeNetEaseNewsAdapter(Context mContext,List<News> news) {
		this.news = news;
		this.mContext = mContext;
		imageLoader=McgRadyImageLoader.getInstance(3, Type.LIFO);
	}

	@Override
	public int getCount() {
		return news.size();
	}

	@Override
	public Object getItem(int position) {
		return news.get(position);

	}

	@Override
	public long getItemId(int position) {
		return position;
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
	
	@Override
	public int getViewTypeCount() {
		return 4;
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		ViewHolder0 holder0 = null;
		ViewHolder1 holder1 = null;
		ViewHolder2 holder2 = null;
		ViewHolder3 holder3 = null; 
		int type=getItemViewType(position);
		if(convertView==null){
			switch (type) {
			case TYPE_0:
				holder0=new ViewHolder0();
				convertView=LayoutInflater.from(mContext).inflate(R.layout.activity_kobe_fragment_news_netease_item_header, null);
				holder0.viewPager=(MyViewPager) convertView.findViewById(R.id.title_viewpager);
				holder0.titleContent=(TextView) convertView.findViewById(R.id.news_pic_text_title);
				convertView.setTag(holder0);
				break;
			case TYPE_1:
				holder1=new ViewHolder1();
				convertView=LayoutInflater.from(mContext).inflate(R.layout.activity_kobe_fragment_news_netease_item_content, null);
				holder1.imageView=(ImageView) convertView.findViewById(R.id.news_content_pic);
				holder1.title=(TextView) convertView.findViewById(R.id.news_content_title);
				holder1.content=(TextView) convertView.findViewById(R.id.news_content);
				holder1.replyCount=(TextView) convertView.findViewById(R.id.news_content_reploycount);
				convertView.setTag(holder1);
				break;
			case TYPE_2:
				holder2=new ViewHolder2();
				convertView=LayoutInflater.from(mContext).inflate(R.layout.activity_kobe_fragment_news_netease_item_center, null);
				holder2.centerContent=(TextView) convertView.findViewById(R.id.center_contents);
				holder2.left=(ImageView) convertView.findViewById(R.id.left);
				holder2.center=(ImageView) convertView.findViewById(R.id.center);
				holder2.right=(ImageView) convertView.findViewById(R.id.right);
				holder2.centerReplyCount=(TextView) convertView.findViewById(R.id.center_replucount);
				convertView.setTag(holder2);
				break;
			case TYPE_3:
				holder3=new ViewHolder3();
				convertView=LayoutInflater.from(mContext).inflate(R.layout.activity_kobe_fragment_news_netease_item_content, null);
				holder3.imageView=(ImageView) convertView.findViewById(R.id.news_content_pic);
				holder3.title=(TextView) convertView.findViewById(R.id.news_content_title);
				holder3.content=(TextView) convertView.findViewById(R.id.news_content);
				holder3.replyCount=(TextView) convertView.findViewById(R.id.news_content_reploycount);
				convertView.setTag(holder3);
				break;
			default:
				break;
			}
		}else{
			switch (type) {
			case TYPE_0:
				holder0=(ViewHolder0) convertView.getTag();
				break;
			case TYPE_1:
				holder1=(ViewHolder1) convertView.getTag();
				break;
			case TYPE_2:
				holder2=(ViewHolder2) convertView.getTag();
				break;
			case TYPE_3:
				holder3=(ViewHolder3) convertView.getTag();
				break;
			default:
				break;
			}
		}
		switch (type) {
		case TYPE_0:
			final ViewHolder0 holder=holder0;
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
			holder.titleContent.setText(zero);
			holder.viewPager.setAdapter(mAdapter);
			holder.viewPager.setOnPageChangeListener(new OnPageChangeListener() {
				@Override
				public void onPageSelected(int position) {
					switch (position) {
					case 0:
						holder.titleContent.setText(zero);
						break;
					case 1:
						holder.titleContent.setText(one);
						break;
					case 2:
						holder.titleContent.setText(two);
						break;
					case 3:
						holder.titleContent.setText(three);
						break;
					case 4:
						holder.titleContent.setText(four);
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
			return convertView;
		case TYPE_1:
			if(news.get(position).getImgsrc()!=null&&holder1.imageView!=null){
				imageLoader.loadImage(news.get(position).getImgsrc(),holder1.imageView,true);
				holder1.title.setText(news.get(position).getTitle());
				holder1.content.setText(news.get(position).getDigest());
				holder1.replyCount.setText(news.get(position).getReplyCount()+mContext.getResources().getString(R.string.gentie));
			}
			return convertView;
		case TYPE_2:
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
			return convertView;
		case TYPE_3:
			if(news.get(position).getImgsrc()!=null&&holder3.imageView!=null){
				imageLoader.loadImage(news.get(position).getImgsrc(),holder3.imageView,true);
				holder3.title.setText(news.get(position).getTitle());
				holder3.content.setText(news.get(position).getDigest());
				holder3.replyCount.setText(news.get(position).getReplyCount()+mContext.getResources().getString(R.string.gentie));
			}
			return convertView;
		default:
			return convertView;
		}
	}

	class ViewHolder0{
		MyViewPager viewPager;
		TextView titleContent;
	}
	class ViewHolder1{
		ImageView imageView;
		TextView title;
		TextView content;
		TextView replyCount;
	}
	class ViewHolder2{
		ImageView left,center,right;
		TextView centerContent,centerReplyCount;
	}
	class ViewHolder3{
		ImageView imageView;
		TextView title;
		TextView content;
		TextView replyCount;
	}
}
