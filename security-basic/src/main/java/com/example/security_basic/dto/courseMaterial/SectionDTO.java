package com.example.security_basic.dto.courseMaterial;


import lombok.*;

import java.util.List;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SectionDTO {

    private String sectionId;

    private String name;
    private List<VideoDTO> videos;


}
