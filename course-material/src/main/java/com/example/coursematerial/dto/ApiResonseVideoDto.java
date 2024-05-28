package com.example.coursematerial.dto;


import com.example.coursematerial.entity.Video;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ApiResonseVideoDto{

    private UUID sectionId;

    private String sectionName;

    private List<VideoDTO> videos;
}
