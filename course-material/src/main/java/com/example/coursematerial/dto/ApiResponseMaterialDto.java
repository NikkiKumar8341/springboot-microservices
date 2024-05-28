package com.example.coursematerial.dto;

import lombok.*;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ApiResponseMaterialDto {

    private CourseMaterialDTO courseMaterial;
    private List<SectionDTO> sections;
    private List<VideoDTO> videos;
}
