package com.example.security_basic.dto.request;

import com.example.security_basic.entity.Course;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class TeacherRequestDto {


    private List<UUID> courseIds;
}


