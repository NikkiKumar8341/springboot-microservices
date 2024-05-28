package com.example.security_basic.dto.courseMaterial;


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
