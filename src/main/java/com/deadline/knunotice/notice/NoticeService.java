package com.deadline.knunotice.notice;

import com.deadline.knunotice.major.MajorResponseDTO;
import com.deadline.knunotice.member.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;

public interface NoticeService {

     Page<NoticeAndETC> findAll(Pageable pageable);

     Page<NoticeAndETC> searchAll(String keyword, Pageable pageable);

    Integer recodePinOrBookmark(Member member, Long noticeId, Integer type);

    Page<NoticeAndETC> findAllVersionSQL(Long id, String keyword, Pageable pageable);

    Page<NoticeAndETC> findAllVersionSQLAndMajor(Long id, String keyword, ArrayList<MajorResponseDTO> majors, Pageable pageable);

    Page<NoticeAndETC> findAllForMajors(ArrayList<MajorResponseDTO> majors, Pageable pageable);
    
}
