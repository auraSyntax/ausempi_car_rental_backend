package com.aura.syntax.ausempi.demo.repository;

import com.aura.syntax.ausempi.demo.api.dto.UserDto;
import com.aura.syntax.ausempi.demo.entity.Users;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<Users, Long> {

    @Query("""
                SELECT NEW com.aura.syntax.ausempi.demo.api.dto.UserDto(
                    u.id,
                    CASE 
                        WHEN u.lastName IS NULL OR u.lastName = '' THEN u.firstName
                        ELSE CONCAT(u.firstName, ' ', u.lastName)
                    END,
                    u.phoneNumber,
                    u.email,
                    u.employeeId,
                    u.createdAt,
                    u.isExamCompleted,
                    u.isActive
                )
                FROM Users u
                WHERE (:search IS NULL OR 
                       LOWER(u.firstName) LIKE LOWER(CONCAT('%', :search, '%')) OR
                       LOWER(u.lastName) LIKE LOWER(CONCAT('%', :search, '%')) OR
                       LOWER(u.email) LIKE LOWER(CONCAT('%', :search, '%')) OR
                       LOWER(u.employeeId) LIKE LOWER(CONCAT('%', :search, '%'))
                      )
            """)
    Page<UserDto> getAllUsers(Pageable pageable, String search);

    Optional<Users> findByEmail(String username);
}
