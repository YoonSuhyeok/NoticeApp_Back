package com.deadline.knunotice.notice;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
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

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private String majorName;

}
