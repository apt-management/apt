/*

package com.login.login.Service;

import com.login.login.Mapper.NoticeMapper;
import com.login.login.Model.Notice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoticeServiceImpl implements NoticeService {

    @Autowired
    private NoticeMapper noticeMapper;

    @Override
    public List<Notice> getNotices(int page, int limit) {
        int offset = (page - 1) * limit;
        return noticeMapper.getNotices(limit, offset);
    }

    @Override
    public int getTotalNotices() {
        return noticeMapper.getTotalNotices();
    }
}
*/

