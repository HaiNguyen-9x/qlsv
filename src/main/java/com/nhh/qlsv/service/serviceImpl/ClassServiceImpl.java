package com.nhh.qlsv.service.serviceImpl;

import com.nhh.qlsv.dto.ClassDto;
import com.nhh.qlsv.dto.Mapper;
import com.nhh.qlsv.entity.Class;
import com.nhh.qlsv.entity.Course;
import com.nhh.qlsv.entity.Student;
import com.nhh.qlsv.entity.Teacher;
import com.nhh.qlsv.exception.APIException;
import com.nhh.qlsv.exception.ResourceNotFoundException;
import com.nhh.qlsv.repository.ClassRepository;
import com.nhh.qlsv.repository.CourseRepository;
import com.nhh.qlsv.repository.StudentRepository;
import com.nhh.qlsv.repository.TeacherRepository;
import com.nhh.qlsv.service.ClassService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClassServiceImpl implements ClassService {
    ClassRepository classRepository;
    StudentRepository studentRepository;
    TeacherRepository teacherRepository;
    CourseRepository courseRepository;

    public ClassServiceImpl(ClassRepository classRepository,
                            StudentRepository studentRepository,
                            TeacherRepository teacherRepository,
                            CourseRepository courseRepository) {
        this.classRepository = classRepository;
        this.studentRepository = studentRepository;
        this.teacherRepository = teacherRepository;
        this.courseRepository = courseRepository;
    }

    @Override
    public ClassDto createClass(Long courseId, ClassDto classDto) {
        Course course = courseRepository.findById(courseId)
                        .orElseThrow(()-> new ResourceNotFoundException("Course", "id", courseId));
        Class aClass = new Class();
        aClass.setCourse(course);
        aClass.setClassName(classDto.getClassName());
        classRepository.save(aClass);
        return Mapper.mapToClassDto(aClass);
    }

    @Override
    public ClassDto findClassById(Long classId) {
        Class aClass = classRepository.findById(classId)
                .orElseThrow(()-> new ResourceNotFoundException("Class", "id", classId));
        return Mapper.mapToClassDto(aClass);
    }

    @Override
    public List<ClassDto> findAllClasses() {
        return classRepository.findAll().stream()
                .map(Mapper::mapToClassDto)
                .collect(Collectors.toList());
    }

    @Override
    public String addStudentToClass(Long studentId, Long classId) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(()-> new ResourceNotFoundException("Student", "id", studentId));
        Class aClass = classRepository.findById(classId)
                .orElseThrow(()-> new ResourceNotFoundException("Class", "id", classId));
        student.getClasses().add(aClass);
        classRepository.save(aClass);

        return "Add student " + studentId + " into class with id " + classId + " successfully!";
    }

    @Override
    public String removeStudent(Long classId, Long studentId) {
        Class aClass = classRepository.findById(classId)
                .orElseThrow(()->new ResourceNotFoundException("Class", "id", classId));
        Student student = studentRepository.findById(studentId)
                .orElseThrow(()->new ResourceNotFoundException("Student", "id", studentId));
        if(aClass.getStudents().contains(student)) {
            aClass.getStudents().remove(student);
            student.getClasses().remove(aClass);
            classRepository.save(aClass);
            return "Remove student successfully";
        } else {
            throw new APIException(HttpStatus.BAD_REQUEST, "Student is not exist in class.");
        }
    }

    @Override
    public String addTeacherToClass(Long teacherId, Long classId) {
        Teacher teacher = teacherRepository.findById(teacherId)
                .orElseThrow(()-> new ResourceNotFoundException("Teacher", "id", teacherId));
        Class aClass = classRepository.findById(classId)
                .orElseThrow(()-> new ResourceNotFoundException("Class", "id", classId));
        if (aClass.getTeacher() != null) throw new APIException(HttpStatus.BAD_REQUEST, "This class is having a teacher!");

        teacher.getClasses().add(aClass);
        aClass.setTeacher(teacher);
        classRepository.save(aClass);
        teacherRepository.save(teacher);

        return "Add teacher " + teacher.getId() + " into class with id " + classId + " successfully!";
    }

    @Override
    public String removeTeacher(Long classId) {
        Class aClass = classRepository.findById(classId)
                .orElseThrow(()->new ResourceNotFoundException("Class", "id", classId));
        Teacher teacher = aClass.getTeacher();
        if(teacher != null) {
            teacher.getClasses().remove(aClass);
            classRepository.save(aClass);
            teacherRepository.save(teacher);
            return "Remove teacher successfully";
        } else {
            throw new APIException(HttpStatus.BAD_REQUEST, "Class " + aClass.getId() + " is not having a teacher.") ;
        }
    }

    @Override
    public void deleteClass(Long classId) {
        classRepository.findById(classId)
                .orElseThrow(()->new ResourceNotFoundException("Class", "id", classId));
        classRepository.deleteById(classId);
    }
}
