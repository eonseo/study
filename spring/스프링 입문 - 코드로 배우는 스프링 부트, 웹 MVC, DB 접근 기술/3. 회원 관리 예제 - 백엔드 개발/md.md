# 회원 관리 예제 - 백엔드 개발

# 목차

1. 웹 애플리케이션 계층 구조와 클래스 의존 관계
2. 회원 리포지토리 테스트케이스 작성
3. 회원 서비스 테스트

# 1. 웹 애플리케이션 계층 구조와 클래스 의존 관계

**일반적인 웹 애플리케이션 계층 구조**

<img src="./img/1.png">

- 컨트롤러: 웹 MVC 의 컨트롤러 역할
- 서비스: 핵심 비즈니스 로직 구현
- 리포지토리: 데이터베이스에 접근, 도메인 객체를 DB 에 저장하고 관리
- 도메인: 비즈니스 도메인 객체
  - ex) 회원, 주문, 쿠폰 등등 주로 데이터베이스에 저장하고 관리됨

**클래스 의존관계**

<img src="./img/2.png">

# 2. 회원 리포지토리 테스트케이스 작성

개발한 기능을 실행해서 테스트 할 때 자바의 main 메서드를 통해서 실행하거나, 웹 애플리케이션의 컨트롤러를 통해 해당 기능을 실행한다. 이러한 방법은 준비하고 실해앟는데 오래 거릴고, 반복 실행하기 어렵고, 여러 테스트를 한 번에 실행하기 어렵다는 단점이 있다. 자바는 JUnit 이라는 프레임워크로 테스트를 실행해서 이러한 문제를 해결한다.

- test 는 서로 순서와 상관없이, 의존관계 없이 설계가 되어야함
  - 하나의 테스트가 끝날 때마다 저장소나 공용 데이터들을 다시 비워주기(`@AfterEach -> clear`)
- 개발 -> 테스트 작성
- 테스트 주도 개발(TDD): 테스트 작성 -> 개발

# 3. 회원 서비스 테스트

이해가 잘 안되는 부분 !

`MemberService.java`

```java
private final MemberRepository memberRepository;

    // MemberService 를 만들 때 어떤 저장소(MemberRepository) 를 쓸지 외부에서 넣어주는 방식(생성자 주입)
    public MemberService(MemberRepository memberRepsitory) {
        this.memberRepository = memberRepsitory;
    }
```

`MemberServiceTest.java`

```java
MemberService memberService;
    MemoryMemberRepository memberRepository;

    @BeforeEach
    public void beforeEach() {
        memberRepository = new MemoryMemberRepository();
        memberService = new MemberService(memberRepository);
    }
```

✅ 코드가 하는 일 (유치원생 수준 설명)

1. MemberService는 회원 관련 기능을 담당하는 서비스 <br>
   그런데 이 서비스는 혼자서 회원을 저장할 수 없음 <br>
   **저장소(MemberRepository)**가 필요

2. MemberService를 만들 때,
   “어떤 저장소를 쓸지” 밖에서 넣어줌 = 생성자 주입 <br>
   MemberService: “나는 저장소가 필요해! 누가 좀 넣어줘!”

3. 테스트할 때는 MemoryMemberRepository라는 가짜 저장소 사용 <br>
   왜냐하면 진짜 DB는 무겁고 느리니까, 테스트용으로 메모리에 저장하는 저장소 사용

4. `@BeforeEach`는 테스트 시작 전에 매번 실행되는 준비 작업

여기서 하는 일:

새로운 MemoryMemberRepository를 만든다.
그걸 MemberService에 넣어서 MemberService를 만든다.
