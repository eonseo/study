package com.study.live.washer.bean;

public class WasherUser {
	Washer washer;
	
	public WasherUser(Washer washer) {
		this.washer = washer;
	}
	
	public Washer getWasher() {
		return this.washer;
	}
	
	public void useWasher() {
		washer.wash("바지");
	}
}
