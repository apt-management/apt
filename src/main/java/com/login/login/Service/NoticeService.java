package com.login.login.Service;

import com.login.login.Model.Notice;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoticeService {

    // 총 공지사항 개수 반환
    public int getTotalNotices() {
        // 실제 DB 쿼리로 총 공지사항 개수를 반환
        return 100;  // 예시로 100개로 설정
    }

    // 특정 페이지에 해당하는 공지사항 리스트 반환
    public List<Notice> getNotices(int page, int pageSize) {
        // 실제 DB 쿼리로 특정 페이지에 해당하는 공지사항 리스트 반환
        return List.of(new Notice(1, "공지사항 1", "2025-02-01", 10),
                new Notice(2, "공지사항 2", "2025-02-02", 20));  // 예시 데이터
    }
}
