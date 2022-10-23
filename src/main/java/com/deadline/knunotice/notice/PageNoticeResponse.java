package com.deadline.knunotice.notice;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PageNoticeResponse {

    List<NoticeResponseDTO> content;

    Integer number;

    Integer totalPages;

}
