package com.llc.android_coolview.mcgrady.adapter;

import com.llc.android_coolview.R;
import com.llc.android_coolview.mcgrady.controll.McgRadyImageLoader;
import com.llc.android_coolview.mcgrady.controll.McgRadyImageLoader.Type;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

public class GalleryAdapter extends ArrayAdapter<String> {

	private McgRadyImageLoader imageLoader;
	private LayoutInflater view;
	public GalleryAdapter(Context context, int resource, String[] objects) {
		super(context, resource,objects);
		imageLoader=McgRadyImageLoader.getInstance(3, Type.LIFO);
		view=LayoutInflater.from(context);
	}

	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if(convertView==null){
			convertView=view.inflate(R.layout.activity_mcgrady_gallery_item_image, null);
		}
		ImageView imageView=(ImageView) convertView.findViewById(R.id.mcgrady_gallery_item_single_img);
		imageLoader.loadImage(getItem(position), imageView, true);
		return convertView;
	}
	
	
	
}
