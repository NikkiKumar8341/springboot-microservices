package com.example.security_basic.controller;



import com.example.security_basic.dto.request.StudentRequestDto;
import com.example.security_basic.dto.response.StudentResponseDto;
import com.example.security_basic.entity.Course;
import com.example.security_basic.service.CourseService;
import com.example.security_basic.service.StudentService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/student")
public class StudentController {


    @Autowired
    private StudentService studentService;


    @Autowired
    private CourseService courseService;



//    @PreAuthorize("hasAnyAuthority('TEACHER','STUDENT')")
//    @GetMapping("/getAllCourse")
//    public ResponseEntity<List<CourseDto>> getAllCourses() {
//        List<CourseDto> courses =courseService.getAllCourses();
//        return ResponseEntity.ok(courses);
//    }

    @PreAuthorize("hasAnyAuthority('STUDENT')")
    @GetMapping("/getAllStudentById/{id}")
    public ResponseEntity<StudentResponseDto> getStudentById(@PathVariable String id){

        StudentResponseDto studentResponseDto=studentService.getStudentById(id);

       return new ResponseEntity<>(studentResponseDto, HttpStatus.OK);
    }


    @PreAuthorize("hasAnyAuthority('STUDENT')")
    @GetMapping("/getAllStudent")
    public ResponseEntity<List<StudentResponseDto>> getAllStudent(){
        List<StudentResponseDto> studentResponseDtoList=studentService.getAllStudent();

        return new ResponseEntity<>(studentResponseDtoList,HttpStatus.OK);
    }


    @PreAuthorize("hasAnyAuthority('STUDENT')")
    @PostMapping("/registerCourse")
    public ResponseEntity<String> registerForCourse(@RequestParam("studentId") String studentId,
                                                    @RequestParam("courseId") String courseId) {
        try {
            courseService.registerStudentForCourse(courseId,studentId);
            return ResponseEntity.ok("Student successfully registered for the course.");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred.");
        }
    }

    @PutMapping("/students/{userId}")
    public ResponseEntity<?> editStudentById(@PathVariable String userId,@RequestBody StudentRequestDto studentRequestDto){
        try {
           StudentResponseDto studentResponseDto= studentService.editStudentById(userId,studentRequestDto);
            return ResponseEntity.ok(studentResponseDto);
        }catch (EntityNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("An error occurred while editing the student.");
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred.");
        }

    }


    @PreAuthorize("hasAnyAuthority('STUDENT')")
    @GetMapping("/hi")
    public String hi(){
        return "hello";
    }






}
