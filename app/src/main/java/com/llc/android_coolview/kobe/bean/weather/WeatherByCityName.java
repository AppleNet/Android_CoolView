package com.llc.android_coolview.kobe.bean.weather;

import org.json.JSONException;
import org.json.JSONObject;

import com.llc.android_coolview.util.DateUtils;

public class WeatherByCityName {

	private String resultcode;
	private String reason;
	private WeatherResult result;
	private String error_code;
	public String getResultcode() {
		return resultcode;
	}
	public void setResultcode(String resultcode) {
		this.resultcode = resultcode;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public WeatherResult getResult() {
		return result;
	}
	public void setResult(WeatherResult result) {
		this.result = result;
	}
	public String getError_code() {
		return error_code;
	}
	public void setError_code(String error_code) {
		this.error_code = error_code;
	}
	
	
	public static WeatherByCityName getWeatherResultList(String json){
		try {
			
			WeatherByCityName weatherByCityName=new WeatherByCityName();
			JSONObject jsonObject=new JSONObject(json);
				String resultcode=jsonObject.getString("resultcode");
				String reason=jsonObject.getString("reason");
				String error_code=jsonObject.getString("error_code");
				JSONObject result=jsonObject.getJSONObject("result");
			
			WeatherResult weatherResult=new WeatherResult();
			JSONObject sk=result.getJSONObject("sk");
				String temp=sk.getString("temp");
				String wind_direction=sk.getString("wind_direction");
				String wind_strength=sk.getString("wind_strength");
				String humidity=sk.getString("humidity");
				String time=sk.getString("time");
			SK mSK=new SK();
			   mSK.setTemp(temp);
			   mSK.setWind_direction(wind_direction);
			   mSK.setWind_strength(wind_strength);
			   mSK.setHumidity(humidity);
			   mSK.setTime(time);
			   
			JSONObject today=result.getJSONObject("today");
				String temperature=today.getString("temperature");
				String weather=today.getString("weather");
				JSONObject weather_id=today.getJSONObject("weather_id");
					String fa=weather_id.getString("fa");
					String fb=weather_id.getString("fb");
				String wind=today.getString("wind");
				String week=today.getString("week");
				String city=today.getString("city");
				String date_y=today.getString("date_y");
				String dressing_index=today.getString("dressing_index");
				String dressing_advice=today.getString("dressing_advice");
				String uv_index=today.getString("uv_index");
				String comfort_index=today.getString("comfort_index");
				String wash_index=today.getString("wash_index");
				String travel_index=today.getString("travel_index");
				String exercise_index=today.getString("exercise_index");
				String drying_index=today.getString("drying_index");
			Today mToday=new Today();
				mToday.setTemperature(temperature);
				mToday.setWeather(weather);
				WeatherId mId=new WeatherId();
					mId.setFa(fa);
					mId.setFb(fb);
				mToday.setWeather_id(mId);
				mToday.setWind(wind);
				mToday.setWeek(week);
				mToday.setCity(city);
				mToday.setDate_y(date_y);
				mToday.setDressing_index(dressing_index);
				mToday.setDressing_advice(dressing_advice);
				mToday.setUv_index(uv_index);
				mToday.setComfort_index(comfort_index);
				mToday.setWash_index(wash_index);
				mToday.setTravel_index(travel_index);
				mToday.setExercise_index(exercise_index);
				mToday.setDrying_index(drying_index);
				
			JSONObject future=result.getJSONObject("future");
			Future mFuture=new Future();
			JSONObject current=future.getJSONObject(DateUtils.getAfterDay(0));
				String currentTemperature=current.getString("temperature");
				String currentWeather=current.getString("weather");
				JSONObject currentWeatherId=current.getJSONObject("weather_id");
					String currentFa=currentWeatherId.getString("fa");
					String curentFb=currentWeatherId.getString("fb");
				String currentWind=current.getString("wind");
				String currentWeek=current.getString("week");
				String currentDate=current.getString("date");
			FutureOne futureOne=new FutureOne();
				futureOne.setTemperature(currentTemperature);
				futureOne.setWeather(currentWeather);
				WeatherId mId2=new WeatherId();
				mId2.setFa(currentFa);
				mId2.setFb(curentFb);
				futureOne.setWeather_id(mId2);
				futureOne.setWind(currentWind);
				futureOne.setWeek(currentWeek);
				futureOne.setDate(currentDate);
			mFuture.setCurrent(futureOne);
			JSONObject tommorrow=future.getJSONObject(DateUtils.getAfterDay(1));
				String tomTemp=tommorrow.getString("temperature");
				String tomWeather=tommorrow.getString("weather");
				JSONObject tomWeatherId=tommorrow.getJSONObject("weather_id");
					String tomFa=tomWeatherId.getString("fa");
					String tomFb=tomWeatherId.getString("fb");
				String tomWind=tommorrow.getString("wind");
				String tomWeek=tommorrow.getString("week");
				String tomDate=tommorrow.getString("date");
			FutureOne futureTwo=new FutureOne();
				futureTwo.setTemperature(tomTemp);
				futureTwo.setWeather(tomWeather);
				WeatherId mId3=new WeatherId();
				mId3.setFa(tomFa);
				mId3.setFb(tomFb);
				futureTwo.setWeather_id(mId3);
				futureTwo.setWeek(tomWeek);
				futureTwo.setWind(tomWind);
				futureTwo.setDate(tomDate);
			mFuture.setTommorrow(futureTwo);
			
			weatherResult.setSk(mSK);
			weatherResult.setFuture(mFuture);
			weatherResult.setToday(mToday);
			
			weatherByCityName.setError_code(error_code);
			weatherByCityName.setReason(reason);
			weatherByCityName.setResult(weatherResult);
			weatherByCityName.setResultcode(resultcode);
			return weatherByCityName;
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
}
