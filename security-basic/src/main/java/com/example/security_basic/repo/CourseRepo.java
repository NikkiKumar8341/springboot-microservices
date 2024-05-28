package com.example.security_basic.repo;

import com.example.security_basic.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.UUID;

@Repository
public interface CourseRepo extends JpaRepository<Course, String> {
}
