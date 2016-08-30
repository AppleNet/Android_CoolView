package com.llc.android_coolview.kobe.activity;

import java.util.List;

import com.google.gson.reflect.TypeToken;
import com.llc.android_coolview.R;
import com.llc.android_coolview.kobe.adapter.KobeSelectCityAdapter;
import com.llc.android_coolview.kobe.bean.weather.CityList;
import com.llc.android_coolview.util.PreferenceUtil;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class KobeWeatherSelectCityActivity extends Activity implements OnClickListener, OnItemClickListener {

	private ImageView back,edit,add;
	private ListView mListView;
	private PreferenceUtil preference;
	private List<CityList> cityList;
	private KobeSelectCityAdapter adapter;
	private boolean flag=false;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_kobe_fragment_weather_select_city);
		if(VERSION.SDK_INT>=VERSION_CODES.LOLLIPOP){
			Window window = getWindow();
			window.setStatusBarColor(getResources().getColor(R.color.select_city_title));
		}
		initViews();
		initOnClickListener();
		initData();
	}
	
	private void initViews(){
		back=(ImageView) this.findViewById(R.id.back);
		edit=(ImageView) this.findViewById(R.id.edit);
		add=(ImageView) this.findViewById(R.id.add);
		mListView=(ListView) this.findViewById(R.id.city_list);
	}
	
	private void initOnClickListener(){
		back.setOnClickListener(this);
		edit.setOnClickListener(this);
		add.setOnClickListener(this);
		mListView.setOnItemClickListener(this);
	}

	private void initData(){
		preference=new PreferenceUtil(this,"selectcitylist");
		TypeToken<List<CityList>> token=new TypeToken<List<CityList>>(){};
		cityList=preference.getBean("SelectCityList", token);
		if(cityList!=null&&cityList.size()>0){
			adapter=new KobeSelectCityAdapter(this, cityList);
			mListView.setAdapter(adapter);
		}
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.back:
			this.finish();
			break;
		case R.id.edit:
			if(!flag){
				edit.setImageDrawable(getResources().getDrawable(R.drawable.kobe_weather_edit_ok));
				flag=true;
			}else {
				edit.setImageDrawable(getResources().getDrawable(R.drawable.kobe_weather_edit));
				flag=false;
			}
			adapter.setCheckBoxVisible(flag);
			break;
		case R.id.add:
			Intent intent=new Intent(KobeWeatherSelectCityActivity.this,KobeWeatherSearchCityActivity.class);
			startActivity(intent);
			this.finish();
			break;
		default:
			break;
		}
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,long id) {
		String value=((TextView)view.findViewById(R.id.cityname)).getText().toString();
		Intent intent=new Intent(KobeWeatherSelectCityActivity.this,KobeActivity.class);
		intent.putExtra("TagID", "KobeWeatherSelectCityActivity");
		intent.putExtra("value", value);
		startActivity(intent);
	}
}
