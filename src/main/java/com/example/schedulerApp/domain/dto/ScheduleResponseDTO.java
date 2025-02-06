package com.example.schedulerApp.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class ScheduleResponseDTO {
    private String id; // 스케줄 ID
    private String todo; // 일정 내용
    private String creatorName; // 작성자 이름
    private String creatorId; // 작성자 ID
    private LocalDateTime createdAt; // 생성 시간
    private LocalDateTime updatedAt; // 업데이트 시간
}