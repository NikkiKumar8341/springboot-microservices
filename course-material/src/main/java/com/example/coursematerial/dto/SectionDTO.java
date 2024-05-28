package com.example.coursematerial.dto;

import lombok.*;

import java.util.List;
import java.util.UUID;


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
