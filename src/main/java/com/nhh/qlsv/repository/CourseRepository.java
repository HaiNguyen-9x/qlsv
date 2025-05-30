package com.nhh.qlsv.repository;

import com.nhh.qlsv.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course, Long> {
}
