package com.example.security_basic.dto.courseMaterial;

import lombok.*;

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
