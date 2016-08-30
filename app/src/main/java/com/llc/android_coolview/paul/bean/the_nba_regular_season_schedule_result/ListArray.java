package com.llc.android_coolview.paul.bean.the_nba_regular_season_schedule_result;

import java.util.List;

public class ListArray {

	private String title;
	private List<Tr> tr;
	private List<BottomLink> bottomlink;
	private List<LiveLink> livelink;
	private List<Live> live;
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public List<Tr> getTr() {
		return tr;
	}
	public void setTr(List<Tr> tr) {
		this.tr = tr;
	}
	public List<BottomLink> getBottomlink() {
		return bottomlink;
	}
	public void setBottomlink(List<BottomLink> bottomlink) {
		this.bottomlink = bottomlink;
	}
	public List<LiveLink> getLivelink() {
		return livelink;
	}
	public void setLivelink(List<LiveLink> livelink) {
		this.livelink = livelink;
	}
	public List<Live> getLive() {
		return live;
	}
	public void setLive(List<Live> live) {
		this.live = live;
	}
	
}
