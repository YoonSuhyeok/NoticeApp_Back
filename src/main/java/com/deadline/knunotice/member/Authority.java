package com.deadline.knunotice.member;

import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class Authority {

    @Id
    @GeneratedValue
    private Integer id;

    private String name;

    @JoinColumn(name = "member")
    @ManyToOne
    private Member member;

}
