package com.deadline.knunotice.notice;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class NoticeServiceImpl implements NoticeService {

    private final NoticeRepository noticeRepository;

    public NoticeServiceImpl(NoticeRepository noticeRepository) {
        this.noticeRepository = noticeRepository;
    }

    @Override
    public List<NoticeResponseDTO> findAll() {
        List<Notice> notices = noticeRepository.findAll();

        List<NoticeResponseDTO> noticeResponseDTOS = new ArrayList<>();
        for(Notice notice: notices) {
            noticeResponseDTOS.add(notice.toDto());
        }

        return noticeResponseDTOS;
    }

}
