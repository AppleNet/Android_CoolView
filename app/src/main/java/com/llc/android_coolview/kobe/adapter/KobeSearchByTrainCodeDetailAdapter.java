package com.llc.android_coolview.kobe.adapter;

import java.util.List;
import java.util.Map;

import com.llc.android_coolview.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class KobeSearchByTrainCodeDetailAdapter extends BaseAdapter {

	private Context mContext;
	private List<Map<Object, Object>> list;
	
	
	public KobeSearchByTrainCodeDetailAdapter(Context mContext,
			List<Map<Object, Object>> list) {
		this.mContext = mContext;
		this.list = list;
	}

	
	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder=null;
		if(convertView==null){
			holder=new ViewHolder();
			convertView=LayoutInflater.from(mContext).inflate(R.layout.activity_kobe_fragment_list_item_station_name, null);
			holder.trainCode=(TextView) convertView.findViewById(R.id.traincode);
			holder.firstStation=(TextView) convertView.findViewById(R.id.firststation);
			holder.lastStation=(TextView) convertView.findViewById(R.id.laststation);
			holder.startTime=(TextView) convertView.findViewById(R.id.starttime);
			holder.arriveTime=(TextView) convertView.findViewById(R.id.arrivetime);
			holder.startStation=(TextView) convertView.findViewById(R.id.start_staion);
			holder.arriveStation=(TextView) convertView.findViewById(R.id.end_station);
			holder.km=(TextView) convertView.findViewById(R.id.tickets_price);
			holder.useDate=(TextView) convertView.findViewById(R.id.seat_type1);
			convertView.setTag(holder);
		}else{
			holder=(ViewHolder) convertView.getTag();
		}
		holder.trainCode.setText((CharSequence) list.get(position).get("TrainCode"));
		holder.firstStation.setText(mContext.getResources().getString(R.string.sf)+(CharSequence) list.get(position).get("FirstStation"));
		holder.lastStation.setText(mContext.getResources().getString(R.string.zdz)+(CharSequence) list.get(position).get("LastStation"));
		holder.startTime.setText(mContext.getResources().getString(R.string.sfsj)+(CharSequence) list.get(position).get("StartTime"));
		holder.arriveTime.setText(mContext.getResources().getString(R.string.zdsj)+(CharSequence) list.get(position).get("ArriveTime"));
		holder.startStation.setText(mContext.getResources().getString(R.string.ksz)+(CharSequence) list.get(position).get("StartStation"));
		holder.arriveStation.setText(mContext.getResources().getString(R.string.ddz)+(CharSequence)list.get(position).get("ArriveStation"));
		holder.km.setText(mContext.getResources().getString(R.string.gl)+(CharSequence) list.get(position).get("KM"));
		holder.useDate.setText(mContext.getResources().getString(R.string.sc)+(CharSequence) list.get(position).get("UseDate"));
		return convertView;
	}

	class ViewHolder{
		public TextView trainCode,firstStation,lastStation,startTime,arriveTime,startStation,arriveStation,km,useDate;
	}
}
