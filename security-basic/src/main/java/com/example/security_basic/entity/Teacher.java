package com.example.security_basic.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class Teacher {

    @Id
    private String teacherId;

    // Additional fields specific to teachers

    @OneToOne
    @JoinColumn(name = "userId", referencedColumnName = "userId")
    private UserRegister user;

    // Other fields as needed


    @OneToMany(mappedBy = "teacher")
    private List<Course> courses;


    @PrePersist
    public void generateTeacherId() {
        // Generate random 4-digit number
        int randomNum = (int) (Math.random() * 9000) + 1000; // Generates number between 1000 and 9999

        // Assign generated studentId with the prefix "stud" + random 4-digit number
        this.teacherId = "teach" + randomNum;
    }




}
