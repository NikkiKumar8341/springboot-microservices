package com.example.security_basic.service;



import com.example.security_basic.dto.mapper;
import com.example.security_basic.dto.response.StudentResponseDto;
import com.example.security_basic.dto.response.TeacherResponseDto;
import com.example.security_basic.entity.Student;
import com.example.security_basic.entity.Teacher;
import com.example.security_basic.repo.CourseRepo;
import com.example.security_basic.repo.TeacherRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class TeacherService {


    @Autowired
    private TeacherRepo teacherRepo;

    @Autowired
    private CourseRepo courseRepository;



    public TeacherResponseDto getTeacherById(String teacherId){

        Teacher teacher=getTeacher(teacherId);

        return mapper.teacherToTeacherResponseDto(teacher);
    }


    public Teacher getTeacher(String teacherId){

        Teacher teacher=teacherRepo.findById(teacherId).orElseThrow(()->new IllegalArgumentException("cannot find teacher by id: "+teacherId));

        return teacher;
    }


    public List<TeacherResponseDto> getAllTeacher(){


        List<Teacher> teachers=StreamSupport
                .stream(teacherRepo.findAll().spliterator(),false)
                .collect(Collectors.toList());


        return mapper.teacherToTeacherResponseDtos(teachers);
    }





}
