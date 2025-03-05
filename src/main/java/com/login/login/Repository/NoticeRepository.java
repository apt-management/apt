package com.login.login.Repository;

import com.login.login.Model.Notice;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class NoticeRepository {

    private final JdbcTemplate jdbcTemplate;

    public NoticeRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // 최근 공지사항 조회
    public List<Notice> findRecentNotices(int limit) {
        String sql = "SELECT id, title FROM notice ORDER BY id DESC LIMIT ?";

        List<Notice> notices = jdbcTemplate.query(sql, new Object[]{limit}, (rs, rowNum) -> {
            Notice notice = new Notice();
            notice.setId(rs.getInt("id"));
            notice.setTitle(rs.getString("title"));
            return notice;
        });

        System.out.println("조회된 공지사항 개수: " + notices.size());
        return notices;
    }

    // 공지사항 ID로 조회
    public Notice findById(int id) {
        String sql = "SELECT * FROM notice WHERE id = ?";

        List<Notice> notices = jdbcTemplate.query(sql, new Object[]{id}, new RowMapper<Notice>() {
            @Override
            public Notice mapRow(java.sql.ResultSet rs, int rowNum) throws java.sql.SQLException {
                Notice notice = new Notice();
                notice.setId(rs.getInt("id"));
                notice.setTitle(rs.getString("title"));
                notice.setContent(rs.getString("content"));
                notice.setAttach(rs.getString("attach"));
                return notice;
            }
        });

        return notices.isEmpty() ? null : notices.get(0);  // 결과가 없으면 null 반환
    }

    // 공지사항 저장 (Insert 또는 Update 처리)
    public void save(Notice notice) {
        if (notice.getId() == 0) {  // id가 0이면 새 객체로 판단
            // Insert - 새 공지사항 추가
            String sql = "INSERT INTO notice (title, content, attach) VALUES (?, ?, ?)";
            jdbcTemplate.update(sql, notice.getTitle(), notice.getContent(), notice.getAttach());
        } else {
            // Update - 기존 공지사항 수정
            String sql = "UPDATE notice SET title = ?, content = ?, attach = ? WHERE id = ?";
            jdbcTemplate.update(sql, notice.getTitle(), notice.getContent(), notice.getAttach(), notice.getId());
        }
    }
}
