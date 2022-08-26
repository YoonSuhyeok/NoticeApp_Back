package com.deadline.knunotice.colleage;

import com.deadline.knunotice.major.Major;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Builder
@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class CollegeToMajor {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    private College college;

    @ManyToOne
    private Major major;

}
