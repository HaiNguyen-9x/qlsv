package com.nhh.qlsv.controller;

import com.nhh.qlsv.dto.ClassDto;
import com.nhh.qlsv.entity.Class;
import com.nhh.qlsv.service.ClassService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/classes")
public class ClassController {

    private ClassService classService;

    public ClassController(ClassService classService) {
        this.classService = classService;
    }

    @PostMapping("/courseId/{courseId}")
    public ResponseEntity<ClassDto> createClass(@PathVariable("courseId") Long courseId,
                                                @RequestBody ClassDto classDto) {
        return new ResponseEntity<>(classService.createClass(courseId, classDto), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClassDto> findClassById(@PathVariable("id") Long classId) {
        return ResponseEntity.ok(classService.findClassById(classId));
    }

    @GetMapping
    public ResponseEntity<List<ClassDto>> findAllClasses() {
        return ResponseEntity.ok(classService.findAllClasses());
    }

    @PutMapping("/{id}/addStudent/{studentId}")
    public ResponseEntity<String> addStudentToClass(@PathVariable("studentId") Long studentId,
                                                    @PathVariable("id") Long classId) {
        return ResponseEntity.ok(classService.addStudentToClass(studentId, classId));
    }

    @PutMapping("/{id}/removeStudent/{studentId}")
    public ResponseEntity<String> removeStudent(@PathVariable("id") Long classId,
                                                @PathVariable("studentId") Long studentId) {
        return ResponseEntity.ok(classService.removeStudent(classId, studentId));
    }

    @PutMapping("{id}/addTeacher/{teacherId}")
    public ResponseEntity<String> addTeacherToClass(@PathVariable("teacherId") Long teacherId,
                                                    @PathVariable("id") Long classId) {
        return ResponseEntity.ok(classService.addTeacherToClass(teacherId, classId));
    }

    @PutMapping("/{id}/deleteTeacher")
    public ResponseEntity<String> removeTeacher(@PathVariable("id") Long classId) {
        return ResponseEntity.ok(classService.removeTeacher(classId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteClass(@PathVariable("id") Long classId) {
        classService.deleteClass(classId);
        return ResponseEntity.ok("Delete class successfully");
    }
}
