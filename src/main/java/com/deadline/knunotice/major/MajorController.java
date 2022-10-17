package com.deadline.knunotice.major;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/major")
public class MajorController {

    private final MajorService majorService;

    public MajorController(MajorService majorService) {
        this.majorService = majorService;
    }

    @GetMapping()
    public ResponseEntity<List<MajorResponseDTO>> getMajors() {

        List<MajorResponseDTO> majorResponseDTOS = majorService.getMajors();

        return new ResponseEntity<>(majorResponseDTOS, HttpStatus.OK);
    }
}
