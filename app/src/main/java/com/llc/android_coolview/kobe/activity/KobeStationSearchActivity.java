package com.llc.android_coolview.kobe.activity;

import java.util.ArrayList;
import java.util.List;

import com.llc.android_coolview.R;
import com.llc.android_coolview.kobe.bean.Constant;
import com.llc.android_coolview.kobe.control.search.AbstractSearchService;
import com.llc.android_coolview.kobe.control.search.SearchCallback;
import com.llc.android_coolview.kobe.control.search.SearchService;
import com.llc.android_coolview.kobe.view.tagflowlayout.FlowLayout;
import com.llc.android_coolview.kobe.view.tagflowlayout.TagAdapter;
import com.llc.android_coolview.kobe.view.tagflowlayout.TagFlowLayout;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.inputmethod.InputMethodManager;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class KobeStationSearchActivity extends Activity implements OnClickListener, TextWatcher, OnItemClickListener, OnTouchListener {

	//private ScrollView mScrollView;
	private TagFlowLayout mFlowLayout;
	private TagAdapter<String> mAdapter ;
	private Intent intent;
	private int tagResult;
	private ListView mListView;
	private EditText searchStationTv;
	private AbstractSearchService searchService;
	private View search_station_layout;
	private ImageView back;
	private ResultAdapter resultAdapter=new ResultAdapter();
	private static final int MAX_HITS = 20;
	private InputMethodManager mInputMethodManager;
	private Handler handler=new Handler(Looper.getMainLooper());
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_kobe_fragment_tickets_search_station);
		initViews();
	}
	
	private void initViews(){
		//mScrollView=(ScrollView) this.findViewById(R.id.hot_city_scrollview);
		mFlowLayout=(TagFlowLayout) this.findViewById(R.id.id_flowlayout);
		mListView=(ListView) this.findViewById(R.id.search_result_lv);
		searchStationTv=(EditText) this.findViewById(R.id.search_station);
		search_station_layout=this.findViewById(R.id.search_station_layout);
		back=(ImageView) this.findViewById(R.id.back);
		back.setOnClickListener(this);
		mInputMethodManager=(InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		//addViews();	
		initData();
	}
	
	private void initData(){
		intent=getIntent();
		tagResult=intent.getIntExtra("Tag", 0);
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
		searchStationTv.addTextChangedListener(this);
		searchService=SearchService.getInstance(this);
		mListView.setOnTouchListener(this);
		mListView.setOnItemClickListener(this);
		mFlowLayout.setAdatper(mAdapter=new TagAdapter<String>(Constant.hotCitys) {

			@Override
			public View getView(FlowLayout parent, int position, String t) {
				TextView tv = (TextView) getLayoutInflater().inflate(R.layout.activity_kobe_fragment_tickets_tv,mFlowLayout, false);
                tv.setText(t);
                tv.setTag(position);
                tv.setOnClickListener(KobeStationSearchActivity.this);
                return tv;
			}
		});
		mAdapter.setSelectedList(0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29);
	}
	
//	private void addViews(){
//		
//		LinearLayout.LayoutParams layoutParams=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT);
//		LinearLayout linearLayout=new LinearLayout(this);
//		linearLayout.setOrientation(LinearLayout.VERTICAL);
//		linearLayout.setLayoutParams(layoutParams);
//		
//		linearLayout.addView(drawLayoutViews(0, 2));
//		linearLayout.addView(drawLayoutViews(3, 5));
//		linearLayout.addView(drawLayoutViews(6, 8));
//		linearLayout.addView(drawLayoutViews(9, 11));
//		linearLayout.addView(drawLayoutViews(12, 14));
//		linearLayout.addView(drawLayoutViews(15, 17));
//		linearLayout.addView(drawLayoutViews(18, 20));
//		linearLayout.addView(drawLayoutViews(21, 23));
//		linearLayout.addView(drawLayoutViews(24, 26));
//		linearLayout.addView(drawLayoutViews(27, 29));
//		
//		//mScrollView.addView(linearLayout);
//	}
	
//	private LinearLayout drawLayoutViews(int start,int end){
//		LinearLayout.LayoutParams layoutParams=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT);
//		LinearLayout linearLayout=new LinearLayout(this);
//		linearLayout.setOrientation(LinearLayout.HORIZONTAL);
//		linearLayout.setLayoutParams(layoutParams);
//		for(int i=start;i<=end;i++){
//			TextView textView=drawTextViews();
//			textView.setText(Constant.hotCitys[i]);
//			textView.setTag(i);
//			textView.setOnClickListener(this);
//			linearLayout.addView(textView);
//		}
//		return linearLayout;
//	}
	
//	private TextView drawTextViews(){
//		LinearLayout.LayoutParams textViewParams=new LinearLayout.LayoutParams(0,LinearLayout.LayoutParams.MATCH_PARENT);
//		textViewParams.weight=1;
//		textViewParams.topMargin=20;
//		textViewParams.bottomMargin=20;
//		TextView textView=new TextView(this);
//		textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16.0F);
//		textView.setGravity(Gravity.CENTER);
//		textView.setBackgroundResource(R.drawable.list_item_bg);
//		textView.setLayoutParams(textViewParams);
//		return textView;
//	}

	@Override
	public void onClick(View v) {
		if(v.getId()==R.id.back){
			this.finish();
			return;
		}
		int tag=(int) v.getTag();
		String city = null;
		switch (tag) {
		case 0:
			city=Constant.hotCitys[0];
			break;
		case 1:
			city=Constant.hotCitys[1];
			break;
		case 2:
			city=Constant.hotCitys[2];
			break;
		case 3:
			city=Constant.hotCitys[3];
			break;
		case 4:
			city=Constant.hotCitys[4];
			break;
		case 5:
			city=Constant.hotCitys[5];
			break;
		case 6:
			city=Constant.hotCitys[6];
			break;
		case 7:
			city=Constant.hotCitys[7];
			break;
		case 8:
			city=Constant.hotCitys[8];
			break;
		case 9:
			city=Constant.hotCitys[9];
			break;
		case 10:
			city=Constant.hotCitys[10];
			break;
		case 11:
			city=Constant.hotCitys[11];
			break;
		case 12:
			city=Constant.hotCitys[12];
			break;
		case 13:
			city=Constant.hotCitys[13];
			break;
		case 14:
			city=Constant.hotCitys[14];
			break;
		case 15:
			city=Constant.hotCitys[15];
			break;
		case 16:
			city=Constant.hotCitys[16];
			break;
		case 17:
			city=Constant.hotCitys[17];
			break;
		case 18:
			city=Constant.hotCitys[18];
			break;
		case 19:
			city=Constant.hotCitys[19];
			break;
		case 20:
			city=Constant.hotCitys[20];
			break;
		case 21:
			city=Constant.hotCitys[21];
			break;
		case 22:
			city=Constant.hotCitys[22];
			break;
		case 23:
			city=Constant.hotCitys[23];
			break;
		case 24:
			city=Constant.hotCitys[24];
			break;
		case 25:
			city=Constant.hotCitys[25];
			break;
		case 26:
			city=Constant.hotCitys[26];
			break;
		case 27:
			city=Constant.hotCitys[27];
			break;
		case 28:
			city=Constant.hotCitys[28];
			break;
		case 29:
			city=Constant.hotCitys[29];
			break;
		default:
			break;
		}
		intent.putExtra("city", city);
		setResult(tagResult, intent);
		this.finish();
	}

	@Override
	public void beforeTextChanged(CharSequence s, int start, int count,
			int after) {
		
	}

	@Override
	public void onTextChanged(CharSequence s, int start, int before,int count) {
		String searchText = searchStationTv.getText().toString();
		if (!TextUtils.isEmpty(searchText)) {
			search(searchText);
		}else{
			mListView.setVisibility(View.GONE);
            search_station_layout.setVisibility(View.VISIBLE);
		}
	}

	@Override
	public void afterTextChanged(Editable s) {
		
	}
	
	private void search(String query){
		SearchCallback searchCallback=new SearchCallback() {
			
			@Override
			public void onSearchResult(String query, long hits, final List<String> result) {
				handler.post(new Runnable() {
					
					@Override
					public void run() {
						resultAdapter.setItems(result);
	                    resultAdapter.notifyDataSetChanged();
	                    mListView.setAdapter(resultAdapter);
	                    mListView.smoothScrollToPosition(0);
	                    mListView.setVisibility(View.VISIBLE);
	                    search_station_layout.setVisibility(View.GONE);
					}
				});
			}
		};
		searchService.query(query, MAX_HITS, true, searchCallback);
	}
	
	private class ResultAdapter extends BaseAdapter{

		private List<String> items = new ArrayList<String>();
		
		public synchronized void setItems(List<String> items) {
            this.items = items;
        }
		
		@Override
		public synchronized int getCount() {
			return items.size();
		}

		@Override
		public Object getItem(int position) {
			return items.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View rowView = convertView;
			if (rowView == null) {
                rowView = LayoutInflater.from(getBaseContext()).inflate(R.layout.activity_list_item, parent, false);
                ViewHolder viewHolder = new ViewHolder();
                viewHolder.name = (TextView) rowView.findViewById(R.id.list_item_textview);
                rowView.setTag(viewHolder);
            }
			ViewHolder holder = (ViewHolder) rowView.getTag();
            String searchRes = items.get(position);
            holder.name.setText(searchRes);
			return rowView;
		}
		
	}
	
	private static class ViewHolder {
        public TextView name;
    }

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,long id) {
		String result=((TextView)view.findViewById(R.id.list_item_textview)).getText().toString();
		intent.putExtra("city", result);
		setResult(tagResult, intent);
		this.finish();
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		mInputMethodManager.hideSoftInputFromWindow(searchStationTv.getWindowToken(), 0);
		return false;
	}
}
