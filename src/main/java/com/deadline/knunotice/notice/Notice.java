package com.deadline.knunotice.notice;

import com.deadline.knunotice.BasicEntity;
import com.deadline.knunotice.major.Major;
import com.deadline.knunotice.member.MemberToNotice;
import lombok.*;

import javax.persistence.*;
import java.util.List;
import java.util.Date;

@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Notice extends BasicEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 255, nullable = false)
    private String title;

    @Column(length = 255, nullable = false)
    private String url;

    @Column(nullable = false)
    private Date createdDate;

    @ManyToOne
    private Major major;

    @OneToMany(mappedBy = "notice", fetch = FetchType.EAGER)
    private List<MemberToNotice> notices;

    public NoticeResponseDTO toDto() {
        return NoticeResponseDTO.builder()
                .id(id)
                .title(title)
                .url(url)
                .build();
    }

}
