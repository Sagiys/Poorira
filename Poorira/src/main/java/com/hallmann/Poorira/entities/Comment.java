package com.hallmann.Poorira.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Comment {

	@Id
	@GeneratedValue
	private int ID;
	
	private String comment;
	
	public Comment() {
		
	}

	public Comment(int iD, String comment) {
		ID = iD;
		this.comment = comment;
	}

	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}
}
