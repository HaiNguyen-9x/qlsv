package com.nhh.qlsv.dto;

import com.nhh.qlsv.entity.Course;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ClassDto {
    private Long id;
    private String className;
    private Course course;
    private TeacherDto teacherDto;
    private Set<StudentDto> students = new HashSet<>();
}
