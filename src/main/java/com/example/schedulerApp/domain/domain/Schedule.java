package com.example.schedulerApp.domain.domain;

import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Schedule {

    private String id; // id값은 UUID
    private String todo;
    private String password;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private User creator;
    private String creatorId; // User의 id (users 테이블의 FK, UUID값)


    public Schedule(String id, String todo, String password) {
        this.id = UUID.randomUUID().toString(); // 랜덤한 UUID값 생성 후, id값으로 저장
        this.todo = todo;
        this.password = password;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    public void update() { // 내용 수정 시에 날짜 업데이트
        this.updatedAt = LocalDateTime.now();
    }

    // 작성자(User 객체)를 설정하는 메서드
    public void setCreator(User creator) {
        this.creator = creator;           // User 객체를 설정
        this.creatorId = creator.getId(); // User의 ID를 creatorId에 자동 설정
    }
}