package com.study.live.washer.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.study.live.washer.bean.LWasher;
import com.study.live.washer.bean.SWasher;
import com.study.live.washer.bean.WasherUser;

@Configuration
public class WasherConfig {
	@Bean
	public SWasher sWasher() {
		return new SWasher();
	}
	
	@Bean
	public LWasher lWasher() {
		return new LWasher();
	}
	
	@Bean
	public WasherUser washerUser() {
		// 생성자를 통한 빈 주입
		return new WasherUser(sWasher());
	}
}
