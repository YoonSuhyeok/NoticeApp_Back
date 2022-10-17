package com.deadline.knunotice.notice;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NoticeRepository extends JpaRepository<Notice, Long> {

    Page<Notice> findAllByTitleContainingOrderByCreatedDate(String word, Pageable pageable);

    @Query(value = "select n.id as id, n.title as title, n.url as url, n.created_date as created_date, n.created_at as created_at, n.updated_at as updated_at, m.name as major_name, IFNULL(mtn.is_bookmark, false) as is_bookmark, IFNULL(mtn.is_pin, false) as is_pin " +
            "from (select mtn.notice_id, mtn.is_bookmark, mtn.is_pin from member_to_notice mtn where mtn.member_id = :memberId) mtn " +
            "right join notice n on n.id = mtn.notice_id join major m on n.major_id = m.id",
            countQuery = "select n.id, n.title, n.url, n.created_date, n.created_at, n.updated_at, n.major_id, IFNULL(mtn.is_bookmark, false), IFNULL(mtn.is_pin, false) " +
            "from (select mtn.notice_id, mtn.is_bookmark, mtn.is_pin from member_to_notice mtn where mtn.member_id = :memberId) mtn " +
            "right join notice n on n.id = mtn.notice_id",
            nativeQuery = true)
    Page<NoticeAndETC> findNoticeDto(@Param("memberId") Long mId, Pageable page);

    @Query(value = "select n.id, n.title, n.url, n.created_date, n.created_at, n.updated_at, m.name as major_name, IFNULL(mtn.is_bookmark, false) as is_bookmark, IFNULL(mtn.is_pin, false) as is_pin from (select mtn.notice_id, mtn.is_bookmark, mtn.is_pin from member_to_notice mtn where mtn.member_id = :memberId) mtn right join notice n on n.id = mtn.notice_id join major m on n.major_id = m.id where n.title like concat('%', :keyword, '%')",
            countQuery = "select n.id, n.title, n.url, n.created_date, n.created_at, n.updated_at, m.name as major_name, IFNULL(mtn.is_bookmark, false) as is_bookmark, IFNULL(mtn.is_pin, false) as is_pin from (select mtn.notice_id, mtn.is_bookmark, mtn.is_pin from member_to_notice mtn where mtn.member_id = :memberId) mtn right join notice n on n.id = mtn.notice_id join major m on n.major_id = m.id where n.title like concat('%', :keyword, '%')",
            nativeQuery = true)
    Page<NoticeAndETC> findNoticeDtoForKeyword(@Param("memberId") Long mId, @Param("keyword") String keyword, Pageable page);

    @Query(value = "select notice.id, notice.title, notice.url, notice.created_date, notice.created_at, notice.updated_at, major.name as major_name from notice join major on notice.major_id = major.id where major.id in (:majors)",
            countQuery = "select notice.id, notice.title, notice.url, notice.created_date, notice.created_at, notice.updated_at, major.name as major_name from notice join major on notice.major_id = major.id where major.id in (:majors)",
            nativeQuery = true)
    Page<NoticeAndETC> findAllByMajor(@Param("majors") List<String> majors, Pageable pageable);

}