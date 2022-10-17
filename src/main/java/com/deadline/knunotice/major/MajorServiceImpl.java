package com.deadline.knunotice.major;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MajorServiceImpl implements MajorService {

    private final MajorRepository majorRepository;

    public MajorServiceImpl(MajorRepository majorRepository) {
        this.majorRepository = majorRepository;
    }

    @Override
    public List<MajorResponseDTO> getMajors() {
        List<Major> majors = majorRepository.findAll();
        return majors.stream().map(Major::toDto).toList();
    }

}
