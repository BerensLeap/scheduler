package com.example.schedulerApp.domain.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ScheduleResponseDTO {
    private String id;
    private String creatorName;
    private String creatorId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
