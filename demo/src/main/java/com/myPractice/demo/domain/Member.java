package com.myPractice.demo.domain;

import lombok.Getter;

@Getter
public class Member {
    private Long id;
    private String name;

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }
}
