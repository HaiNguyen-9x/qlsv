package com.nhh.qlsv.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StudentResponse {
    private List<StudentDto> students;
    private int pageNo;
    private int pageSize;
    private Long totalElements;
    private int totalPages;
    private boolean isLast;
}
