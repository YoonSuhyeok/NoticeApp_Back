package com.deadline.knunotice.college;

import com.deadline.knunotice.config.aop.LogExecutionTime;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
@RequestMapping("/api/college")
public class CollegeController {

    private final CollegeService collegeService;

    public CollegeController(CollegeService collegeService) {
        this.collegeService = collegeService;
    }

    @LogExecutionTime
    @GetMapping()
    public ResponseEntity<List<CollegeResponseDTO>> findAll() {
        List<CollegeResponseDTO> colleges = collegeService.findAll();
        return new ResponseEntity<>(colleges, HttpStatus.OK);
    }

}
