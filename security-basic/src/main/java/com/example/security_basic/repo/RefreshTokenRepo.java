package com.example.security_basic.repo;

import com.example.security_basic.entity.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RefreshTokenRepo extends JpaRepository<RefreshToken,Integer> {

    Optional<RefreshToken> findByToken(String token);
}
