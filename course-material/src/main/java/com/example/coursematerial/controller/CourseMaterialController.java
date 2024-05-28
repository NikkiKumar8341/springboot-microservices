package com.example.coursematerial.controller;


import com.example.coursematerial.dto.*;
import com.example.coursematerial.entity.CourseMaterial;
import com.example.coursematerial.entity.Section;
import com.example.coursematerial.entity.Video;
import com.example.coursematerial.service.CourseMaterialService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/courseMaterial")
public class CourseMaterialController {


    @Autowired
    private CourseMaterialService courseMaterialService;

//    @GetMapping("/{courseId}/materials")
//    public ResponseEntity<List<CourseMaterial>> getCourseMaterials(@PathVariable UUID courseId) {
//        List<CourseMaterial> materials = courseMaterialService.getCourseMaterials(courseId);
//        return ResponseEntity.ok(materials);
//    }



    @GetMapping("/getCourseId")
    public ResponseEntity<CourseDTO> getCourseById(@RequestParam String courseId) {
        CourseDTO courseDTO=courseMaterialService.getCourseById(courseId);

        return ResponseEntity.ok(courseDTO);
    }



    @GetMapping("/getAll/material")
    public ResponseEntity<?> getAllCourse(){
        List<CourseMaterial> courseMaterialList=courseMaterialService.getAll();
        return ResponseEntity.status(HttpStatus.OK).body(courseMaterialList);
    }


    @PostMapping("/course/materials")
    public ResponseEntity<ApiResponseMaterialDto> createCourseMaterial(@RequestBody CourseMaterialRequest request) {

        ApiResponseMaterialDto response = courseMaterialService.createCourseMaterial(
                request.getCourseId(),
                request.getTitle(),
                request.getContent(),
                request.getSections()
        );
        return ResponseEntity.ok(response);
    }

    @GetMapping("/getCourse/material/{courseId}")
    public ApiResponseMaterialDto getCourseMaterialByCourseId(@PathVariable String courseId) {
        return courseMaterialService.getCourseMaterialByCourseId(courseId);
    }




    private CourseMaterialDTO mapToDTO(CourseMaterial courseMaterial) {
        CourseMaterialDTO courseMaterialDTO = new CourseMaterialDTO();
//        courseMaterialDTO.setCourseId=(courseMaterial.getCourseDTO().getCourseId());

        courseMaterialDTO.setContent(courseMaterial.getContent());

        if (courseMaterial.getSections() != null) {
            List<SectionDTO> sectionDTOs = courseMaterial.getSections().stream().map(this::mapToDTO).collect(Collectors.toList());
//            courseMaterialDTO.setSections(sectionDTOs);
        }

        return courseMaterialDTO;
    }

    private SectionDTO mapToDTO(Section section) {
        SectionDTO sectionDTO = new SectionDTO();
        sectionDTO.setName(section.getName());

        if (section.getVideos() != null) {
            List<VideoDTO> videoDTOs = section.getVideos().stream().map(this::mapToDTO).collect(Collectors.toList());
            sectionDTO.setVideos(videoDTOs);
        }

        return sectionDTO;
    }

    private VideoDTO mapToDTO(Video video) {
        VideoDTO videoDTO = new VideoDTO();
        videoDTO.setTitle(video.getTitle());
        videoDTO.setUrl(video.getUrl());

        return videoDTO;
    }





}
