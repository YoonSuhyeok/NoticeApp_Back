package com.deadline.knunotice.notice;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class NoticeServiceImpl implements NoticeService {

    private final NoticeRepository noticeRepository;

    public NoticeServiceImpl(NoticeRepository noticeRepository) {
        this.noticeRepository = noticeRepository;
    }

    @Override
    public Page<NoticeResponseDTO> findAll(Pageable pageable) {
        // TODO 생성일 기준 정렬 필요
        Page<Notice> notices = noticeRepository.findAll(pageable);
        return NoticeResponseDTO.builder().build().toDtoPage(notices);
    }

    @Override
    public Page<NoticeResponseDTO> searchAll(String keyword, Pageable pageable) {
        Page<Notice> notices = noticeRepository.findAllByTitleContainingOrderByCreatedDate(keyword, pageable);
        return NoticeResponseDTO.builder().build().toDtoPage(notices);
    }

}
