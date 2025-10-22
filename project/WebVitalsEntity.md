# 목차

1. Entity란?
   - 1-1. 특징
   - 1-2. 개발에서의 확장된 의미
     - 1-2-1. JPA/ORM Entity
2. ORM 이란?
   - 2-1. 주요 기능 및 장점
3. 기본 키 전략 비교
   - 3-1. 기본 키 예시로 이해하기
     - 3-1-1. AUTO
     - 3-1-2. IDENTITY
     - 3-1-3. SEQUENCE
     - 3-1-4. TABLE
     - 3-1-5. UUID
4. WebVitalsEntity 코드 설명

# 1. Entity 란?

- 데이터 테이블을 자바 객체로 나타낸 것
- 데이터를 구조화하고, 식별하며, 관리하는데 필요한 핵심 단위

## 1-1. 특징

- 실체 또는 객체: 현실 세계에 존재하거나 개념적으로 존재
  - ex) 고객, 상품, 주문, 직원 등
- 데이터 저장 및 관리 단위
- 속성 보유
  - ex) 고객 엔터티는 고객 id, 이름, 주소, 전화번호 등의 속성을 가짐
- 유일한 식별자: 각 엔터티의 인스턴스를 유일하게 구별할 수 있는 식별자를 반드시 가짐
- 인스턴스의 집합

## 1-2. 개발에서의 확장된 의미

### 1-2-1. JPA/ORM Entity

- Object-Relational Mapping 기술(ex. JPA)을 사용할 때, DB 테이블과 1:1 로 매핑되는 클래스를 entity 라 부름
- 이 클래스는 DB 테이블 구조를 반영하며, 개발자가 객체 지향 방식으로 DB 를 다룰 수 있게 해줌

# 2. ORM 이란?

- 객체와 관계형 데이터베이스에서 사용하는 테이블 간의 데이터를 자동으로 변환하고 연결해주는 프로그래밍 기법
- 개발자가 데이터베이스에 접근하려면 항상 SQL 쿼리를 작성하고, 그 결과를 다시 프로그램의 객체로 변화하는 반복적인 작업을 해야 했다. ORM 은 이 번거로운 변환 작업을 자동해주는 솔루션이다.

## 2-1. 주요 기능 및 장점

| **기능/장점**        | **설명**                                                                                                                                                              |
| -------------------- | --------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| **SQL 추상화**       | 개발자는 SQL 쿼리를 직접 작성할 필요 없이, 객체의 메서드 호출 (`user.save()`, `db.findAll()`)만으로 데이터베이스를 조작할 수 있습니다.                                |
| **생산성 증가**      | 반복적이고 부수적인 SQL 코드 작성이 줄어들어, 개발자는 **비즈니스 로직** 구현에 더 집중할 수 있습니다.                                                                |
| **객체 지향적 코딩** | 데이터베이스 작업을 마치 객체 자체를 다루는 것처럼 할 수 있어 코드가 더 직관적이고 가독성이 높아집니다.                                                               |
| **DBMS 독립성**      | 대부분의 ORM은 데이터베이스 종류(MySQL, PostgreSQL, Oracle 등)에 독립적입니다. 설정만 바꾸면 데이터베이스를 교체하더라도 프로그램 코드를 크게 수정할 필요가 없습니다. |

# 3. 기본 키 전략 비교

- 기본 키: 테이블에서 각 행을 구별할 수 있는 유일한 값

| 전략 이름           | 생성 주체           | 주로 사용하는 DB   | 핵심 개념 요약                  |
| ------------------- | ------------------- | ------------------ | ------------------------------- |
| `AUTO`              | JPA (자동 판단)     | 모든 DB            | JPA가 DB에 맞게 알아서 선택     |
| `IDENTITY`          | DB (AUTO_INCREMENT) | MySQL, MariaDB     | DB가 ID를 자동 증가시킴         |
| `SEQUENCE`          | DB (시퀀스 객체)    | Oracle, PostgreSQL | 별도의 “시퀀스”에서 번호 가져옴 |
| `TABLE`             | JPA (전용 테이블)   | 모든 DB            | ID 전용 테이블을 만들어 관리    |
| `UUID` *(JPA 3.2+)* | Java 코드           | 모든 DB            | 자바에서 랜덤 UUID 생성         |

## 3-1. 기본 키 예시로 이해하기

### 3-1-1. `AUTO`: JPA 가 알아서 선택

```java
@Id
@GeneratedValue(strategy = GenerationType.AUTO)
private Long id;
```

- JPA 가 사용하는 DB 에 따라 자동으로 가장 적절한 전략 선택
  - MySQL: IDENTITY 사용
  - Oracle: SEQUENCE 사용
  - H2: SEQUENCE 사용

### 3-1-2. `IDENTITY`: DB 에서 알아서 AUTO_INCREMENT

