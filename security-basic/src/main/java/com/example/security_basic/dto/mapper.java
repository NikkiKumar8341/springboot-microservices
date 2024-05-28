package com.example.security_basic.dto;

import com.example.security_basic.dto.request.StudentInfoForTeacher;
import com.example.security_basic.dto.response.CourseInfo;
import com.example.security_basic.dto.response.CourseResponseDto;
import com.example.security_basic.dto.response.StudentResponseDto;
import com.example.security_basic.dto.response.TeacherResponseDto;
import com.example.security_basic.entity.Course;
import com.example.security_basic.entity.Student;
import com.example.security_basic.entity.Teacher;
import com.example.security_basic.entity.UserRegister;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Component
public class mapper {

    public static StudentResponseDto studentToStudentResponseDto(Student student){

        StudentResponseDto studentResponseDto=new StudentResponseDto();

        studentResponseDto.setStudentId(student.getStudentId());

        studentResponseDto.setStudentName(student.getUser().getFirstName());

        studentResponseDto.setStudentEmail(student.getUser().getEmailId());



        List<String> names=new ArrayList<>();


        List<Course> courses=new ArrayList<>();

        for (Course course:student.getCourses()){
            names.add(course.getTitle());
        }

        studentResponseDto.setCourses(names);





        return studentResponseDto;

    }


    public static List<StudentResponseDto> studentToStudentResponseDtos(List<Student> students){
        List<StudentResponseDto> studentResponseDtos=new ArrayList<>();

        for (Student student:students){
            studentResponseDtos.add(studentToStudentResponseDto(student));
        }

        return studentResponseDtos;
    }

    public static TeacherResponseDto teacherToTeacherResponseDto(Teacher teacher){

        TeacherResponseDto teacherResponseDto=new TeacherResponseDto();


        teacherResponseDto.setTeacherId(teacher.getTeacherId());
        teacherResponseDto.setFirstName(teacher.getUser().getFirstName());
        teacherResponseDto.setLastName(teacher.getUser().getLastName());
        teacherResponseDto.setEmail(teacher.getUser().getEmailId());


        List<CourseInfo> courseInfos = new ArrayList<>();
        for (Course course : teacher.getCourses()) {
            CourseInfo courseInfo = new CourseInfo();
            courseInfo.setCourseId(course.getCourseId());
            courseInfo.setCourseTitle(course.getTitle());
            courseInfos.add(courseInfo);
        }
        teacherResponseDto.setCourseInfos(courseInfos);


        List<StudentInfoForTeacher> students = new ArrayList<>();

        for (Course course : teacher.getCourses()) {
            for (Student student : course.getStudents()) {
                StudentInfoForTeacher studentInfoDto = new  StudentInfoForTeacher();
                studentInfoDto.setStudentId(student.getStudentId());
                studentInfoDto.setFullName(student.getUser().getFirstName() + " " + student.getUser().getLastName());
                students.add(studentInfoDto);
            }
        }
        teacherResponseDto.setStudents(students);


        return teacherResponseDto;
    }


    public static List<TeacherResponseDto> teacherToTeacherResponseDtos(List<Teacher> teachers){
        List<TeacherResponseDto> teacherResponseDtos=new ArrayList<>();

        for (Teacher teacher:teachers){
            teacherResponseDtos.add(teacherToTeacherResponseDto(teacher));
        }

        return teacherResponseDtos;
    }


    public static CourseResponseDto courseToCourseResponseDto(Course course){

        CourseResponseDto courseResponseDto=new CourseResponseDto();

        courseResponseDto.setCourseId(course.getCourseId());
        courseResponseDto.setTitle(course.getTitle());

        courseResponseDto.setTeacherName(course.getTeacher().getUser().getFirstName());


        return courseResponseDto;
    }


    public static List<CourseResponseDto> courseToCourseResponseDtos(List<Course> courses){

        List<CourseResponseDto> courseResponseDtos=new ArrayList<>();

        for (Course course:courses){
            courseResponseDtos.add(courseToCourseResponseDto(course));
        }

        return courseResponseDtos;
    }

}
