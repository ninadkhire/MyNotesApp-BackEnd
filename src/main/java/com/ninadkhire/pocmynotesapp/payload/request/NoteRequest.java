package com.ninadkhire.pocmynotesapp.payload.request;

import javax.validation.constraints.NotBlank;

public class NoteRequest {
	private Long id;
	
	@NotBlank
	private String title;
	
	@NotBlank
	private String note;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}
	
}
