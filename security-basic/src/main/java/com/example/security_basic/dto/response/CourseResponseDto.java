package com.example.security_basic.dto.response;

import lombok.Data;

import java.util.UUID;


@Data
public class CourseResponseDto {

    private String courseId;

    private String title;

    private String teacherName;
}
