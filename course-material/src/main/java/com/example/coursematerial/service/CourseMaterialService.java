package com.example.coursematerial.service;


import com.example.coursematerial.client.CourseFeignClient;
import com.example.coursematerial.dto.*;
import com.example.coursematerial.entity.CourseMaterial;
import com.example.coursematerial.entity.Section;
import com.example.coursematerial.entity.Video;
import com.example.coursematerial.repo.CourseMaterialRepo;
import com.example.coursematerial.repo.SectionRepo;
import com.example.coursematerial.repo.VideoRepo;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CourseMaterialService {

    @Autowired
    private CourseMaterialRepo courseMaterialRepo;

    @Autowired
    private  CourseFeignClient courseFeignClient;

    @Autowired
    private SectionRepo sectionRepo;

    @Autowired
    private VideoRepo videoRepo;


    @Transactional
    public ApiResponseMaterialDto createCourseMaterial(String courseId, String title, String content, List<SectionDTO> sections) {
        CourseDTO courseDTO = getCourseById(courseId);


        log.info("courseId : ",courseDTO.getCourseId());

        // Create CourseMaterial
        CourseMaterial courseMaterial = new CourseMaterial();
        courseMaterial.setContent(content);
        courseMaterial.setCourseId(courseDTO.getCourseId());

        // Save CourseMaterial without sections first
        CourseMaterial savedCourseMaterial = courseMaterialRepo.save(courseMaterial);

        List<SectionDTO> sectionDTOList = new ArrayList<>();
        List<VideoDTO> videoDTOList = new ArrayList<>();

        for (SectionDTO sectionDTO : sections) {
            String sectionName = sectionDTO.getName();

            // Check if the section exists for the given CourseMaterial
            Section section = sectionRepo.findByNameAndCourseId(sectionName, courseId)
                    .orElseGet(() -> {
                        Section newSection = new Section();
                        newSection.setName(sectionName);
                        newSection.setCourseId(courseDTO.getCourseId());
                        newSection.setCourseMaterial(savedCourseMaterial);
                        return sectionRepo.save(newSection);
                    });

            // Fetch the saved section from the repository to ensure it has an ID
            Section savedSection = sectionRepo.findById(section.getSectionId()).orElse(section);

            log.info("saved section object {}: ",savedSection);

            List<VideoDTO> videos = new ArrayList<>();
            // Save new videos to the section
            for (VideoDTO videoDTO : sectionDTO.getVideos()) {
                Video video = new Video();
                video.setTitle(videoDTO.getTitle());
                video.setUrl(videoDTO.getUrl());
                video.setCourseName(courseDTO.getTitle());
                video.setSection(savedSection);
                video.setCourseName(courseDTO.getTitle());
                Video savedVideo = videoRepo.save(video);

                VideoDTO newVideoDTO = new VideoDTO();
                newVideoDTO.setVideoId(savedVideo.getVideoId());
                newVideoDTO.setTitle(savedVideo.getTitle());
                newVideoDTO.setUrl(savedVideo.getUrl());
                videos.add(newVideoDTO);
            }
            videoDTOList.addAll(videos);

            SectionDTO newSectionDTO = new SectionDTO();
            newSectionDTO.setSectionId(savedSection.getSectionId());
            newSectionDTO.setName(savedSection.getName());
            newSectionDTO.setVideos(videos);
            sectionDTOList.add(newSectionDTO);
        }

        CourseMaterialDTO courseMaterialDTO = new CourseMaterialDTO();
        courseMaterialDTO.setMaterialId(savedCourseMaterial.getMaterialId());
        courseMaterialDTO.setContent(savedCourseMaterial.getContent());
        courseMaterialDTO.setCourseId(savedCourseMaterial.getCourseId());
        courseMaterialDTO.setCourseName(courseDTO.getTitle());

        ApiResponseMaterialDto response = new ApiResponseMaterialDto();
        response.setCourseMaterial(courseMaterialDTO);
        response.setSections(sectionDTOList);
        response.setVideos(videoDTOList);

        return response;
    }

    public CourseDTO getCourseById(String courseId) {
        CourseDTO response = courseFeignClient.getCourseById(courseId);

        return response;
    }

    public ApiResponseMaterialDto getCourseMaterialByCourseId(String courseId){

        CourseDTO courseDTO = courseFeignClient.getCourseById(courseId);

        // Fetch all course materials for the given courseId
        List<CourseMaterial> courseMaterials = courseMaterialRepo.findByCourseId(courseId);

        if(courseMaterials.isEmpty()) {
            throw new IllegalArgumentException("CourseMaterial not found for courseId: " + courseId);
        }

        // Since there may be multiple course materials, we'll choose the first one for simplicity
        CourseMaterial courseMaterial = courseMaterials.get(0);

        List<Section> sections = sectionRepo.findByCourseId(courseId);
        List<SectionDTO> sectionDTOList = new ArrayList<>();
        List<VideoDTO> videoDTOList = new ArrayList<>();

        for (Section section : sections) {
            List<Video> videos = videoRepo.findBySection(section);
            List<VideoDTO> videoDTOs = videos.stream()
                    .map(video -> VideoDTO.builder()
                            .videoId(video.getVideoId())
                            .title(video.getTitle())
                            .url(video.getUrl())
                            .build())
                    .collect(Collectors.toList());

            SectionDTO sectionDTO = SectionDTO.builder()
                    .sectionId(section.getSectionId())
                    .name(section.getName())
                    .videos(videoDTOs)
                    .build();

            sectionDTOList.add(sectionDTO);
            videoDTOList.addAll(videoDTOs);
        }

        CourseMaterialDTO courseMaterialDTO = CourseMaterialDTO.builder()
                .materialId(courseMaterial.getMaterialId())
                .courseId(courseMaterial.getCourseId())
                .content(courseMaterial.getContent())// Assuming you have this field
                .courseName(courseDTO.getTitle())
                .build();

        return ApiResponseMaterialDto.builder()
                .courseMaterial(courseMaterialDTO)
                .sections(sectionDTOList)
                .videos(videoDTOList)
                .build();
    }


    public List<CourseMaterial> getAll(){
        List<CourseMaterial> courseMaterial=courseMaterialRepo.findAll();

        return courseMaterial;
    }
}
