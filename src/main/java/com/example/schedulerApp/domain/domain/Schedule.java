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

    private String creatorName;
    private String password;
    private String creatorId; // User의 id값 참조
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;


    public Schedule(String id, String creatorName, String password) {
        this.id = UUID.randomUUID().toString(); // 랜덤한 UUID값 생성 후, id값으로 저장
        this.creatorName = creatorName;
        this.password = password;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public void update() { // 내용 수정 시에 날짜 업데이트
        this.updatedAt = LocalDateTime.now();
    }
}
