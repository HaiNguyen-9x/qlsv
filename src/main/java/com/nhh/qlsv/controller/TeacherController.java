package com.nhh.qlsv.controller;

import com.nhh.qlsv.dto.PasswordChangeRequest;
import com.nhh.qlsv.dto.TeacherDto;
import com.nhh.qlsv.entity.Teacher;
import com.nhh.qlsv.entity.User;
import com.nhh.qlsv.exception.ForbiddenException;
import com.nhh.qlsv.repository.TeacherRepository;
import com.nhh.qlsv.security.JwtTokenProvider;
import com.nhh.qlsv.service.TeacherService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/api/v1/teachers")
public class TeacherController {
    TeacherService teacherService;

    TeacherRepository teacherRepository;

    JwtTokenProvider jwtTokenProvider;

    public TeacherController(TeacherService teacherService,
                             TeacherRepository teacherRepository,
                             JwtTokenProvider jwtTokenProvider) {
        this.teacherService = teacherService;
        this.teacherRepository = teacherRepository;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @PostMapping
    public ResponseEntity<TeacherDto> createTeacher(@RequestBody TeacherDto teacherDto) {
        return new ResponseEntity<>(teacherService.createTeacher(teacherDto), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TeacherDto> findTeacherById(@PathVariable("id") Long teacherId) {
        return ResponseEntity.ok(teacherService.findTeacherById(teacherId));
    }

    @GetMapping
    public ResponseEntity<List<TeacherDto>> findAllTeachers() {
        return ResponseEntity.ok(teacherService.findAllTeachers());
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('TEACHER'')")
    public ResponseEntity<TeacherDto> updateTeacher(@PathVariable("id") Long teacherId,
                                                    @RequestBody TeacherDto teacherDto,
                                                    @RequestHeader("Authorization") String authHeader) {
        if (teacherService.isPermission(authHeader, teacherId))
            return ResponseEntity.ok(teacherService.updateTeacher(teacherId, teacherDto));
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
    }

    @PutMapping("/{id}/password")
    @PreAuthorize("hasRole('TEACHER')")
    public ResponseEntity<String> changePassword(@PathVariable("id") Long teacherId,
                                                 @RequestBody PasswordChangeRequest passwordChangeRequest,
                                                 @RequestHeader("Authorization") String authHeader) {
        if(teacherService.isPermission(authHeader, teacherId))
        {
            teacherService.changePassword(teacherId, passwordChangeRequest);
            return ResponseEntity.ok("Password has been changed!");
        }

        throw new  ForbiddenException();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTeacher(@PathVariable("id") Long teacherId) {
        teacherService.deleteTeacher(teacherId);
        return ResponseEntity.ok("Delete teacher successfully");
    }
}