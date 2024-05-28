package com.example.coursematerial.dto;

import lombok.*;

import java.util.List;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CourseMaterialDTO {

    private String materialId;
    private String content;
    private String courseId;
    private String courseName;



}
