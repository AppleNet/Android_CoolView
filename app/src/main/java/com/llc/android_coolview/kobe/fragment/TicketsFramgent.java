package com.llc.android_coolview.kobe.fragment;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.google.gson.reflect.TypeToken;
import com.llc.android_coolview.R;
import com.llc.android_coolview.kobe.activity.KobeActivity;
import com.llc.android_coolview.kobe.activity.KobeCalendarActivity;
import com.llc.android_coolview.kobe.activity.KobeShowSearchResultActivity;
import com.llc.android_coolview.kobe.activity.KobeStationSearchActivity;
import com.llc.android_coolview.kobe.adapter.KobeSearchByTrainCodeDetailAdapter;
import com.llc.android_coolview.kobe.adapter.KobeSearchByTrainCodeListAdapter;
import com.llc.android_coolview.kobe.adapter.KobeSearchHistoryAdapter;
import com.llc.android_coolview.kobe.bean.tickets.History;
import com.llc.android_coolview.kobe.bean.tickets.TrainCodeDeatils;
import com.llc.android_coolview.kobe.control.TicketsWebServiceUtils;
import com.llc.android_coolview.kobe.impl.MenuOnClickListener;
import com.llc.android_coolview.kobe.impl.dao.TicketsMenuShow;
import com.llc.android_coolview.kobe.view.dialog.TrainDetailDialog;
import com.llc.android_coolview.kobe.view.dialog.TrainDetailDialog.OnCheckedChangedListener;
import com.llc.android_coolview.util.DateUtils;
import com.llc.android_coolview.util.InputKeyBoardUtils;
import com.llc.android_coolview.util.PreferenceUtil;
import com.llc.android_coolview.util.ProgressDialogUtils;
import com.rengwuxian.materialedittext.MaterialEditText;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class TicketsFramgent extends Fragment implements MenuOnClickListener, OnClickListener, OnItemClickListener {

	public KobeActivity mKobeActivity;

	private View view;
	private View startAndLastStationSubView;
	private TicketsMenuShow menuShow;
	private ListView mListView,historyListView;
	private TextView mTitle;
	private BaseAdapter adapter;
	private TicketsWebServiceUtils ticketsWebServiceUtils;
	private ProgressDialogUtils dialog;
	private List<Map<Object,Object>> result;
	private List<History> histories;
	private History history;
	private KobeSearchHistoryAdapter kshAdapter;
	private PreferenceUtil preferenceUtil;
	private MaterialEditText input;
	private MaterialEditText detailInput;
	private TextView search,startStation,lastStation,train,date,week;
	private FloatingActionButton searchBtn;
	private FloatingActionButton btnSearch;
	private View startDateTime,trainDetail;
	private View startStationInput,lastStationInput;
	private TrainDetailDialog mTrainDetailDialog;
	private ViewStub trainCodeViewSub,startAndLastStationViewSub,detailViewSub;
	private ImageView chageImg;
	private static final int the_train_number_query_this_train_schedule=1;
	private static final int the_train_number_query_train_through_the_detail_at_the_station=3;
	private static final int REQUEST_CODE=1;
	private static final int START_REQUEST_CODE=2;
	private static final int LAST_REQUEST_CODE=3;


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		view=inflater.inflate(R.layout.activity_kobe_fragment_tickets, null);
		initViews();
		initDatas();
		return view;
	}

	private final Handler handler=new Handler(new Handler.Callback() {

		@SuppressWarnings("unchecked")
		@Override
		public boolean handleMessage(Message msg) {
			dialog.dismissProgressDialog();
			trainCodeViewSub.setVisibility(View.GONE);
			startAndLastStationViewSub.setVisibility(View.GONE);
			detailViewSub.setVisibility(View.GONE);
			switch(msg.what){
				case the_train_number_query_this_train_schedule:
					result=(List<Map<Object,Object>>) msg.obj;
					if(result!=null){
						adapter=new KobeSearchByTrainCodeListAdapter(getActivity(), result);
						mListView.setAdapter(adapter);
						mListView.setVisibility(View.VISIBLE);
					}else{
						Toast.makeText(getActivity(), "网络不给力！",Toast.LENGTH_SHORT).show();
					}
					break;
				case the_train_number_query_train_through_the_detail_at_the_station:
					result=(List<Map<Object, Object>>) msg.obj;
					if(result!=null){
						adapter=new KobeSearchByTrainCodeDetailAdapter(getActivity(), result);
						mListView.setAdapter(adapter);
						mListView.setVisibility(View.VISIBLE);
					}else {
						Toast.makeText(getActivity(), "网络不给力！",Toast.LENGTH_SHORT).show();
					}
					break;
			}
			return false;
		}
	});

	private void initViews(){
		mListView=(ListView) view.findViewById(R.id.show_data_list);
		mTitle=(TextView) view.findViewById(R.id.title);
		trainCodeViewSub=((ViewStub)view.findViewById(R.id.search_by_traincode));
		startAndLastStationViewSub=((ViewStub)view.findViewById(R.id.search_by_start_last_station));
		detailViewSub=((ViewStub)view.findViewById(R.id.search_detail_by_traincode));
		startAndLastStationViewSub.inflate();
		preferenceUtil=new PreferenceUtil(getActivity());
		startAndLastStationSubView=view.findViewById(R.id.activity_kobe_tickets_fragment_start_last_station);
		search=(TextView) startAndLastStationSubView.findViewById(R.id.search_start_last_station);
		search.setOnClickListener(this);
		startStationInput=startAndLastStationSubView.findViewById(R.id.input_startstation);
		startStationInput.setOnClickListener(this);
		lastStationInput=startAndLastStationSubView.findViewById(R.id.input_laststation);
		lastStationInput.setOnClickListener(this);
		startStation=(TextView) startAndLastStationSubView.findViewById(R.id.start_staion_tv);
		lastStation=(TextView) startAndLastStationSubView.findViewById(R.id.last_station_tv);
		startStation.setOnClickListener(this);
		lastStation.setOnClickListener(this);
		startDateTime=startAndLastStationSubView.findViewById(R.id.start_date_time);
		startDateTime.setOnClickListener(this);
		date=(TextView) startAndLastStationSubView.findViewById(R.id.date);
		date.setText(DateUtils.getCurrentDay("MM月dd日"));
		week=(TextView) startAndLastStationSubView.findViewById(R.id.week);
		trainDetail=startAndLastStationSubView.findViewById(R.id.train_detail);
		trainDetail.setOnClickListener(this);
		train=(TextView) startAndLastStationSubView.findViewById(R.id.train);
		chageImg=(ImageView) startAndLastStationSubView.findViewById(R.id.change_img);
		chageImg.setOnClickListener(this);
		historyListView=(ListView) startAndLastStationSubView.findViewById(R.id.common_line_lv);
		historyListView.setOnItemClickListener(this);
		TypeToken<List<History>> token=new TypeToken<List<History>>(){};
		histories=preferenceUtil.getBean("history", token);
		if(histories!=null){
			kshAdapter=new KobeSearchHistoryAdapter(histories, getActivity());
			View v=this.getActivity().getLayoutInflater().inflate(R.layout.activity_kobe_fragment_tickets_history_title, null);
			if(historyListView.getHeaderViewsCount()<=0){
				historyListView.addHeaderView(v);
			}
			historyListView.setAdapter(kshAdapter);
		}
	}

	private void initDatas(){
		dialog=new ProgressDialogUtils(getActivity());
		menuShow=new TicketsMenuShow(mKobeActivity);
		menuShow.setOnMenuClickListener(this);
		ticketsWebServiceUtils=new TicketsWebServiceUtils();
		mTitle.setText(getResources().getString(R.string.the_train_number_query_this_train_schedule));
	}

	@Override
	public void onMenuClick(int id) {
		switch (id) {
			case R.id.the_train_number_query_this_train_schedule:
				mTitle.setText(getResources().getString(R.string.the_train_number_query_this_train_schedule));
				if (trainCodeViewSub==null) {
					trainCodeViewSub=((ViewStub)view.findViewById(R.id.search_by_traincode));
					trainCodeViewSub.inflate();
				}else {
					trainCodeViewSub.setVisibility(View.VISIBLE);
				}
				startAndLastStationViewSub.setVisibility(View.GONE);
				detailViewSub.setVisibility(View.GONE);
				mListView.setVisibility(View.GONE);
				View trainCodeSubView=view.findViewById(R.id.activity_kobe_tickets_fragment_traincode);
				input=(MaterialEditText) trainCodeSubView.findViewById(R.id.input_traincode);
				input.requestFocus();
				InputKeyBoardUtils.showKeyBorad(input, getActivity());
				searchBtn=(FloatingActionButton) trainCodeSubView.findViewById(R.id.search_traincode);
				searchBtn.setOnClickListener(this);
				break;
			case R.id.the_station_and_arrival_station_query_train_schedules:
				mTitle.setText(getResources().getString(R.string.the_station_and_arrival_station_query_train_schedules));
				if(startAndLastStationViewSub==null){
					startAndLastStationViewSub=((ViewStub)view.findViewById(R.id.search_by_start_last_station));
					startAndLastStationViewSub.inflate();
				}else {
					startAndLastStationViewSub.setVisibility(View.VISIBLE);
				}
				trainCodeViewSub.setVisibility(View.GONE);
				detailViewSub.setVisibility(View.GONE);
				mListView.setVisibility(View.GONE);
				startAndLastStationSubView=view.findViewById(R.id.activity_kobe_tickets_fragment_start_last_station);
				search=(TextView) startAndLastStationSubView.findViewById(R.id.search_start_last_station);
				search.setOnClickListener(this);
				startStationInput=startAndLastStationSubView.findViewById(R.id.input_startstation);
				startStationInput.setOnClickListener(this);
				lastStationInput=startAndLastStationSubView.findViewById(R.id.input_laststation);
				lastStationInput.setOnClickListener(this);
				startStation=(TextView) startAndLastStationSubView.findViewById(R.id.start_staion_tv);
				lastStation=(TextView) startAndLastStationSubView.findViewById(R.id.last_station_tv);
				startStation.setOnClickListener(this);
				lastStation.setOnClickListener(this);
				startDateTime=startAndLastStationSubView.findViewById(R.id.start_date_time);
				startDateTime.setOnClickListener(this);
				date=(TextView) startAndLastStationSubView.findViewById(R.id.date);
				date.setText(DateUtils.getCurrentDay("MM月dd日"));
				week=(TextView) startAndLastStationSubView.findViewById(R.id.week);
				trainDetail=startAndLastStationSubView.findViewById(R.id.train_detail);
				trainDetail.setOnClickListener(this);
				train=(TextView) startAndLastStationSubView.findViewById(R.id.train);
				chageImg=(ImageView) startAndLastStationSubView.findViewById(R.id.change_img);
				chageImg.setOnClickListener(this);
				historyListView=(ListView) startAndLastStationSubView.findViewById(R.id.common_line_lv);
				historyListView.setOnItemClickListener(this);
				TypeToken<List<History>> token=new TypeToken<List<History>>(){};
				histories=preferenceUtil.getBean("history", token);
				if(histories!=null){
					kshAdapter=new KobeSearchHistoryAdapter(histories, getActivity());
					View v=this.getActivity().getLayoutInflater().inflate(R.layout.activity_kobe_fragment_tickets_history_title, null);
					if(historyListView.getHeaderViewsCount()<=0){
						historyListView.addHeaderView(v);
					}
					historyListView.setAdapter(kshAdapter);
				}
				InputKeyBoardUtils.hideKeyBoard(getView(), getActivity());
				break;
			case R.id.the_train_number_query_train_through_the_detail_at_the_station:
				mTitle.setText(getResources().getString(R.string.the_train_number_query_train_through_the_detail_at_the_station));
				if(detailViewSub==null){
					detailViewSub=((ViewStub)view.findViewById(R.id.search_detail_by_traincode));
					detailViewSub.inflate();
				}else {
					detailViewSub.setVisibility(View.VISIBLE);
				}
				trainCodeViewSub.setVisibility(View.GONE);
				startAndLastStationViewSub.setVisibility(View.GONE);
				mListView.setVisibility(View.GONE);
				View detailSubView=view.findViewById(R.id.activity_kobe_tickets_fragment_query_station_detail);
				btnSearch=(FloatingActionButton) detailSubView.findViewById(R.id.search_traincode_for_detail);
				btnSearch.setOnClickListener(this);
				detailInput=(MaterialEditText) detailSubView.findViewById(R.id.input_traincode_for_detail);
				InputKeyBoardUtils.showKeyBorad(detailInput, getActivity());
				break;
			default:
				break;
		}
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(resultCode==1){
			if(requestCode==REQUEST_CODE){
				if(data!=null){
					date.setText(data.getStringExtra("DATE"));
					week.setText(data.getStringExtra("WEEK"));
				}
			}
		}else if(resultCode==2){
			if (requestCode==START_REQUEST_CODE) {
				if(data!=null){
					startStation.setText(data.getStringExtra("city"));
				}
			}
		}else if(resultCode==3){
			if (requestCode==LAST_REQUEST_CODE) {
				if(data!=null){
					lastStation.setText(data.getStringExtra("city"));
				}
			}
		}
	}

	@Override
	public void onResume() {
		super.onResume();
		TypeToken<List<History>> token=new TypeToken<List<History>>(){};
		if(startAndLastStationSubView!=null){
			historyListView=(ListView) startAndLastStationSubView.findViewById(R.id.common_line_lv);
			historyListView.setOnItemClickListener(this);
			histories=preferenceUtil.getBean("history", token);
			if(histories!=null){
				kshAdapter=new KobeSearchHistoryAdapter(histories, getActivity());
				if(historyListView.getHeaderViewsCount()<=0){
					View v=this.getActivity().getLayoutInflater().inflate(R.layout.activity_kobe_fragment_tickets_history_title, null);
					historyListView.addHeaderView(v);
				}
				historyListView.setAdapter(kshAdapter);
				kshAdapter.notifyDataSetChanged();
			}
		}
	}

	@Override
	public void onClick(View v) {
		Intent intent;
		switch(v.getId()){
			case R.id.search_traincode:
				// 火车车次查询本火车时刻表
				final String trainCode=input.getText().toString().trim();
				if(!TextUtils.isEmpty(trainCode)){
					dialog.showProgressDialog("正在查询，请稍候...");
					new Thread(){
						@Override
						public void run() {
							super.run();
							TrainCodeDeatils result=ticketsWebServiceUtils.getSearchByTrainCodeResult();
							Message message=new Message();
							message.obj=result;
							message.what=the_train_number_query_this_train_schedule;
							handler.sendMessage(message);
						}

					}.start();
				}else {
					Toast.makeText(getActivity(), "请输入要查询的车次！", Toast.LENGTH_SHORT).show();
				}
				break;
			case R.id.search_start_last_station:
				final String sStation=startStation.getText().toString().trim();
				final String lStation=lastStation.getText().toString().trim();
				final String type=train.getText().toString();
				if(startStation!=null&&!sStation.equals("")&&startStation.length()>0&&!TextUtils.isEmpty(lStation)){
					history=new History();
					intent=new Intent(getActivity(),KobeShowSearchResultActivity.class);
					intent.putExtra("startStation", sStation);
					intent.putExtra("lastStation", lStation);
					history.setStartStation(sStation);
					history.setEndStation(lStation);
					TypeToken<List<History>> token=new TypeToken<List<History>>(){};
					histories=preferenceUtil.getBean("history", token);
					if(histories==null){
						histories=new ArrayList<>();
					}else{
						for(int i=0;i<histories.size();i++){
							if(histories.get(i).getStartStation().equals(sStation)||histories.get(i).equals(lStation)){
								histories.remove(i);
							}
						}
					}
					histories.add(history);
					preferenceUtil.setBeanPreference("history", histories);
					intent.putExtra("type", type);
					intent.putExtra("date", date.getText().toString());
					String d=date.getText().toString();
					String atemp=d.substring(0,d.indexOf("月"));
					String btemp=d.substring(d.indexOf("月")+1, d.indexOf("日"));
					String dd=DateUtils.getCurrYear()+"-"+atemp+"-"+btemp;
					intent.putExtra("buyTicketsDate", dd);
					startActivity(intent);
				}else{
					Toast toast = Toast.makeText(getActivity(), "请输入始发站或者终点站！",Toast.LENGTH_SHORT);
					toast.setGravity(Gravity.CENTER, 0, 0);
					toast.show();
				}
				break;
			case R.id.search_traincode_for_detail:
				final String trainCodeForDetail=detailInput.getText().toString().trim();
				if (!TextUtils.isEmpty(trainCodeForDetail)) {
					dialog.showProgressDialog("正在查询，请稍候...");
					new Thread(){
						@Override
						public void run() {
							super.run();
							TrainCodeDeatils result=ticketsWebServiceUtils.getSearchByTrainCodeResult();
							Message message=new Message();
							message.obj=result;
							message.what=the_train_number_query_this_train_schedule;
							handler.sendMessage(message);
						}

					}.start();
				}else {
					Toast.makeText(getActivity(), "请输入要查看明细的车次！", Toast.LENGTH_SHORT).show();
				}
				break;
			case R.id.start_date_time:
				intent=new Intent(getActivity(),KobeCalendarActivity.class);
				Calendar calendar=Calendar.getInstance();
				int year=calendar.get(Calendar.YEAR);
				int index = date.getText().toString().indexOf("月");
				String month=date.getText().toString().substring(0, index);
				String d=date.getText().toString().substring(index+1, date.getText().toString().length()-1);
				String params=year+"-"+month+"-"+d;
				intent.putExtra("date", params);
				this.startActivityForResult(intent, REQUEST_CODE);
				break;
			case R.id.train_detail:
				mTrainDetailDialog=new TrainDetailDialog(getActivity(),train.getText().toString().trim());
				mTrainDetailDialog.show();
				mTrainDetailDialog.setmOnCheckedChangedListener(new OnCheckedChangedListener() {

					@Override
					public void checkChanged(HashMap<Integer, String> items) {
						Set<Integer>set=items.keySet();
						StringBuilder sb=new StringBuilder();
						for(Integer integer : set){
							sb.append(items.get(integer)).append(",");
						}
						train.setText(sb.toString());
						mTrainDetailDialog.dismiss();
					}
				});
				break;
			case R.id.input_startstation:
			case R.id.start_staion_tv:
				intent=new Intent(getActivity(),KobeStationSearchActivity.class);
				intent.putExtra("Tag", START_REQUEST_CODE);
				startActivityForResult(intent, START_REQUEST_CODE);
				break;
			case R.id.input_laststation:
			case R.id.last_station_tv:
				intent=new Intent(getActivity(),KobeStationSearchActivity.class);
				intent.putExtra("Tag", LAST_REQUEST_CODE);
				startActivityForResult(intent, LAST_REQUEST_CODE);
				break;
			case R.id.change_img:
				break;
		}
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,long id) {
		String mstartStation=((TextView)view.findViewById(R.id.history_startstation)).getText().toString();
		String mendStation=((TextView)view.findViewById(R.id.history_endstation)).getText().toString();
		startStation.setText(mstartStation);
		lastStation.setText(mendStation);
	}

}
