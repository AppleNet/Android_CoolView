package com.llc.android_coolview.kobe.fragment;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.llc.android_coolview.R;
import com.llc.android_coolview.animation.SwitchAnimationUtil;
import com.llc.android_coolview.kobe.activity.KobeActivity;
import com.llc.android_coolview.kobe.activity.KobeNewsDetailActivity;
import com.llc.android_coolview.kobe.adapter.KobeSinaSportsRecyclerViewAdapter;
import com.llc.android_coolview.kobe.adapter.KobeSinaSportsRecyclerViewAdapter.MyItemClickListener;
import com.llc.android_coolview.kobe.bean.Constant;
import com.llc.android_coolview.kobe.bean.sports.CommentCountInfo;
import com.llc.android_coolview.kobe.bean.sports.Data;
import com.llc.android_coolview.kobe.bean.sports.PicList;
import com.llc.android_coolview.kobe.bean.sports.Pics;
import com.llc.android_coolview.kobe.bean.sports.SinaSports;
import com.llc.android_coolview.kobe.bean.sports.SportNews;
import com.llc.android_coolview.kobe.impl.SportMenuOnClickListener;
import com.llc.android_coolview.kobe.impl.dao.SportMenuShow;
import com.llc.android_coolview.util.HttpClientHelper;
import com.llc.android_coolview.util.ProgressDialogUtils;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.Toast;

public class SportsFragment extends Fragment implements SportMenuOnClickListener, MyItemClickListener {

	private View view;
	private SportMenuShow menShow;
	public KobeActivity mKobeActivity;
	private ViewStub sinaViewSub;
	private View sinaSubView;

	private SwipeRefreshLayout refreshLayout;
	private RecyclerView mRecyclerView;
	private ProgressDialogUtils mProgressDialogUtils;
	private KobeSinaSportsRecyclerViewAdapter kssrvaAdapter;
	private SinaSports sinaSports;

	private boolean isUsed=false;
	private boolean isOnCreate=false,isOnResume=false;

	private SwitchAnimationUtil switchAnimationUtil;

