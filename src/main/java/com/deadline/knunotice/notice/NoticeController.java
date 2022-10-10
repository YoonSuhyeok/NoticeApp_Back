package com.deadline.knunotice.notice;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/notice")
public class NoticeController {

    private final NoticeService noticeService;

    public NoticeController(NoticeService noticeService) {
        this.noticeService = noticeService;
    }

    @GetMapping()
    public ResponseEntity<Page<NoticeResponseDTO>> noticeFindAll(@PageableDefault(size=20) Pageable pageable){
        Page<NoticeResponseDTO> notices = noticeService.findAll(pageable);
        return new ResponseEntity<>(notices, HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<NoticeResponseDTO>> searchAll(@Param("keyword") String keyword, @PageableDefault(size=20) Pageable pageable){
        Page<NoticeResponseDTO> notices;
        if(keyword == null) {
            notices = noticeService.findAll(pageable);
        } else {
            notices = noticeService.searchAll(keyword, pageable);
        }
        return new ResponseEntity<>(notices, HttpStatus.OK);
    }
}
