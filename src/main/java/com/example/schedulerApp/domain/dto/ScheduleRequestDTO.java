package com.example.schedulerApp.domain.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ScheduleRequestDTO {
    private String creatorName;
    private String password;
    private String creatorId;
}