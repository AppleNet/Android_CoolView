package com.llc.android_coolview.paul.view.activity;

import java.util.List;

import com.llc.android_coolview.R;
import com.llc.android_coolview.paul.adapter.XListViewAdapter;
import com.llc.android_coolview.paul.adapter.XListViewTeamCompetitionScheduleQueryAdapter;
import com.llc.android_coolview.paul.bean.PaulParams;
import com.llc.android_coolview.paul.bean.team_competition_schedule_query.TeamCompetitionScheduleQuery;
import com.llc.android_coolview.paul.bean.teams_event_query.ResultList;
import com.llc.android_coolview.paul.bean.the_nba_regular_season_schedule_result.ListArray;
import com.llc.android_coolview.paul.bean.the_nba_regular_season_schedule_result.NBARegularSeasonScheduleResultList;
import com.llc.android_coolview.paul.control.PaulManager;
import com.llc.android_coolview.paul.control.task.NbaRegularSeasonScheduleResultThread;
import com.llc.android_coolview.paul.view.xlistview.XListView;
import com.llc.android_coolview.paul.view.xlistview.XListView.IXListViewListener;
import com.llc.android_coolview.util.JSONUtil;
import com.nineoldandroids.view.ViewHelper;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.DrawerLayout.DrawerListener;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class NBAGamesSpreadActivity extends FragmentActivity implements IXListViewListener, OnClickListener {

	public static final int TEAM_COMPETITION_SCHEDULE_QUERY = 1;
	public static final int TEAMS_EVENT_QUERY = 2;
	public static final int THE_NBA_REGULAR_SEASON_SCHEDULE_RESULT = 3;

	private DrawerLayout myDrawerLayout;
	private Intent intent;
	private XListView myListView;
	private ResultList resultList;
	private XListViewAdapter adapter;
	private ProgressDialog myProgressDialog;

	private RelativeLayout the_nba_regular_season_schedule_result;
	private RelativeLayout team_event_query;
	private RelativeLayout team_competition_schedule_query;

	private String teamName;
	private Handler handler = new Handler(new Handler.Callback() {

		@Override
		public boolean handleMessage(Message msg) {
			dismissLoadingDialog();
			switch (msg.what) {
				case TEAMS_EVENT_QUERY:
					break;
				case TEAM_COMPETITION_SCHEDULE_QUERY:
					String resultQuery=(String) msg.obj;
					TeamCompetitionScheduleQuery tcsq=JSONUtil.fromJson(resultQuery, TeamCompetitionScheduleQuery.class);
					if(tcsq.getResult()!=null){
						if(tcsq.getResult().getList()!=null){
							XListViewTeamCompetitionScheduleQueryAdapter adapter=new XListViewTeamCompetitionScheduleQueryAdapter(NBAGamesSpreadActivity.this,tcsq);
							myListView.setAdapter(adapter);
						}else {
							Toast.makeText(NBAGamesSpreadActivity.this, "error:"+tcsq.getReason(), Toast.LENGTH_LONG).show();
						}
					}else{
						Toast.makeText(NBAGamesSpreadActivity.this, "网络不给力！", Toast.LENGTH_LONG).show();
					}
					break;
				case THE_NBA_REGULAR_SEASON_SCHEDULE_RESULT:
					// NBA常规赛赛程结果
					String result=(String) msg.obj;
					NBARegularSeasonScheduleResultList nbarssrl=JSONUtil.fromJson(result, NBARegularSeasonScheduleResultList.class);
					if(nbarssrl!=null){
						if(nbarssrl.getResult()==null){
							Toast.makeText(NBAGamesSpreadActivity.this, "error:"+nbarssrl.getReason(), Toast.LENGTH_LONG).show();
						}else{
							List<ListArray> list=nbarssrl.getResult().getList();
							if(list!=null){

							}else{
								Toast.makeText(NBAGamesSpreadActivity.this, "error:"+nbarssrl.getReason(), Toast.LENGTH_LONG).show();
							}
						}
					}else{
						Toast.makeText(NBAGamesSpreadActivity.this, "网络不给力！", Toast.LENGTH_LONG).show();
					}
					break;
			}
			return false;
		}
	});

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().requestFeature(Window.FEATURE_NO_TITLE);
		if (VERSION.SDK_INT >= VERSION_CODES.LOLLIPOP) {
			getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
		}
		this.setContentView(R.layout.activity_paul_nba_games_sprea);
		initView();
		initEvents();
		initData();
	}

	private void initView() {
		myDrawerLayout = (DrawerLayout) this.findViewById(R.id.id_drawlayout);
		myDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED, Gravity.RIGHT);
		myListView = (XListView) this.findViewById(R.id.paul_listview);
		myListView.setPullLoadEnable(true);
		the_nba_regular_season_schedule_result=(RelativeLayout) findViewById(R.id.the_nba_regular_season_schedule_result);
		team_event_query=(RelativeLayout) findViewById(R.id.team_event_query);
		team_competition_schedule_query=(RelativeLayout) findViewById(R.id.team_competition_schedule_query);
	}

	private void initData() {
		intent = getIntent();
		teamName = intent.getStringExtra("teamName");
		showLoadingDialog("正在加载，请稍候...");
		GetTeamEventQueryMessageTask getMessageTask = new GetTeamEventQueryMessageTask();
		PaulParams params = new PaulParams();
		params.setTeam(teamName);
		getMessageTask.execute(params);
	}

	private void initEvents() {
		myDrawerLayout.setDrawerListener(new DrawerListener() {

			@Override
			public void onDrawerStateChanged(int arg0) {

			}

			@Override
			public void onDrawerSlide(View drawerView, float slideOffset) {
				View mContent = myDrawerLayout.getChildAt(0);
				View mMenu = drawerView;
				float scale = 1 - slideOffset;
				float rightScale = 0.8f + scale * 0.2f;
				if (drawerView.getTag().equals("LEFT")) {
					float leftScale = 1 - 0.3f * scale;
					ViewHelper.setScaleX(mMenu, leftScale);
					ViewHelper.setScaleY(mMenu, leftScale);
					ViewHelper.setAlpha(mMenu, 0.6f + 0.4f * (1 - scale));
					ViewHelper.setTranslationX(mContent, mMenu.getMeasuredWidth() * (1 - scale));
					ViewHelper.setPivotX(mContent, 0);
					ViewHelper.setPivotY(mContent, mContent.getMeasuredHeight() / 2);
					mContent.invalidate();
					ViewHelper.setScaleX(mContent, rightScale);
					ViewHelper.setScaleY(mContent, rightScale);
				} else {
					ViewHelper.setTranslationX(mContent, -mMenu.getMeasuredWidth() * slideOffset);
					ViewHelper.setPivotX(mContent, mContent.getMeasuredWidth());
					ViewHelper.setPivotY(mContent, mContent.getMeasuredHeight() / 2);
					mContent.invalidate();
					ViewHelper.setScaleX(mContent, rightScale);
					ViewHelper.setScaleY(mContent, rightScale);
				}
			}

			@Override
			public void onDrawerOpened(View arg0) {

			}

			@Override
			public void onDrawerClosed(View arg0) {
				myDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED, Gravity.RIGHT);
			}
		});
		myListView.setXListViewListener(this);
		the_nba_regular_season_schedule_result.setOnClickListener(this);
		team_event_query.setOnClickListener(this);
		team_competition_schedule_query.setOnClickListener(this);
	}

	@Override
	public void onRefresh() {
		handler.postDelayed(new Runnable() {

			@Override
			public void run() {
				onLoad();
			}
		}, 2000);
	}

	@Override
	public void onLoadMore() {
		handler.postDelayed(new Runnable() {

			@Override
			public void run() {
				onLoad();
			}
		}, 2000);
	}

	private void onLoad() {
		myListView.stopRefresh();
		myListView.stopLoadMore();
		myListView.setRefreshTime("刚刚");
	}

	private class GetTeamEventQueryMessageTask extends AsyncTask<PaulParams, Integer, String> {

		private PaulParams myPaulParams;

		@Override
		protected String doInBackground(PaulParams... params) {
			myPaulParams = params[0];
			String result = PaulManager.getTeamEventQuery(myPaulParams);
			return result;
		}

		@Override
		protected void onPostExecute(String result) {
			dismissLoadingDialog();
			resultList = JSONUtil.fromJson(result, ResultList.class);
			if(resultList!=null){
				if(resultList.getResult()!=null){
					adapter = new XListViewAdapter(NBAGamesSpreadActivity.this, resultList);
					myListView.setAdapter(adapter);
				}else{
					Toast.makeText(NBAGamesSpreadActivity.this, "error"+resultList.getReason(), Toast.LENGTH_SHORT).show();
				}
			}else{
				Toast.makeText(NBAGamesSpreadActivity.this, "网络不给力！", Toast.LENGTH_SHORT).show();
			}
		}
	}

	private void showLoadingDialog(String msg) {
		if (myProgressDialog != null && myProgressDialog.isShowing()) {
			return;
		}
		myProgressDialog = new ProgressDialog(NBAGamesSpreadActivity.this);
		myProgressDialog.setMessage(msg);
		myProgressDialog.setCanceledOnTouchOutside(true);
		myProgressDialog.show();
	}

	private void dismissLoadingDialog() {
		if (myProgressDialog != null) {
			myProgressDialog.dismiss();
		}
	}

	@Override
	public void onClick(View v) {
		switch(v.getId()){
			case R.id.the_nba_regular_season_schedule_result:
				// 关闭侧滑菜单
				myDrawerLayout.closeDrawer(Gravity.LEFT);
				showLoadingDialog("正在加载，请稍候...");
				NbaRegularSeasonScheduleResultThread nrssrt=new NbaRegularSeasonScheduleResultThread(handler);
				nrssrt.start();
				break;
			case R.id.team_event_query:
				// 关闭侧滑菜单
				myDrawerLayout.closeDrawer(Gravity.LEFT);
				showLoadingDialog("正在加载，请稍候...");
				GetTeamEventQueryMessageTask getMessageTask = new GetTeamEventQueryMessageTask();
				PaulParams params = new PaulParams();
				params.setTeam(teamName);
				getMessageTask.execute(params);
				break;
			case R.id.team_competition_schedule_query:
				// 关闭侧滑菜单
				myDrawerLayout.closeDrawer(Gravity.LEFT);
				Builder builder=new AlertDialog.Builder(NBAGamesSpreadActivity.this);
				View view=getLayoutInflater().inflate(R.layout.activity_paul_dialog, null);
				final EditText nameEdv=(EditText) view.findViewById(R.id.team_name);
				builder.setView(view);
				builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						showLoadingDialog("正在加载，请稍候...");
						new Thread(new Runnable() {

							@Override
							public void run() {
								String tName=nameEdv.getText().toString();
								PaulParams params=new PaulParams();
								params.setHteam(teamName);
								params.setVteam(tName);
								String result=PaulManager.getTeamCompetitionScheduleQuery(params);
								Message msg=new Message();
								msg.what=TEAM_COMPETITION_SCHEDULE_QUERY;
								msg.obj=result;
								handler.sendMessage(msg);
							}
						}).start();
					}
				});
				builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				});
				builder.create().show();
				break;
		}
	}
}
