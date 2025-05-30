package com.nhh.qlsv.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class StudentDto {
    private Long id;
    private String fullName;
    private String gender;
    private String date;
    private String address;
    private String email;
    private String phoneNumber;
}
