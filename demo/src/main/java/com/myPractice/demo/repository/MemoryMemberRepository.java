package com.myPractice.demo.repository;

import com.myPractice.demo.domain.Member;

import java.util.*;

public class MemoryMemberRepository implements MemberRepository {

    static Map<Long, Member> store = new HashMap<>();
    static long sequence;

    @Override
    public Member save(Member member) {
        member.setId(++sequence);
        store.put(member.getId(), member);
        return member;
    }

    @Override
    public Optional<Member> findById(Long id) {
        return Optional.ofNullable(store.get(id)); // 값이 있으면 Optional<Member> 가 들어가고
        // 값이 없으면 optional.empty() 가 돼서 안전하게 처리 가능
    }

    @Override
    public Optional<Member> findByName(String name) {
        // .values: 그 map 에 들어있는 모든 값을 모아서 collection<member> 로 변환
        // stream: collection 을 데이터 흐름처럼 다룰 수 있게 함. 즉, 회원 객체들을 하나씩 흘려보내면서 조건을 걸거나 변환 가능
        return store.values().stream()
                .filter(member -> member.getName().equals(name)) // 조건에 맞는 데이터만 걸러내기
                .findAny();
    }

    @Override
    public List<Member> findAll() {
        return new ArrayList<>(store.values()); //  store.values 값들을 복사해서 새로운 ArrayList 를 만듦
    }

    public void clearStore() {
        store.clear();
    }
}
