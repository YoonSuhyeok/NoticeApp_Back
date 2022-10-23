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

    private Date created_date;

    private Integer is_pin;

    private Integer is_bookmark;

    private LocalDateTime created_at;

    private LocalDateTime updated_at;

    private String major_name;

}
