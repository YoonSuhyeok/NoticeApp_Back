package com.deadline.knunotice.notice;

import com.deadline.knunotice.major.MajorResponseDTO;
import com.deadline.knunotice.member.Member;
import com.deadline.knunotice.member.MemberAuthentication;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;

public interface NoticeService {

     Page<NoticeAndETC> findAll(Pageable pageable);

     Page<NoticeAndETC> searchAll(String keyword, Pageable pageable);

    Integer recordPinOrBookmark(Member member, Long noticeId, Integer type);

    Page<NoticeAndETC> findAllVersionSQL(Long id, String keyword, Pageable pageable);

    Page<NoticeAndETC> findAllVersionSQLAndMajor(Long id, String keyword, ArrayList<MajorResponseDTO> majors, Pageable pageable);

    Page<NoticeAndETC> findAllForMajors(ArrayList<MajorResponseDTO> majors, Pageable pageable);

    List<NoticeResponseDTO> findBookmarkAll(MemberAuthentication memberAuthentication, Pageable pageable);

}
