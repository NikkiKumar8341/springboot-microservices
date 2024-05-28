package com.example.coursematerial.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.util.UUID;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Video {


    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
//    @GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
    private String videoId;

    private String title;

    private String courseName;

    private String url;

    @ManyToOne
    @JoinColumn(name = "section_id")
    private Section section;
}
