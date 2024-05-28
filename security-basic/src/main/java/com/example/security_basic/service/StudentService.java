package com.example.security_basic.service;


import com.example.security_basic.client.ApiClient;
import com.example.security_basic.dto.request.DepartmentDto;
import com.example.security_basic.dto.request.StudentRequestDto;
import com.example.security_basic.dto.response.StudentResponseDto;
import com.example.security_basic.entity.Course;
import com.example.security_basic.entity.Student;
import com.example.security_basic.repo.CourseRepo;
import com.example.security_basic.repo.StudentRepo;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.example.security_basic.dto.mapper;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class StudentService {

    @Autowired
    private StudentRepo studentRepo;

//    @Autowired
//    private CourseService courseService;

    @Autowired
    private CourseRepo courseRepo;

    @Autowired
    private ApiClient apiClient;

    public StudentResponseDto getStudentById(String id) {


        Student student = getByStudent(id);

        return mapper.studentToStudentResponseDto(student);
    }


    public Student getByStudent(String id) {

        Student findbyId = studentRepo.findById(id).orElseThrow(
                () -> new IllegalArgumentException("cannot find student by id: " + id));

        return findbyId;
    }


    public List<StudentResponseDto> getAllStudent() {

        List<Student> students = StreamSupport.
                stream(studentRepo.findAll().spliterator(), false)
                .collect(Collectors.toList());


        return mapper.studentToStudentResponseDtos(students);
    }


    @Transactional
    public StudentResponseDto editStudentById(String userId, StudentRequestDto studentRequestDto) {
        // Retrieve the student entity by ID
        Student student = studentRepo.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("Student with ID " + userId + " not found"));


        // Check if student exists
        if (student == null) {
            // Handle the case where student is not found
            // For example, throw an exception or return a specific response
            // Here, we throw an exception for simplicity
            throw new UsernameNotFoundException("Student with ID " + userId + " not found");
        }



            // Assuming StudentResponseDto has setters for student details
        StudentResponseDto studentResponseDto = new StudentResponseDto();
        studentResponseDto.setStudentId(student.getStudentId());
        studentResponseDto.setStudentName(student.getUser().getFirstName()+" "+student.getUser().getLastName());
        studentResponseDto.setStudentEmail(studentRequestDto.getStudentEmail());

        // Set courses
        List<String> courseTitles = new ArrayList<>();
        for (Course course : student.getCourses()) {
            courseTitles.add(course.getTitle());
        }
        studentResponseDto.setCourses(courseTitles);

        // Set department code


        Student updatedStudent = studentRepo.save(student);

        // Set other student details as needed

        return mapper.studentToStudentResponseDto(updatedStudent);
        }

}
