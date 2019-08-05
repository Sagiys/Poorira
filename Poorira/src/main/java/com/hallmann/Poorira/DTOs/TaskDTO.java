package com.hallmann.Poorira.DTOs;

import java.util.ArrayList;
import java.util.List;

import com.hallmann.Poorira.entities.Comment;
import com.hallmann.Poorira.entities.LogWork;

public class TaskDTO {

	private String identifier;
	private String name;
	private String personAssigned;
	private String description;
	private int sprintID;
	private int projectID;
	private List<Comment> comments;
	private long estimatedTime;
	private int priority;
	private List<LogWork> logWorks;
	private int taskID;

	public TaskDTO() {
		comments = new ArrayList<>();

	}

	public TaskDTO(String identifier, String name, int priority) {
		this.identifier = identifier;
		this.name = name;
		this.priority = priority;
		comments = new ArrayList<>();

	}

	public String getIdentifier() {
		return identifier;
	}

	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<Comment> getComments() {
		return comments;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}

	public int getProjectID() {
		return projectID;
	}

	public void setProjectID(int projectID) {
		this.projectID = projectID;
	}

	public String getPersonAssigned() {
		return personAssigned;
	}

	public void setPersonAssigned(String personAssigned) {
		this.personAssigned = personAssigned;
	}

	public long getEstimatedTime() {
		return estimatedTime;
	}

	public void setEstimatedTime(long estimatedTime) {
		this.estimatedTime = estimatedTime;
	}

	public List<LogWork> getLogWorks() {
		return logWorks;
	}

	public void setLogWorks(List<LogWork> logWorks) {
		this.logWorks = logWorks;
	}

	public int getTaskID() {
		return taskID;
	}

	public void setTaskID(int taskID) {
		this.taskID = taskID;
	}

	public int getSprintID() {
		return sprintID;
	}

	public void setSprintID(int sprintID) {
		this.sprintID = sprintID;
	}

}
