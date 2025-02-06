package com.example.schedulerApp.domain.service;

import com.example.schedulerApp.domain.domain.Schedule;
import com.example.schedulerApp.domain.domain.User;
import com.example.schedulerApp.domain.dto.ScheduleRequestDTO;
import com.example.schedulerApp.domain.dto.ScheduleResponseDTO;
import com.example.schedulerApp.domain.repository.ScheduleRepository;
import com.example.schedulerApp.domain.repository.UserRepository; // UserRepository 추가
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final UserRepository userRepository; // UserRepository 주입

    // 일정 생성
    public ScheduleResponseDTO createSchedule(ScheduleRequestDTO requestDTO) throws SQLException {
        // DTO -> Entity 변환
        Schedule schedule = new Schedule(
                requestDTO.getTodo(),
                requestDTO.getPassword(),
                requestDTO.getCreatorId()
        );

        // User 객체를 찾아서 Schedule에 설정
        User creator = userRepository.findById(requestDTO.getCreatorId());
        schedule.setCreator(creator); // User 객체 설정

        // 저장
        Schedule savedSchedule = scheduleRepository.save(schedule);

        // Entity -> DTO 변환
        return mapToResponseDTO(savedSchedule);
    }

    // 전체 일정 조회
    public List<ScheduleResponseDTO> getAllSchedules(String creatorId) throws SQLException {
        List<Schedule> schedules = scheduleRepository.findAll(creatorId);

        // Entity 리스트를 DTO 리스트로 변환
        List<ScheduleResponseDTO> dtoList = new ArrayList<>();
        for (Schedule schedule : schedules) {
            dtoList.add(mapToResponseDTO(schedule));
        }
        return dtoList;
    }

    // ID로 일정 조회
    public ScheduleResponseDTO getScheduleById(String id) throws SQLException {
        Schedule schedule = scheduleRepository.findById(id);

        // Entity -> DTO 변환
        return mapToResponseDTO(schedule);
    }

    // 일정 수정
    public void updateSchedule(String id, String todo) throws SQLException {
        scheduleRepository.update(id, todo);
    }

    // 일정 삭제
    public void deleteSchedule(String id) throws SQLException {
        scheduleRepository.delete(id);
    }

    // Entity -> ResponseDTO 변환
    private ScheduleResponseDTO mapToResponseDTO(Schedule schedule) {
        return new ScheduleResponseDTO(
                schedule.getId(),
                schedule.getTodo(),
                schedule.getCreator().getName(),
                schedule.getCreator().getId(),
                schedule.getCreatedAt(),
                schedule.getUpdatedAt()
        );
    }
}