	private Handler handler=new Handler(new Handler.Callback() {

		@Override
		public boolean handleMessage(Message msg) {
			mProgressDialogUtils.dismissProgressDialog();
			onLoad();
			if(msg.what==1){
				String result=(String) msg.obj;
				if(result!=null){
					sinaSports=new SinaSports();
					try {
						JSONObject jsonObject=new JSONObject(result);
						int status=jsonObject.getInt("status");
						Data mData=new Data();
						JSONObject data=jsonObject.getJSONObject("data");
						String is_intro=data.getString("is_intro");
						mData.setIs_intro(is_intro);
						List<SportNews> sportNewsList=new ArrayList<>();
						JSONArray list=data.getJSONArray("list");
						for (int i = 0; i < list.length(); i++) {
							JSONObject sublist=list.getJSONObject(i);
							SportNews sportNews=new SportNews();
							if(sublist.has("id"))
								sportNews.setId(sublist.getString("id"));
							if(sublist.has("title"))
								sportNews.setTitle(sublist.getString("title"));
							if(sublist.has("long_title"))
								sportNews.setLong_title(sublist.getString("long_title"));
							if(sublist.has("source"))
								sportNews.setSource(sublist.getString("source"));
							if(sublist.has("link"))
								sportNews.setLink(sublist.getString("link"));
							if(sublist.has("pic"))
								sportNews.setPic(sublist.getString("pic"));
							if(sublist.has("kpic"))
								sportNews.setKpic(sublist.getString("kpic"));
							if(sublist.has("bpic"))
								sportNews.setBpic(sublist.getString("bpic"));
							if(sublist.has("intro"))
								sportNews.setIntro(sublist.getString("intro"));
							if(sublist.has("pubDate"))
								sportNews.setPubDate(sublist.getString("pubDate"));
							if(sublist.has("comments"))
								sportNews.setComments(sublist.getString("comments"));
							if(sublist.has("pics")){
								Pics mPics=new Pics();
								JSONObject pics=sublist.getJSONObject("pics");
								mPics.setTotal(pics.getString("total"));
								JSONArray subpics=pics.getJSONArray("list");
								List<PicList> mPicList=new ArrayList<>();
								for(int j=0;j<subpics.length();j++){
									PicList piclist=new PicList();
									JSONObject subPics=subpics.getJSONObject(j);
									piclist.setPic(subPics.getString("pic"));
									piclist.setAlt(subPics.getString("alt"));
									piclist.setKpic(subPics.getString("kpic"));
									mPicList.add(piclist);
								}
								mPics.setList(mPicList);
								sportNews.setPics(mPics);
							}
							if(sublist.has("feedShowStyle"))
								sportNews.setFeedShowStyle(sublist.getString("feedShowStyle"));
							if(sublist.has("category"))
								sportNews.setCategory(sublist.getString("category"));
							if(sublist.has("is_focus"))
								sportNews.setIs_focus(sublist.getString("is_focus"));
							if(sublist.has("comment"))
								sportNews.setComment(sublist.getString("comment"));
							if(sublist.has("comment_count_info")){
								JSONObject comment_count_info=sublist.getJSONObject("comment_count_info");
								CommentCountInfo commentCountInfo=new CommentCountInfo();
								if(comment_count_info.has("comment_status"))
									commentCountInfo.setComment_status(comment_count_info.getString("comment_status"));
								if(comment_count_info.has("qreply"))
									commentCountInfo.setQreply(comment_count_info.getString("qreply"));
								if(comment_count_info.has("total"))
									commentCountInfo.setTotal(comment_count_info.getString("total"));
								if(comment_count_info.has("show"))
									commentCountInfo.setShow(comment_count_info.getString("show"));
								if(comment_count_info.has("praise"))
									commentCountInfo.setDispraise(comment_count_info.getString("praise"));
								if(comment_count_info.has("dispraise"))
									commentCountInfo.setDispraise(comment_count_info.getString("dispraise"));
								sportNews.setComment_count_info(commentCountInfo);
							}
							sportNewsList.add(sportNews);
						}
						mData.setList(sportNewsList);
						sinaSports.setStatus(status);
						sinaSports.setData(mData);
					} catch (JSONException e) {
						e.printStackTrace();
					}
					if(sinaSports!=null){
						kssrvaAdapter=new KobeSinaSportsRecyclerViewAdapter(sinaSports, getActivity());
						kssrvaAdapter.setOnItemClickListener(SportsFragment.this);
						mRecyclerView.setAdapter(kssrvaAdapter);
					}else{
						Toast.makeText(getActivity(), "SinaSports:"+getResources().getString(R.string.network_error), Toast.LENGTH_SHORT).show();
					}
				}else{
					Toast.makeText(getActivity(), getResources().getString(R.string.network_error), Toast.LENGTH_SHORT).show();
				}
			}
			return false;
		}
	});

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		view=inflater.inflate(R.layout.activity_kobe_fragment_sports, null);
		initViews();
		if(isUsed==true&&isOnCreate==false&&isOnResume==false){
			initDatas();
		}
		isOnCreate=true;
		return view;
	}

	@Override
	public void setUserVisibleHint(boolean isVisibleToUser) {
		super.setUserVisibleHint(isVisibleToUser);
		isUsed=isVisibleToUser;
		if(isUsed&&isOnCreate&&isOnResume){
			initDatas();
		}
	}

	@Override
	public void onResume() {
		super.onResume();
		isOnResume=true;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		((KobeActivity)getActivity()).setOnKobeSportsResumeWindowFocusChanged(new KobeActivity.OnKobeSportsResumeWindowFocusChanged() {
			@Override
			public void sportsResumeWindowFocusChanged() {
				if (switchAnimationUtil==null){
					switchAnimationUtil=new SwitchAnimationUtil();
					switchAnimationUtil.startAnimation(mRecyclerView, SwitchAnimationUtil.AnimationType.SCALE);
				}
			}
		});
	}

	private void initDatas(){
		menShow=new SportMenuShow(mKobeActivity);
		menShow.setSportMenuOnClickListener(this);
		initThread();
	}

	private void initViews(){
		mProgressDialogUtils=new ProgressDialogUtils(getActivity());
		sinaViewSub=(ViewStub) view.findViewById(R.id.sports_sina);
		sinaViewSub.inflate();
		sinaSubView=view.findViewById(R.id.activity_kobe_sport_fragment_sina);
		refreshLayout=(SwipeRefreshLayout) sinaSubView.findViewById(R.id.sports_sina_refresher);
		mRecyclerView=(RecyclerView) sinaSubView.findViewById(R.id.sports_sina_recyclerView);
		int orientation = LinearLayoutManager.VERTICAL;
		mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), orientation, false));
		refreshLayout.setColorSchemeColors(Color.RED);
		refreshLayout.setOnRefreshListener(new OnRefreshListener() {

			@Override
			public void onRefresh() {
				initThread();
			}
		});
	}

	private void initThread(){
		mProgressDialogUtils.showProgressDialog("正在获取体育信息，请稍候...");
		new Thread(){

			@Override
			public void run() {
				super.run();
				HttpClientHelper helper=new HttpClientHelper();
				helper.setRequestMethod("GET");
				String result=helper.readHtml(Constant.SINA_SPORTS_NEWS);
				Message msg=new Message();
				msg.what=1;
				msg.obj=result;
				handler.sendMessage(msg);
			}

		}.start();
	}

	@Override
	public void onSportMenuClick(int id) {
		switch (id) {
			case R.id.tencent_sports:
				Toast.makeText(getActivity(), "Tencent Sports", Toast.LENGTH_SHORT).show();
				break;
			case R.id.netease_sports:
				Toast.makeText(getActivity(), "Netease Sports", Toast.LENGTH_SHORT).show();
				break;
			case R.id.sina_sports:
				Toast.makeText(getActivity(), "Sina Sports", Toast.LENGTH_SHORT).show();

				break;
			default:
				break;
		}
	}

	@Override
	public void onItemClickListener(View view, int position) {
		Intent intent=new Intent(getActivity(),KobeNewsDetailActivity.class);
		String url_3w=sinaSports.getData().getList().get(position).getLink();
		String title=sinaSports.getData().getList().get(position).getTitle();
		intent.putExtra("url_3w", url_3w);
		intent.putExtra("title", title);
		startActivity(intent);
	}

	private void onLoad() {
		refreshLayout.setRefreshing(false);
	}
}
