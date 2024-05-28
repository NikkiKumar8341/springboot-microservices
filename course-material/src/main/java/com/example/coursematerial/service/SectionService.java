package com.example.coursematerial.service;


import com.example.coursematerial.dto.ApiResponseSectionDto;
import com.example.coursematerial.dto.SectionDTO;
import com.example.coursematerial.entity.Section;
import com.example.coursematerial.repo.SectionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class SectionService {


    @Autowired
    private SectionRepo sectionRepo;


    public Section createOrUpdateSection(Section section) {

        Section section1= Section.builder()
                .sectionId(section.getSectionId())
                .courseMaterial(section.getCourseMaterial())
                .build();
        // Implement logic to create or update section

        return section1;
    }

    public ApiResponseSectionDto findBySection(String courseId){

      List<Section> sections=sectionRepo.findByCourseId(courseId);

     ApiResponseSectionDto sectionDTO=new ApiResponseSectionDto();

      sectionDTO.setSectionId(courseId);

        List<String> names = sections.stream()
                .map(Section::getName)
                .collect(Collectors.toList());

        sectionDTO.setName(names);

        return sectionDTO;
    }

    public String findSectionName(String courseId,String name){


       Section section=sectionRepo.findBySectionName(name,courseId);

       String sectionID=section.getSectionId();
       String sectionName=section.getName();

       return "sectionid is : "+sectionID+ "And section Name: "+sectionName;

    }



//    public Optional<Section> findSectionByName(String name) {
//        return sectionRepo.findByName(name);
//    }
}
