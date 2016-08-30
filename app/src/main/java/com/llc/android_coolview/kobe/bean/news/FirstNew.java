package com.llc.android_coolview.kobe.bean.news;

import java.util.List;

public class FirstNew extends News{

	private String template;
	private String hasCover;
	private String hasHead;
	private String alias;
	private String hasImg;
	private String hasIcon;
	private String cid;
	private String hasAD;
	private String order;
	private List<Imgextra> imgextra;
	private String priority;
	private String ename;
	private String tname;
	private List<Ads> ads;
	private String photosetID;
	public String getTemplate() {
		return template;
	}
	public void setTemplate(String template) {
		this.template = template;
	}
	public String getHasCover() {
		return hasCover;
	}
	public void setHasCover(String hasCover) {
		this.hasCover = hasCover;
	}
	public String getHasHead() {
		return hasHead;
	}
	public void setHasHead(String hasHead) {
		this.hasHead = hasHead;
	}
	public String getAlias() {
		return alias;
	}
	public void setAlias(String alias) {
		this.alias = alias;
	}
	public String getHasImg() {
		return hasImg;
	}
	public void setHasImg(String hasImg) {
		this.hasImg = hasImg;
	}
	public String getHasIcon() {
		return hasIcon;
	}
	public void setHasIcon(String hasIcon) {
		this.hasIcon = hasIcon;
	}
	public String getCid() {
		return cid;
	}
	public void setCid(String cid) {
		this.cid = cid;
	}
	public String getHasAD() {
		return hasAD;
	}
	public void setHasAD(String hasAD) {
		this.hasAD = hasAD;
	}
	public String getOrder() {
		return order;
	}
	public void setOrder(String order) {
		this.order = order;
	}
	public List<Imgextra> getImgextra() {
		return imgextra;
	}
	public void setImgextra(List<Imgextra> imgextra) {
		this.imgextra = imgextra;
	}
	public String getPriority() {
		return priority;
	}
	public void setPriority(String priority) {
		this.priority = priority;
	}
	public String getEname() {
		return ename;
	}
	public void setEname(String ename) {
		this.ename = ename;
	}
	public String getTname() {
		return tname;
	}
	public void setTname(String tname) {
		this.tname = tname;
	}
	public List<Ads> getAds() {
		return ads;
	}
	public void setAds(List<Ads> ads) {
		this.ads = ads;
	}
	public String getPhotosetID() {
		return photosetID;
	}
	public void setPhotosetID(String photosetID) {
		this.photosetID = photosetID;
	}
}
