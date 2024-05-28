package com.example.security_basic.service;


import com.example.security_basic.entity.Course;
import com.example.security_basic.entity.Teacher;
import com.example.security_basic.exceptions.custom.CourseNotFoundException;
import com.example.security_basic.exceptions.custom.TeacherNotFoundException;
import com.example.security_basic.exceptions.custom.UnauthorizedAccessException;
import com.example.security_basic.repo.CourseRepo;
import com.example.security_basic.repo.TeacherRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TeacherCourseRegisterService {

    @Autowired
    private TeacherRepo teacherRepo;

    @Autowired
    private CourseRepo courseRepo;


    public String createCourseByTeacher(String teacherId,String titleOfCourse,String categoryName){

        Optional<Teacher> teacherOptional=teacherRepo.findById(teacherId);

        if(teacherOptional.isPresent()){
            Teacher teacher=teacherOptional.get();

            Course course= Course.builder()
                    .title(titleOfCourse)
                    .category(categoryName)
                    .teacher(teacher)
                    .build();

            courseRepo.save(course);
            return "course has been added succesfully";
        }
        else {
            // Handle the case where the teacher does not exist
            throw new RuntimeException("Teacher with ID " + teacherId + " not found.");
        }
    }


    public String updateCourseByTeacher(String teacherId, String courseId, String newTitleOfCourse, String newCategoryName) {
        // Validate input parameters
        if (teacherId == null || teacherId.isEmpty()) {
            throw new IllegalArgumentException("Teacher ID must not be null or empty.");
        }
        if (courseId == null || courseId.isEmpty()) {
            throw new IllegalArgumentException("Course ID must not be null or empty.");
        }
        if (newTitleOfCourse == null || newTitleOfCourse.isEmpty()) {
            throw new IllegalArgumentException("New title of the course must not be null or empty.");
        }
        if (newCategoryName == null || newCategoryName.isEmpty()) {
            throw new IllegalArgumentException("New category name must not be null or empty.");
        }

        // Fetch the teacher by ID from the repository
        Optional<Teacher> teacherOptional = teacherRepo.findById(teacherId);
        if (!teacherOptional.isPresent()) {
            throw new TeacherNotFoundException("Teacher with ID " + teacherId + " not found.");
        }

        // Fetch the course by ID from the repository
        Optional<Course> courseOptional = courseRepo.findById(courseId);
        if (!courseOptional.isPresent()) {
            throw new CourseNotFoundException("Course with ID " + courseId + " not found.");
        }

        Course course = courseOptional.get();

        // Check if the course belongs to the teacher
        if (!course.getTeacher().getTeacherId().equals(teacherId)) {
            throw new UnauthorizedAccessException("Teacher with ID " + teacherId + " is not authorized to update this course.");
        }

        // Update the course details
        course.setTitle(newTitleOfCourse);
        course.setCategory(newCategoryName);

        // Save the updated course to the repository
        courseRepo.save(course);

        return "Course has been updated successfully";
    }

}
