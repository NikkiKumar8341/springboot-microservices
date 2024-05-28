package com.example.security_basic.dto.courseMaterial;

import java.util.List;

public class CourseMaterialRequest {
    private String courseId;
    private String title;
    private String content;
    private List<SectionDTO> sections;
}
