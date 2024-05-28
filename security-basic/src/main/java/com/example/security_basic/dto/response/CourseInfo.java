package com.example.security_basic.dto.response;


import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class CourseInfo {

    private String courseId;
    private String courseTitle;
}
