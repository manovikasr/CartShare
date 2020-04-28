package com.cmpe275project.requestObjects;

public class MessageRequest {
	private String sender_screen_name;
	private String receiver_screen_name;
	private String message;
	
	public String getSender_screen_name() {
		return sender_screen_name;
	}
	public void setSender_screen_name(String sender_screen_name) {
		this.sender_screen_name = sender_screen_name;
	}
	public String getReceiver_screen_name() {
		return receiver_screen_name;
	}
	public void setReceiver_screen_name(String receiver_screen_name) {
		this.receiver_screen_name = receiver_screen_name;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
}
