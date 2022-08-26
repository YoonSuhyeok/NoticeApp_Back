package com.deadline.knunotice.notice;

import com.deadline.knunotice.BasicEntity;
import com.deadline.knunotice.major.Major;
import lombok.*;

import javax.persistence.*;
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

    @Column(nullable = false)
    private Boolean isPin;

    @Column(nullable = false)
    private Boolean isBookmark;

    @ManyToOne
    private Major major;

    public NoticeResponseDTO toDto() {
        return NoticeResponseDTO.builder()
                .id(id)
                .title(title)
                .url(url)
                .isBookmark(isBookmark)
                .isPin(isPin)
                .build();
    }

}
