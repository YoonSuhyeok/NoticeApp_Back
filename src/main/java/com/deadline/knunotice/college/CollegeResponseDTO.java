package com.deadline.knunotice.college;

import com.deadline.knunotice.major.MajorResponseDTO;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class CollegeResponseDTO {

    private Long id;

    private String name;

    private List<MajorResponseDTO> majors;

}
