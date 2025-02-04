package com.example.schedulerApp.domain.controller;

import com.example.schedulerApp.domain.domain.Schedule;
import com.example.schedulerApp.domain.dto.ScheduleRequestDTO;
import com.example.schedulerApp.domain.dto.ScheduleResponseDTO;
import com.example.schedulerApp.domain.repository.ScheduleRepository;
import com.example.schedulerApp.domain.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/schedules")
public class ScheduleController {

    private final ScheduleService service;
    private final ScheduleRepository repository;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<ScheduleResponseDTO> createSchedule(@ModelAttribute ScheduleRequestDTO requestDTO) {
        ScheduleResponseDTO responseDTO = service.createSchedule(requestDTO);
        return ResponseEntity.ok(responseDTO);
    }
}
