package com.llc.android_coolview.paul.control;

import com.llc.android_coolview.paul.bean.PaulParams;
import com.llc.android_coolview.util.HttpClientHelper;

public class PaulManager {

	
	public static String getTeamEventQuery(PaulParams params){
		HttpClientHelper http = new HttpClientHelper();
		http.addParam("key", "1da9cfab1ab78c93ffac0d1229b77c57");
		http.addParam("team", params.getTeam());
		http.addParam("dtype", "json");
		String result = http.readHtml("http://op.juhe.cn/onebox/basketball/team");
		return result;
	}
	
	public static String getNbaRegularSeasonScheduleResult(){
		HttpClientHelper http = new HttpClientHelper();
		http.addParam("key", "1da9cfab1ab78c93ffac0d1229b77c57");
		http.addParam("dtype", "json");
		String result = http.readHtml("http://op.juhe.cn/onebox/basketball/nba");
		return result;
	}
	
	public static String getTeamCompetitionScheduleQuery(PaulParams params){
		HttpClientHelper http = new HttpClientHelper();
		http.addParam("key", "1da9cfab1ab78c93ffac0d1229b77c57");
		http.addParam("dtype", "json");
		http.addParam("hteam", params.getHteam());
		http.addParam("vteam", params.getVteam());
		String result = http.readHtml("http://op.juhe.cn/onebox/basketball/combat");
		return result;
	}
}
