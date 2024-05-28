package com.example.coursematerial.controller;


import com.example.coursematerial.dto.ApiResponseSectionDto;
import com.example.coursematerial.entity.Section;
import com.example.coursematerial.service.SectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/section")
public class SectionController {


    @Autowired
    private SectionService sectionService;


    @GetMapping("/{courseId}")
    public ResponseEntity<?> getBySectionNameAndCourseId(@PathVariable String courseId){

       ApiResponseSectionDto sectionList= sectionService.findBySection(courseId);

        return ResponseEntity.ok(sectionList);
    }

    @GetMapping("/{courseId}/{name}")
    public ResponseEntity<?> findSectionName(@PathVariable String courseId,@PathVariable String name ){
       String sectionName= sectionService.findSectionName(courseId,name);

       return ResponseEntity.ok(sectionName);
    }
}
