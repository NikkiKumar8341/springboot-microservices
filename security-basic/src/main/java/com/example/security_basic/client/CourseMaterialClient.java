package com.example.security_basic.client;

import com.example.security_basic.dto.courseMaterial.ApiResponseMaterialDto;
import com.example.security_basic.dto.courseMaterial.CourseMaterialRequest;
import com.example.security_basic.entity.CourseMaterial;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@FeignClient(name = "course-material")
public interface CourseMaterialClient {

    @GetMapping("/getCourse/material/{courseId}")
    @LoadBalanced
    List<ApiResponseMaterialDto> getCourseMaterials(@PathVariable("courseId") String courseId);

    @PostMapping("/api/courseMaterial/course/materials")
    @LoadBalanced
    ApiResponseMaterialDto createCourseMaterial(@RequestBody CourseMaterialRequest material);
}
