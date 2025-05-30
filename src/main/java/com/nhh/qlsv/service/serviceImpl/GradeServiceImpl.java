package com.nhh.qlsv.service.serviceImpl;

import com.nhh.qlsv.dto.GradeDto;
import com.nhh.qlsv.dto.Mapper;
import com.nhh.qlsv.entity.Class;
import com.nhh.qlsv.entity.Grade;
import com.nhh.qlsv.entity.Student;
import com.nhh.qlsv.exception.APIException;
import com.nhh.qlsv.exception.ResourceNotFoundException;
import com.nhh.qlsv.repository.ClassRepository;
import com.nhh.qlsv.repository.GradeRepository;
import com.nhh.qlsv.repository.StudentRepository;
import com.nhh.qlsv.service.GradeService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GradeServiceImpl implements GradeService {
    private GradeRepository gradeRepository;
    private StudentRepository studentRepository;
    private ClassRepository classRepository;

    public GradeServiceImpl(GradeRepository gradeRepository, StudentRepository studentRepository, ClassRepository classRepository) {
        this.gradeRepository = gradeRepository;
        this.studentRepository = studentRepository;
        this.classRepository = classRepository;
    }

    @Override
    public GradeDto addGrade(Long studentId, Long classId, GradeDto gradeDto) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new ResourceNotFoundException("Student", "id", studentId));
        Class aClass = classRepository.findById(classId)
                .orElseThrow(() -> new ResourceNotFoundException("Class", "id", classId));
        if (aClass.getStudents().contains(student)) {
            Grade grade = new Grade();
            grade.setStudent(student);
            grade.setAClass(aClass);
            grade.setScore(gradeDto.getScore());
            grade.setScoreLetter(gradeDto.getScoreLetter());
            Grade savedGrade = gradeRepository.save(grade);
            return Mapper.mapToGradeDto(savedGrade);
        }
        throw new APIException(HttpStatus.BAD_REQUEST, "Student is not existed in class");
    }

    @Override
    public GradeDto findGradeById(Long id) {
        return Mapper.mapToGradeDto(isGradeExist(id));
    }

    @Override
    public List<GradeDto> findAllGrades() {
        return gradeRepository.findAll()
                .stream().map(Mapper::mapToGradeDto)
                .toList();
    }

    @Override
    public GradeDto updateGrade(Long id, GradeDto gradeDto) {
        Grade grade = isGradeExist(id);
        grade.setScoreLetter(grade.getScoreLetter());
        grade.setScore(gradeDto.getScore());
        Grade updatedGrade = gradeRepository.save(grade);
        return Mapper.mapToGradeDto(updatedGrade);
    }

    @Override
    public void deleteGrade(Long id) {
        isGradeExist(id);
        gradeRepository.deleteById(id);
    }

    private Grade isGradeExist(Long gradeId) {
        return gradeRepository.findById(gradeId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Grade", "id", gradeId));
    }
}
