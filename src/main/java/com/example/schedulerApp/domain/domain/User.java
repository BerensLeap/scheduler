package com.example.schedulerApp.domain.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
public class User {
    private String id = UUID.randomUUID().toString(); // 랜덤한 UUID값 생성 후, id값으로 저장
    private String name;
    private String email;
    private LocalDateTime createdAt = LocalDateTime.now();
    private LocalDateTime updatedAt = LocalDateTime.now();
    private List<Schedule> schedules = new ArrayList<>(); // 1:N 관계

}
