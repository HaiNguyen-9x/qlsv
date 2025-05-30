package com.nhh.qlsv.repository;

import com.nhh.qlsv.entity.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface TeacherRepository extends JpaRepository<Teacher, Long> {
    Teacher findByUserEmail(String email);
}
