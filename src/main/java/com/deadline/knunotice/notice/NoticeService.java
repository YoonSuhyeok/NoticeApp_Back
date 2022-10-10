package com.deadline.knunotice.notice;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface NoticeService {

     Page<NoticeResponseDTO> findAll(Pageable pageable);

     Page<NoticeResponseDTO> searchAll(String keyword, Pageable pageable);

}
