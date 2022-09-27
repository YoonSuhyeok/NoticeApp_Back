package com.deadline.knunotice.member;

import lombok.Getter;

import javax.persistence.*;

@Getter
@Entity
public class Authority {

    @Id
    @GeneratedValue
    private Integer id;

    private String name;

    @JoinColumn(name = "authorities")
    @ManyToOne
    private Member member;

}
