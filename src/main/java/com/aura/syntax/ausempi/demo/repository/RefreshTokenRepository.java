package com.aura.syntax.ausempi.demo.repository;

import com.aura.syntax.ausempi.demo.entity.RefreshToken;
import com.aura.syntax.ausempi.demo.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByToken(String token);

    void deleteByUser_Id(Long userId);

    Optional<RefreshToken> findByUser(Users user);
}
