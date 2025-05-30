package com.nhh.qlsv.controller;


import com.nhh.qlsv.dto.*;
import com.nhh.qlsv.entity.User;
import com.nhh.qlsv.exception.APIException;
import com.nhh.qlsv.exception.ForbiddenException;
import com.nhh.qlsv.repository.StudentRepository;
import com.nhh.qlsv.security.JwtTokenProvider;
import com.nhh.qlsv.service.StudentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.random.RandomGenerator;

@Controller
@RequestMapping("/api/v1/students")
public class StudentController {
    StudentService studentService;
    JwtTokenProvider jwtTokenProvider;
    StudentRepository studentRepository;

    public StudentController(StudentService studentService,
                             JwtTokenProvider jwtTokenProvider,
                             StudentRepository studentRepository) {
        this.studentService = studentService;
        this.jwtTokenProvider = jwtTokenProvider;
        this.studentRepository = studentRepository;
    }

    @PostMapping
    public ResponseEntity<StudentDto> createStudent(@RequestBody StudentDto studentDto) {
        return new ResponseEntity<>(studentService.createStudent(studentDto), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<StudentDto> findStudentById(@PathVariable("id") Long studentId) {
        return ResponseEntity.ok(studentService.findStudentById(studentId));
    }

    @GetMapping
    public ResponseEntity<StudentResponse> findAllStudents(@RequestParam(name = "pageNo", defaultValue = "1", required = false) int pageNo,
                                                           @RequestParam(name = "pageSize", defaultValue = "5", required = false) int pageSize,
                                                           @RequestParam(name = "sortBy", defaultValue = "id", required = false) String sortBy,
                                                           @RequestParam(name = "sortDir", defaultValue = "asc", required = false) String sortDir) {
        return ResponseEntity.ok(studentService.findAllStudents(pageNo, pageSize, sortBy, sortDir));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('STUDENT')")
    public ResponseEntity<StudentDto> updateStudent(@PathVariable("id") Long studentId,
                                                    @RequestBody StudentDto studentDto,
                                                    @RequestHeader("Authorization") String authHeader) {
        if (studentService.isPermission(authHeader, studentId))
            return ResponseEntity.ok(studentService.updateStudent(studentId, studentDto));
        throw new ForbiddenException();
    }

    @GetMapping("/{id}/grades")
    public ResponseEntity<List<StudentGradeDto>> showGrades(@PathVariable("id") Long studentId) {
        return ResponseEntity.ok(studentService.showGrades(studentId));
    }

    @PutMapping("{id}/password")
    @PreAuthorize("hasRole('STUDENT')")
    public ResponseEntity<String> changePassword(@PathVariable("id") Long studentId,
                                                 @RequestBody PasswordChangeRequest passwordChangeRequest,
                                                 @RequestHeader("Authorization") String authHeader) {
        if(studentService.isPermission(authHeader, studentId))
        {
            studentService.changePassword(studentId, passwordChangeRequest);
            return ResponseEntity.ok("Password has been changed!");
        }

        throw new ForbiddenException();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteStudent(@PathVariable("id") Long studentId) {
        studentService.deleteStudent(studentId);
        return ResponseEntity.ok("Delete student successfully");
    }
}