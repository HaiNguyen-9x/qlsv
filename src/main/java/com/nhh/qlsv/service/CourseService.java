package com.nhh.qlsv.service;

import com.nhh.qlsv.dto.CourseDto;
import com.nhh.qlsv.entity.Course;

import java.util.List;

public interface CourseService {
    CourseDto addCourse(CourseDto courseDto);

    CourseDto findCourseById(Long id);

    List<CourseDto> findAllCourses();

    CourseDto updateCourse(Long courseId, CourseDto updateCourse);

    void deleteCourse(Long id);
}
