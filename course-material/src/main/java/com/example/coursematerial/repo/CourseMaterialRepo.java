package com.example.coursematerial.repo;

import com.example.coursematerial.entity.CourseMaterial;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CourseMaterialRepo extends JpaRepository<CourseMaterial, String> {


    List<CourseMaterial> findByCourseId(String courseId);

//    @EntityGraph(attributePaths = {"sections.videos"})
//    Optional<CourseMaterial> findByIdWithSectionsAndVideos(UUID id);

    @EntityGraph(attributePaths = {"sections"})
    Optional<CourseMaterial> findById(String id);
}
