package com.llc.android_coolview.kobe.control;

import java.io.UnsupportedEncodingException;

import com.llc.android_coolview.util.HttpClientHelper;

public class WeatherWebServiceUtils {

	public static String getWeatherByCityName(String cityName){
		HttpClientHelper http = new HttpClientHelper();
		try {
			String url="http://v.juhe.cn/weather/index?"+"cityname="+java.net.URLEncoder.encode(cityName,"utf-8")+"&dtype=&format=&key=44c08b0a9338793874acac10a79bc176";
			String result=http.readHtml(url);
			return result;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static String getWeatherCityList(){
		HttpClientHelper http=new HttpClientHelper();
		http.addParam("key", "44c08b0a9338793874acac10a79bc176");
		http.addParam("dtype", "json");
		String result=http.readHtml("http://v.juhe.cn/weather/citys");
		return result;
	}
	
	public static String getAQIByCityName(String cityName){
		HttpClientHelper http = new HttpClientHelper();
		String url="http://web.juhe.cn:8080/environment/air/cityair?"+"city="+cityName+"&dtype=&format=&key=fa0f303c61719eb3baff7161b47890ae";
		String result=http.readHtml(url);
		return result;
	}
}
