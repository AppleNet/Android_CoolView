package com.llc.android_coolview.kobe.adapter;

import java.util.List;

import com.llc.android_coolview.R;
import com.llc.android_coolview.kobe.bean.weather.CityList;
import com.llc.android_coolview.util.PreferenceUtil;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.TextView;

public class KobeSelectCityAdapter extends BaseAdapter {

	private Context mContext;
	private List<CityList> cityList;
	private ViewHolder holder;
	private PreferenceUtil preference;
	private boolean flag;
	public KobeSelectCityAdapter(Context mContext, List<CityList> cityList) {
		this.mContext = mContext;
		this.cityList = cityList;
		preference=new PreferenceUtil(mContext, "selectcitylist");
	}

	@Override
	public int getCount() {
		return cityList.size();
	}

	@Override
	public Object getItem(int position) {
		return cityList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		if(convertView==null){
			holder=new ViewHolder();
			convertView=LayoutInflater.from(mContext).inflate(R.layout.activity_kobe_fragment_weather_citylist_item_header, null);
			holder.mCheckBox=(CheckBox) convertView.findViewById(R.id.city_delete);
			holder.weather=(ImageView) convertView.findViewById(R.id.weather);
			holder.location=(ImageView) convertView.findViewById(R.id.location);
			holder.city=(TextView) convertView.findViewById(R.id.cityname);
			holder.tempeture=(TextView) convertView.findViewById(R.id.tempeture);
			holder.delete=(TextView) convertView.findViewById(R.id.delete);
			convertView.setTag(holder);
		}else{
			holder=(ViewHolder) convertView.getTag();
		}
		
		if(position==0){
			holder.location.setVisibility(View.VISIBLE);
		}else{
			holder.location.setVisibility(View.GONE);
		}
		
		String mCity=cityList.get(position).getCity();
		if(mCity!=null){
			holder.city.setText(mCity);
		}
		String temp=cityList.get(position).getTemperture();
		if(temp!=null){
			holder.tempeture.setText(temp);
		}
		
		if(flag){
			holder.mCheckBox.setVisibility(View.VISIBLE);
		}else{
			holder.mCheckBox.setVisibility(View.GONE);
			if(holder.delete.isShown()){
				holder.tempeture.setVisibility(View.VISIBLE);
				holder.delete.setVisibility(View.GONE);
			}
		}
		
		holder.mCheckBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if(isChecked){
					holder.tempeture.setVisibility(View.VISIBLE);
					holder.delete.setVisibility(View.GONE);
				}else{
					holder.tempeture.setVisibility(View.GONE);
					holder.delete.setVisibility(View.VISIBLE);
				}
			}
		});
		
		holder.delete.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				cityList.remove(position);
				preference.setBeanPreference("SelectCityList", cityList);
				KobeSelectCityAdapter.this.notifyDataSetChanged();
			}
		});
		return convertView;
	}

	public void setCheckBoxVisible(boolean flag){
		this.flag=flag;
		this.notifyDataSetChanged();
	}
	
	class ViewHolder{
		CheckBox mCheckBox;
		ImageView weather;
		ImageView location;
		TextView city;
		TextView tempeture;
		TextView delete;
	}
}
