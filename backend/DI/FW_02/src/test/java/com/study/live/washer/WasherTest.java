package com.study.live.washer;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.study.live.washer.bean.LWasher;
import com.study.live.washer.bean.SWasher;
import com.study.live.washer.bean.Washer;
import com.study.live.washer.bean.WasherUser;
import com.study.live.washer.config.WasherConfig;

public class WasherTest {

	ApplicationContext ctx;
	
	@BeforeEach // 매번 테스트 전에 해야할 것
	public void setup() {
		ctx = new AnnotationConfigApplicationContext(WasherConfig.class);
	}
	
	@Test
	public void userTest() {
		WasherUser user = ctx.getBean(WasherUser.class);
		Assertions.assertNotNull(user); // 존재하는지 확인
	}
	
	@Test
	public void singletonTest() { // 싱글톤인지 확인(세탁기가 같은 물건인지 확인)
		WasherUser user= ctx.getBean(WasherUser.class);
		Washer washer1 = user.getWasher();
		Washer washer2 = ctx.getBean(SWasher.class);
		Assertions.assertSame(washer1, washer2);
	}
}
