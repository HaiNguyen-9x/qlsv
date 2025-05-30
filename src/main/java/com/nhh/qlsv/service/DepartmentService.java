package com.nhh.qlsv.service;

import com.nhh.qlsv.dto.DepartmentDto;
import com.nhh.qlsv.entity.Department;

import java.util.List;

public interface DepartmentService {
    DepartmentDto addDepartment(DepartmentDto departmentDto);

    DepartmentDto findDepartmentById(Long id);

    List<DepartmentDto> findAllDepartments();

    DepartmentDto updateDepartment(Long departmentId, DepartmentDto departmentDto);

    void deleteDepartment(Long id);
}
