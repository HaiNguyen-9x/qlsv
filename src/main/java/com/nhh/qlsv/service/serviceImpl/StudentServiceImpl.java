package com.nhh.qlsv.service.serviceImpl;

import com.nhh.qlsv.dto.*;
import com.nhh.qlsv.entity.Student;
import com.nhh.qlsv.entity.User;
import com.nhh.qlsv.exception.APIException;
import com.nhh.qlsv.exception.ResourceNotFoundException;
import com.nhh.qlsv.repository.ClassRepository;
import com.nhh.qlsv.repository.GradeRepository;
import com.nhh.qlsv.repository.StudentRepository;
import com.nhh.qlsv.repository.UserRepository;
import com.nhh.qlsv.security.JwtTokenProvider;
import com.nhh.qlsv.service.StudentService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {
    StudentRepository studentRepository;
    UserRepository userRepository;
    ClassRepository classRepository;
    GradeRepository gradeRepository;
    BCryptPasswordEncoder passwordEncoder;
    JwtTokenProvider jwtTokenProvider;

    public StudentServiceImpl(StudentRepository studentRepository,
                              UserRepository userRepository,
                              ClassRepository classRepository,
                              GradeRepository gradeRepository,
                              BCryptPasswordEncoder passwordEncoder,
                              JwtTokenProvider jwtTokenProvider) {
        this.studentRepository = studentRepository;
        this.userRepository = userRepository;
        this.classRepository = classRepository;
        this.gradeRepository = gradeRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    @Transactional
    public StudentDto createStudent(StudentDto studentDto) {
        Student student = new Student();
        User user = new User(
                null,
                studentDto.getEmail(),
                passwordEncoder.encode("1"),
                "ROLE_STUDENT",
                student,
                null
        );

        student.setUser(user);

        setStudent(studentDto, student);

        studentRepository.save(student);
        userRepository.save(user);

        return Mapper.mapToStudentDto(student);
    }

    @Override
    public StudentDto findStudentById(Long studentId) {
        Student student = isStudentExist(studentId);
        return Mapper.mapToStudentDto(student);
    }

    @Override
    public StudentResponse findAllStudents(int pageNo, int pageSize, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);

        Page<Student> listOfStudents = studentRepository.findAll(pageable);

        StudentResponse studentResponse = new StudentResponse();

        studentResponse.setStudents(listOfStudents.stream().map(Mapper::mapToStudentDto).toList());
        studentResponse.setPageNo(pageNo);
        studentResponse.setPageSize(pageSize);
        studentResponse.setTotalElements(listOfStudents.getTotalElements());
        studentResponse.setTotalPages(listOfStudents.getTotalPages());
        studentResponse.setLast(listOfStudents.isLast());

        return studentResponse;
    }


    @Override
    @Transactional
    public StudentDto updateStudent(Long studentId, StudentDto studentDto) {
        Student updateStudent = isStudentExist(studentId);
        setStudent(studentDto, updateStudent);
        Student student = studentRepository.save(updateStudent);
        userRepository.save(student.getUser());
        return Mapper.mapToStudentDto(student);
    }

    @Override
    public void changePassword(Long studentId, PasswordChangeRequest passwordChangeRequest) {
        Student changePasswordStudent = isStudentExist(studentId);
        changePasswordStudent.getUser().setPassword(passwordEncoder.encode(passwordChangeRequest.getPassword()));
        userRepository.save(changePasswordStudent.getUser());
        studentRepository.save(changePasswordStudent);
    }

    @Override
    public List<StudentGradeDto> showGrades(Long studentId) {
        Student student = isStudentExist(studentId);

        return gradeRepository.findAllByStudent(student)
                .stream().map(Mapper::mapToStudentGradeDto).toList();
    }

    @Override
    public boolean isPermission(String authHeader, Long studentId) {
        if(StringUtils.hasText(authHeader) && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            User user = jwtTokenProvider.getUser(token);
            if(user.getRole().equals("ROLE_ADMIN")) return true;

            if (!user.getRole().equals("ROLE_STUDENT")) return false;

            Long studentIdFromToken = studentRepository.findByUserEmail(jwtTokenProvider.getUsername(token)).getId();

            return studentIdFromToken.equals(studentId);
        }
        throw new APIException(HttpStatus.BAD_REQUEST, "Something was wrong.");
    }

    @Override
    public void deleteStudent(Long studentId) {
        Student student = isStudentExist(studentId);
        userRepository.delete(student.getUser());
        studentRepository.deleteById(studentId);

    }

    private Student isStudentExist(Long studentId) {
        return studentRepository.findById(studentId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Student", "id", studentId));
    }

    private void setStudent(StudentDto studentDto, Student student) {
        student.setFullName(studentDto.getFullName());
        student.setAddress(studentDto.getAddress());
        student.setGender(studentDto.getGender());
        student.setDate(Mapper.convertToDate(studentDto.getDate()));
        student.setPhoneNumber(studentDto.getPhoneNumber());
        student.getUser().setEmail(studentDto.getEmail());
    }
}