```java
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;
```

- MySQL, MariaDB 에서 주로 사용
- DB 칼럼에 AUTO_INCREMENT 속성을 붙여 자동 증가
- DB 가 직접 ID 를 만들기 때문에 INSERT 후에야 ID 값을 알 수 있음

#### 예시

```java
CREATE TABLE test (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100)
);
```

**장점**

- 설정 간단, DB가 자동 관리

**단점**

- 배치 INSERT 시 비효율적

### 3-1-3. `SEQUENCE`: DB 에 있는 시퀀스 객체에서 번호 뽑기

```java
@ID
@SequenceGenerator(name = "test_seq", sequenceName = "test_sequence", allocationSize = 1)
@GenerateedValue(strategy = GenerationType.SEQUENCE, generator = "test_seq")
private Long id;
```

- Oracle, PostgreSQL 등 시퀀스를 지원하는 DB 전용

#### 예시

```java
CREATE SEQUENCE test_sequence START WITH 1 INCREMENT BY 1;
```

**장점**

- 미리 시퀀스 값을 가져와서 성능이 좋음

**단점**

- 시퀀스를 지원하는 DB 에서만 가능

### 3-1-4. `TABLE`: ID 만 관리하는 전용 테이블을 만들어서 거기서 번호 꺼내기

```JAVA
@Id
@TableGenerator(name = "test_table_gen", table = "id_store", pkColumnValue = "test_id")
@GeneratedValue(strategy = GenerationType.TABLE, generator = "test_table_gen")
private Long id;
```

- DB 에 id_store 같은 전용 테이블을 만들어 놓고, 거기서 다음 ID 번호를 읽어옴

```java
CREATE TABLE id_store (
    pk_name VARCHAR(100) PRIMARY KEY,
    next_val BIGINT
);
```

**장점**

- 모든 DB 에서 사용 가능

**단점**

- 항상 테이블에 접근해야하기 때문에 성능 가장 느림

### 3-1-5. `UUID`: 자바에서 랜덤 ID 생성

```JAVA
@Id
@GeneratedValue(strategy = GenerationType.UUID)
private UUID id;
```

- JPA 3.2 이상에서 지원
- Java 에서 UUID.randomUUID() 로 생성
- DB 타입은 BINARY(16) 또는 CHAR(36)

**장점**

- 전역 고유, 추측 불가(보안 좋음)

**단점**

- 인덱스 성능 저하, 문자열이 김

| 전략       | ID 생성 위치        | 속도      | 이식성 | 보안성 | 주로 쓰는 DB       |
| ---------- | ------------------- | --------- | ------ | ------ | ------------------ |
| `AUTO`     | JPA                 | 중간      | 높음   | 중간   | 모두               |
| `IDENTITY` | DB (AUTO_INCREMENT) | 빠름      | 중간   | 낮음   | MySQL              |
| `SEQUENCE` | DB (시퀀스 객체)    | 빠름      | 낮음   | 낮음   | Oracle, PostgreSQL |
| `TABLE`    | JPA (전용 테이블)   | 느림      | 높음   | 낮음   | 모두               |
| `UUID`     | Java 코드           | 중간~느림 | 높음   | 높음   | 모두               |

---

# 4. WebVitalsEntity 코드 설명

```java
@Entity
```

- 이 클래스는 데이터베이스 테이블과 연결된 엔터티입니다 !

```java
@Table(name = "web_vitals")
```

- 매핑할 DB 테이블: web_vitals

```java
@NoArgsConstructor(access = AccessLevle.PROTECTED)
```

- 기본 생성자를 자동으로 만들어줌
- 접근 제한자는 protected 로

### 예시

```java
@NoArgsConstructor
public class User {
    private String name;
    private int age;
}

// ->

protected User(){}
```

```java
@AllArgsConstructor
```

- 모든 필드를 인자로 받는 생성자를 자동으로 만들어줌

### 예시

```java
@AllArgsConstructor
public class User {
    private String name;
    private int age;
}

// ->

public User(String name, int age) {
    this.name = name;
    this.age = age;
}
```

```java
@Builder
```

- 빌더 패턴을 자동으로 만들어줌

**빌더 패턴?**

- new 대신 체이닝으로 객체 만들기

### 예시

```java
@Builder
public class User {
    private String name;
    private int age;
}

->

User user = User.builder()
    .name("홍길동")
    .age(25)
    .build();
```

```java
@Id
```

- 이 필드가 기본 키

```java
@GeneratedValue(strategy = GenerationRtpe.UUID)
```

- id 값을 자동으로 uuid 로 생성

```java
@Column(name = "test_id", nullable = false, unique = true)
```

- DB 컬럼명, 필수, 중복 불가

```java
@CreationTimestamp
```

- 엔터티가 처음 생성될 때의 시간을 자동으로 넣어줌
