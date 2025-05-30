package com.nhh.qlsv.repository;

import com.nhh.qlsv.entity.Grade;
import com.nhh.qlsv.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GradeRepository extends JpaRepository<Grade, Long> {
    List<Grade> findAllByStudent(Student student);
}
