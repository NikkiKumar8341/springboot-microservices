package com.example.security_basic.service;


import com.example.security_basic.client.CourseMaterialClient;
import com.example.security_basic.dto.courseMaterial.ApiResponseMaterialDto;
import com.example.security_basic.dto.courseMaterial.CourseMaterialRequest;
import com.example.security_basic.dto.mapper;
import com.example.security_basic.dto.response.CourseResponseDto;
import com.example.security_basic.dto.response.StudentResponseDto;
import com.example.security_basic.entity.Course;
import com.example.security_basic.entity.CourseMaterial;
import com.example.security_basic.entity.Student;
import com.example.security_basic.repo.CourseRepo;
import com.example.security_basic.repo.StudentRepo;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class CourseService {


    @Autowired
    private CourseRepo courseRepo;

    @Autowired
    private StudentService studentService;

    @Autowired
    private StudentRepo studentRepo;

    @Autowired
    private CourseMaterialClient courseMaterialClient;



    public CourseResponseDto getCourseById(String courseId){

        Course course=getCourse(courseId);

       return mapper.courseToCourseResponseDto(course);

    }

    public Course getCourse(String courseId) {
       Course findBYId=courseRepo.findById(courseId).orElseThrow(
               ()->new UsernameNotFoundException("Course not found with this ID :"+courseId)
       );

       return findBYId;
    }

    public List<CourseResponseDto>  getAllCourse(){

        List<Course> courses= StreamSupport.
                stream(courseRepo.findAll().spliterator(),false)
                .collect(Collectors.toList());


        return mapper.courseToCourseResponseDtos(courses);
    }


    public List<Course> listOfCourse(){
        List<Course> courses=courseRepo.findAll();

        return courses;
    }

    public void registerStudentForCourse(String courseId, String studentId) {
        Course course = getCourse(courseId);

        Optional<Student> student = studentRepo.findById(studentId);
        if (student.isPresent()) {
            course.registerStudent(student.get());
            courseRepo.save(course);
        } else {
            throw new EntityNotFoundException("Student not found with id: " + studentId);
        }
    }

    public List<Student> getStudentsRegisteredForCourse(String courseId) {
        Course course = getCourse(courseId);
        return course.getStudents();
    }

    public List<ApiResponseMaterialDto> getCourseMaterials(String courseId) {
        return courseMaterialClient.getCourseMaterials(courseId);
    }

    public ApiResponseMaterialDto createCourseMaterial(CourseMaterialRequest material) {
       return courseMaterialClient.createCourseMaterial(material);
    }


}
