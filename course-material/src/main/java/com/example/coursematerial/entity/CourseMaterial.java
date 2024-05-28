package com.example.coursematerial.entity;

import com.example.coursematerial.dto.CourseDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.util.List;


@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class CourseMaterial {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String materialId;

    private String courseId;

//    @Transient // Mark as transient as this won't be persisted
//    private CourseDTO courseDTO;

    private String content;


    @OneToMany(mappedBy = "courseMaterial", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Section> sections;

}
