package com.example.coursematerial.dto;

import com.example.coursematerial.entity.Section;
import com.example.coursematerial.entity.Video;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseMaterialResponseDto {
    private String courseId;
    private List<Section> sections;
    private List<Video> videos;
}
