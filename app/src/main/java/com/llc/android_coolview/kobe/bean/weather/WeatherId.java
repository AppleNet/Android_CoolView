package com.llc.android_coolview.kobe.bean.weather;

public class WeatherId {

	private String fa;/*天气标识00：晴*/
	private String fb;/*天气标识53：霾 如果fa不等于fb，说明是组合天气*/
	public String getFa() {
		return fa;
	}
	public void setFa(String fa) {
		this.fa = fa;
	}
	public String getFb() {
		return fb;
	}
	public void setFb(String fb) {
		this.fb = fb;
	}
	
	
}
