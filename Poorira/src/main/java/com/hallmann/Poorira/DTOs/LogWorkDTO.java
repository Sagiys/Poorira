package com.hallmann.Poorira.DTOs;

import java.util.Date;

public class LogWorkDTO {

	private String worker;
	private String note;
	private long date;
	private long timeSpent;
	
	public LogWorkDTO() {
		Date dateObject = new Date();
		date = dateObject.getTime() - (dateObject.getHours() * 3600000) - (dateObject.getMinutes() * 60000) - (dateObject.getSeconds() * 1000);
	}
	
	public String getWorker() {
		return worker;
	}
	public void setWorker(String worker) {
		this.worker = worker;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public long getDate() {
		return date;
	}
	public void setDate(long date) {
		this.date = date;
	}
	public long getTimeSpent() {
		return timeSpent;
	}
	public void setTimeSpent(long timeSpent) {
		this.timeSpent = timeSpent;
	}
	
}
