package com.login.login.Dao;

import com.login.login.Model.Notice;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NoticeDao extends JpaRepository<Notice, Integer> {

    @Query("SELECT COUNT(n) FROM Notice n")
    int countNotices();

    Page<Notice> findAll(Pageable pageable);

    @Query("SELECT n.id FROM Notice n WHERE n.id < :id ORDER BY n.id DESC")
    List<Integer> getPrevNoticeId(@Param("id") int id, Pageable pageable);

    @Query("SELECT n.id FROM Notice n WHERE n.id > :id ORDER BY n.id ASC")
    List<Integer> getNextNoticeId(@Param("id") int id, Pageable pageable);

    @Modifying
    @Query("UPDATE Notice n SET n.viewer = n.viewer + 1 WHERE n.id = :id")
    void incrementViewer(@Param("id") int id);

    @Modifying
    @Query("DELETE FROM Notice n WHERE n.id = :id")
    void deleteNotice(int id);
}
