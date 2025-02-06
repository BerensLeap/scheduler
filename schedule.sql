CREATE TABLE users (
                       id VARCHAR(36) PRIMARY KEY,  -- UUID 형식으로 변경
                       name VARCHAR(15) NOT NULL,
                       email VARCHAR(255) NOT NULL,
                       created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
                       updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE schedules (
                           id VARCHAR(36) PRIMARY KEY,  -- UUID 형식으로 변경
                           todo VARCHAR(255) NOT NULL,  -- 일정 내용
                           password VARCHAR(80) NOT NULL,
                           creator_id VARCHAR(36),  -- User 테이블의 id를 외래키로 연결
                           created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
                           updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                           FOREIGN KEY (creator_id) REFERENCES users(id)  -- 외래키 설정
);