package com.login.login.Service;

import com.login.login.Model.Notice;

import java.util.List;

public interface NoticeService {

    int getTotalCount();
    List<Notice> getNoticesByPage(int page, int pageSize);

    void addNotice(Notice notice);

    Notice getNoticeById(int id);

    Integer getPrevNoticeId(int id);

    Integer getNextNoticeId(int id);

    void incrementViewer(int id);

    List<Notice> getRecentNotices(int limit);

    void deleteNotice(int id);

    void updateNotice(Notice notice);
}