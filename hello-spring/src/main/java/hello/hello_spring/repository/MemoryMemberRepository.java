package hello.hello_spring.repository;

import hello.hello_spring.domain.Member;

import java.util.*;

public class MemoryMemberRepository implements MemberRepository {

    private static Map<Long, Member> store = new HashMap<>();
    private static long sequence = 0L;

    @Override
    public Member save(Member member) {
        member.setId(++sequence);
        store.put(member.getId(), member);
        return member;
    }

    @Override
    public Optional<Member> findById(Long id) {
        return Optional.ofNullable(store.get(id));
    }

    @Override
    // Optional<Member> 👉 "있을 수도 있고 없을 수도 있는 상자"(Optional)에 담아서 돌려주기.
    //있으면 그 안에 회원이 들어 있고,
    //없으면 빈 상자(Optional.empty)가 반환돼요.
    public Optional<Member> findByName(String name) {
        // store.values() 👉 store라는 저장소(Map 같은 자료구조)에 들어있는 모든 회원들을 꺼냄 (즉, 회원 목록 전체)
        // .stream() 👉 그 목록을 "줄 세우기" 해서 하나씩 검사할 수 있게 만들기 (회원들을 줄 세워서 차례대로 살펴보는 느낌)
        return store.values().stream()
                .filter(member -> member.getName().equals(name)) // 👉 "이름이 내가 찾는 name과 같은 회원만 남겨라!" 라는 조건
                .findAny(); // 👉 조건에 맞는 회원 중 아무거나 하나를 꺼냄 (보통 이름은 중복이 없으니까, 사실상 딱 한 명을 찾는 거)
    }

    @Override
    public List<Member> findAll() {
        return new ArrayList<>(store.values());
    }

    public void clearStore() {
        store.clear();
    }
}
