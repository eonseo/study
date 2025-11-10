package com.study.live.accesory;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.study.live.accessory.bean.Bag;
import com.study.live.accessory.config.BagConfig;

public class BagTest {
	
	ApplicationContext ctx;
	
	@BeforeEach
	// 테스트 실행 전에 미리 세팅
	// 보통 DB 연결, 스프링 컨테이너 작성, 객체 초기화 등을 여기서 함
	// 비유1. 테스트 하기 전에 가방을 미리 챙겨둬!
	public void setup() { // 비유2. 가방을 실제로 챙기는 행동
		// 스프링에서 설정 클래스(@Configuration) 기반으로 빈을 등록하고 관리하는 컨테이너
		// 비유3. BagConfig 에서 어떤 물건을 넣을지 보고 가방에 다 담는 것
		ctx = new AnnotationConfigApplicationContext(BagConfig.class);
	}
	
	@Test
	public void bagTest() {
		Bag bag = ctx.getBean(Bag.class);
		Assertions.assertNotNull(bag); // bag 이라는 변수가 null 이면 테스트 실패
		System.out.println("success");
	}
}
