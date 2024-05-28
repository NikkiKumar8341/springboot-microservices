package com.example.coursematerial.controller;


import com.example.coursematerial.dto.ApiResonseVideoDto;
import com.example.coursematerial.entity.Section;
import com.example.coursematerial.entity.Video;
import com.example.coursematerial.repo.VideoRepo;
import com.example.coursematerial.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@RestController
@RequestMapping("/videos")
public class VideoController {


//    @Value("${application.bucket.name}")
//    private String bucketName;

   @Autowired
   private VideoService videoService;


//   @GetMapping("/{name}/{courseId}")
//   public ResponseEntity<?> getVideoBySectionIdAndCourseId(String name, UUID courseId){
//
//       ApiResonseVideoDto apiResonseVideoDto=videoService.getBySectionNameAndCourseId(name,courseId);
//
//       return ResponseEntity.ok(apiResonseVideoDto);
//   }

    @GetMapping("/hi")
    public String hi(){
    return "hello Video" ;
    }


    @GetMapping("/{name}/{courseId}")
    public ResponseEntity<String> getBySectionNameAndCourseId(@PathVariable String name, @PathVariable String courseId){

        String video= videoService.getBySectionNameAndCourseId(name,courseId);

        return ResponseEntity.ok(video);
    }



}
