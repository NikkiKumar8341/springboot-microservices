package com.example.security_basic.dto.request;


import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class StudentRequestDto {


    private GuardianRequestDto guardian;

    private String studentId;

    private String studentName;

    private String studentEmail;

    private List<String> courses;

    private String departmentCode;



}
