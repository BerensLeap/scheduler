package com.example.schedulerApp.domain.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ScheduleRequestDTO {

/*
    private String id; // 스케줄 ID
    private LocalDateTime createdAt; // 생성 시간
    private LocalDateTime updatedAt; // 업데이트 시간

*/  // 서버가 생성하는 부분.

    private String todo; // 일정 내용
    private String password; // 비밀번호
    private String creatorId; // 작성자 ID
//    private String creatorName; // 작성자 이름, creatorID 를 통해 DB에서 가져오는게 더 효율적임.

}