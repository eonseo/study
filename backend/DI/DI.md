# 의존성 주입(DI) 학습 정리

## 📋 목차

1. [의존성과 DI란?](#1-의존성과-di란)
2. [DI의 필요성](#2-di의-필요성)
3. [Spring Framework의 역할](#3-spring-framework의-역할)
4. [명시적 DI - @Configuration과 @Bean](#4-명시적-di---configuration과-bean)
5. [Bean 생명주기](#5-bean-생명주기)

---

## 1. 의존성과 DI란?

### 1.1 의존성(Dependency)의 개념

**의존성**이란?

- 어떤 객체(빈)가 비즈니스 로직 처리를 위해 다른 객체(빈)에 의존하는 관계
- 객체 간 **has-a 관계**
- 예시: "A는 B 없이 일을 못한다" = A는 B에 의존한다

### 1.2 의존성 주입(DI, Dependency Injection)

**DI란?**

- 객체의 의존성(멤버 변수 = 필요한 부품)을 **외부에서 주입**하는 것
- 의존 관계 관리를 위해 DI 개념을 사용하며, 이 과정에서 **제어의 역전(IoC)** 발생

**스프링 = IoC(Inversion Of Control) 컨테이너**

- 비유: **렌탈회사**
- 전통적으로는 의존성을 직접 구매했지만, 최근에는 렌탈 서비스가 급격히 증가!

### 1.3 스프링 빈(Spring Bean)

**스프링 빈이란?**

- 스프링 프레임워크에 의해 **생성되고 관리되는 자바 객체**
- 스프링은 빈의 생성, 의존관계 설정, 객체 관리 등 **빈의 라이프 사이클을 관리**
- **스프링 = 빈의 컨테이너**

**POJO(Plain Old Java Object)**

- 그냥 평범한 자바 객체
- Spring은 WasherUser, SWasher, LWasher 같은 객체를 빈으로 관리
- 특별한 제약 없이 일반적인 자바 객체로 만들면 됨

---

## 2. DI의 필요성

### 2.1 직접 생성 버전 (문제점)

**코드 구조**

```
┌────────────┐        ┌────────────┐
│ WasherUser │------> │   Swasher  │
└────────────┘        └────────────┘
        │
        │  has-a (직접 생성)
        ▼
의존성 강함 (강결합, Tight Coupling)
```

**코드 예시**

```java
public class WasherUser {
    Swasher washer; // has-a 관계: 의존성

    public WasherUser() {
        this.washer = new Swasher(); // 의존성을 직접 생성
    }

    public void useWasher(String clothes) {
        washer.wash(clothes);
    }
}

public class SWasher {
    public void wash(String clothes) {
        System.out.println("SWasher 로 세탁: " + clothes);
    }
}
```

**문제점**

- 세탁기를 SWasher에서 다른 것으로 바꾸려면 **유지보수가 어려움**
- 코드 수정이 필요함
- 강결합(Tight Coupling) 상태

### 2.2 Factory + 주입 버전 (해결책)

**코드 구조**

```
┌────────────┐        ┌────────────┐
│  WasherUser│------>│   Washer   │◄──────────────┐
└────────────┘        └────────────┘               │
        │                      ▲                   │
        │ (의존성 주입)         │ (상속/구현)        │
        ▼                     │                   │
    느슨한 결합               ┌───────────┐   ┌───────────┐
                             │  LWasher  │   │  Swasher  │
                             └───────────┘   └───────────┘
                                                   ▲
                                                   │
                                      ┌────────────┘
                                      │
                             ┌────────────────────┐
                             │   WasherFactory    │
                             │ createWasher(type) │
                             └────────────────────┘
```

**코드 예시**

```java
public class WasherUser {
    Washer washer; // has-a 관계 - 의존성

    // 의존성을 직접 생성하지 않고 외부에서 주입받음
    public WasherUser(Washer washer) {
        this.washer = washer;
    }
}

public class WasherFactory {
    public static Washer createWasher(String type) {
        return switch(type) {
            case "L" -> new LWasher();
            default -> new Swasher();
        };
    }
}
```

**장점**

- WasherFactory를 통해 세탁기를 얻어서 주입
- WasherUser는 더 이상 세탁기를 생성하지 않음
- 느슨한 결합(Loose Coupling) 상태

### 2.3 비교표

| 구분            | 직접 생성 버전                         | Factory + 주입 버전                                 |
| --------------- | -------------------------------------- | --------------------------------------------------- |
| **의존성 형태** | 강결합 (Tight Coupling)                | 느슨한 결합 (Loose Coupling)                        |
| **특징**        | 클래스 간 변경이 어렵고, 유지보수 힘듦 | Washer 종류가 바뀌어도 User 코드는 그대로 유지 가능 |
| **비유**        | 내가 직접 세탁기 삼                    | 공장에서 받아씀                                     |
| **코드 관계**   | `new Swasher()`                        | `WasherUser(Washer washer)`                         |
| **결과**        | 세탁기 바꾸려면 코드 고쳐야 함 😭      | 세탁기 바뀌어도 난 그대로 😎                        |

---

## 3. Spring Framework의 역할

### 3.1 Spring의 등장

**Spring의 역할**

- WasherUser, SWasher, LWasher 같은 객체를 **빈이라는 개념으로 관리**
- 설정에 따라 WasherUser의 의존성인 Washer에 가지고 있는 빈(SWasher, LWasher 중 하나)을 **자동으로 주입**

### 3.2 Spring의 빈 관리 과정

<img src="./img/3.png">

**3단계 프로세스**

1. **개발자는 POJO로 빈 작성**

   - 일반적인 자바 객체로 작성

2. **메타정보 전달**

   - 빈의 생성 방법 및 관계 설정 정보를 스프링 컨테이너에게 전달

3. **런타임 처리**
   - 스프링은 메타 정보를 보고 빈 객체 생성 → **싱글턴 형태로 관리**
   - 빈 관계 정보에 따라 **주입 처리**

### 3.3 DI 방법

객체의 의존성을 주입하는 방법:

1. **직접 field에 할당하는 방법**
2. **생성자를 활용하는 방법** (권장)
3. **setter 메서드를 이용하는 방법**

---

## 4. 명시적 DI - @Configuration과 @Bean

### 4.1 명시적 DI란?

**정의**

- 빈을 생성하고 의존성을 주입하는 코드를 **별도의 파일에 명시**
- 설정 클래스를 통해 빈을 직접 정의

### 4.2 @Configuration

**역할**

- "이 클래스는 설정을 담고 있는 곳이야!" 라는 표시
- 클래스 안에서 어떤 빈을 만들지 정리하는 역할
- Java 기반으로 설정 파일을 만들기 위한 annotation
- **클래스 레벨에 선언**

```java
@Configuration
public class WasherConfig {
    // 빈 정의 메서드들...
}
```

### 4.3 @Bean

**역할**

- 빈을 선언하기 위한 annotation
- **메서드 레벨에 선언**
- **메서드의 이름 = 빈의 이름**

**특징**

- `@Bean`의 `value` 또는 `name` 속성으로 빈 이름 지정 가능
- `autowireCandidate` 속성으로 자동 주입 여부 지정 가능
- 메서드 이름을 지정하지 않으면, 메서드 이름이 빈 이름이 됨

### 4.4 예시 코드

**WasherConfig.java**

```java
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
```

**설명**

- `sWasher()`: SWasher 타입의 빈 생성
- `lWasher()`: LWasher 타입의 빈 생성
- `washerUser()`: WasherUser 타입의 빈 생성, 생성자에 `sWasher()` 주입

### 4.5 사용 예시

**WasherTest.java**

```java
public class WasherTest {
    public static void main(String[] args) {
        // 스프링의 IoC 컨테이너(빈을 관리하는 통) 생성
        ApplicationContext ctx = new AnnotationConfigApplicationContext(WasherConfig.class);

        // 컨테이너에서 빈 꺼내기
        Washer washer = ctx.getBean(SWasher.class);
        WasherUser user = ctx.getBean(WasherUser.class);

        // 빈 사용
        user.useWasher();

        // 같은 객체인지 확인 (싱글턴)
        System.out.println(user.getWasher() == washer);
    }
}
```

**실행 흐름**

1. `ApplicationContext` 생성 → `WasherConfig` 읽기
2. `WasherConfig`의 `@Bean` 메서드들 실행
3. 빈들이 컨테이너에 등록됨
4. `getBean()`으로 빈 꺼내서 사용

---

## 5. Bean 생명주기

### 5.1 Bean 생명주기 7단계

<img src="./img/bean_lifecycle.jpg">

**순서**

1. **스프링 IoC 컨테이너 생성**
2. **스프링 빈 생성**
3. **의존관계 주입**
4. **초기화 콜백 메소드 호출** (`@PostConstruct`, `afterPropertiesSet()`)
5. **사용**
6. **소멸 전 콜백 메소드 호출** (`@PreDestroy`, `destroy()`)
7. **스프링 종료**

**핵심**

- 스프링 컨테이너가 빈의 전체 생명주기를 관리
- 각 단계마다 콜백 메서드로 커스터마이징 가능
- 자동으로 정리 작업 수행

---

## 📌 핵심 정리

### 의존성과 DI

- **의존성**: 객체 간 has-a 관계
- **DI**: 의존성을 외부에서 주입
- **IoC**: 제어의 역전, 스프링이 객체 생명주기 관리

### DI의 필요성

- **강결합**: 직접 생성 → 유지보수 어려움
- **느슨한 결합**: 주입 받기 → 유지보수 용이

### 명시적 DI

- **@Configuration**: 설정 클래스 표시
- **@Bean**: 빈 정의 메서드
- 생성자 주입을 통한 의존성 주입

### Bean 생명주기

- 컨테이너 생성 → 빈 생성 → 의존성 주입 → 초기화 → 사용 → 소멸 → 종료
- 스프링이 전체 생명주기 관리

---

## 📚 참고 자료

- 프로젝트: `backend/DI/FW_02/`
- 주요 파일:
  - `Washer.java` (Interface)
  - `SWasher.java` (implements Washer)
  - `LWasher.java` (implements Washer)
  - `WasherUser.java`
  - `WasherConfig.java` (설정 클래스)
  - `WasherTest.java` (테스트 클래스)
  - `com.study.live.washer/WasherTest.java`(테스트 파일)
