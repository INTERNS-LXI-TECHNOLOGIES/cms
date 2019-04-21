package com.lxisoft.sis.service.dto;

public class ErrorDTO {
	private String title;
	private String subtitle;
	private String message;
	private String status;

	public ErrorDTO(String title, String subtitle, String message, String status) {
		super();
		this.title = title;
		this.subtitle = subtitle;
		this.message = message;
		this.status = status;
	}
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getSubtitle() {
		return subtitle;
	}
	public void setSubtitle(String subtitle) {
		this.subtitle = subtitle;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
}
