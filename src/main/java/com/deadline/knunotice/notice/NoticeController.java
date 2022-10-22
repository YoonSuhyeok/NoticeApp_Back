package com.deadline.knunotice.notice;

import com.deadline.knunotice.major.MajorResponseDTO;
import com.deadline.knunotice.member.MemberAuthentication;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/notice")
public class NoticeController {

    private final NoticeService noticeService;

    public NoticeController(NoticeService noticeService) {
        this.noticeService = noticeService;
    }

    @GetMapping()
    public ResponseEntity<Page<NoticeAndETC>> noticeFindAll(@PageableDefault(size=20) Pageable pageable){
        Page<NoticeAndETC> notices = noticeService.findAll(pageable);
        return new ResponseEntity<>(notices, HttpStatus.OK);
    }

    @PostMapping("/search")
    public ResponseEntity<?> searchAll(MemberAuthentication memberAuthentication, @Param("keyword") String keyword, @PageableDefault(size=20) Pageable pageable, @RequestBody ArrayList<MajorResponseDTO> majors)
    {
        Page<NoticeAndETC> notices;

        // 멤버인 경우
        if(memberAuthentication != null) {
            // 전체 조회
            if(keyword == null && majors.get(0).getId() == 0) {
                notices = noticeService.findAllVersionSQL(memberAuthentication.getMember().getId(), keyword, pageable);
            } else {
                notices = noticeService.findAllVersionSQLAndMajor(memberAuthentication.getMember().getId(), keyword, majors, pageable);
            }
        }
        // 멤버가 아닌 경우
        else {
            if(keyword == null && majors.get(0).getId() == 0 ){
                notices = noticeService.findAll(pageable);
            } else if(keyword != null && majors.get(0).getId() == 0) {
                notices = noticeService.searchAll(keyword, pageable);
            } else if(keyword == null && majors.get(0).getId() != 0) {
                notices = noticeService.findAllForMajors(majors, pageable);
            } else {
                //notices = noticeService.findAllVersionSQLAndMajor()
                notices = null;
            }
        }

        return new ResponseEntity<>(notices, HttpStatus.OK);
    }

    @PostMapping("/record")
    public ResponseEntity<Integer> recordPinOrBookmark(MemberAuthentication memberAuthentication,
                                       @Param("noticeId") Long noticeId, @Param("type") Integer type) {

        Integer result = noticeService.recordPinOrBookmark(memberAuthentication.getMember(), noticeId, type);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/bookmark")
    public ResponseEntity<List<NoticeResponseDTO>> findBookmarkAll(MemberAuthentication memberAuthentication, @PageableDefault(size=20) Pageable pageable) {
        List<NoticeResponseDTO> notices = noticeService.findBookmarkAll(memberAuthentication, pageable);
        return new ResponseEntity<>(notices, HttpStatus.OK);
    }

}
