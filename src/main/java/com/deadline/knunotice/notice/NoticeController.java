package com.deadline.knunotice.notice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/notice")
public class NoticeController {

    private final NoticeService noticeService;

    public NoticeController(NoticeService noticeService) {
        this.noticeService = noticeService;
    }

    @GetMapping("/")
    public ResponseEntity<List<NoticeResponseDTO>> noticeFindAll(){
        List<NoticeResponseDTO> notices = noticeService.findAll();
        return new ResponseEntity<>(notices, HttpStatus.OK);
    }

}
