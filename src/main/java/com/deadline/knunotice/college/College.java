package com.deadline.knunotice.college;

import com.deadline.knunotice.BasicEntity;
import com.deadline.knunotice.major.Major;
import com.deadline.knunotice.major.MajorResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Builder
@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class College extends BasicEntity {

    @Id
    @GeneratedValue
    private Long id;

    @Column(length = 50, nullable = false)
    private String name;

    @OneToMany(mappedBy = "college")
    private List<Major> majors;

    public CollegeResponseDTO toDto() {
        List<MajorResponseDTO> majorResponseDTOS = new ArrayList<>();
        for (Major major : majors) {
            MajorResponseDTO majorResponseDTO = MajorResponseDTO.builder().name(major.getName()).build();
            majorResponseDTOS.add(majorResponseDTO);
        }

        return CollegeResponseDTO.builder()
                .id(id)
                .name(name)
                .majors(majorResponseDTOS)
                .build();
    }
}
