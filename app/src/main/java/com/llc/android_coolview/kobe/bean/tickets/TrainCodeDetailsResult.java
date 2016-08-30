package com.llc.android_coolview.kobe.bean.tickets;

import java.util.List;

public class TrainCodeDetailsResult {

	private TrainInfo train_info;
	private List<StationList> station_list;
	public TrainInfo getTrain_info() {
		return train_info;
	}
	public void setTrain_info(TrainInfo train_info) {
		this.train_info = train_info;
	}
	public List<StationList> getStation_list() {
		return station_list;
	}
	public void setStation_list(List<StationList> station_list) {
		this.station_list = station_list;
	}
	
}
