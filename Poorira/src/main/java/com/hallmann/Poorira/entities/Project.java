package com.hallmann.Poorira.entities;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

@Entity
public class Project {

	@Id
	@GeneratedValue
	private int ID;
	private int ownerID;
	private int numOfTasks;
	private String name;
	private String identifier;
	@OneToMany
	private List<Task> unFinishedTasks;
	@OneToMany
	private List<Task> usedTasks;
	@OneToMany
	private List<Task> finishedTasks;
	@OneToMany
	private List<Sprint> sprints;
	@ManyToMany
	private List<User> users;

	public Project() {
	}

	public Project(int ID, String name, int ownerID) {
		this.ID = ID;
		this.name = name;
		this.ownerID = ownerID;
	}

	public int getID() {
		return ID;
	}

	public void setID(int ID) {
		this.ID = ID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getOwnerID() {
		return ownerID;
	}

	public void setOwnerID(int ownerID) {
		this.ownerID = ownerID;
	}

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	public String getIdentifier() {
		return identifier;
	}

	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

	public int getNumOfTasks() {
		return numOfTasks;
	}

	public void setNumOfTasks(int numOfTasks) {
		this.numOfTasks = numOfTasks;
	}

	public List<Task> getUnFinishedTasks() {
		return unFinishedTasks;
	}

	public void setUnFinishedTasks(List<Task> unFinishedTasks) {
		this.unFinishedTasks = unFinishedTasks;
	}

	public List<Task> getUsedTasks() {
		return usedTasks;
	}

	public void setUsedTasks(List<Task> usedTasks) {
		this.usedTasks = usedTasks;
	}

	public List<Task> getFinishedTasks() {
		return finishedTasks;
	}

	public void setFinishedTasks(List<Task> finishedTasks) {
		this.finishedTasks = finishedTasks;
	}

	public List<Sprint> getSprints() {
		return sprints;
	}

	public void setSprints(List<Sprint> sprints) {
		this.sprints = sprints;
	}
}
