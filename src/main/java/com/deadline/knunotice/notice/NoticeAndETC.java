package com.deadline.knunotice.notice;

import java.time.LocalDateTime;

public interface NoticeAndETC {

    Long getId();

    String getTitle();

    String getUrl();

    LocalDateTime getCreated_date();

    LocalDateTime getCreated_at();

    LocalDateTime getUpdated_at();

    String getMajor_name();

    Integer getIs_bookmark();

    Integer getIs_pin();

}
