package com.llc.android_coolview.kobe.bean.weather;

public class FutureOne {

	private String temperature;
	private String weather;
	private WeatherId weather_id;
	private String wind;
	private String week;
	private String date;
	public String getTemperature() {
		return temperature;
	}
	public void setTemperature(String temperature) {
		this.temperature = temperature;
	}
	public String getWeather() {
		return weather;
	}
	public void setWeather(String weather) {
		this.weather = weather;
	}
	public WeatherId getWeather_id() {
		return weather_id;
	}
	public void setWeather_id(WeatherId weather_id) {
		this.weather_id = weather_id;
	}
	public String getWind() {
		return wind;
	}
	public void setWind(String wind) {
		this.wind = wind;
	}
	public String getWeek() {
		return week;
	}
	public void setWeek(String week) {
		this.week = week;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
}
