package com.llc.android_coolview.kobe.adapter;

import java.util.List;

import com.llc.android_coolview.R;
import com.llc.android_coolview.kobe.bean.tickets.History;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class KobeSearchHistoryAdapter extends BaseAdapter {

	private List<History> histories;
	private Context mContext;
	
	public KobeSearchHistoryAdapter(List<History> histories, Context mContext) {
		this.histories = histories;
		this.mContext = mContext;
	}

	@Override
	public int getCount() {
		return histories.size();
	}

	@Override
	public Object getItem(int position) {
		return histories.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if(convertView==null){
			holder=new ViewHolder();
			convertView=LayoutInflater.from(mContext).inflate(R.layout.activity_kobe_fragment_tickets_history, null);
			holder.startStation=(TextView) convertView.findViewById(R.id.history_startstation);
			holder.endStation=(TextView) convertView.findViewById(R.id.history_endstation);
			convertView.setTag(holder);
		}else{
			holder=(ViewHolder) convertView.getTag();
		}
		History history=histories.get(position);
		holder.startStation.setText(history.getStartStation());
		holder.endStation.setText(history.getEndStation());
		return convertView;
	}

	class ViewHolder{
		TextView startStation,endStation;
	}
}
