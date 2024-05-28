package com.example.coursematerial.dto;

import lombok.*;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
public class CourseDTO {

    private String courseId;
    private String title;
}
