package com.myPractice.demo.repository;

import com.myPractice.demo.domain.Member;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

public interface MemberRepository {

    Member save(Member member); // 회원 저장
    Optional<Member> findById(Long id); // ID 로 회원 조회
    Optional<Member> findByName(String name); // 이름으로 회원 조회
    List<Member> findAll(); // 전체 회원 조회
}
