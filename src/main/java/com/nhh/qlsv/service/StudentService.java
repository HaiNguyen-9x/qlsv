package com.nhh.qlsv.service;

import com.nhh.qlsv.dto.*;
import com.nhh.qlsv.entity.Student;

import java.util.List;

public interface StudentService {
    StudentDto createStudent(StudentDto studentDto);

    StudentDto findStudentById(Long studentId);

    StudentResponse findAllStudents(int pageNo, int pageSize, String sortBy, String sortDir);

    StudentDto updateStudent(Long studentId, StudentDto studentDto);

    void changePassword(Long studentId, PasswordChangeRequest password);

    List<StudentGradeDto> showGrades(Long studentId);

    boolean isPermission(String authHeader, Long studentId);

    void deleteStudent(Long studentId);
}
