package com.turbulence6th;

public class EventController {
	
	@Event("click")
	public void clickEvent(Message message) {
		System.out.println("click " + message.args[0]);
	}
	
	@Event("click")
	public void click2Event(Message message) {
		System.out.println("click2 " + message.args[0]);
	}
	
	@Event("focus")
	public void focusEvent(Message message) {
		System.out.println("focus " + message.args[0]);
	}

}
