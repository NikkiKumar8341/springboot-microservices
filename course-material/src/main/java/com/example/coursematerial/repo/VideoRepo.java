package com.example.coursematerial.repo;

import com.example.coursematerial.entity.Section;
import com.example.coursematerial.entity.Video;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;


@Repository
public interface VideoRepo extends JpaRepository<Video, String> {

//    List<Video> findByCourseId(UUID courseId);

    List<Video> findBySectionSectionId(String sectionId);

    List<Video> findBySection(Section section);
}
