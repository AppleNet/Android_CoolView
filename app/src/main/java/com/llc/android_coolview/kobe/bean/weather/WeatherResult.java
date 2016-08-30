package com.llc.android_coolview.kobe.bean.weather;


public class WeatherResult {

	private SK sk;/*当前实况天气*/
	private Today today;
	private Future future;
	public SK getSk() {
		return sk;
	}
	public void setSk(SK sk) {
		this.sk = sk;
	}
	public Today getToday() {
		return today;
	}
	public void setToday(Today today) {
		this.today = today;
	}
	public Future getFuture() {
		return future;
	}
	public void setFuture(Future future) {
		this.future = future;
	}
	
	
}
