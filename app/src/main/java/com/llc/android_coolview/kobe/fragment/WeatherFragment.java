package com.llc.android_coolview.kobe.fragment;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.location.LocationManagerProxy;
import com.amap.api.location.LocationProviderProxy;
import com.google.gson.reflect.TypeToken;
import com.llc.android_coolview.R;
import com.llc.android_coolview.kobe.activity.KobeActivity;
import com.llc.android_coolview.kobe.activity.KobeWeatherSelectCityActivity;
import com.llc.android_coolview.kobe.bean.aqi.AQI;
import com.llc.android_coolview.kobe.bean.aqi.AQIResult;
import com.llc.android_coolview.kobe.bean.city.City;
import com.llc.android_coolview.kobe.bean.city.CityList;
import com.llc.android_coolview.kobe.bean.weather.WeatherByCityName;
import com.llc.android_coolview.kobe.control.WeatherWebServiceUtils;
import com.llc.android_coolview.kobe.impl.WeatherMenuOnClickListener;
import com.llc.android_coolview.kobe.impl.dao.WeatherMenuShow;
import com.llc.android_coolview.util.DateUtils;
import com.llc.android_coolview.util.JSONUtil;
import com.llc.android_coolview.util.PreferenceUtil;
import com.llc.android_coolview.util.ProgressDialogUtils;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import cn.sharesdk.onekeyshare.OnekeyShare;

public class WeatherFragment extends Fragment implements OnClickListener, WeatherMenuOnClickListener {

	public KobeActivity mKobeActivity;

	private View view;
	private WeatherMenuShow menuShow;
	private TextView mTitle;
	private ProgressDialogUtils dialog;
	private String city;

	private TextView todayHighLowTemp;
	private TextView todayTempWeather;
	private ImageView todayPicWeather;

	private TextView tommorrowHighLowTemp;
	private TextView tommorrowTempWeather;
	private ImageView tommorrowPicWeather;

	private TextView currentTempWeather;
	private ImageView currentTempLeft, currentTempRight;

	private ViewStub locationViewSub;
	private ViewStub cityNameViewSub;
	private ViewStub cityDetailsViewSub;

	private EditText inputCityName;
	private Button search;

	private CityList cityList;
	private PreferenceUtil sp;

	private TextView aqiQuality,todayAirQuality,tomAirQuality;

	private TextView titleDate,updateDate;

	private static final String TAG="WeatherFragment";
	// amap
	private LocationManagerProxy aMapManager;

	private ImageView share,selectCity;
	private String shareImgPath;

	private String cityValue;

	private boolean isOnCreate=false,isOnResume=false,isUsed=false;

