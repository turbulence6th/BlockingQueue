package com.turbulence6th;


public class Message {
	
	String event;
	
	String[] args;
	
	Message(String event, String... args) {
		this.event = event;
		this.args = args;
	}

}
