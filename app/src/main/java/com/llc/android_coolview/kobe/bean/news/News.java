package com.llc.android_coolview.kobe.bean.news;

import android.os.Parcel;
import android.os.Parcelable;

public class News implements Parcelable{

	private String replyCount;
	private String digest;
	private String docid;
	private String title;
	private String lmodify;
	private String imgsrc;
	private String ptime;
	private int imgType;
	private String skipID;
	private String skipType;
	private String flag;
	public String getReplyCount() {
		return replyCount;
	}
	public void setReplyCount(String replyCount) {
		this.replyCount = replyCount;
	}
	public String getDigest() {
		return digest;
	}
	public void setDigest(String digest) {
		this.digest = digest;
	}
	public String getDocid() {
		return docid;
	}
	public void setDocid(String docid) {
		this.docid = docid;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getLmodify() {
		return lmodify;
	}
	public void setLmodify(String lmodify) {
		this.lmodify = lmodify;
	}
	public String getImgsrc() {
		return imgsrc;
	}
	public void setImgsrc(String imgsrc) {
		this.imgsrc = imgsrc;
	}
	public String getPtime() {
		return ptime;
	}
	public void setPtime(String ptime) {
		this.ptime = ptime;
	}
	public int getImgType() {
		return imgType;
	}
	public void setImgType(int imgType) {
		this.imgType = imgType;
	}
	public String getSkipID() {
		return skipID;
	}
	public void setSkipID(String skipID) {
		this.skipID = skipID;
	}
	public String getSkipType() {
		return skipType;
	}
	public void setSkipType(String skipType) {
		this.skipType = skipType;
	}
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	@Override
	public int describeContents() {
		return 0;
	}
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		
	}
	
	
	
}