	private final Handler handler = new Handler() {

		@SuppressWarnings("unchecked")
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			dialog.dismissProgressDialog();
			switch (msg.what) {
				case 1:
					HashMap<String, Object> items=new HashMap<String, Object>();
					items=(HashMap<String, Object>) msg.obj;
					if(items!=null&&items.size()>0){
						//获取城市
						city = String.valueOf(items.get("City"));
						//获取区县
						String point=String.valueOf(items.get("Pinpoint"));
						mTitle.setText(city+point);
						mTitle.setVisibility(View.VISIBLE);
						titleDate.setText(String.valueOf(items.get("Date")));
						getWeather(city.substring(0, 2));
						stopAmap();
					}else{
						Toast.makeText(getActivity(), "定位失败，未获取到当前城市信息！", Toast.LENGTH_SHORT).show();
					}
					break;
				case 2:
					String weatherResult = (String) msg.obj;
					if(weatherResult!=null){
						if(weatherResult.equals("error")){
							Toast.makeText(getActivity(), weatherResult, Toast.LENGTH_SHORT).show();
							return;
						}
						if(locationViewSub==null){
							locationViewSub=(ViewStub) view.findViewById(R.id.search_by_location);
							locationViewSub.inflate();
						}else {
							locationViewSub.setVisibility(View.VISIBLE);
						}
						cityNameViewSub.setVisibility(View.GONE);
						cityDetailsViewSub.setVisibility(View.GONE);
						WeatherByCityName weatherByCityName=WeatherByCityName.getWeatherResultList(weatherResult);
						if(weatherByCityName!=null){
							if(weatherByCityName.getResultcode().equals("200")||weatherByCityName.getReason().equals("successed!")){
								showWeather(weatherByCityName);
								sp=new PreferenceUtil(getActivity(),"selectcitylist");
								TypeToken<List<com.llc.android_coolview.kobe.bean.weather.CityList>> token=new TypeToken<List<com.llc.android_coolview.kobe.bean.weather.CityList>>(){};
								ArrayList<com.llc.android_coolview.kobe.bean.weather.CityList> list=(ArrayList<com.llc.android_coolview.kobe.bean.weather.CityList>) sp.getBean("SelectCityList", token);
								if(list==null||list.size()<0){
									list=new ArrayList<com.llc.android_coolview.kobe.bean.weather.CityList>();
								}
								for(int i=0;i<list.size();i++){
									if(list.get(i).getCity().equals(city)){
										continue;
									}else{
										String temperture=weatherByCityName.getResult().getToday().getTemperature();
										com.llc.android_coolview.kobe.bean.weather.CityList mCityList=new com.llc.android_coolview.kobe.bean.weather.CityList();
										mCityList.setCity(city);
										mCityList.setTemperture(temperture);
										list.add(mCityList);
										sp.setBeanPreference("SelectCityList", list);
										break;
									}
								}

							}else{
								Toast.makeText(getActivity(), "error:"+weatherByCityName.getReason(), Toast.LENGTH_SHORT).show();
							}
						}else{
							Toast.makeText(getActivity(), "服务器返回数据json解析失败", Toast.LENGTH_SHORT).show();
						}
					}else {
						Toast.makeText(getActivity(), "网络不给力！", Toast.LENGTH_SHORT).show();
					}
					break;
				case 3:
					String cityResult=(String) msg.obj;
					cityList=JSONUtil.fromJson(cityResult, CityList.class);
					// 将城市集合存入缓存中
					if(null!=cityList){
						sp=new PreferenceUtil(getActivity(),"CityList");
						sp.setBeanPreference("cityList", cityList);
					}
					break;
				case 4:
					String aqiResult=(String) msg.obj;
					if(aqiResult!=null){
						List<AQIResult> list=AQI.getJsonResult(aqiResult);
						if(list!=null&&list.size()>0){
							showAQI(list);
						}else{
							Toast.makeText(getActivity(), "暂未查到相关空气质量数据，请稍候查询，只为您展示当前天气状况！", Toast.LENGTH_SHORT).show();
						}
					}else{
						Toast.makeText(getActivity(), "暂未查到相关空气质量数据，请稍候查询，只为您展示当前天气状况！", Toast.LENGTH_SHORT).show();
					}
					break;
				default:
					break;
			}
		}

	};



	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.activity_kobe_fragment_weather, null);
		initViews();
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
		if(isUsed&&isOnCreate&&isOnResume){
			initDatas();
		}
		isOnResume=true;

	}

	public String getCityValue() {
		return cityValue;
	}

	public void setCityValue(String cityValue) {
		this.cityValue = cityValue;
	}

	private void initViews() {
		dialog = new ProgressDialogUtils(getActivity());
		cityNameViewSub=(ViewStub) view.findViewById(R.id.search_by_city_name);
		cityDetailsViewSub=(ViewStub) view.findViewById(R.id.search_by_city_details);
		locationViewSub=(ViewStub) view.findViewById(R.id.search_by_location);
		locationViewSub.inflate();
		View viewSubLocation=view.findViewById(R.id.activity_kobe_fragment_weather_location);

		mTitle = (TextView) viewSubLocation.findViewById(R.id.title);

		todayHighLowTemp=(TextView) viewSubLocation.findViewById(R.id.high_low_temp);
		todayTempWeather=(TextView) viewSubLocation.findViewById(R.id.temp_weather);
		todayPicWeather=(ImageView) viewSubLocation.findViewById(R.id.pic_weather);

		tommorrowHighLowTemp=(TextView) viewSubLocation.findViewById(R.id.tommorrow_high_low_temp);
		tommorrowTempWeather=(TextView) viewSubLocation.findViewById(R.id.tommorrow_temp_weather);
		tommorrowPicWeather=(ImageView) viewSubLocation.findViewById(R.id.tommorrow_pic_weather);

		currentTempWeather=(TextView) viewSubLocation.findViewById(R.id.current_temp_weather);
		currentTempLeft=(ImageView) viewSubLocation.findViewById(R.id.current_temp_left);
		currentTempRight=(ImageView) viewSubLocation.findViewById(R.id.current_temp_right);

		aqiQuality=(TextView) viewSubLocation.findViewById(R.id.aqi_text);
		todayAirQuality=(TextView) viewSubLocation.findViewById(R.id.today_air_quality);
		tomAirQuality=(TextView) viewSubLocation.findViewById(R.id.tommorrow_air_quality);

		titleDate=(TextView) viewSubLocation.findViewById(R.id.title_date);
		updateDate=(TextView) viewSubLocation.findViewById(R.id.update_date);

		share=(ImageView)viewSubLocation.findViewById(R.id.title_share);
		share.setOnClickListener(this);
		selectCity=(ImageView) viewSubLocation.findViewById(R.id.select_city);
		selectCity.setOnClickListener(this);
	}

	private void initDatas() {
		shareImgPath=Environment.getExternalStorageDirectory().getAbsolutePath()+"/DCIM/Screenshots/1.png";
		menuShow = new WeatherMenuShow(mKobeActivity);
		menuShow.setWeatherMenuOnClickListener(this);
		sp=new PreferenceUtil(getActivity(),"CityList");
		cityList=sp.getBean("cityList", CityList.class);
		if(cityList==null){
			getCityList();
		}
		if(getCityValue()!=null){
			city=getCityValue();
			mTitle.setText(city);
			mTitle.setVisibility(View.VISIBLE);
			getWeather(city.substring(0,2));
		}else{
			getLocation();
		}
	}

	private void getLocation(){
		new Thread(){
			@Override
			public void run() {
				super.run();
				Looper.prepare();
				startAmap();
				Looper.loop();
			}

		}.start();
	}

	private void startAmap(){
		// amap
		aMapManager = LocationManagerProxy.getInstance(getActivity());
		/*
		 * mAMapLocManager.setGpsEnable(false);
		 * 1.0.2版本新增方法，设置true表示混合定位中包含gps定位，false表示纯网络定位，默认是true Location
		 * API定位采用GPS和网络混合定位方式
		 * ，第一个参数是定位provider，第二个参数时间最短是2000毫秒，第三个参数距离间隔单位是米，第四个参数是定位监听者
		 */
		aMapManager.requestLocationUpdates(LocationProviderProxy.AMapNetwork,2000, 10, mAMapLocationListener);
	}

	@SuppressWarnings("deprecation")
	private void stopAmap(){
		if (aMapManager != null) {
			aMapManager.removeUpdates(mAMapLocationListener);
			aMapManager.destory();
		}
		aMapManager = null;
	}

	private final AMapLocationListener mAMapLocationListener = new AMapLocationListener() {

		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {

		}

		@Override
		public void onProviderEnabled(String provider) {

		}

		@Override
		public void onProviderDisabled(String provider) {

		}

		@Override
		public void onLocationChanged(Location location) {

		}

		@SuppressWarnings("deprecation")
		@Override
		public void onLocationChanged(AMapLocation location) {
			if (location != null) {
				Double geoLat = location.getLatitude();
				Double geoLng = location.getLongitude();
				String cityCode = "";
				String desc = "";
				Bundle locBundle = location.getExtras();
				if (locBundle != null) {
					cityCode = locBundle.getString("citycode");
					desc = locBundle.getString("desc");
				}
				String str = ("定位成功:(" + geoLng + "," + geoLat + ")"
						+ "\n精    度    :" + location.getAccuracy() + "米"
						+ "\n定位方式:" + location.getProvider() + "\n定位时间:"
						+ new Date(location.getTime()).toLocaleString() + "\n城市编码:"
						+ cityCode + "\n位置描述:" + desc + "\n省:"
						+ location.getProvince() + "\n市:" + location.getCity()
						+ "\n区(县):" + location.getDistrict() + "\n区域编码:" + location
						.getAdCode());
				Log.d("LocationUtil", "高德地图："+str);
				HashMap<String, Object> items=new HashMap<String, Object>();
				items.put("City", location.getCity());
				items.put("Pinpoint", location.getDistrict());
				items.put("Date", DateUtils.getCurrentDay("MM月dd日"));
				Log.d("WeatherFragment", "区(县):"+location.getDistrict());
				Message msg=new Message();
				msg.what=1;
				msg.obj=items;
				handler.sendMessage(msg);
			}
		}
	};

	private void getCityList(){
		new Thread(){
			@Override
			public void run() {
				super.run();
				String result=WeatherWebServiceUtils.getWeatherCityList();
				Message msg=new Message();
				msg.obj=result;
				msg.what=3;
				handler.sendMessage(msg);
			}

		}.start();
	}

	private void getWeather(final String cityName){
		dialog.showProgressDialog("正在查询，请稍候...");
		new Thread(){
			@Override
			public void run() {
				super.run();
				Message msg = new Message();
				String result = WeatherWebServiceUtils.getWeatherByCityName(cityName);
				msg.obj = result;
				msg.what = 2;
				handler.sendMessage(msg);
			}

		}.start();
		new Thread(){

			@Override
			public void run() {
				super.run();
				Message msg=new Message();
				String result=WeatherWebServiceUtils.getAQIByCityName(cityName);
				msg.obj=result;
				msg.what=4;
				handler.sendMessage(msg);
			}

		}.start();
	}

	private void showWeather(WeatherByCityName weatherByCityName) {

		//当前
		currentTempWeather.setText(weatherByCityName.getResult().getToday().getWeather());
		if(weatherByCityName.getResult().getSk().getTemp().length()==1){
			currentTempLeft.setImageResource(parseTempLeft("5"));
			currentTempRight.setImageResource(parseTempCenter(weatherByCityName.getResult().getSk().getTemp()));
		}else{
			currentTempLeft.setImageResource(parseTempLeft(weatherByCityName.getResult().getSk().getTemp()));
			currentTempRight.setImageResource(parseTempRight(weatherByCityName.getResult().getSk().getTemp()));
		}

		//今天
		todayHighLowTemp.setText(weatherByCityName.getResult().getToday().getTemperature());
		todayTempWeather.setText(weatherByCityName.getResult().getToday().getWeather());
		todayPicWeather.setImageResource(parseIcon(weatherByCityName.getResult().getToday().getWeather_id().getFa()));

		//明天
		tommorrowHighLowTemp.setText(weatherByCityName.getResult().getFuture().getTommorrow().getTemperature());
		tommorrowTempWeather.setText(weatherByCityName.getResult().getFuture().getTommorrow().getWeather());
		tommorrowPicWeather.setImageResource(parseIcon(weatherByCityName.getResult().getFuture().getTommorrow().getWeather_id().getFa()));

		updateDate.setText(DateUtils.getCurrentDay("今天"+"HH:mm"+"发布"));
	}

	private void showAQI(List<AQIResult> list){
		AQIResult aqiResult=list.get(0);
		aqiQuality.setText(aqiResult.getCitynow().getAQI()+" "+aqiResult.getCitynow().getQuality());
		setTodadyOrTomAQI(todayAirQuality, aqiResult.getCitynow().getQuality());
		setTodadyOrTomAQI(tomAirQuality, aqiResult.getLastTwoWeeks().getAQITwo());
	}

	private void setTodadyOrTomAQI(TextView textView,String quality){
		switch (quality) {
			case "优":
				textView.setText(quality);
				textView.setBackgroundResource(R.drawable.aqi_main_best);
				break;
			case "良":
				textView.setText(quality);
				textView.setBackgroundResource(R.drawable.aqi_main_good);
				break;
			case "轻度污染":
				textView.setText(quality);
				textView.setBackgroundResource(R.drawable.aqi_main_mild);
				break;
			case "中度污染":
				textView.setText(quality);
				textView.setBackgroundResource(R.drawable.aqi_main_moderate);
				break;
			case "重度污染":
				textView.setText(quality);
				textView.setBackgroundResource(R.drawable.aqi_main_other);
				break;
			default:
				break;
		}
	}

	// 解析十位的度数
	private static int parseTempLeft(String strIcon){
		String left=strIcon.substring(0,1);
		Log.d(TAG, "left:"+left);
		switch(left){
			case "0":
				return R.drawable.org4_widget_nw0;
			case "1":
				return R.drawable.org4_widget_nw1;
			case "2":
				return R.drawable.org4_widget_nw2;
			case "3":
				return R.drawable.org4_widget_nw3;
			case "4":
				return R.drawable.org4_widget_nw4;
			default :
				return R.drawable.org4_widget_nw0;
		}
	}

	private static int parseTempRight(String strIcon){
		String right=strIcon.substring(1);
		switch (right) {
			case "0":
				return R.drawable.org4_widget_nw0;
			case "1":
				return R.drawable.org4_widget_nw1;
			case "2":
				return R.drawable.org4_widget_nw2;
			case "3":
				return R.drawable.org4_widget_nw3;
			case "4":
				return R.drawable.org4_widget_nw4;
			case "5":
				return R.drawable.org4_widget_nw5;
			case "6":
				return R.drawable.org4_widget_nw6;
			case "7":
				return R.drawable.org4_widget_nw7;
			case "8":
				return R.drawable.org4_widget_nw8;
			case "9":
				return R.drawable.org4_widget_nw9;
			default:
				return R.drawable.org4_widget_nw0;
		}
	}

	private static int parseTempCenter(String strIcon){
		switch (strIcon) {
			case "0":
				return R.drawable.org4_widget_nw0;
			case "1":
				return R.drawable.org4_widget_nw1;
			case "2":
				return R.drawable.org4_widget_nw2;
			case "3":
				return R.drawable.org4_widget_nw3;
			case "4":
				return R.drawable.org4_widget_nw4;
			case "5":
				return R.drawable.org4_widget_nw5;
			case "6":
				return R.drawable.org4_widget_nw6;
			case "7":
				return R.drawable.org4_widget_nw7;
			case "8":
				return R.drawable.org4_widget_nw8;
			case "9":
				return R.drawable.org4_widget_nw9;
			default:
				return R.drawable.org4_widget_nw0;
		}
	}

	// 该方法负责把返回的天气图标字符串，转换为程序的图片资源ID。
	private static int parseIcon(String strIcon) {
		if (strIcon == null) {
			return -1;
		}
		if ("00".equals(strIcon)) {
			return R.drawable.ww0;
		}
		if ("01".equals(strIcon)) {
			return R.drawable.ww1;
		}
		if ("02".equals(strIcon)) {
			return R.drawable.ww2;
		}
		if ("03".equals(strIcon)) {
			return R.drawable.ww3;
		}
		if ("04".equals(strIcon)) {
			return R.drawable.ww4;
		}
		if ("05".equals(strIcon)) {
			return R.drawable.ww5;
		}
		if ("06".equals(strIcon)) {
			return R.drawable.ww6;
		}
		if ("07".equals(strIcon)) {
			return R.drawable.ww7;
		}
		if ("08".equals(strIcon)) {
			return R.drawable.ww8;
		}
		if ("09".equals(strIcon)) {
			return R.drawable.ww9;
		}
		if ("10".equals(strIcon)) {
			return R.drawable.ww10;
		}
		if ("13".equals(strIcon)) {
			return R.drawable.ww13;
		}
		if ("14".equals(strIcon)) {
			return R.drawable.ww14;
		}
		if ("15".equals(strIcon)) {
			return R.drawable.ww15;
		}
		if ("16".equals(strIcon)) {
			return R.drawable.ww16;
		}
		if ("17".equals(strIcon)) {
			return R.drawable.ww17;
		}
		if ("18".equals(strIcon)) {
			return R.drawable.ww18;
		}
		if ("19".equals(strIcon)) {
			return R.drawable.ww19;
		}
		if ("20".equals(strIcon)) {
			return R.drawable.ww20;
		}
		if ("22".equals(strIcon)) {
			return R.drawable.ww32;
		}
		if ("23".equals(strIcon)) {
			return R.drawable.ww33;
		}
		if ("24".equals(strIcon)) {
			return R.drawable.ww34;
		}
		if ("25".equals(strIcon)) {
			return R.drawable.ww35;
		}
		if ("26".equals(strIcon)) {
			return R.drawable.ww36;
		}
		if ("27".equals(strIcon)) {
			return R.drawable.ww45;
		}
		if ("29".equals(strIcon)) {
			return R.drawable.ww29;
		}
		if ("30".equals(strIcon)) {
			return R.drawable.ww30;
		}
		if ("31".equals(strIcon)) {
			return R.drawable.ww31;
		}
		return 0;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.search_city_name:
				String cityName=inputCityName.getText().toString().trim();
				if(!TextUtils.isEmpty(cityName)){
					//判断输入的城市是否在所查询范围、
					sp=new PreferenceUtil(getActivity(),"CityList");
					cityList=sp.getBean("cityList", CityList.class);
					boolean flag=false;
					for(City city : cityList.getResult()){
						if(city.getCity().equals(cityName)){
							flag=true;
							break;
						}else{
							flag=false;
						}
					}
					if (flag) {
						getWeather(cityName);
					}else {
						Toast.makeText(getActivity(), "您输入的城市"+cityName+"暂时不支持查询！", Toast.LENGTH_SHORT).show();
					}
				}else {
					Toast.makeText(getActivity(), "城市不能为空", Toast.LENGTH_SHORT).show();
				}
				break;
			case R.id.title_share:
				showShare();
				break;
			case R.id.select_city:
				Intent intent=new Intent(getActivity(),KobeWeatherSelectCityActivity.class);
				startActivity(intent);
				break;
			default:
				break;
		}
	}

	@Override
	public void onWeatherMenuClick(int id) {
		switch (id) {
			case R.id.weather_location:
				//定位获取
				if (locationViewSub==null) {
					locationViewSub=(ViewStub) view.findViewById(R.id.search_by_location);
					locationViewSub.inflate();
				}else {
					locationViewSub.setVisibility(View.VISIBLE);
				}
				cityNameViewSub.setVisibility(View.GONE);
				cityDetailsViewSub.setVisibility(View.GONE);
				break;
			case R.id.weather_city:
				//通过输入城市名称查询相关天气情况
				if(cityNameViewSub==null){
					cityNameViewSub=(ViewStub) view.findViewById(R.id.search_by_city_name);
					cityNameViewSub.inflate();
				}else {
					cityNameViewSub.setVisibility(View.VISIBLE);
				}
				locationViewSub.setVisibility(View.GONE);
				cityDetailsViewSub.setVisibility(View.GONE);
				View viewStubCityName=view.findViewById(R.id.activity_kobe_fragment_weather_city_name);
				inputCityName=(EditText) viewStubCityName.findViewById(R.id.input_city_name);
				search=(Button) viewStubCityName.findViewById(R.id.search_city_name);
				search.setOnClickListener(this);
				break;
			case R.id.weather_city_detail:
				//城市详情
				if(cityDetailsViewSub==null){
					cityDetailsViewSub=(ViewStub) view.findViewById(R.id.search_by_city_details);
					cityDetailsViewSub.inflate();
				}else {
					cityDetailsViewSub.setVisibility(View.VISIBLE);
				}
				locationViewSub.setVisibility(View.GONE);
				cityNameViewSub.setVisibility(View.GONE);
				break;
			default:
				break;
		}
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
		stopAmap();
	}

	private void showShare() {
		OnekeyShare oks = new OnekeyShare();
		// 关闭sso授权
		oks.disableSSOWhenAuthorize();
		// 分享时Notification的图标和文字 2.5.9以后的版本不调用此方法
		// oks.setNotification(R.drawable.ic_launcher,getString(R.string.app_name));
		// title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
		oks.setTitle(getString(R.string.share));
		// titleUrl是标题的网络链接，仅在人人网和QQ空间使用
		oks.setTitleUrl("http://sharesdk.cn");
		// text是分享文本，所有平台都需要这个字段
		oks.setText(getString(R.string.share_text));
		// imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
		oks.setImagePath(shareImgPath);// 确保SDcard下面存在此张图片
		// url仅在微信（包括好友和朋友圈）中使用
		oks.setUrl(getString(R.string.share_url));
		// comment是我对这条分享的评论，仅在人人网和QQ空间使用
		oks.setComment(getString(R.string.share_comment));
		// site是分享此内容的网站名称，仅在QQ空间使用
		oks.setSite(getString(R.string.app_name));
		// siteUrl是分享此内容的网站地址，仅在QQ空间使用
		oks.setSiteUrl("http://sharesdk.cn");
		// 启动分享GUI
		oks.show(getActivity());
	}
}
