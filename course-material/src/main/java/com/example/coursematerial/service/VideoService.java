package com.example.coursematerial.service;


import com.example.coursematerial.entity.Section;
import com.example.coursematerial.entity.Video;
import com.example.coursematerial.repo.SectionRepo;
import com.example.coursematerial.repo.VideoRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class VideoService {


    @Autowired
    private VideoRepo videoRepo;

    @Autowired
    private SectionRepo sectionRepo;


    public String getBySectionNameAndCourseId(String name, String courseId) {
        // Find section by name and courseId
        Section section = sectionRepo.findBySectionName(name, courseId);

        if (section == null) {
            log.error("No section found for name: {} and courseId: {}", name, courseId);
            return "No section found";
        }

        // Log section id
        String sectionId = section.getSectionId();

        if (sectionId != null) {
            log.info("Section ID: {}", sectionId);
        } else {
            log.error("Section ID is null for section: {}", section);
            return "Section ID is null";
        }

        // Fetch video list by sectionId
        List<Video> videoList = videoRepo.findBySectionSectionId(sectionId);

        if (videoList == null || videoList.isEmpty()) {
            log.info("No videos found for section ID: {}", sectionId);
            return "No videos found";
        }

        // Log each video id
        for (Video video : videoList) {
            String videoId = video.getVideoId();
            if (videoId != null) {
                log.info("Video ID: {}", videoId.toString());
            } else {
                log.warn("Video ID is null for video: {}", video);
            }
        }

        // Return video list as a string
        return "Video List: " + videoList;
    }




//    public ApiResonseVideoDto getBySectionNameAndCourseId(String name, UUID courseId){
//
//        Section section=sectionRepo.findBySectionName(name, courseId);
//
//        SectionDTO sectionDTO=new SectionDTO();
//        sectionDTO.setSectionId(section.getSectionId());
//        sectionDTO.setName(section.getName());
//
//        List<Video> videos=videoRepo.getVideoList(section.getSectionId());
//
//        List<VideoDTO> videoDTOs = videos.stream()
//                .map(video -> new VideoDTO(video.getVideoId(), video.getTitle(), video.getUrl()))
//                .collect(Collectors.toList());
//
//        ApiResonseVideoDto response = new ApiResonseVideoDto();
//        response.setSectionId(sectionDTO.getSectionId());
//        response.setSectionName(sectionDTO.getName());
//        response.setVideos(videoDTOs);
//
//        return response;
//
//    }


}
