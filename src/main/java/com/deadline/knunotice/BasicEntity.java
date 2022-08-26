package com.deadline.knunotice;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

/**
 * MappedSuperclass 공통 매핑 컬럼
 * AuditingEntityListener JPA 라이브러리에서 자동으로 시간 기록 기능
 */
@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class BasicEntity {

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

}
