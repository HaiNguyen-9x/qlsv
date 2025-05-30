package com.nhh.qlsv.service.serviceImpl;

import com.nhh.qlsv.dto.Mapper;
import com.nhh.qlsv.dto.PasswordChangeRequest;
import com.nhh.qlsv.dto.TeacherDto;
import com.nhh.qlsv.entity.Teacher;
import com.nhh.qlsv.entity.User;
import com.nhh.qlsv.exception.APIException;
import com.nhh.qlsv.exception.ResourceNotFoundException;
import com.nhh.qlsv.repository.TeacherRepository;
import com.nhh.qlsv.repository.UserRepository;
import com.nhh.qlsv.security.JwtTokenProvider;
import com.nhh.qlsv.service.TeacherService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class TeacherServiceImpl implements TeacherService {
    
    TeacherRepository teacherRepository;
    UserRepository userRepository;
    BCryptPasswordEncoder passwordEncoder;
    JwtTokenProvider jwtTokenProvider;

    public TeacherServiceImpl(TeacherRepository teacherRepository,
                              UserRepository userRepository,
                              BCryptPasswordEncoder passwordEncoder,
                              JwtTokenProvider jwtTokenProvider) {
        this.teacherRepository = teacherRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    @Transactional
    public TeacherDto createTeacher(TeacherDto teacherDto) {
        Teacher teacher = new Teacher();
        User user = new User(
                null,
                teacherDto.getEmail(),
                passwordEncoder.encode("1"),
                "ROLE_TEACHER",
                null,
                teacher
        );

        teacher.setUser(user);

        setTeacher(teacherDto, teacher);

        teacherRepository.save(teacher);
        userRepository.save(user);

        return Mapper.mapToTeacherDto(teacher);
    }

    @Override
    public TeacherDto findTeacherById(Long teacherId) {
        Teacher teacher = isTeacherExist(teacherId);
        return Mapper.mapToTeacherDto(teacher);
    }

    @Override
    public List<TeacherDto> findAllTeachers() {
        return teacherRepository.findAll()
                .stream()
                .map(Mapper::mapToTeacherDto)
                .toList();
    }

    @Override
    @Transactional
    public TeacherDto updateTeacher(Long teacherId, TeacherDto teacherDto) {
        Teacher updateTeacher = isTeacherExist(teacherId);
        setTeacher(teacherDto, updateTeacher);
        Teacher teacher = teacherRepository.save(updateTeacher);
        userRepository.save(teacher.getUser());
        return Mapper.mapToTeacherDto(teacher);
    }

    @Override
    public void changePassword(Long teacherId, PasswordChangeRequest password) {
        Teacher teacher = isTeacherExist(teacherId);
        teacher.getUser().setPassword(passwordEncoder.encode(password.getPassword()));
        teacherRepository.save(teacher);
        userRepository.save(teacher.getUser());
    }

    @Override
    @Transactional
    public void deleteTeacher(Long teacherId) {
        Teacher teacher = isTeacherExist(teacherId);
        userRepository.delete(teacher.getUser());
        teacherRepository.deleteById(teacherId);
    }

    public boolean isPermission(String authHeader, Long teacherId) {
        if(StringUtils.hasText(authHeader) && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            User user = jwtTokenProvider.getUser(token);
            if(user.getRole().equals("ROLE_ADMIN")) return true;

            if (!user.getRole().equals("ROLE_TEACHER")) return false;

            Long teacherIdFromToken = teacherRepository.findByUserEmail(jwtTokenProvider.getUsername(token)).getId();

            return teacherIdFromToken.equals(teacherId);
        }
        throw new  APIException(HttpStatus.BAD_REQUEST, "Something was wrong.");
    }

    private Teacher isTeacherExist(Long teacherId) {
        return teacherRepository.findById(teacherId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Teacher", "id", teacherId));
    }

    private void setTeacher(TeacherDto teacherDto, Teacher teacher) {
        teacher.setFullName(teacherDto.getFullName());
        teacher.setPhoneNumber(teacherDto.getPhoneNumber());
        teacher.getUser().setEmail(teacherDto.getEmail());
    }
}
