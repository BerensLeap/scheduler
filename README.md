# Spring 기초 일정 관리 앱 서버 구현 과제

---

**목적**  
SpringMVC와 JDBC를 활용하여 MySQL에 데이터를 저장하는 웹서비스 개발

**기간**  
2025.1.27 ~ 2025.2.3

**사용 기술**  
- SpringMVC  
- JDBC  
- MySQL

**개발 환경**  
- temurin-17  
- IntelliJ

---

## API & ERD 명세

### 1. 스케줄 관련 API

| HTTP Method | URI                     | 설명               |
|-------------|-------------------------|--------------------|
| POST        | /schedules               | 일정 생성          |
| GET         | /schedules               | 전체 일정 조회     |
| GET         | /schedules/{id}          | 특정 일정 조회     |
| PUT         | /schedules/{id}          | 일정 수정          |
| DELETE      | /schedules/{id}          | 일정 삭제          |

### 2. ERD

**users 테이블**  
작성자 정보 관리 (ID, 이름, 이메일)

**schedules 테이블**  
일정 내용 및 작성자 ID (`creator_id`)를 포함

-- users 테이블 생성 (작성자 정보 관리)
CREATE TABLE users (
    id          VARCHAR(36) PRIMARY KEY,         -- 고유 ID
    name        VARCHAR(50) NOT NULL,            -- 작성자 이름
    email       VARCHAR(255) NOT NULL,           -- 작성자 이메일
    created_at  DATETIME DEFAULT CURRENT_TIMESTAMP, -- 생성 시간
    updated_at  DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP -- 수정 시간
);

-- schedules 테이블 생성 (일정 관리)
CREATE TABLE schedules (
    id          VARCHAR(36) PRIMARY KEY,         -- 고유 ID
    todo        VARCHAR(255) NOT NULL,           -- 일정 내용
    password    VARCHAR(80) NOT NULL,            -- 일정 비밀번호
    creator_id  VARCHAR(36) NOT NULL,            -- 작성자 ID (외래키)
    created_at  DATETIME DEFAULT CURRENT_TIMESTAMP, -- 생성 시간
    updated_at  DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP, -- 수정 시간
    FOREIGN KEY (creator_id) REFERENCES users(id)  -- 외래키 연결 (users 테이블)
);

### 3. CRUD 구현 내역
*일정 생성
  POST /schedules: 클라이언트에서 받은 데이터로 일정 생성 후 저장.
*전체 일정 조회
  GET /schedules: 작성자 ID로 일정 조회.
*특정 일정 조회
  GET /schedules/{id}: 일정 ID로 특정 일정 조회.
*일정 수정
  PUT /schedules/{id}: 일정 내용 수정.
*일정 삭제
  DELETE /schedules/{id}: 일정 삭제.

### 4. 예외 처리

*일정이 없을 때: SQLException 예외 처리.
*수정 시 유효하지 않은 ID: 예외 발생.
*DB 연결 오류: 적절한 오류 메시지 제공.

### 5. 구조 및 특징 

*서비스: 비즈니스 로직 처리 (ScheduleService)
*리포지토리: DB 연동 (ScheduleRepository)
*DTO: ScheduleRequestDTO와 ScheduleResponseDTO로 클라이언트와 데이터 교환
*JDBC: MySQL과의 연결을 통해 CRUD 작업 수행

### 6. 기타 

*데이터베이스: users와 schedules 테이블 간 외래키 연결.
*HTTP 상태 코드: 정상 응답은 200, 오류는 404/500으로 처리.





