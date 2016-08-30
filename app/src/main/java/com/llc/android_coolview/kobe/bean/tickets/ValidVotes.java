package com.llc.android_coolview.kobe.bean.tickets;

import java.util.List;

public class ValidVotes {

	private String reason;
	private List<ValidResult> result;
	private String error_code;
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public List<ValidResult> getResult() {
		return result;
	}
	public void setResult(List<ValidResult> result) {
		this.result = result;
	}
	public String getError_code() {
		return error_code;
	}
	public void setError_code(String error_code) {
		this.error_code = error_code;
	}
	
	
}
