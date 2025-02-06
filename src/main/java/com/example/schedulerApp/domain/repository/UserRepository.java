package com.example.schedulerApp.domain.repository;

import com.example.schedulerApp.domain.domain.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Slf4j
@Repository
@RequiredArgsConstructor
public class UserRepository {

    private final DataSource dataSource;

    // ID로 사용자 조회
    public User findById(String id) throws SQLException {
        String sql = "SELECT * FROM users WHERE id = ?";

        try (Connection con = dataSource.getConnection();
             PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setString(1, id);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return mapToUser(rs);
                } else {
                    throw new SQLException("해당 ID의 사용자가 존재하지 않습니다: " + id);
                }
            }
        }
    }

    // ResultSet을 User 객체로 매핑
    private User mapToUser(ResultSet rs) throws SQLException {
        User user = new User();
        user.setId(rs.getString("id"));
        user.setName(rs.getString("name"));
        user.setEmail(rs.getString("email"));
        return user;
    }
}
