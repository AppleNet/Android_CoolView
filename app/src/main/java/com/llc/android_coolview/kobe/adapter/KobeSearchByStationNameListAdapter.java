package com.llc.android_coolview.kobe.adapter;

import java.util.List;

import com.llc.android_coolview.R;
import com.llc.android_coolview.kobe.bean.tickets.DoubleStation;
import com.llc.android_coolview.kobe.bean.tickets.ValidVotes;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class KobeSearchByStationNameListAdapter extends BaseAdapter {

	private Context mContext;
	private DoubleStation mDoubleStation;
	private ValidVotes mValidVotes;
	private List<String> types;
	
	public void setmDoubleStation(DoubleStation mDoubleStation) {
		this.mDoubleStation = mDoubleStation;
	}

	public void setmValidVotes(ValidVotes mValidVotes) {
		this.mValidVotes = mValidVotes;
	}

	public void setTypes(List<String> types) {
		this.types = types;
	}

	public KobeSearchByStationNameListAdapter(Context mContext) {
		this.mContext = mContext;
	}

	@Override
	public int getCount() {
		return mDoubleStation.getResult().getData().size();
	}

	@Override
	public Object getItem(int position) {
		return mDoubleStation.getResult().getData().get(position);
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
			holder.trainOpp=(TextView) convertView.findViewById(R.id.trainOpp);
			holder.leave_time=(TextView) convertView.findViewById(R.id.leave_time);
			holder.arrived_time=(TextView) convertView.findViewById(R.id.arrived_time);
			holder.startStation=(TextView) convertView.findViewById(R.id.start_staion);
			holder.arriveStation=(TextView) convertView.findViewById(R.id.end_station);
			holder.time_difference=(TextView) convertView.findViewById(R.id.time_difference);
			holder.ticketsPrice=(TextView) convertView.findViewById(R.id.tickets_price);
			holder.seatType1=(TextView) convertView.findViewById(R.id.seat_type1);
			holder.seatType2=(TextView) convertView.findViewById(R.id.seat_type2);
			holder.seatType3=(TextView) convertView.findViewById(R.id.seat_type3);
			holder.seatType4=(TextView) convertView.findViewById(R.id.seat_type4);
			convertView.setTag(holder);
		}else{
			holder=(ViewHolder) convertView.getTag();
		}
		String trainNo=mDoubleStation.getResult().getData().get(position).getTrain_no();
		String trainNov=null;
		if(mDoubleStation.getResult().getData().size()==mValidVotes.getResult().size()){
			trainNov=mValidVotes.getResult().get(position).getTrain_no();
			if(trainNo.equals(trainNov)){
				for(String s:types){
					String temp=trainNo.substring(0, 1);
					holder.trainOpp.setText(trainNo);
					if(s.equals(temp)){
						if(temp.equals("G")||temp.equals("D")){
							holder.seatType1.setText(mContext.getResources().getString(R.string.business)+mValidVotes.getResult().get(position).getSwz_num());
							holder.seatType2.setText(mContext.getResources().getString(R.string.special)+mValidVotes.getResult().get(position).getTz_num());
							holder.seatType3.setText(mContext.getResources().getString(R.string.first)+mValidVotes.getResult().get(position).getZy_num());
							holder.seatType4.setText(mContext.getResources().getString(R.string.second)+mValidVotes.getResult().get(position).getZe_num());
						}else{
							holder.seatType1.setText(mContext.getResources().getString(R.string.rw)+mValidVotes.getResult().get(position).getRw_num());
							holder.seatType2.setText(mContext.getResources().getString(R.string.yw)+mValidVotes.getResult().get(position).getYw_num());
							holder.seatType3.setText(mContext.getResources().getString(R.string.yz)+mValidVotes.getResult().get(position).getYz_num());
							holder.seatType4.setText(mContext.getResources().getString(R.string.wz)+mValidVotes.getResult().get(position).getWz_num());
						}
					}
				}
			}
		}else{
			for(int i=0;i<mValidVotes.getResult().size();i++){
				trainNov=mValidVotes.getResult().get(i).getTrain_no();
				if(trainNo.equals(trainNov)){
					for(String s:types){
						String temp=trainNo.substring(0, 1);
						holder.trainOpp.setText(trainNo);
						if(s.equals(temp)){
							if(temp.equals("G")||temp.equals("D")){
								holder.seatType1.setText(mContext.getResources().getString(R.string.business)+mValidVotes.getResult().get(i).getSwz_num());
								holder.seatType2.setText(mContext.getResources().getString(R.string.special)+mValidVotes.getResult().get(i).getTz_num());
								holder.seatType3.setText(mContext.getResources().getString(R.string.first)+mValidVotes.getResult().get(i).getZy_num());
								holder.seatType4.setText(mContext.getResources().getString(R.string.second)+mValidVotes.getResult().get(i).getZe_num());
							}else{
								holder.seatType1.setText(mContext.getResources().getString(R.string.rw)+mValidVotes.getResult().get(i).getRw_num());
								holder.seatType2.setText(mContext.getResources().getString(R.string.yw)+mValidVotes.getResult().get(i).getYw_num());
								holder.seatType3.setText(mContext.getResources().getString(R.string.yz)+mValidVotes.getResult().get(i).getYz_num());
								holder.seatType4.setText(mContext.getResources().getString(R.string.wz)+mValidVotes.getResult().get(i).getWz_num());
							}
						}
					}
				}else{
					for(String s:types){
						String temp=trainNo.substring(0, 1);
						holder.trainOpp.setText(trainNo);
						if(s.equals(temp)){
							if(temp.equals("G")||temp.equals("D")){
								holder.seatType1.setText(mContext.getResources().getString(R.string.business_seat));
								holder.seatType2.setText(mContext.getResources().getString(R.string.special_seat));
								holder.seatType3.setText(mContext.getResources().getString(R.string.first_seat));
								holder.seatType4.setText(mContext.getResources().getString(R.string.second_seat));
							}else{
								holder.seatType1.setText(mContext.getResources().getString(R.string.rw_seat));
								holder.seatType2.setText(mContext.getResources().getString(R.string.yw_seat));
								holder.seatType3.setText(mContext.getResources().getString(R.string.yz_seat));
								holder.seatType4.setText(mContext.getResources().getString(R.string.wz_seat));
							}
						}
					}
				}
			}
		}
		holder.leave_time.setText(mDoubleStation.getResult().getData().get(position).getStart_time());
		holder.arrived_time.setText(mDoubleStation.getResult().getData().get(position).getEnd_time());
		holder.startStation.setText(mDoubleStation.getResult().getData().get(position).getStart_staion());
		holder.arriveStation.setText(mDoubleStation.getResult().getData().get(position).getEnd_station());
		holder.time_difference.setText(mDoubleStation.getResult().getData().get(position).getRun_time());
		holder.ticketsPrice.setText(mDoubleStation.getResult().getData().get(position).getPrice_list().get(0).getPrice());
		return convertView;
	}

	
	class ViewHolder{
		public TextView trainOpp,time_difference,leave_time,arrived_time,startStation,arriveStation,ticketsPrice,
		seatType1,seatType2,seatType3,seatType4;
	}
}
