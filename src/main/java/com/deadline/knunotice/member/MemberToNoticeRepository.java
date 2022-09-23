package com.deadline.knunotice.member;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberToNoticeRepository extends JpaRepository<MemberToNotice, Long> {
}
