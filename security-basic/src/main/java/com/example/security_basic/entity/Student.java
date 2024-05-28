package com.example.security_basic.entity;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Student {

    private static int lastStudentId = 999;

    @Id
    private String studentId;

    // Additional fields specific to students

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "userId", referencedColumnName = "userId")
    private UserRegister user;

    // Other fields as needed

    @Embedded
    private Guardian guardian;


    @ManyToMany(mappedBy = "students")
    private List<Course> courses;


    private String departmentCode;


    @PrePersist
    public void generateStudentId() {
        // Increment the last generated studentId
        lastStudentId++;

        // Reset to 1000 if it exceeds 9999 (4-digit limit)
        if (lastStudentId > 9999) {
            lastStudentId = 1000; // Start from 1000 again
        }

        // Assign the incremented studentId with the prefix "stud"
        this.studentId = "stud" + lastStudentId;
    }

}


