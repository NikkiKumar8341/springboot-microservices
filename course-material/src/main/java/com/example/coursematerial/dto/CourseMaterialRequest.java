package com.example.coursematerial.dto;

import lombok.*;

import java.util.List;
import java.util.UUID;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CourseMaterialRequest {

    private String courseId;
    private String title;
    private String content;
    private List<SectionDTO> sections;
}
