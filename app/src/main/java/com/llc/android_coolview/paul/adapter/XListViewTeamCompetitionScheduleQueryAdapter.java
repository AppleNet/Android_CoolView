package com.llc.android_coolview.paul.adapter;

import java.util.List;

import com.llc.android_coolview.R;
import com.llc.android_coolview.paul.bean.team_competition_schedule_query.ResultList;
import com.llc.android_coolview.paul.bean.team_competition_schedule_query.TeamCompetitionScheduleQuery;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class XListViewTeamCompetitionScheduleQueryAdapter extends BaseAdapter {

	private Context mContext;
	private TeamCompetitionScheduleQuery tcsq;

	public XListViewTeamCompetitionScheduleQueryAdapter(Context context,TeamCompetitionScheduleQuery tcsq) {
		this.mContext=context;
		this.tcsq = tcsq;
	}

	@Override
	public int getCount() {
		return tcsq.getResult().getList().size();
	}

	@Override
	public Object getItem(int position) {
		return tcsq.getResult().getList().get(position);
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
			if(position==0){
				//第一条数据
				convertView=LayoutInflater.from(mContext).inflate(R.layout.activity_paul_title, null);
				holder.title=(TextView) convertView.findViewById(R.id.paul_item_title);
			}else{
				convertView=LayoutInflater.from(mContext).inflate(R.layout.activity_paul_xlist_item, null);
				holder.visitName=(TextView) convertView.findViewById(R.id.nba_visiting_team);
				holder.homeName=(TextView) convertView.findViewById(R.id.nba_home_team);
				holder.resultOfGame=(TextView) convertView.findViewById(R.id.nba_result);
				convertView.setTag(holder);
			}
		}else{
			holder=(ViewHolder) convertView.getTag();
		}
		if(position==0){
			holder.title.setText(tcsq.getResult().getTitle());
		}else{
			List<ResultList> lis=tcsq.getResult().getList();
			if(lis.size()>0){
				ResultList game=lis.get(position);
				if(game!=null){
					holder.visitName.setText(tcsq.getResult().getList().get(position).getC4T1());
					holder.homeName.setText(tcsq.getResult().getList().get(position).getC4T2());
					holder.resultOfGame.setText(tcsq.getResult().getList().get(position).getC4R());
				}
			}
		}
		return convertView;
	}

	class ViewHolder{
		TextView visitName;
		TextView homeName;
		TextView resultOfGame;
		TextView title;
	}
}
