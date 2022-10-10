package com.deadline.knunotice.notice;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface NoticeRepository extends JpaRepository<Notice, Long> {

    Page<Notice> findAllByTitleContainingOrderByCreatedDate(String word, Pageable pageable);

    @Query(value = "SELECT * FROM member_to_notice mtn RIGHT OUTER JOIN notice ON mtn.id = notice.id ORDER BY is_bookmark DESC",
            countQuery = "SELECT count(notice.id) FROM member_to_notice mtn RIGHT OUTER JOIN notice ON mtn.id = notice.id",
            nativeQuery = true)
    Page<Notice> findNoticeDto(Pageable page);

}