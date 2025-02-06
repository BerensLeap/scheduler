package com.example.schedulerApp.domain.repository;

import com.example.schedulerApp.domain.domain.Schedule;
import com.example.schedulerApp.domain.domain.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Repository
@RequiredArgsConstructor
public class ScheduleRepository {

    private final DataSource dataSource;

    // 일정 저장 (Entity 반환)
    public Schedule save(Schedule schedule) throws SQLException {
        String sql = "INSERT INTO schedule (id, todo, password, creator_id, created_at, updated_at) " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection con = dataSource.getConnection();
             PreparedStatement pstmt = con.prepareStatement(sql)) {

            pstmt.setString(1, schedule.getId());
            pstmt.setString(2, schedule.getTodo());
            pstmt.setString(3, schedule.getPassword());
            pstmt.setString(4, schedule.getCreatorId());
            pstmt.setTimestamp(5, Timestamp.valueOf(schedule.getCreatedAt()));
            pstmt.setTimestamp(6, Timestamp.valueOf(schedule.getUpdatedAt()));

            pstmt.executeUpdate();
            return schedule;
        }
    }

    // 전체 일정 조회
    public List<Schedule> findAll(String creatorId) throws SQLException {
        String sql = "SELECT s.*, u.name AS creator_name, u.email AS creator_email " +
                "FROM schedule s " +
                "JOIN users u ON s.creator_id = u.id";

        if (creatorId != null && !creatorId.isEmpty()) {
            sql += " WHERE s.creator_id = ?";
        }

        try (Connection con = dataSource.getConnection();
             PreparedStatement pstmt = con.prepareStatement(sql)) {

            if (creatorId != null && !creatorId.isEmpty()) {
                pstmt.setString(1, creatorId);
            }

            try (ResultSet rs = pstmt.executeQuery()) {
                List<Schedule> schedules = new ArrayList<>();
                while (rs.next()) {
                    schedules.add(mapToSchedule(rs));
                }
                return schedules;
            }
        }
    }

    // ID로 일정 조회
    public Schedule findById(String id) throws SQLException {
        String sql = "SELECT s.*, u.name AS creator_name, u.email AS creator_email " +
                "FROM schedule s " +
                "JOIN users u ON s.creator_id = u.id " +
                "WHERE s.id = ?";

        try (Connection con = dataSource.getConnection();
             PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setString(1, id);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return mapToSchedule(rs);
                } else {
                    throw new SQLException("해당 ID의 일정이 존재하지 않습니다: " + id);
                }
            }
        }
    }

    // 일정 수정
    public void update(String id, String todo) throws SQLException {
        String sql = "UPDATE schedule SET todo = ?, updated_at = ? WHERE id = ?";

        try (Connection con = dataSource.getConnection();
             PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setString(1, todo);
            pstmt.setTimestamp(2, Timestamp.valueOf(java.time.LocalDateTime.now()));
            pstmt.setString(3, id);

            int rows = pstmt.executeUpdate();
            if (rows == 0) {
                throw new SQLException("수정할 수 없습니다. ID가 잘못되었습니다: " + id);
            }
        }
    }

    // 일정 삭제
    public void delete(String id) throws SQLException {
        String sql = "DELETE FROM schedule WHERE id = ?";

        try (Connection con = dataSource.getConnection();
             PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setString(1, id);

            int rows = pstmt.executeUpdate();
            if (rows == 0) {
                throw new SQLException("삭제할 수 없습니다. ID가 잘못되었습니다: " + id);
            }
        }
    }

    // ResultSet을 Schedule 객체로 매핑
    private Schedule mapToSchedule(ResultSet rs) throws SQLException {
        Schedule schedule = new Schedule();
        schedule.setId(rs.getString("id"));
        schedule.setTodo(rs.getString("todo"));
        schedule.setPassword(rs.getString("password"));
        schedule.setCreatorId(rs.getString("creator_id"));
        schedule.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
        schedule.setUpdatedAt(rs.getTimestamp("updated_at").toLocalDateTime());

        // User 객체 매핑
        User user = new User();
        user.setId(rs.getString("creator_id"));
        user.setName(rs.getString("creator_name"));
        user.setEmail(rs.getString("creator_email"));
        schedule.setCreator(user);

        return schedule;
    }
}
