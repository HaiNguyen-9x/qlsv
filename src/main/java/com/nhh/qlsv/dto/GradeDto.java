package com.nhh.qlsv.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GradeDto {
    private double score;
    private char scoreLetter;
    private StudentDto studentDto;
    private Long classId;
}
