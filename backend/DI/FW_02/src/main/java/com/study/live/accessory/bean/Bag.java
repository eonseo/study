package com.study.live.accessory.bean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Bag {
	
	private Hat hat;
	private Watch watch;
	private SmartWatch smartWatch;
	
	@Autowired
	public Bag(Hat hat, Watch watch) {
		this.hat = hat;
		this.watch = watch;
	}
	
	@Autowired
	public void setSmartWatch(SmartWatch smartWatch) {
		this.smartWatch = smartWatch;
	}
}

