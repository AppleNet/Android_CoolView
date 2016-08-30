package com.llc.android_coolview.kobe.activity;


import java.util.ArrayList;
import java.util.List;

import com.llc.android_coolview.R;
import com.llc.android_coolview.kobe.adapter.KobeSearchByStationNameListAdapter;
import com.llc.android_coolview.kobe.bean.tickets.DoubleStation;
import com.llc.android_coolview.kobe.bean.tickets.ValidVotes;
import com.llc.android_coolview.kobe.control.TicketsWebServiceUtils;
import com.llc.android_coolview.util.DateUtils;
import com.llc.android_coolview.util.ProgressDialogUtils;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class KobeShowSearchResultActivity extends Activity implements OnItemClickListener, OnClickListener {

	private ListView resultListView;
	private Intent intent;
	private String startStation,lastStation,type,buyDay;
	private TextView date;
	private ImageView back,refresh,yesterday,tommorrow;
	private ProgressDialogUtils dialog;
	private KobeSearchByStationNameListAdapter adapter;
	private DoubleStation mDoubleStation;
	private ValidVotes mValidVotes;
	private static final int the_station_and_arrival_station_query_train_schedules=2;
	private List<String> types;
	private Handler handler=new Handler(new Handler.Callback() {
		
		@Override
		public boolean handleMessage(Message msg) {
			if(msg.what==the_station_and_arrival_station_query_train_schedules){
				if(mDoubleStation!=null){
					new Thread(){
						@Override
						public void run() {
							super.run();
							mValidVotes=TicketsWebServiceUtils.getValidVotesByStationNameResult(startStation, lastStation, buyDay, "");
							Message msg=new Message();
							msg.what=3;
							handler.sendMessage(msg);
						}
					}.start();
				}else{
					dialog.dismissProgressDialog();
					Toast.makeText(KobeShowSearchResultActivity.this, getResources().getString(R.string.network_error),Toast.LENGTH_SHORT).show();
				}
			}else if(msg.what==3){
				dialog.dismissProgressDialog();
				if(mValidVotes!=null){
					if(mValidVotes.getResult()!=null&&mValidVotes.getResult().size()>0){
						adapter=new KobeSearchByStationNameListAdapter(KobeShowSearchResultActivity.this);
						adapter.setmDoubleStation(mDoubleStation);
						adapter.setmValidVotes(mValidVotes);
						adapter.setTypes(types);
						resultListView.setAdapter(adapter);
					}else{
						Toast.makeText(KobeShowSearchResultActivity.this, "error:"+mValidVotes.getReason(),Toast.LENGTH_SHORT).show();
					}
				}else{
					Toast.makeText(KobeShowSearchResultActivity.this, getResources().getString(R.string.network_error_no_ticktes),Toast.LENGTH_SHORT).show();
				}
			}
			return false;
		}
	});
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_kobe_fragment_tickets_search_result);
		initViews();
		setOnClickListener();
		initDatas();
	}
	
	private void initViews(){
		resultListView=(ListView) this.findViewById(R.id.result_listview);
		date=(TextView) this.findViewById(R.id.date_go);
		back=(ImageView) this.findViewById(R.id.back);
		yesterday=(ImageView) this.findViewById(R.id.date_left);
		tommorrow=(ImageView) this.findViewById(R.id.date_right);
		refresh=(ImageView) this.findViewById(R.id.refresh_tickets);
		dialog=new ProgressDialogUtils(this);
	}
	
	private void setOnClickListener(){
		resultListView.setOnItemClickListener(this);
		date.setOnClickListener(this);
		back.setOnClickListener(this);
		refresh.setOnClickListener(this);
		yesterday.setOnClickListener(this);
		tommorrow.setOnClickListener(this);
	}
	
	private void initDatas(){
		intent=getIntent();
		if(intent!=null){
			startStation=intent.getStringExtra("startStation");
			lastStation=intent.getStringExtra("lastStation");
			buyDay=intent.getStringExtra("buyTicketsDate");
			date.setText(intent.getStringExtra("date"));
			type=intent.getStringExtra("type");
			initThread();
		}
	}

	private void initThread(){
		dialog.showProgressDialog(getResources().getString(R.string.is_searching));
		new Thread(){

			@Override
			public void run() {
				super.run();
				mDoubleStation=TicketsWebServiceUtils.getSearchByStationNameResult(startStation,lastStation);
				types=switchType(type);
				Message message=new Message();
				message.what=the_station_and_arrival_station_query_train_schedules;
				handler.sendMessage(message);
			}
			
		}.start();
	}
	
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,long id) {
		
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.back:
			this.finish();
			break;
		case R.id.date_go:
			intent=new Intent(KobeShowSearchResultActivity.this,KobeCalendarActivity.class);
			startActivityForResult(intent, 1);
			break;
		case R.id.refresh_tickets:
			initThread();
			break;
		case R.id.date_left:
			buyDay=DateUtils.getSpecifiedDayBefore(buyDay);
			date.setText(buyDay);
			initThread();
			break;
		case R.id.date_right:
			buyDay=DateUtils.getSpecifiedDayAfter(buyDay);
			initThread();
			break;
		default:
			break;
		}
	}
	
	private List<String> switchType(String type){
		List<String> items=new ArrayList<>();
		if(type.contains(getResources().getString(R.string.high_speed_rail))){
			items.add("G");
		}
		if(type.contains(getResources().getString(R.string.motor_car))){
			items.add("D");
		}
		if(type.contains(getResources().getString(R.string.non_stop))){
			items.add("Z");
		}
		if(type.contains(getResources().getString(R.string.express_train))){
			items.add("T");
		}
		if(type.contains(getResources().getString(R.string.quick_train))){
			items.add("K");
		}
		if(type.contains(getResources().getString(R.string.other))){
			items.add("Y");
		}
		if(type.contains(getResources().getString(R.string.all))){
			items.add("Y");
			items.add("G");
			items.add("D");
			items.add("Z");
			items.add("T");
			items.add("K");
		}
		return items;
	}
}
