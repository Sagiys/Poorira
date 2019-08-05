package com.hallmann.Poorira.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Task {

	@Id
	@GeneratedValue
	private int ID;
	private String identifier;
	private String name;
	private String personAssigned;
	private String description;
	private int sprintID;
	private int projectID;
	private long estimatedTime;
	@OneToMany
	@Basic
	private List<Comment> comments;
	private int priority;
	@OneToMany
	private List<LogWork> logWorks;

	public Task() {
		comments = new ArrayList<>();
		description = "";
		personAssigned = "";
		priority = 2;
	}

	public Task(int iD, String identifier, String name, int priority) {
		ID = iD;
		this.identifier = identifier;
		this.name = name;
		this.priority = priority;
		comments = new ArrayList<>();
	}

	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
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

	public int getSprintID() {
		return sprintID;
	}

	public void setSprintID(int sprintID) {
		this.sprintID = sprintID;
	}

}
