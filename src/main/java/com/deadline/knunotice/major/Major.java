package com.deadline.knunotice.major;

import com.deadline.knunotice.BasicEntity;
import com.deadline.knunotice.colleage.CollegeToMajor;
import com.deadline.knunotice.notice.Notice;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Major extends BasicEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 50, nullable = false)
    private String name;

    @OneToMany(mappedBy = "major")
    private List<Notice> notices;

    @OneToMany(mappedBy = "college")
    private List<CollegeToMajor> colleges;

}
