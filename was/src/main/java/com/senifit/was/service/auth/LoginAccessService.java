package com.senifit.was.service.auth;

import com.senifit.was.entity.LoginAccessStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
@Slf4j
public class LoginAccessService {

    private final JdbcTemplate jdbcTemplate;
    
    private static final DateTimeFormatter TABLE_SUFFIX_FORMAT = DateTimeFormatter.ofPattern("yyyyMM");
    
    /**
     * 로그인 이력을 월별 테이블에 저장
     */
    @Transactional
    public void saveLoginAccess(String loginId, String centerName, String ipAddress, LoginAccessStatus status) {
        try {
            LocalDateTime now = LocalDateTime.now();
            String tableName = "login_access_" + now.format(TABLE_SUFFIX_FORMAT);
            
            // 테이블이 없으면 생성
            createTableIfNotExists(tableName);
            
            // 로그인 이력 저장
            String sql = String.format(
                "INSERT INTO %s (login_id, center_name, ip_address, status, access_time) VALUES (?, ?, ?, ?, ?)",
                tableName
            );
            
            jdbcTemplate.update(sql, loginId, centerName, ipAddress, status.name(), now);
            
        } catch (Exception e) {
            log.error("Failed to save login access: {}", e.getMessage(), e);
            // 로그 저장 실패가 로그인을 막지 않도록 예외를 던지지 않음
        }
    }
    
    /**
     * 월별 테이블이 존재하지 않으면 생성
     */
    private void createTableIfNotExists(String tableName) {
        String checkTableSql = String.format(
            "SELECT COUNT(*) FROM information_schema.tables " +
            "WHERE table_schema = DATABASE() AND table_name = '%s'",
            tableName
        );
        
        Integer count = jdbcTemplate.queryForObject(checkTableSql, Integer.class);
        
        if (count == null || count == 0) {
            String createTableSql = String.format(
                "CREATE TABLE %s (" +
                "    id BIGINT AUTO_INCREMENT PRIMARY KEY," +
                "    login_id VARCHAR(255) NOT NULL," +
                "    center_name VARCHAR(255) NOT NULL," +
                "    ip_address VARCHAR(45) NOT NULL," +
                "    status VARCHAR(20) NOT NULL," +
                "    access_time DATETIME NOT NULL," +
                "    INDEX idx_login_id (login_id)," +
                "    INDEX idx_access_time (access_time)" +
                ")",
                tableName
            );
            
            jdbcTemplate.execute(createTableSql);
        }
    }
}
