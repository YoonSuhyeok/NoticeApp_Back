package com.deadline.knunotice.college;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CollegeServiceImpl implements CollegeService {

    private final CollegeRepository collegeRepository;

    public CollegeServiceImpl(CollegeRepository collegeRepository) {
        this.collegeRepository = collegeRepository;
    }

    @Override
    public List<CollegeResponseDTO> findAll() {
        List<College> colleges = collegeRepository.findAll();
        List<CollegeResponseDTO> collegeResponseDTOS = new ArrayList<>();
        for(College college: colleges) {
            collegeResponseDTOS.add(college.toDto());
        }

        return collegeResponseDTOS;
    }

}
