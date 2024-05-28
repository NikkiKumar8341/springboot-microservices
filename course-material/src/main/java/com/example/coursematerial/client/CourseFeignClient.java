package com.example.coursematerial.client;

import com.example.coursematerial.dto.CourseDTO;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.UUID;


@FeignClient(name = "SECURITY-BASIC",url = "http://localhost:9095/api/coursePost")
public interface CourseFeignClient {


    @GetMapping("/getCourseByID")
    @LoadBalanced
    CourseDTO getCourseById(@RequestParam String courseId);
}
