package com.nhh.qlsv.service.serviceImpl;

import com.nhh.qlsv.dto.CourseDto;
import com.nhh.qlsv.dto.Mapper;
import com.nhh.qlsv.entity.Course;
import com.nhh.qlsv.entity.Department;
import com.nhh.qlsv.exception.ResourceNotFoundException;
import com.nhh.qlsv.repository.CourseRepository;
import com.nhh.qlsv.repository.DepartmentRepository;
import com.nhh.qlsv.service.CourseService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseServiceImpl implements CourseService {
    private CourseRepository courseRepository;
    private DepartmentRepository departmentRepository;

    public CourseServiceImpl(CourseRepository courseRepository, DepartmentRepository departmentRepository) {
        this.courseRepository = courseRepository;
        this.departmentRepository = departmentRepository;
    }

    @Override
    public CourseDto addCourse(CourseDto courseDto) {
        Department department = departmentRepository.findById(courseDto.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Department", "id", courseDto.getId()));
        Course newCourse = new Course();
        newCourse.setId(courseDto.getId());
        newCourse.setCourseName(courseDto.getCourseName());
        newCourse.setCredits(courseDto.getCredits());
        newCourse.setDescription(courseDto.getDescription());
        newCourse.setDepartment(department);
        return Mapper.mapToCourseDto(newCourse);
    }

    @Override
    public CourseDto findCourseById(Long courseId) {
        return Mapper.mapToCourseDto(isCourseExist(courseId));
    }

    @Override
    public List<CourseDto> findAllCourses() {
        return courseRepository.findAll()
                .stream()
                .map(Mapper::mapToCourseDto)
                .toList();
    }

    @Override
    public CourseDto updateCourse(Long courseId, CourseDto updateCourse) {
        Department department = departmentRepository.findById(courseId)
                    .orElseThrow(() -> new ResourceNotFoundException("Department", "id", updateCourse.getId()));
        Course updatedCourse = isCourseExist(courseId);
        updatedCourse.setCourseName(updateCourse.getCourseName());
        updatedCourse.setCredits(updateCourse.getCredits());
        updatedCourse.setDescription(updateCourse.getCourseName());
        updatedCourse.setDepartment(department);
        return Mapper.mapToCourseDto(courseRepository.save(updatedCourse));
    }

    @Override
    public void deleteCourse(Long id) {
        isCourseExist(id);
        courseRepository.deleteById(id);
    }

    private Course isCourseExist(Long courseId) {
        return courseRepository.findById(courseId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Course", "id", courseId));
    }
}
