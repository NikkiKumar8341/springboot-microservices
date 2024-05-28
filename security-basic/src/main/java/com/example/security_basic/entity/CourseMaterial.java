package com.example.security_basic.entity;

import lombok.*;

import java.util.UUID;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class CourseMaterial {
    private String materialId;
    private String title;
    private String content;
}
