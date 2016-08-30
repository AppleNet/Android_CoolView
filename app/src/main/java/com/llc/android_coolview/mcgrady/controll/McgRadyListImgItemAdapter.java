package com.llc.android_coolview.mcgrady.controll;

import com.llc.android_coolview.R;
import com.llc.android_coolview.mcgrady.controll.McgRadyImageLoader.Type;
import com.llc.android_coolview.mcgrady.view.activity.McGradySingleImageActivity;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

public class McgRadyListImgItemAdapter extends ArrayAdapter<String> {

	private McgRadyImageLoader imageLoader;
	private LayoutInflater view;
	public McgRadyListImgItemAdapter(Context context, int resource, String[] objects) {
		super(context, resource, objects);
		imageLoader=McgRadyImageLoader.getInstance(3, Type.LIFO);
		view=LayoutInflater.from(context);
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHolder holder=null;
		if (convertView == null) {
			holder=new ViewHolder();
			convertView =view.inflate(R.layout.activity_mcgrady_item_fragment_list_imgs, parent, false);
			holder.imageView = (ImageView) convertView.findViewById(R.id.id_img);
			convertView.setTag(holder);
		}else{
			holder=(ViewHolder) convertView.getTag();
		}
		holder.imageView.setImageResource(R.drawable.pictures_no);
		imageLoader.loadImage(getItem(position), holder.imageView, true);
		convertView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent=new Intent();
				intent.putExtra("url", position);
				intent.setClass(getContext(),McGradySingleImageActivity.class);
				getContext().startActivity(intent);
			}
		});
		return convertView;
	}
	
	class ViewHolder{
		ImageView imageView;
	}
}
