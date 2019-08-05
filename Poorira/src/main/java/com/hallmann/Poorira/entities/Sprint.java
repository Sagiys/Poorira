package com.hallmann.Poorira.entities;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Sprint {

	@Id
	@GeneratedValue
	private int ID;
	private String name;
	private boolean active;
	private int projectID;
	@OneToMany
	private List<Task> finishedTasks;
	@OneToMany
	private List<Task> usedTasks;
	@OneToMany
	private List<Task> unFinishedTasks;
	private long startDate;
	private long estimatedDate;
	private long finishDate;

	public Sprint() {
		this.active = true;
		this.startDate = System.currentTimeMillis();
	}

	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public List<Task> getFinishedTasks() {
		return finishedTasks;
	}

	public void setFinishedTasks(List<Task> finishedTasks) {
		this.finishedTasks = finishedTasks;
	}

	public List<Task> getUsedTasks() {
		return usedTasks;
	}

	public void setUsedTasks(List<Task> usedTasks) {
		this.usedTasks = usedTasks;
	}

	public List<Task> getUnFinishedTasks() {
		return unFinishedTasks;
	}

	public void setUnFinishedTasks(List<Task> unFinishedTasks) {
		this.unFinishedTasks = unFinishedTasks;
	}

	public long getStartDate() {
		return startDate;
	}

	public void setStartDate(long startDate) {
		this.startDate = startDate;
	}

	public long getEstimatedDate() {
		return estimatedDate;
	}

	public void setEstimatedDate(long estimatedDate) {
		this.estimatedDate = estimatedDate;
	}

	public long getFinishDate() {
		return finishDate;
	}

	public void setFinishDate(long finishDate) {
		this.finishDate = finishDate;
	}

	public int getProjectID() {
		return projectID;
	}

	public void setProjectID(int projectID) {
		this.projectID = projectID;
	}
}
