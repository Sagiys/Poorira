package com.hallmann.Poorira.DTOs;

public class CommentDTO {

	private int ID;
	private String comment;
	
	public CommentDTO() {
		
	}

	public CommentDTO(int iD, String comment) {
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
