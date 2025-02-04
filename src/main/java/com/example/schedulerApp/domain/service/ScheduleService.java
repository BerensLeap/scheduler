package com.example.schedulerApp.domain.service;


import com.example.schedulerApp.domain.domain.Schedule;
import com.example.schedulerApp.domain.dto.ScheduleRequestDTO;
import com.example.schedulerApp.domain.dto.ScheduleResponseDTO;
import com.example.schedulerApp.domain.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class ScheduleService {

    private final ScheduleRepository repository;

    public ScheduleResponseDTO createSchedule(ScheduleRequestDTO requestDTO) {
        Schedule schedule = new Schedule();    // Schedule 엔티티 생성, 데이터 세팅
        schedule.setId(UUID.randomUUID().toString());
        schedule.setCreatorName(requestDTO.getCreatorName());
        schedule.setPassword(requestDTO.getPassword());
        schedule.setCreatorId(requestDTO.getCreatorId());
        schedule.setCreatedAt(LocalDateTime.now());
        schedule.setUpdatedAt(LocalDateTime.now());


        Schedule createdSchedule = repository.save(schedule);  // 데이터 저장


        ScheduleResponseDTO responseDTO = new ScheduleResponseDTO();  // 응답 DTO 생성
        responseDTO.setId(createdSchedule.getId());
        responseDTO.setCreatorName(createdSchedule.getCreatorName());
        responseDTO.setCreatorId(createdSchedule.getCreatorId());
        responseDTO.setCreatedAt(createdSchedule.getCreatedAt());
        responseDTO.setUpdatedAt(createdSchedule.getUpdatedAt());

        return responseDTO;
}
