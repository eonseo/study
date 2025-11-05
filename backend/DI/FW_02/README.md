# FW_02 프로젝트

## 프로젝트 개요

Spring Framework를 활용한 Dependency Injection 실습 프로젝트입니다.

## 폴더 구조

```
FW_02/
├── pom.xml
└── src/
    ├── main/
    │   ├── java/
    │   │   ├── com/
    │   │   │   └── study/
    │   │   │       └── live/
    │   │   │           └── washer/
    │   │   │               ├── bean/
    │   │   │               │   ├── LWasher.java
    │   │   │               │   ├── SWasher.java
    │   │   │               │   ├── Washer.java
    │   │   │               │   └── WasherUser.java
    │   │   │               └── config/
    │   │   │                   └── WasherConfig.java
    │   │   └── WasherTest.java
    │   └── resources/
    └── test/
        ├── java/
        │   └── com/
        │       └── study/
        │           └── live/
        │               └── washer/
        │                   └── WasherTest.java
        └── resources/
```

## 주요 파일 설명

### 설정 파일

- `pom.xml`: Maven 프로젝트 설정 파일

### 소스 파일 (src/main/java)

- `com.study.live.washer.bean.*`: 빈(Bean) 클래스들
  - `Washer.java`: 세탁기 인터페이스
  - `LWasher.java`: 큰 세탁기 구현체
  - `SWasher.java`: 작은 세탁기 구현체
  - `WasherUser.java`: 세탁기 사용자 클래스
- `com.study.live.washer.config.WasherConfig.java`: Spring 설정 클래스
- `WasherTest.java`: 메인 테스트 클래스

### 테스트 파일 (src/test/java)

- `com.study.live.washer.WasherTest.java`: JUnit 테스트 클래스

## 기술 스택

- Java 17
- Spring Framework 6.2.12
- JUnit 5.14.1
- Maven
