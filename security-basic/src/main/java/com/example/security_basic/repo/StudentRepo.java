package com.example.security_basic.repo;

import com.example.security_basic.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepo extends JpaRepository<Student,String> {




    @Query("SELECT s.studentId FROM Student s WHERE s.user.userId = :userId")
    String findStudentIdByUserId(@Param("userId") String userId);


    List<Student> findByDepartmentCode(String departmentCode);


//    Optional<Student> findByfirstName(String name);
//
//    Optional<Student> findByEmailId(String email);

//    Optional<Student> findByEmailIdAndPassword(String email,String password);
}
