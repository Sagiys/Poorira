package com.hallmann.Poorira.DTOs;

import java.util.List;

import com.hallmann.Poorira.entities.Sprint;
import com.hallmann.Poorira.entities.Task;
import com.hallmann.Poorira.entities.User;

public class ProjectDTO {

	private int ID;
	private int ownerID;
	private int numOfTasks;
	private String name;
	private String identifier;
	private List<Task> unFinishedTasks;
	private List<Task> usedTasks;
	private List<Task> finishedTasks;
	private List<Sprint> sprints;
	private List<User> users;
	
	public ProjectDTO() {
		
	}
	
	public ProjectDTO(int id, String name, int ownerID) {
		this.ID = id;
		this.name = name;
		this.ownerID = ownerID;
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
