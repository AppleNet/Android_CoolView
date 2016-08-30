package com.llc.android_coolview.kobe.bean.weather;

/*当前实况天气*/
public class SK {

	private String temp;/*当前温度*/
	private String wind_direction;/*当前风向*/
	private String wind_strength;/*当前风力*/
	private String humidity;/*当前湿度*/
	private String time;/*更新时间*/
	public String getTemp() {
		return temp;
	}
	public void setTemp(String temp) {
		this.temp = temp;
	}
	public String getWind_direction() {
		return wind_direction;
	}
	public void setWind_direction(String wind_direction) {
		this.wind_direction = wind_direction;
	}
	public String getWind_strength() {
		return wind_strength;
	}
	public void setWind_strength(String wind_strength) {
		this.wind_strength = wind_strength;
	}
	public String getHumidity() {
		return humidity;
	}
	public void setHumidity(String humidity) {
		this.humidity = humidity;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}


}
