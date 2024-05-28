package com.example.coursematerial.repo;

import com.example.coursematerial.entity.CourseMaterial;
import com.example.coursematerial.entity.Section;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Repository
public interface SectionRepo extends JpaRepository<Section, String> {

//    @Query(value = "SELECT * FROM section s WHERE s.name = ?1", nativeQuery = true)
//    Optional<Section> findByName(String name);

    List<Section> findByCourseId(String courseId);



    @Query(value = "SELECT * FROM SECTION s WHERE LOWER(s.name) = LOWER(:name) AND s.course_Id = :courseId  ", nativeQuery = true)
    Section findBySectionName(@Param("name") String name,@Param("courseId") String courseId);




//    @Query(value = "SELECT * FROM SECTION s WHERE s.name = :sectionName AND s.courseId = :courseId", nativeQuery = true)
//    List<Section> findBySectionNameAndCourseId(@Param("sectionName") String sectionName, @Param("courseId") UUID courseId);


    Optional<Section> findByNameAndCourseId(String name,String courseID);

}
