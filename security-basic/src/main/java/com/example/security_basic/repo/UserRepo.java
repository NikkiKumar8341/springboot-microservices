package com.example.security_basic.repo;

import com.example.security_basic.entity.Student;
import com.example.security_basic.entity.UserRegister;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;


@Repository
public interface UserRepo extends JpaRepository<UserRegister, String> {
    // You can add custom query methods here if needed

    Optional<UserRegister> findByEmailId(String email);
}


