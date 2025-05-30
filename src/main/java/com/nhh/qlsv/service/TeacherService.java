package com.nhh.qlsv.service;

import com.nhh.qlsv.dto.PasswordChangeRequest;
import com.nhh.qlsv.dto.TeacherDto;
import com.nhh.qlsv.entity.Teacher;

import java.util.List;

public interface TeacherService {
    TeacherDto createTeacher(TeacherDto teacherDto);

    TeacherDto findTeacherById(Long teacherId);

    List<TeacherDto> findAllTeachers();

    TeacherDto updateTeacher(Long teacherId, TeacherDto teacherDto);

    void changePassword(Long teacherId, PasswordChangeRequest password);

    void deleteTeacher (Long teacherId);

    boolean isPermission(String authHeader, Long teacherId);
}
