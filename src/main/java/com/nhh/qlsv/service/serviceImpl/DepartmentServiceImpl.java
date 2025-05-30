package com.nhh.qlsv.service.serviceImpl;

import com.nhh.qlsv.dto.DepartmentDto;
import com.nhh.qlsv.dto.Mapper;
import com.nhh.qlsv.entity.Department;
import com.nhh.qlsv.exception.ResourceNotFoundException;
import com.nhh.qlsv.repository.DepartmentRepository;
import com.nhh.qlsv.service.DepartmentService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentServiceImpl implements DepartmentService {
    DepartmentRepository departmentRepository;

    public DepartmentServiceImpl(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    @Override
    public DepartmentDto addDepartment(DepartmentDto departmentDto) {
        Department department = new Department();
        department.setId(department.getId());
        department.setDepartmentName(department.getDepartmentName());
        departmentRepository.save(department);
        return Mapper.mapToDepartmentDto(department);
    }

    @Override
    public DepartmentDto findDepartmentById(Long id) {
        return Mapper.mapToDepartmentDto(isDepartmentExist(id));
    }

    @Override
    public List<DepartmentDto> findAllDepartments() {
        return departmentRepository.findAll()
                .stream()
                .map(Mapper::mapToDepartmentDto)
                .toList();
    }

    @Override
    public DepartmentDto updateDepartment(Long departmentId, DepartmentDto departmentDto) {
        Department department = isDepartmentExist(departmentId);
        department.setDepartmentName(departmentDto.getDepartmentName());
        departmentRepository.save(department);
        return Mapper.mapToDepartmentDto(department);
    }

    @Override
    public void deleteDepartment(Long id) {
        isDepartmentExist(id);
        departmentRepository.deleteById(id);
    }

    private Department isDepartmentExist(Long departmentId) {
        return departmentRepository.findById(departmentId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Department", "id", departmentId));
    }
}
