package com.example.security_basic.dto.response;

import com.example.security_basic.dto.request.StudentInfoForTeacher;
import lombok.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Data
public class TeacherResponseDto {

    private String teacherId;

    private String firstName;

    private String lastName;

    private String email;


    private List<CourseInfo> courseInfos;

//    private List<String> courseName;

    List<StudentInfoForTeacher> students = new ArrayList<>();

//    private Map<String, String> students = new HashMap<>();



}
