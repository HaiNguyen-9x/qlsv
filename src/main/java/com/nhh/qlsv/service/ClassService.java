package com.nhh.qlsv.service;

import com.nhh.qlsv.dto.ClassDto;
import com.nhh.qlsv.entity.Class;

import java.util.List;

public interface ClassService {
    ClassDto createClass(Long courseId, ClassDto classDto);

    ClassDto findClassById(Long classId);

    List<ClassDto> findAllClasses();

    String addStudentToClass(Long studentId, Long classId);

    String removeStudent(Long classId, Long studentId);

    String addTeacherToClass(Long teacherId, Long classId);

    String removeTeacher(Long classId);

    void deleteClass(Long classId);
}
