package com.nhh.qlsv.dto;

import com.nhh.qlsv.entity.*;
import com.nhh.qlsv.entity.Class;
import com.nhh.qlsv.exception.APIException;
import org.springframework.http.HttpStatus;

import java.sql.Date;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class Mapper {
    public static StudentDto mapToStudentDto(Student student) {
        String emailDto = student.getUser().getEmail();
        emailDto = emailDto.substring(0, 3);
        emailDto = emailDto.concat("***@gmail.com");
        return new StudentDto(
                student.getId(),
                student.getFullName(),
                student.getGender(),
                convertToDateDto(student.getDate()),
                student.getAddress(),
                emailDto,
                student.getPhoneNumber()
        );
    }

    public static TeacherDto mapToTeacherDto(Teacher teacher) {
        String emailDto = teacher.getUser().getEmail();
        emailDto = emailDto.substring(0, 3);
        emailDto = emailDto.concat("***@gmail.com");
        if (teacher.getClasses() == null)
            return new TeacherDto(
                    teacher.getId(),
                    teacher.getFullName(),
                    teacher.getPhoneNumber(),
                    emailDto,
                    new ArrayList<>()
            );
        return new TeacherDto(
                teacher.getId(),
                teacher.getFullName(),
                teacher.getPhoneNumber(),
                emailDto,
                teacher.getClasses().stream().map(Class::getId).toList()
        );
    }

    public static ClassDto mapToClassDto(Class aClass) {
        ClassDto classDto = new ClassDto();
        classDto.setId(aClass.getId());
        classDto.setClassName(aClass.getClassName());
        classDto.setCourse(aClass.getCourse());
        if (aClass.getTeacher() != null) classDto.setTeacherDto(Mapper.mapToTeacherDto(aClass.getTeacher()));
        classDto.setStudents(aClass.getStudents().stream().map(Mapper::mapToStudentDto).collect(Collectors.toSet()));
        return classDto;
    }

    public static GradeDto mapToGradeDto(Grade grade) {
        return new GradeDto(
                grade.getScore(),
                grade.getScoreLetter(),
                mapToStudentDto(grade.getStudent()),
                grade.getAClass().getId()
        );
    }

    public static DepartmentDto mapToDepartmentDto(Department department) {
        return new DepartmentDto(
                department.getId(),
                department.getDepartmentName()
        );
    }

    public static CourseDto mapToCourseDto(Course course) {
        return new CourseDto(
                course.getId(),
                course.getCourseName(),
                course.getCredits(),
                course.getDescription(),
                course.getDepartment().getId()
        );
    }

    public static StudentGradeDto mapToStudentGradeDto(Grade grade) {
        return new StudentGradeDto(
                grade.getScore(),
                grade.getScoreLetter(),
                grade.getAClass().getId()
        );
    }

    public static Date convertToDate(String date) {
        if (!date.matches("[0-9]{2}-[0-9]{2}-[0-9]{4}"))
            throw new APIException(HttpStatus.BAD_REQUEST, "Invalid date value. It should be dd/mm/yyyy.");
        String[] dates = date.split("-");
        int year = Integer.parseInt(dates[2]);
        int month = Integer.parseInt(dates[1]);
        int day = Integer.parseInt(dates[0]);
        return new Date(year - 1900, month - 1, day);
    }

    public static String convertToDateDto(Date date) {
        if (date == null) return null;
        String[] tempt = date.toString().split("-");
        String dateDto = "";
        for (int i = 2; i >=0; i--) {
            dateDto = dateDto.concat("-" + tempt[i]);
        }
        dateDto = dateDto.replaceFirst("-", "");
        return dateDto;
    }
}