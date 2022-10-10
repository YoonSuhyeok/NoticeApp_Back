package com.deadline.knunotice.notice;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Page;

import java.util.Date;

@Getter
@Setter
@Builder
public class NoticeResponseDTO {

    private Long id;

    private String title;

    private String url;

    private Date createdDate;

    private Boolean isPin;

    private Boolean isBookmark;

    public Page<NoticeResponseDTO> toDtoPage(Page<Notice> notices) {
        return notices.map(Notice::toDto );
    }

}
