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

public class KobeSearchByTrainCodeListAdapter extends BaseAdapter {

	private List<Map<Object,Object>> result;
	private Context mContext;
	
	public KobeSearchByTrainCodeListAdapter(Context context,List<Map<Object, Object>> result) {
		this.result = result;
		this.mContext=context;
	}

	@Override
	public int getCount() {
		return result.size();
	}

	@Override
	public Object getItem(int position) {
		return result.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHodler holder;
		if(convertView==null){
			holder=new ViewHodler();
			convertView=LayoutInflater.from(mContext).inflate(R.layout.activity_kobe_fragment_list_item, null);
			holder.trainCode=(TextView) convertView.findViewById(R.id.traincode);
			holder.firstStation=(TextView) convertView.findViewById(R.id.firststation);
			holder.lastStation=(TextView) convertView.findViewById(R.id.laststation);
			holder.startTime=(TextView) convertView.findViewById(R.id.starttime);
			holder.arriveTime=(TextView) convertView.findViewById(R.id.arrivetime);
			convertView.setTag(holder);
		}else{
			holder=(ViewHodler) convertView.getTag();
		}
		holder.trainCode.setText((CharSequence) result.get(position).get("TrainCode"));
		holder.firstStation.setText(mContext.getResources().getString(R.string.sf)+(CharSequence) result.get(position).get("FirstStation"));
		holder.lastStation.setText(mContext.getResources().getString(R.string.zdz)+(CharSequence) result.get(position).get("LastStation"));
		holder.startTime.setText(mContext.getResources().getString(R.string.sfsj)+(CharSequence) result.get(position).get("StartTime"));
		holder.arriveTime.setText(mContext.getResources().getString(R.string.zdsj)+(CharSequence) result.get(position).get("ArriveTime"));
		return convertView;
	}

	class ViewHodler{
		public TextView trainCode,firstStation,lastStation,startTime,arriveTime;
	}
}
