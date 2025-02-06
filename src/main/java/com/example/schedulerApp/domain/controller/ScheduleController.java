package com.example.schedulerApp.domain.controller;

import com.example.schedulerApp.domain.dto.ScheduleRequestDTO;
import com.example.schedulerApp.domain.dto.ScheduleResponseDTO;
import com.example.schedulerApp.domain.repository.ScheduleRepository;
import com.example.schedulerApp.domain.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/schedules")
public class ScheduleController {

    private final ScheduleService service;
    private final ScheduleRepository repository;

    // 일정 생성
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<ScheduleResponseDTO> createSchedule(@ModelAttribute ScheduleRequestDTO requestDTO) throws SQLException {
        ScheduleResponseDTO responseDTO = service.createSchedule(requestDTO);
        return ResponseEntity.ok(responseDTO);
    }

    // 전체 일정 조회
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<ScheduleResponseDTO>> getAllSchedules(@RequestParam(required = false) String creatorId) throws SQLException {
        List<ScheduleResponseDTO> schedules = service.getAllSchedules(creatorId);
        return ResponseEntity.ok(schedules);
    }

    // 선택 일정 조회
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ScheduleResponseDTO> getScheduleById(@PathVariable String id) throws SQLException {
        ScheduleResponseDTO schedule = service.getScheduleById(id);
        return ResponseEntity.ok(schedule);
    }

    // 일정 수정
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> updateSchedule(@PathVariable String id, @RequestBody ScheduleRequestDTO requestDTO) throws SQLException {
        service.updateSchedule(id, requestDTO.getTodo());
        return ResponseEntity.ok("일정이 정상적으로 수정되었습니다.");
    }

    // 일정 삭제
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> deleteSchedule(@PathVariable String id) throws SQLException {
        service.deleteSchedule(id);
        return ResponseEntity.ok("일정이 정상적으로 삭제되었습니다.");
    }


}
