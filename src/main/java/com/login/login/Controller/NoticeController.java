package com.login.login.Controller;

import com.login.login.Model.Notice;
import com.login.login.Service.NoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class NoticeController {

    @Autowired
    private NoticeService noticeService;  // 공지사항 서비스를 주입받습니다.

    // 공지사항 페이지 요청 처리
    @RequestMapping("/notice")
    public String noticePage() {
        return "notice";  // notice.html을 반환합니다.
    }

    // Ajax로 공지사항 리스트와 페이지네이션을 처리하는 메서드
    @GetMapping("/notices")
    @ResponseBody
    public Map<String, Object> getNoticesAjax(@RequestParam(defaultValue = "1") int page) {
        int pageSize = 5;  // 한 페이지에 보여줄 공지사항 수
        int totalNotices = noticeService.getTotalNotices(); // 공지사항의 총 개수
        int totalPages = (int) Math.ceil((double) totalNotices / pageSize); // 페이지 수 계산

        // 페이지 범위 보정: 사용자가 요청한 페이지가 유효한지 체크
        if (page < 1) {
            page = 1;
        } else if (page > totalPages) {
            page = totalPages;
        }

        // 공지사항 리스트 가져오기
        List<Notice> notices = noticeService.getNotices(page, pageSize);

        // 페이지네이션 HTML 생성
        StringBuilder paginationHtml = new StringBuilder();
        paginationHtml.append("<a href='#none' class='page-move page-prev'>이전 페이지</a>");

        for (int i = 1; i <= totalPages; i++) {
            paginationHtml.append("<a href='#none' class='").append(i == page ? "now" : "").append("'>")
                    .append(i).append("</a>");
        }

        paginationHtml.append("<a href='#none' class='page-move page-next'>다음 페이지</a>");

        // 공지사항 HTML 생성
        StringBuilder noticesHtml = new StringBuilder();
        for (Notice notice : notices) {
            noticesHtml.append("<tr>")
                    .append("<td>").append(notice.getId()).append("</td>")
                    .append("<td><a href='#none'>").append(notice.getTitle()).append("</a></td>")
                    .append("<td>").append(notice.hasAttachment() ? "<img src='/images/icon-file.svg' alt='파일' class='file'>" : "").append("</td>")
                    .append("<td>").append(notice.getCreateDate()).append("</td>")
                    .append("<td>").append(notice.getViews()).append("</td>")
                    .append("</tr>");
        }

        // 응답할 데이터 설정
        Map<String, Object> response = new HashMap<>();
        response.put("noticesHtml", noticesHtml.toString());
        response.put("paginationHtml", paginationHtml.toString());

        return response;
    }
}
