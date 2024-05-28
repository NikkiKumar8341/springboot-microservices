package com.example.security_basic.controller;


import com.example.security_basic.dto.courseMaterial.ApiResponseMaterialDto;
import com.example.security_basic.dto.courseMaterial.CourseMaterialRequest;
import com.example.security_basic.dto.response.CourseResponseDto;
import com.example.security_basic.entity.CourseMaterial;
import com.example.security_basic.repo.CourseRepo;
import com.example.security_basic.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/coursePost")
public class CourseController {


    @Autowired
    private CourseService courseService;


    @PreAuthorize("hasAuthority('TEACHER')")
    @PostMapping("/materials")
    public ResponseEntity<ApiResponseMaterialDto> createCourseMaterial(@RequestBody CourseMaterialRequest material) {
        // Logic to save the course material associated with the courseId
        ApiResponseMaterialDto courseMaterial= courseService.createCourseMaterial(material);
        return ResponseEntity.status(HttpStatus.CREATED).body(courseMaterial);
    }




    @PreAuthorize("hasAnyAuthority('TEACHER','STUDENT')")
    @GetMapping("/{courseId}/materials")
    public ResponseEntity<List<ApiResponseMaterialDto>> getCourseMaterials(@PathVariable String courseId) {
        // Logic to retrieve course materials associated with the courseId
        List<ApiResponseMaterialDto> materials =courseService.getCourseMaterials(courseId) ;// retrieve materials from database
        return ResponseEntity.ok(materials);
    }


//    @PreAuthorize("hasAuthority('TEACHER')")
    @GetMapping("/getCourseByID")
    public ResponseEntity<CourseResponseDto> getCourseById(@RequestParam String courseId){
        CourseResponseDto courseResponseDto=courseService.getCourseById(courseId);

        return ResponseEntity.ok(courseResponseDto);
    }
}
