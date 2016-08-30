package com.llc.android_coolview.paul.bean.the_nba_regular_season_schedule_result;

import java.util.List;

public class Result {

	private String title;
	private StatusList statuslist;
	private List<ListArray> list;
	private List<TeammatchArray> teammatch;
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public StatusList getStatuslist() {
		return statuslist;
	}
	public void setStatuslist(StatusList statuslist) {
		this.statuslist = statuslist;
	}
	public List<ListArray> getList() {
		return list;
	}
	public void setList(List<ListArray> list) {
		this.list = list;
	}
	public List<TeammatchArray> getTeammatch() {
		return teammatch;
	}
	public void setTeammatch(List<TeammatchArray> teammatch) {
		this.teammatch = teammatch;
	}
	
	
}
