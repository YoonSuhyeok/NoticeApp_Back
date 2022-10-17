package com.deadline.knunotice.member;

import com.deadline.knunotice.notice.Notice;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
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
