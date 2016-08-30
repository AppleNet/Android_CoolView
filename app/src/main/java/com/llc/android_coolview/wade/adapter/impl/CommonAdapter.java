package com.llc.android_coolview.wade.adapter.impl;

import java.util.List;

import com.llc.android_coolview.wade.adapter.holder.ViewHolder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public abstract class CommonAdapter<T> extends BaseAdapter {

	protected LayoutInflater inflater;
	protected Context context;
	protected List<T> mList;
	protected int mItemLayoutId;
	
	
	
	public CommonAdapter(Context context, List<T> mList, int itemLayoutId){
		this.context = context;
		this.inflater = LayoutInflater.from(context);
		this.mList = mList;
		this.mItemLayoutId = itemLayoutId;
	}

	@Override
	public int getCount() {
		return mList.size();
	}

	@Override
	public Object getItem(int position) {
		return mList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@SuppressWarnings("unchecked")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder=getViewHolder(position, convertView, parent);
		convert(holder, (T) getItem(position));
		return holder.getConvertView();
	}

	public abstract void convert(ViewHolder holder,T item);
	
	private ViewHolder getViewHolder(int position,View convertView,ViewGroup paremt){
		return ViewHolder.get(context, convertView, paremt, mItemLayoutId, position);
	}

}
