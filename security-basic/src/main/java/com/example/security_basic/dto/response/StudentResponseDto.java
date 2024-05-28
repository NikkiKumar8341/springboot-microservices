package com.example.security_basic.dto.response;

import lombok.Data;

import java.util.List;



@Data
public class StudentResponseDto {

    private String studentId;

    private String studentName;

    private String studentEmail;

    private List<String> courses;


}
