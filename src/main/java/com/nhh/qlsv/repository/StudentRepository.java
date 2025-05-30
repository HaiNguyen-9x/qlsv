package com.nhh.qlsv.repository;

import com.nhh.qlsv.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Long> {
    Student findByUserEmail(String email);
}
