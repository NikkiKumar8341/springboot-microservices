package com.example.security_basic.controller;


import com.example.security_basic.dto.response.CourseResponseDto;
import com.example.security_basic.dto.response.StudentResponseDto;
import com.example.security_basic.dto.response.TeacherResponseDto;
import com.example.security_basic.entity.Course;
import com.example.security_basic.service.CourseService;
import com.example.security_basic.service.TeacherCourseRegisterService;
import com.example.security_basic.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/teacher")
public class TeacherCourseController {

    @Autowired
    private TeacherCourseRegisterService teacherCourseRegisterService;

    @Autowired
    private TeacherService teacherService;

    @Autowired
    @Lazy
    private CourseService courseService;



    @PreAuthorize("hasAuthority('TEACHER')")
    @PostMapping("/createCourse")
    public ResponseEntity<String> createCourse(@RequestParam String teacherId, @RequestParam String titleOfCourse,@RequestParam String categoryName) {
        try {
            String message = teacherCourseRegisterService.createCourseByTeacher(teacherId, titleOfCourse,categoryName);
            return ResponseEntity.ok(message);
        } catch (Exception e) {
            throw new IllegalStateException("internal server Error", e);
        }
    }

    @PreAuthorize("hasAuthority('TEACHER')")
    @PutMapping("/updateCourse")
    public ResponseEntity<String> updateCourseByTeacher(@RequestParam String teacherId
            ,@RequestParam String courseId,@RequestParam String newTitleOfCourse,@RequestParam String newCategoryName){

        try {
            String message=teacherCourseRegisterService.updateCourseByTeacher(teacherId,courseId,newTitleOfCourse,newCategoryName);
            return ResponseEntity.ok(message);
        }catch (Exception e){
            throw new IllegalStateException("internal server Error",e);
        }

    }



//    @GetMapping("/listStudentRegistered")
////    @PreAuthorize("hasAuthority('TEACHER')")
//    public ResponseEntity<List<StudentDto>> getStudentRegisterForCourse(@RequestParam String teacherId,@RequestParam UUID courseId){
//        try {
//           List<StudentDto> studentDtoList= teacherService.getStudentsRegisteredForCourse(teacherId,courseId);
//
//            return ResponseEntity.ok(studentDtoList);
//        }catch (RuntimeException e){
//            return ResponseEntity.ok().body(null);
//        }
//    }

    @PreAuthorize("hasAuthority('TEACHER')")
    @GetMapping("/teacherById/{teacherId}")
    public ResponseEntity<TeacherResponseDto> getTeacherById(@PathVariable String teacherId){

        TeacherResponseDto teacherResponseDto=teacherService.getTeacherById(teacherId);
        return new ResponseEntity<>(teacherResponseDto, HttpStatus.OK);

    }

    @PreAuthorize("hasAuthority('TEACHER')")
    @GetMapping("/giveAllCourses")
    public ResponseEntity<List<CourseResponseDto>> getAllCourses(){

       List<CourseResponseDto> courseResponseDtoList=courseService.getAllCourse();

       return new ResponseEntity<>(courseResponseDtoList,HttpStatus.OK);
    }





    @GetMapping("/all")
    @PreAuthorize("hasAuthority('TEACHER')")
    public String hi(){
        return "hi";
    }
}
