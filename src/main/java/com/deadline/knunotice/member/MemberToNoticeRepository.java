package com.deadline.knunotice.member;

import com.deadline.knunotice.notice.Notice;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberToNoticeRepository extends JpaRepository<MemberToNotice, Long> {

    Optional<MemberToNotice> findMemberToNoticeByMemberAndNotice(Member member, Notice notice);

    Page<MemberToNotice> findMemberToNoticeByMember(Member member, Pageable pageable);

}
