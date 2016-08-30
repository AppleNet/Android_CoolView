package com.llc.android_coolview.kobe.bean.aqi;


import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class AQI {

	public static List<AQIResult> getJsonResult(String json){
		try {
			List<AQIResult> list=new ArrayList<>();
			AQIResult aqiResult=new AQIResult();
			CityNow cityNow=new CityNow();
			LastTwoWeeks lastTwoWeeks=new LastTwoWeeks();
			JSONObject jsonObject = new JSONObject(json);
			JSONArray result=jsonObject.getJSONArray("result");
			for(int i=0;i<result.length();i++){
				JSONObject resultObject=result.getJSONObject(i);
				
				JSONObject citynow=resultObject.getJSONObject("citynow");
				String city=citynow.getString("city");
				String AQI=citynow.getString("AQI");
				String quality=citynow.getString("quality");
				String date=citynow.getString("date");
				cityNow.setAQI(AQI);
				cityNow.setCity(city);
				cityNow.setQuality(quality);
				cityNow.setDate(date);
				aqiResult.setCitynow(cityNow);
				
				JSONObject lastTwoWeek=resultObject.getJSONObject("lastTwoWeeks");
				
				JSONObject one=lastTwoWeek.getJSONObject("1");
				String cityOne=one.getString("city");
				String AQIOne=one.getString("quality");
				String qualityOne=one.getString("quality");
				String dateOne=one.getString("date");
				lastTwoWeeks.setCityOne(cityOne);
				lastTwoWeeks.setAQIOne(AQIOne);
				lastTwoWeeks.setQualityOne(qualityOne);
				lastTwoWeeks.setDateOne(dateOne);
				JSONObject two=lastTwoWeek.getJSONObject("2");
				String cityTwo=two.getString("city");
				String AQITwo=two.getString("quality");
				String qualityTwo=two.getString("quality");
				String dateTwo=two.getString("date");
				lastTwoWeeks.setAQITwo(AQITwo);
				lastTwoWeeks.setCityTwo(cityTwo);
				lastTwoWeeks.setDateTwo(dateTwo);
				lastTwoWeeks.setQualityTwo(qualityTwo);
				
				aqiResult.setLastTwoWeeks(lastTwoWeeks);
				list.add(aqiResult);
			}
			return list;
		} catch (JSONException e1) {
			e1.printStackTrace();
		}
		return null;
	}
}
