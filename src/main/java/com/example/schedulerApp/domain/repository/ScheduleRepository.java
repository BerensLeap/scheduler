package com.example.schedulerApp.domain.repository;

import com.example.schedulerApp.domain.domain.Schedule;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Slf4j
@Repository
@RequiredArgsConstructor
public class ScheduleRepository {

    private final DataSource dataSource;


    public Long save(String creatorName, String password, String creatorId) throws SQLException {  // 일정 생성
        String sql = "INSERT INTO schedule (creator_name, password, creator_id, created_at, updated_at) VALUES (?, ?, ?, ?, ?)";

        try (Connection con = dataSource.getConnection();
             PreparedStatement pstmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, creatorName);
            pstmt.setString(2, password);
            pstmt.setString(3, creatorId);
            pstmt.setTimestamp(4, Timestamp.valueOf(java.time.LocalDateTime.now()));
            pstmt.setTimestamp(5, Timestamp.valueOf(java.time.LocalDateTime.now()));

            pstmt.executeUpdate();

            try (ResultSet rs = pstmt.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getLong(1);
                }
                throw new IllegalStateException("일정이 올바르게 생성되지 않았습니다.");
            }
        }
    }


    public List<Schedule> findAll(String creatorName, Date updatedDate) throws SQLException {   // 전체 일정 조회
        StringBuilder query = new StringBuilder("SELECT * FROM schedule WHERE 1=1");
        List<Object> params = new ArrayList<>();

        if (creatorName != null && !creatorName.isEmpty()) {
            query.append(" AND creator_name = ?");
            params.add(creatorName);
        }
        if (updatedDate != null) {
            query.append(" AND DATE(updated_at) = ?");
            params.add(updatedDate);
        }
        query.append(" ORDER BY updated_at DESC");

        try (Connection con = dataSource.getConnection();
             PreparedStatement pstmt = con.prepareStatement(query.toString())) {

            for (int i = 0; i < params.size(); i++) {
                pstmt.setObject(i + 1, params.get(i));
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


    public Schedule findById(Long id) throws SQLException { // 선택 일정 조회
        String sql = "SELECT * FROM schedule WHERE id = ?";

        try (Connection con = dataSource.getConnection();
             PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setLong(1, id);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return mapToSchedule(rs);
                }
                throw new NoSuchElementException("해당 ID의 일정이 존재하지 않습니다: " + id);
            }
        }
    }


    public void update(Long id, String todo, String creatorName) throws SQLException {     // 선택 일정 수정
        String sql = "UPDATE schedule SET todo = ?, creator_name = ?, updated_at = ? WHERE id = ?";

        try (Connection con = dataSource.getConnection();
             PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setString(1, todo);
            pstmt.setString(2, creatorName);
            pstmt.setTimestamp(3, Timestamp.valueOf(java.time.LocalDateTime.now()));
            pstmt.setLong(4, id);

            int rows = pstmt.executeUpdate();
            if (rows == 0) {
                throw new IllegalStateException("수정할 수 없습니다. ID가 잘못되었습니다: " + id);
            }
        }
    }


    public void delete(Long id) throws SQLException { // 선택 일정 삭제
        String sql = "DELETE FROM schedule WHERE id = ?";

        try (Connection con = dataSource.getConnection();
             PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setLong(1, id);

            int rows = pstmt.executeUpdate();
            if (rows == 0) {
                throw new IllegalStateException("삭제할 수 없습니다. ID가 잘못되었습니다: " + id);
            }
        }
    }


    private Schedule mapToSchedule(ResultSet rs) throws SQLException { // ResultSet -> Schedule 객체 매핑
        return new Schedule(
                rs.getString("id"),
                rs.getString("creator_name"),
                rs.getString("password"),
                rs.getString("creator_id"),
                rs.getTimestamp("created_at").toLocalDateTime(),
                rs.getTimestamp("updated_at").toLocalDateTime()
        );
    }
}