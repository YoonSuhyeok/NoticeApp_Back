package com.deadline.knunotice.colleage;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CollegeToMajorRepository extends JpaRepository<CollegeToMajor, Long> {
}
