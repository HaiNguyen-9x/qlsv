package com.nhh.qlsv.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TeacherDto {
    private Long id;
    private String fullName;
    private String phoneNumber;
    private String email;
    private List<Long> classesId;
}
