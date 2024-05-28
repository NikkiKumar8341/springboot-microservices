package com.example.coursematerial.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Entity
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Section {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
   // @GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
    private String sectionId;


    private String name;


    private String courseId;
    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "course_material_id")
    private CourseMaterial courseMaterial;

    @OneToMany(mappedBy = "section", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Video> videos;
}
