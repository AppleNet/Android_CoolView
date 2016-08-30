package com.llc.android_coolview.kobe.bean.tickets;

import java.util.List;

public class Details {

	private String train_no;
	private String m_train_url;
	private String train_type;
	private String start_station;
	private String start_station_type;
	private String end_station;
	private String end_station_type;
	private String start_time;
	private String end_time;
	private String run_time;
	private String run_distance;
	private List<PriceList> price_list;
	private String m_chaxun_url;
	public String getTrain_no() {
		return train_no;
	}
	public void setTrain_no(String train_no) {
		this.train_no = train_no;
	}
	public String getM_train_url() {
		return m_train_url;
	}
	public void setM_train_url(String m_train_url) {
		this.m_train_url = m_train_url;
	}
	public String getTrain_type() {
		return train_type;
	}
	public void setTrain_type(String train_type) {
		this.train_type = train_type;
	}
	public String getStart_staion() {
		return start_station;
	}
	public void setStart_staion(String start_staion) {
		this.start_station = start_staion;
	}
	public String getStart_station_type() {
		return start_station_type;
	}
	public void setStart_station_type(String start_station_type) {
		this.start_station_type = start_station_type;
	}
	public String getEnd_station() {
		return end_station;
	}
	public void setEnd_station(String end_station) {
		this.end_station = end_station;
	}
	public String getEnd_station_type() {
		return end_station_type;
	}
	public void setEnd_station_type(String end_station_type) {
		this.end_station_type = end_station_type;
	}
	public String getStart_time() {
		return start_time;
	}
	public void setStart_time(String start_time) {
		this.start_time = start_time;
	}
	public String getEnd_time() {
		return end_time;
	}
	public void setEnd_time(String end_time) {
		this.end_time = end_time;
	}
	public String getRun_time() {
		return run_time;
	}
	public void setRun_time(String run_time) {
		this.run_time = run_time;
	}
	public String getRun_distance() {
		return run_distance;
	}
	public void setRun_distance(String run_distance) {
		this.run_distance = run_distance;
	}
	public List<PriceList> getPrice_list() {
		return price_list;
	}
	public void setPrice_list(List<PriceList> price_list) {
		this.price_list = price_list;
	}
	public String getM_chaxun_url() {
		return m_chaxun_url;
	}
	public void setM_chaxun_url(String m_chaxun_url) {
		this.m_chaxun_url = m_chaxun_url;
	}
	
	
}
