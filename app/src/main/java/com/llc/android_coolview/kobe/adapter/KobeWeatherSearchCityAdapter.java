package com.llc.android_coolview.kobe.adapter;

import com.llc.android_coolview.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class KobeWeatherSearchCityAdapter extends BaseAdapter {

	private String[] list;
	private Context mContext;
	
	public KobeWeatherSearchCityAdapter(String[] list, Context mContext) {
		this.list = list;
		this.mContext = mContext;
	}

	@Override
	public int getCount() {
		return list.length;
	}

	@Override
	public Object getItem(int position) {
		return list[position];
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if(convertView==null){
			convertView=LayoutInflater.from(mContext).inflate(R.layout.activity_kobe_fragment_weather_grid_item, null);
		}
		TextView textView=(TextView) convertView.findViewById(R.id.grid_item_text);
		textView.setText(list[position]);
		
		return convertView;
	}

}
