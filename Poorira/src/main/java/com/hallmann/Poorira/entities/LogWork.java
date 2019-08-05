package com.hallmann.Poorira.entities;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class LogWork {

	@Id
	@GeneratedValue
	private int ID;
	private String worker;
	private String note;
	private long date;
	private long timeSpent;
	private int taskID;

	public LogWork() {
		Date dateObject = new Date();
		date = dateObject.getTime() - (dateObject.getHours() * 3600000) - (dateObject.getMinutes() * 60000) - (dateObject.getSeconds() * 1000);
	}

	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
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

	public int getTaskID() {
		return taskID;
	}

	public void setTaskID(int taskID) {
		this.taskID = taskID;
	}
	
}
