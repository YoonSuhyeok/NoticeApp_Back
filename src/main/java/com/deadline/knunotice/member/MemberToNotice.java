package com.deadline.knunotice.member;

import com.deadline.knunotice.notice.Notice;

import javax.persistence.*;

@Entity
public class MemberToNotice {

    @Id
    @GeneratedValue
    private Long id;

    @JoinColumn
    @ManyToOne()
    private Member member;

    @JoinColumn
    @ManyToOne()
    private Notice notice;

    private boolean isPin;

    private boolean isBookmark;

}
