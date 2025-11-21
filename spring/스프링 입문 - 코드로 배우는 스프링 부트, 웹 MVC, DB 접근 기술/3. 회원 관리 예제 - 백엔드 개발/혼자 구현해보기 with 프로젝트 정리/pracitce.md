# 1. 프로젝트 설정

## spring initializr 을 사용하는 이유는?

- spring 프로젝트를 빠르고 표준화된 방식으로 시작할 수 있도록 도와주는 도구이기 때문

1. 프로젝트 구조 자동 생성

- maven 또는 gradle 기반의 빌드 설정 파일을 자동으로 만들어줌
- 디렉터리 구조를 표준 spring boot 방식으로 생성 -> 개발자가 바로 코딩 시작 가능

2. 의존성 관리 편리

- 필요한 라이브러리를 선택하면 자동으로 추가
- 버전 호환성 문제를 줄여줌 -> spring boot 가 권장하는 안정적인 버전으로 설정

3. 설정 최소화

- application.properties 기본 설정 제공
- 내장 서버(tomcat) 등 포함 -> 별도 서버 설치 없이 바로 실행 가능

4. 생산성 향상

- 초기 설정에 시간을 쓰지 않고 비즈니스 로직 개발에 집중 가능
- 팀 프로젝트에서 동일한 구조로 시작 -> 협업 효율성 증가

---

## 내가 추가하려는 의존성

### Spring Web

- 목적: 웹 애플리케이션을 만들기 위한 핵심 모듈
- 포함 내용:
  - Spring MVC: HTTP 요청/응답 처리, 컨트롤러, 라우팅 등
  - 내장 톰캣 서버: 별도의 서버 설정 없이 실행 가능
- 사용 예시:
  - REST API 개발
  - 웹 페이지 라우팅
  - JSON 응답 처리

즉, 웹 애플리케이션의 백엔드 로직을 담당

### Thymeleaf

- 목적: 서버 사이드 템플릿 엔진
- 특징:
  - HTML 파일에 동적으로 데이터를 바인딩
  - JSP 대신 많이 사용됨
  - Spring MVC 와 자연스럽게 통합
- 사용 예시:
  - HTML 페이지에서 ${변수} 형태로 데이터 출력
  - 조건문, 반복문을 HTML 안에서 처리

즉, 뷰 렌더링을 담당

## 프로젝트 구조

- 패키지

- `domain`: 도메인 모델 클래스
- `repository`: 저장소 인터페이스 및 구현체
- `service`: 비즈니스 로직
- `controller`: 웹 컨트롤러

1. 도메인

- 애플리케이션의 핵심 데이터와 개념을 표현
- 구현: 주로 entity 클래스(User, Product) 로 데이터베이스 테이블과 매핑
- 예시

```java
@Entity
public class User {
    @Id @GeneratedValue
    private Long id;
    private String name;
}
```

- 핵심: 비즈니스에서 다루는 실제 개념을 코드로 모델링

2. 리포지토리(Repository)

- 역할: 데이터베이스 접근 담당
- 구현: Spring Data JPA 의 JpaRepository 인터페이스를 상속
- 예시

```java
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByName(String name);
}
```

- 핵심 CRUD 작업을 쉽게 처리

3. 비즈니스 로직(service)

- 역할: 핵심 로직을 처리하는 계층
- 구현: @Service 클래스에서 리포지토리를 호출하고, 필요한 계산/검증 수행
- 예시:

```java
@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    publicUser registerUser(String name) {
        // 비즈니스 규칙 적용
        return userRepository.save(new User(name));
    }
}
```

- 핵심: 컨트롤러와 리포지토리 사이에서 비즈니스 규칙을 담당

4. 컨트롤러

- 클라이언트 요청을 받아서 응답 반환
- 구현: @Controller 또는 @RestController 사용
- 예시

```java
@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @PostMapping
    public User createUser(@RequestParam String name) {
        return userService.registerUser(name);
    }
}
```

- 핵심: HTTP 요청/응답 처리, 뷰 렌더링 또는 JSON 반환

## 정리 구조

`Controller -> Service -> Repository -> Domain`

2. Domain 계층 구현

`MemberRepository.java`

## 왜 인터페이스에는 @Repository 라고 붙이지 않을까?

- @Repository 는 Spring 이 해당 클래스를 빈(Bean) 으로 등록하고 예외 변환(AOP) 를 적용하기 위한 어노테이션이다.
- 하지만 인터페이스 자체는 빈으로 등록되지 않고, 구현체가 빈으로 등록됨
- 예를 들어
  - MemberRepository 는 인터페이스 -> 빈이 아님
  - MemoryMemberRepository 구현체 -> @Repository 붙이면 빈으로 등록됨

## 권장 방식

- 메모리 구현체나 직접 구현한 클래스에는 @Repository 붙이기
- 인터페이스에는 붙이지 않아도 됨

## 인터페이스 메서드에 public 을 붙여야 할까?

-> No !

✅ Java 규칙

- 인터페이스 안의 메서드는 자동으로 public abstract 임
- 따라서 다음 두 코드는 동일함

```java
Member save(Member member); // OK
public Member save(Member member); // OK (하지만 불필요)
```
