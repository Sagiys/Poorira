package com.hallmann.Poorira.DTOs;

import java.util.List;

import com.hallmann.Poorira.entities.Project;

public class UserDTO {

	private String username;
	private String password;
	private List<Project> projects;

	public UserDTO(String username, String password) {
		this.username = username;
		this.password = password;
	}

	public UserDTO() {

	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<Project> getProjects() {
		return projects;
	}

	public void setProjects(List<Project> projects) {
		this.projects = projects;
	}

}
