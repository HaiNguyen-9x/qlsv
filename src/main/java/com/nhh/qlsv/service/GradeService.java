package com.nhh.qlsv.service;

import com.nhh.qlsv.dto.GradeDto;
import com.nhh.qlsv.entity.Grade;

import java.util.List;

public interface GradeService {
    GradeDto addGrade(Long studentId, Long classId, GradeDto gradeDto);

    GradeDto findGradeById(Long id);

    List<GradeDto> findAllGrades();

    GradeDto updateGrade(Long id, GradeDto gradeDto);

    void deleteGrade(Long id);
}
