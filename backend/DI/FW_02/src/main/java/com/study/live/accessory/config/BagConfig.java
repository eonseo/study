package com.study.live.accessory.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("com.study.live.accessory.bean")
// 스프링 설정 클래스
// 이 클래스 안에 @Bean 이나 @ComponentScan 이 있어서 스프링이 어떤 객체를 관리해야하는지 알려줌
public class BagConfig {

}
