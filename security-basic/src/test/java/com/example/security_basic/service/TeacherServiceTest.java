package com.example.security_basic.service;

import com.example.security_basic.entity.Teacher;
import com.example.security_basic.repo.CourseRepo;
import com.example.security_basic.repo.TeacherRepo;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;


@SpringBootTest
class TeacherServiceTest {
    @Autowired
    private TeacherRepo teacherRepo;



    @Autowired
    private CourseRepo courseRepository;




    @Test
    public void  getAllStudent(){

        String teacherId="teach1222";

        UUID courseId= UUID.fromString("2875f31d-8a46-45df-8b23-389456dc41b5");

        Teacher teacher = teacherRepo.findById(teacherId)
                .orElseThrow(() -> new EntityNotFoundException("Teacher not found with id: " + teacherId));



//        Course course = courseRepository.findById(courseId)
//                .orElseThrow(() -> new EntityNotFoundException("Course not found with id: " + courseId));


//         List<StudentDto> studentDtoList =course.getStudents().stream()
//                .map(studentMapper::convertToDto)
//                .collect(Collectors.toList());

//        System.out.println(studentDtoList);


    }

}