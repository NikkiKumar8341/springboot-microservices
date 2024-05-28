package com.example.security_basic.repo;

import com.example.security_basic.entity.Teacher;
import com.example.security_basic.entity.UserRegister;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TeacherRepo extends JpaRepository<Teacher,String> {



    @Query("SELECT t.teacherId FROM Teacher t WHERE t.user.userId = :userId")
    String findTeacherIdByUserId(@Param("userId") String userId);
}
