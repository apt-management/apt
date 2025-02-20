package com.login.login.Repository;

import com.login.login.Model.Notice;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class NoticeRepository {

    private final JdbcTemplate jdbcTemplate;

    public NoticeRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Notice> findRecentNotices(int limit) {
        String sql = "SELECT id, title FROM notice ORDER BY id DESC LIMIT ?";

        List<Notice> notices = jdbcTemplate.query(sql, (rs, rowNum) -> {
            Notice notice = new Notice();
            notice.setId(rs.getInt("id"));
            notice.setTitle(rs.getString("title"));
            return notice;
        }, limit);

        System.out.println("조회된 공지사항 개수: " + notices.size());
        return notices;
    }
}
