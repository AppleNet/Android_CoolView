package com.llc.android_coolview.kobe.bean.news;

import java.util.List;

public class ThirdNew extends News{

	private int priority;
	private String photosetID;
	private List<Editor> editor;
	private String videosource;
	private String TAGS;
	private String videoID;
	private List<Imgextra> imgextra;
	
	public List<Imgextra> getImgextra() {
		return imgextra;
	}
	public int getPriority() {
		return priority;
	}
	public void setPriority(int priority) {
		this.priority = priority;
	}
	public void setImgextra(List<Imgextra> imgextra) {
		this.imgextra = imgextra;
	}
	public String getVideosource() {
		return videosource;
	}
	public void setVideosource(String videosource) {
		this.videosource = videosource;
	}
	public String getTAGS() {
		return TAGS;
	}
	public void setTAGS(String tAGS) {
		TAGS = tAGS;
	}
	public String getVideoID() {
		return videoID;
	}
	public void setVideoID(String videoID) {
		this.videoID = videoID;
	}
	public List<Editor> getEditor() {
		return editor;
	}
	public void setEditor(List<Editor> editor) {
		this.editor = editor;
	}
	public String getPhotosetID() {
		return photosetID;
	}
	public void setPhotosetID(String photosetID) {
		this.photosetID = photosetID;
	}
}
