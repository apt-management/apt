package com.login.login.Service;

import com.login.login.Dao.NoticeDao;
import com.login.login.Model.Notice;
import com.login.login.Repository.NoticeRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class NoticeServiceImpl implements NoticeService {

    @Autowired
    private NoticeDao noticeDao;

    @Autowired
    private NoticeRepository noticeRepository;

    @Override
    public int getTotalCount() {
        return noticeDao.countNotices();
    }

    public int getTotalPages(int pageSize) {
        int totalCount = getTotalCount();
        return (int) Math.ceil((double) totalCount / pageSize);
    }

    @Override
    public List<Notice> getNoticesByPage(int page, int pageSize) {
        int totalPages = getTotalPages(pageSize);

        if (page < 1) {
            page = 1;
        } else if (page > totalPages && totalPages > 0) {
            page = totalPages;
        }

        Pageable pageable = PageRequest.of(page - 1, pageSize);
        Page<Notice> noticesPage = noticeDao.findAll(pageable);
        return noticesPage.getContent();
    }

    @Override
    public void addNotice(Notice notice) {
        noticeDao.save(notice);
    }

    @Override
    public Notice getNoticeById(int id) {
        return noticeDao.findById(id)
                .orElseThrow(() -> new RuntimeException("공지사항을 찾을 수 없습니다. ID: " + id));
    }

    @Override
    public Integer getPrevNoticeId(int id) {
        List<Integer> result = noticeDao.getPrevNoticeId(id, PageRequest.of(0, 1));
        return result.isEmpty() ? null : result.get(0);
    }

    @Override
    public Integer getNextNoticeId(int id) {
        List<Integer> result = noticeDao.getNextNoticeId(id, PageRequest.of(0, 1));
        return result.isEmpty() ? null : result.get(0);
    }

    @Override
    public void incrementViewer(int id) {
        Notice notice = noticeDao.findById(id)
                .orElseThrow(() -> new RuntimeException("공지사항을 찾을 수 없습니다. ID: " + id));

        notice.setViewer(notice.getViewer() + 1);
        noticeDao.save(notice);
    }

    public NoticeServiceImpl(NoticeRepository noticeRepository) {
        this.noticeRepository = noticeRepository;
    }

    @Override
    public List<Notice> getRecentNotices(int limit) {
        return noticeRepository.findRecentNotices(limit);
    }

    @Override
    @Transactional
    public void deleteNotice(int id) {
        noticeDao.deleteNotice(id);
    }

    @Override
    public void updateNotice(Notice notice) {
        noticeRepository.save(notice);
    }

}
