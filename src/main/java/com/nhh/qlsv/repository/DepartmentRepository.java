package com.nhh.qlsv.repository;

import com.nhh.qlsv.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepository extends JpaRepository<Department, Long> {
}
