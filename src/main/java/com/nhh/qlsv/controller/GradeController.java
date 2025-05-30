package com.nhh.qlsv.controller;

import com.nhh.qlsv.dto.GradeDto;
import com.nhh.qlsv.entity.Grade;
import com.nhh.qlsv.service.GradeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/grades")
public class GradeController {
    GradeService gradeService;

    public GradeController(GradeService gradeService) {
        this.gradeService = gradeService;
    }

    @PostMapping("student/{studentId}/class/{classId}")
    public ResponseEntity<GradeDto> createGrade(@PathVariable("studentId") Long studentId,
                                              @PathVariable("classId") Long classId,
                                              @RequestBody GradeDto gradeDto) {
        return new ResponseEntity<>(gradeService.addGrade(studentId, classId, gradeDto), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GradeDto> findGradeById(@PathVariable("id") Long gradeId) {
        return ResponseEntity.ok(gradeService.findGradeById(gradeId));
    }

    @GetMapping
    public ResponseEntity<List<GradeDto>> findAllGrades() {
        return ResponseEntity.ok(gradeService.findAllGrades());
    }

    @PutMapping("/{id}")
    public ResponseEntity<GradeDto> updateGrade(@PathVariable("id") Long gradeId,
                                                @RequestBody GradeDto gradeDto) {
        return ResponseEntity.ok(gradeService.updateGrade(gradeId, gradeDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteGrade(@PathVariable("id") Long gradeId) {
        gradeService.deleteGrade(gradeId);
        return ResponseEntity.ok("Delete grade successfully!");
    }
}