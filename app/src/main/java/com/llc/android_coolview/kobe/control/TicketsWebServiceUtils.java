package com.llc.android_coolview.kobe.control;


import java.io.UnsupportedEncodingException;

import com.llc.android_coolview.kobe.bean.tickets.DoubleStation;
import com.llc.android_coolview.kobe.bean.tickets.TrainCodeDeatils;
import com.llc.android_coolview.kobe.bean.tickets.ValidVotes;
import com.llc.android_coolview.util.HttpClientHelper;
import com.llc.android_coolview.util.JSONUtil;

public class TicketsWebServiceUtils {

	public static DoubleStation getSearchByStationNameResult(String start,String end){
		HttpClientHelper httpClientHelper=new HttpClientHelper();
		try {
			String url = "http://apis.juhe.cn/train/s2swithprice?"+"start="+java.net.URLEncoder.encode(start,"utf-8")+"&end="+java.net.URLEncoder.encode(end,"utf-8")+"&dtype=&key=9a3150e47022e6cbb217909ef962d307";
			String result=httpClientHelper.readHtml(url);
			DoubleStation mDoubleStation=JSONUtil.fromJson(result, DoubleStation.class);
			return mDoubleStation;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static ValidVotes getValidVotesByStationNameResult(String start,String end,String date,String tt){
		HttpClientHelper httpClientHelper=new HttpClientHelper();
		try {
			String url="http://apis.juhe.cn/train/yp?"+"from="+java.net.URLEncoder.encode(start,"utf-8")+"&to="+java.net.URLEncoder.encode(end,"utf-8")+"&date="+date+"&tt="+tt+"&key=9a3150e47022e6cbb217909ef962d307";
			String result=httpClientHelper.readHtml(url);
			ValidVotes validVotes=JSONUtil.fromJson(result, ValidVotes.class);
			return validVotes;
		} catch (Exception e) {
			
		}
		return null;
	}
	
	public TrainCodeDeatils getSearchByTrainCodeResult(){
		HttpClientHelper httpClientHelper=new HttpClientHelper();
		String url="http://apis.juhe.cn/train/s";
		String result=httpClientHelper.readHtml(url);
		TrainCodeDeatils mTrainCodeDeatils=JSONUtil.fromJson(result, TrainCodeDeatils.class);
		return mTrainCodeDeatils;
	}
}
