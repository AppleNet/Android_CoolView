package com.llc.android_coolview.paul.bean.teams_event_query;

import java.util.ArrayList;

public class Result {

	private String title;
	private ArrayList<GameList> list;
	private More1 more1;
	private More2 more2;
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public ArrayList<GameList> getList() {
		return list;
	}
	public void setList(ArrayList<GameList> list) {
		this.list = list;
	}
	public More1 getMore1() {
		return more1;
	}
	public void setMore1(More1 more1) {
		this.more1 = more1;
	}
	public More2 getMore2() {
		return more2;
	}
	public void setMore2(More2 more2) {
		this.more2 = more2;
	}
	
	
}
