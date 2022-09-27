package com.deadline.knunotice.member;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

import static com.deadline.knunotice.member.EncryptionAlgorithm.BCRYPT;

@Getter
@Setter
@Entity
public class Member {

    @Id
    @GeneratedValue
    private Long id;

    @Column
    private String username;

    @Column
    private String password;

    @Column
    private String email;

    @Enumerated(EnumType.STRING)
    private EncryptionAlgorithm algorithm;

    @OneToMany(mappedBy = "member")
    private List<Authority> authorities;

    @OneToMany(mappedBy = "notice")
    private List<MemberToNotice> notices;

    public Member() {

    }

    public Member(SignUpDTO signUpDTO){
        this.username = signUpDTO.getUsername();
        this.email = signUpDTO.getEmail();
        this.password = signUpDTO.getPassword();
        this.algorithm = BCRYPT;
    }

}
